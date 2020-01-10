package ru.fix.completable.reactor.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.fix.completable.reactor.example.services.*;
import ru.fix.completable.reactor.graph.JGraph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * # Defines purchase process when user buys good in the shop.
 */
@Configuration
public class PurchaseGraph extends JGraph<
        PurchaseSubmit,
        PurchaseSubmit.Request,
        PurchaseSubmit.Response,
        PurchaseGraph.Context> {

    class Context {
        ServiceInfo serviceInfo;
        UserProfile userInfo;
        Long bonusService;

        public ServiceInfo getServiceInfo() {
            return serviceInfo;
        }

        public Context setServiceInfo(ServiceInfo serviceInfo) {
            this.serviceInfo = serviceInfo;
            return this;
        }

        public UserProfile getUserInfo() {
            return userInfo;
        }

        public Context setUserInfo(UserProfile userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public Long getBonusService() {
            return bonusService;
        }

        public Context setBonusService(Long bonusService) {
            this.bonusService = bonusService;
            return this;
        }
    }


    @Autowired
    UserProfileManager userProfileManager;

    @Autowired
    TransactionLog transactionLog;

    @Autowired
    UserJournal userJournal;

    @Autowired
    Notifier notifier;

    @Autowired
    Bank bank;

    @Autowired
    ServiceRegistry serviceRegistry;

    @Autowired
    MarketingService marketingService;

    @Autowired
    CompletableReactor completableReactor;

    @PostConstruct
    public void initialize() {
        completableReactor.registerGraphIfAbsent(this);
    }

    Vertex loadUserProfile =
            handler(
                    /*
                     * # load user profile
                     * Loads user profile from database by user id
                     */
                    pld -> userProfileManager.loadUserProfileById(pld.request.getUserId())
            ).withRoutingMerger(
                    //# check profile state
                    (pld, result) -> {
                        if (pld.response.getStatus() != null) {
                            return Flow.STOP;
                        }

                        switch (result.status) {
                            case USER_NOT_FOUND:
                            case USER_IS_BLOCKED:
                                pld.response.setStatus(result.status);
                                return Flow.STOP;

                            case OK:
                                pld.context.setUserInfo(result.userProfile);
                                return Flow.CONTINUE;
                        }
                        throw new IllegalArgumentException("result.status = " + result.status);
                    });

    Vertex logTransaction() {
        return handler(
                pld -> transactionLog.logTransactioin(pld.request.getUserId())
        ).withoutMerger();
    }

    Vertex logTransaction = logTransaction();

    Vertex logTransaction2 = logTransaction();

    Vertex logActionToUserJournal =
            handler(
                    pld -> userJournal.logAction(
                            pld.request.getUserId(),
                            String.format("Request type: %s", pld.getClass().getSimpleName()))
            ).withoutMerger();

    Vertex sendWebNotification =
            handler(pld -> notifier.sendHttpPurchaseNotification(pld.request.getUserId()))
                    .withoutMerger();

    Vertex sendSmsNotification =
            handler(pld -> notifier.sendSmsPurchaseNotification(pld.request.getUserId()))
                    .withoutMerger();

    Vertex withdrawMoneyWithMinus =
            handler(/*
                        # WithdrawMoneyMinus
                        Withdraw money even if user does not have money on his account.
                        User will end up with negative balance after this operation.
                    */
                    pld -> bank.withdrawMoneyWithMinus(
                            pld.context.getUserInfo(),
                            pld.context.getServiceInfo())
            ).withRoutingMerger(
                    /*
                        # CheckWithdraw
                        Checks result of withdraw operation
                        Sets new amount and withdrawal status in payload
                        Stops in case if operation is failed
                    */
                    (pld, withdraw) -> {
                        switch (withdraw.getStatus()) {
                            case WALLET_NOT_FOUND:
                            case USER_IS_BLOCKED:
                                pld.response.setStatus(withdraw.getStatus());
                                return Flow.STOP;
                            case OK:
                                pld.response
                                        .setNewAmount(withdraw.getNewAmount())
                                        .setWithdrawalWasInMinus(withdraw.getNewAmount().compareTo(BigDecimal.ZERO) < 0)
                                        .setStatus(Bank.Withdraw.Status.OK);
                                return Flow.CONTINUE;
                            default:
                                throw new IllegalArgumentException("Status: " + withdraw.getStatus());
                        }
                    });


    Vertex isPartnerService =
            mutator(/*
                       Check if given service is provided by a partner.
                       Update payload response.
                   */
                    pld -> {
                        if (pld.context.serviceInfo.isPartnerService()) {
                            pld.response.isPartnerService = true;
                        }
                    });

    Vertex loadServiceInfo =
            handler(
                    /*
                     * Loads data for given service from database.
                     * Service could be not active. In that case purchase request will be denied.
                     */
                    pld -> serviceRegistry.loadServiceInformation(pld.request.getServiceId())

            ).withRoutingMerger(
                    //# checkServiceState
                    (pld, result) -> {
                        if (pld.response.getStatus() != null) {
                            return Flow.STOP;
                        }

                        switch (result.getStatus()) {
                            case SERVICE_NOT_FOUND:
                                pld.response.setStatus(result.getStatus());
                                return Flow.STOP;
                            case OK:
                                pld.context.setServiceInfo(result.getServiceInfo());

                                if (result.getServiceInfo().isActive()) {
                                    return Flow.WITHDRAWAL;
                                } else {
                                    return Flow.NO_WITHDRAWAL;
                                }
                        }
                        return Flow.CONTINUE;
                    });


    Vertex checkBonuses =
            router(pld -> {
                Optional<Long> bonus = marketingService.checkBonuses(pld.request.userId, pld.request.serviceId);

                if (bonus.isPresent()) {
                    pld.context.bonusService = bonus.get();
                    return Flow.BONUS_EXIST;
                } else {
                    return Flow.NO_BONUS;
                }
            });

    Vertex bonusPurchaseSubgraph =
            subgraph(
                    //# Make bonus purchase
                    PurchaseSubmit.class,
                    pld -> {
                        PurchaseSubmit.Request request = new PurchaseSubmit.Request();
                        request
                                .setServiceId(107L)
                                .setUserId(pld.request.userId);
                        return request;
                    }

            ).withMerger((pld, subgraphResponse) ->
                    pld.response.bonusServiceStatus = subgraphResponse.status
            );

    {

        payload()
                .handleBy(loadUserProfile)
                .handleBy(loadServiceInfo);

        loadUserProfile
                .on(Flow.STOP).complete()
                .on(Flow.CONTINUE).mergeBy(loadServiceInfo);

        loadServiceInfo
                .on(Flow.WITHDRAWAL).handleBy(withdrawMoneyWithMinus)
                .on(Flow.NO_WITHDRAWAL).handleBy(logTransaction2)
                .on(Flow.NO_WITHDRAWAL).handleBy(sendWebNotification)
                .on(Flow.NO_WITHDRAWAL).handleBy(sendSmsNotification)
                .on(Flow.STOP).complete();

        withdrawMoneyWithMinus.onAny().handleBy(isPartnerService);

        isPartnerService.onAny().handleBy(logTransaction);

        logTransaction.onAny().handleBy(logActionToUserJournal);

        logTransaction2.onAny().handleBy(logActionToUserJournal);

        logActionToUserJournal.onAny().handleBy(checkBonuses);

        checkBonuses
                //User can claim bonus purchase
                .on(Flow.BONUS_EXIST).handleBy(bonusPurchaseSubgraph)
                //User does not have any bonuses
                .on(Flow.NO_BONUS).complete();

        bonusPurchaseSubgraph.onAny().complete();

        coordinates()
                .pd(495, 34)
                .vx(bonusPurchaseSubgraph, 309, 917)
                .vx(checkBonuses, 595, 806)
                .vx(isPartnerService, 376, 513)
                .vx(sendSmsNotification, 823, 392)
                .vx(sendWebNotification, 926, 333)
                .vx(loadServiceInfo, 565, 96, 643, 232)
                .vx(loadUserProfile, 734, 97, 737, 160)
                .vx(logActionToUserJournal, 554, 694, 593, 757)
                .vx(logTransaction, 434, 586, 464, 658)
                .vx(logTransaction2, 700, 481, 719, 551)
                .vx(withdrawMoneyWithMinus, 349, 377, 378, 441)
                .ct(checkBonuses, 775, 934)
                .ct(loadServiceInfo, 497, 293)
                .ct(loadUserProfile, 897, 225);
    }
}
