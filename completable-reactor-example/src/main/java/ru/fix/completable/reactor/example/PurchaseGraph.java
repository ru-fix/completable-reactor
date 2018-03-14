package ru.fix.completable.reactor.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.fix.completable.reactor.example.services.*;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * # Defines purchase process when user buys good in the shop.
 */
@Configuration
public class PurchaseGraph extends Graph<PurchasePayload> {

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
            ).withMerger(
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
                                pld.intermediateData.setUserInfo(result.userProfile);
                                return Flow.CONTINUE;
                        }
                        throw new IllegalArgumentException("result.status = " + result.status);
                    });

    Vertex logTransaction =
            handler(
                    pld -> transactionLog.logTransactioin(pld.request.getUserId())
            ).withMerger(
                    (pld, any) -> Flow.CONTINUE
            );

    Vertex logTransaction2 = logTransaction.clone();

    Vertex logActionToUserJournal =
            handler(
                    pld -> userJournal.logAction(
                            pld.request.getUserId(),
                            String.format("Request type: %s", pld.getClass().getSimpleName()))
            ).withMerger((pld, result) -> Flow.CONTINUE);

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
                            pld.intermediateData.getUserInfo(),
                            pld.intermediateData.getServiceInfo())
            ).withMerger(
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
            router(/*
                       Check if given service is provided by a partner.
                       Update payload response.
                   */
                    pld -> {
                        if (pld.intermediateData.serviceInfo.isPartnerService()) {
                            pld.response.isPartnerService = true;
                            return Flow.CONTINUE;
                        } else {
                            return Flow.CONTINUE;
                        }
                    });

    Vertex loadServiceInfo =
            handler(
                    /*
                     * Loads data for given service from database.
                     * Service could be not active. In that case purchase request will be denied.
                     */
                    pld -> serviceRegistry.loadServiceInformation(pld.request.getServiceId())

            ).withMerger(
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
                                pld.intermediateData.setServiceInfo(result.getServiceInfo());

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
                    pld.intermediateData.bonusService = bonus.get();
                    return Flow.BONUS_EXIST;
                } else {
                    return Flow.NO_BONUS;
                }
            });

    Vertex bonusPurchaseSubgraph =
            subgraph(
                    //# Make bonus purchase
                    PurchasePayload.class,
                    pld -> {
                        PurchasePayload subgraphRequest = new PurchasePayload();
                        subgraphRequest.request
                                .setServiceId(107L)
                                .setUserId(pld.request.userId);
                        return subgraphRequest;
                    }

            ).withMerger((pld, subgraphResult) ->
                    pld.response.bonusServiceStatus = subgraphResult.response.status
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
                .payload(627, 46)
                .handler(withdrawMoneyWithMinus, 349, 377)
                .handler(loadServiceInfo, 565, 96)
                .handler(sendSmsNotification, 823, 392)
                .handler(logTransaction, 434, 586)
                .handler(logTransaction2, 700, 481)
                .handler(logActionToUserJournal, 554, 694)
                .handler(loadUserProfile, 734, 97)
                .handler(sendWebNotification, 926, 333)
                .router(isPartnerService, 376, 513)
                .router(checkBonuses, 595, 806)
                .merger(withdrawMoneyWithMinus, 378, 441)
                .merger(bonusPurchaseSubgraph, 582, 1033)
                .merger(loadServiceInfo, 643, 232)
                .merger(logTransaction, 464, 658)
                .merger(logTransaction2, 719, 551)
                .merger(logActionToUserJournal, 593, 757)
                .merger(loadUserProfile, 737, 160)
                .complete(bonusPurchaseSubgraph, 578, 1086)
                .complete(checkBonuses, 775, 934)
                .complete(loadServiceInfo, 497, 293)
                .complete(loadUserProfile, 897, 225);
    }
}
