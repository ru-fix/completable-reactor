package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.api.Description;
import ru.fix.completable.reactor.example.chain.PurchasePayload;
import ru.fix.completable.reactor.example.chain.ServiceInfoPayloadMixin;
import ru.fix.completable.reactor.example.chain.SubscribePayload;
import ru.fix.completable.reactor.example.chain.UserProfilePayloadMixin;
import ru.fix.completable.reactor.example.processors.*;
import ru.fix.completable.reactor.runtime.*;

import java.math.BigDecimal;

/**
 * Created by Kamil Asfandiyarov
 */
public class Configuration {

    final ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder();


    /**
     * Processors
     */
    UserProfileService userProfile = new UserProfileService();
    ServiceInfoProcessor serviceInfo = new ServiceInfoProcessor();
    BankProcessor bank = new BankProcessor();
    TransactionLogProcessor txLog = new TransactionLogProcessor();
    UserLogProcessor userLog = new UserLogProcessor();
    NotificationProcessor notification1 = new NotificationProcessor();


    /**
     * Graph processors
     */
    GraphProcessorDescription<UserProfileService, UserProfilePayloadMixin> gUserProfileDescription =
            graphBuilder.describeProcessor(UserProfileService.class)
                    .forPayload(UserProfilePayloadMixin.class)
                    .passArg(pld -> pld.getUserId())
                    .passArg(pld -> pld.getUserId().toString())
                    .withHandler(UserProfileService::loadUserProfileById)
                    .withMerger((pld, result) -> {
                        if (pld.getStatus() != null) {
                            return MergeStatus.STOP;
                        }

                        switch (result.status) {
                            case USER_NOT_FOUND:
                            case USER_IS_BLOCKED:
                                pld.setStatus(result.status);
                                return MergeStatus.STOP;

                            case OK:
                                pld.setUserProfile(result.userProfile);
                                return MergeStatus.CONTINUE;
                        }
                        throw new IllegalArgumentException("result.status = " + result.status);
                    });

    GraphProcessor<UserProfileService, UserProfilePayloadMixin> gUserProfile = gUserProfileDescription.buildProcessor(userProfile);


    GraphProcessor<TransactionLogProcessor, UserProfilePayloadMixin> gTxLog = graphBuilder.describeProcessor(TransactionLogProcessor.class)
            .forPayload(UserProfilePayloadMixin.class)
            .passArg(pld -> pld.getUserId())
            .withHandler(TransactionLogProcessor::logTransactioin)
            .withMerger((pld, any) -> MergeStatus.CONTINUE)
            .buildProcessor(txLog);


    GraphProcessor<UserLogProcessor, UserProfilePayloadMixin> gUserLog = graphBuilder.describeProcessor(UserLogProcessor.class)
            .forPayload(UserProfilePayloadMixin.class)
            .passArg(pld -> pld.getUserId())
            .passArg(pld -> String.format("Request type: %s", pld.getClass().getSimpleName()))
            .withHandler(UserLogProcessor::logAction)
            .withMerger((pld, result) -> MergeStatus.CONTINUE)
            .buildProcessor(userLog);


    GraphProcessor<NotificationProcessor, UserProfilePayloadMixin> gNotification = graphBuilder.describeProcessor(NotificationProcessor.class)
            .forPayload(UserProfilePayloadMixin.class)
            .copyArg(pld -> pld.getUserId())
            .withHandler(NotificationProcessor::sendPurchaseNotification)
            .withoutMerger()
            .buildProcessor(notification1);

    enum Status {OK}


    @Description({
            "Checks result of withdraw operation",
            "Sets new amount and withdrawal status in payload",
            "Stops in case if operation is failed"})
    static Enum checkWinthdrawResult(PurchasePayload pld, BankProcessor.Withdraw withdraw) {
        switch (withdraw.getStatus()) {
            case WALLET_NOT_FOUND:
            case USER_IS_BLOCKED:
                pld.response.setStatus(withdraw.getStatus());
                return MergeStatus.STOP;
            case OK:
                pld.response
                        .setNewAmount(withdraw.getNewAmount())
                        .setWithdrawalWasInMinus(withdraw.getNewAmount().compareTo(BigDecimal.ZERO) < 0)
                        .setStatus(BankProcessor.Withdraw.Status.OK);
                return MergeStatus.CONTINUE;

            default:
                throw new IllegalArgumentException("Status: " + withdraw.getStatus());
        }
    }

    GraphProcessor<BankProcessor, PurchasePayload> gBankPurchase = graphBuilder.describeProcessor(BankProcessor.class)
            .forPayload(PurchasePayload.class)
            .passArg(pld -> pld.intermediateData.getUserInfo())
            .passArg(pld -> pld.intermediateData.getServiceInfo())
            .withHandler(BankProcessor::withdrawMoneyWithMinus)
            .withMerger(Configuration::checkWinthdrawResult)
            .buildProcessor(bank);

    GraphProcessor<BankProcessor, SubscribePayload> gBankSubsribe = graphBuilder.describeProcessor(BankProcessor.class)
            .forPayload(SubscribePayload.class)
            .passArg(pld -> pld.intermediateData.getUserInfo())
            .passArg(pld -> pld.intermediateData.getServiceInfo())
            .withHandler(BankProcessor::withdrawMoney)
            .withMerger((pld, withdraw) -> {
                switch (withdraw.getStatus()) {
                    case WALLET_NOT_FOUND:
                    case USER_IS_BLOCKED:
                        pld.response.setStatus(withdraw.getStatus());
                        return MergeStatus.STOP;
                    case OK:
                        pld.response.setNewAmount(withdraw.getNewAmount());
                        pld.response.setStatusIfNull(BankProcessor.Withdraw.Status.OK);
                        return MergeStatus.CONTINUE;

                    default:
                        throw new IllegalArgumentException("Status: " + withdraw.getStatus());
                }
            })
            .buildProcessor(bank);


    ReactorGraph<PurchasePayload> purchaseGraph() throws Exception {

        GraphProcessor<ServiceInfoProcessor, ServiceInfoPayloadMixin> gServiceInfo = graphBuilder.describeProcessor(ServiceInfoProcessor.class)
                .forPayload(ServiceInfoPayloadMixin.class)
                .passArg(pld -> pld.getServiceId())
                .withHandler(ServiceInfoProcessor::loadServiceInformation)
                .withMerger((pld, result) -> {
                    if (pld.getStatus() != null) {
                        return MergeStatus.STOP;
                    }

                    switch (result.getStatus()) {
                        case SERVICE_NOT_FOUND:
                            pld.setStatus(result.getStatus());
                            return MergeStatus.STOP;
                        case OK:
                            pld.setServiceInfo(result.getServiceInfo());

                            if (result.getServiceInfo().isActive()) {
                                return MergeStatus.WITHDRAWAL;
                            } else {
                                return MergeStatus.NO_WITHDRAWAL;
                            }
                    }
                    return MergeStatus.CONTINUE;
                })
                .buildProcessor(serviceInfo);


        return graphBuilder.payload(PurchasePayload.class)
                .startPoint()
                .handleBy(gUserProfile)
                .handleBy(gServiceInfo)

                .multiMerge()
                .merge(gUserProfile)
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).merge(gServiceInfo)


                .merge(gServiceInfo)
                .on(MergeStatus.WITHDRAWAL).handleBy(gBankPurchase)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gUserLog)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gNotification)
                .on(MergeStatus.STOP).complete()

                .singleMerge(gBankPurchase)
                .onAny()
                .handleBy(gTxLog)

                .singleMerge(gTxLog)
                .onAny()
                .handleBy(gUserLog)

                .singleMerge(gUserLog)
                .onAny()
                .complete()

                .coordinates()
                .proc("BankProcessor@0", 410, 440)
                .proc("NotificationProcessor@0", 880, 430)
                .proc("ServiceInfoProcessor@0", 480, 120)
                .proc("TransactionLogProcessor@0", 420, 650)
                .proc("UserLogProcessor@0", 680, 820)
                .proc("UserProfileService@0", 770, 120)
                .merge("UserProfileService@0", 830, 250)
                .merge("ServiceInfoProcessor@0", 640, 280)
                .merge("BankProcessor@0", 480, 550)
                .merge("TransactionLogProcessor@0", 530, 770)
                .merge("UserLogProcessor@0", 760, 930)
                .start(680, 60)
                .complete("UserProfileService@0", 920, 300)
                .complete("ServiceInfoProcessor@0", 480, 310)
                .complete("UserLogProcessor@0", 740, 1020)

                .buildGraph();
    }

    ReactorGraph<SubscribePayload> subscribeGraph() throws Exception {

        GraphProcessor<ServiceInfoProcessor, ServiceInfoPayloadMixin> gServiceInfo = graphBuilder.describeProcessor(ServiceInfoProcessor.class)
                .forPayload(ServiceInfoPayloadMixin.class)
                .passArg(pld -> pld.getServiceId())
                .withHandler(ServiceInfoProcessor::loadServiceInformation)
                .withMerger((pld, result) -> {
                    if (pld.getStatus() != null) {
                        return MergeStatus.STOP;
                    }

                    switch (result.getStatus()) {
                        case SERVICE_NOT_FOUND:
                            pld.setStatus(result.getStatus());
                            return MergeStatus.STOP;
                        case OK:
                            pld.setServiceInfo(result.getServiceInfo());

                            if (result.getServiceInfo().isActive()) {
                                return MergeStatus.CONTINUE;
                            } else {
                                return MergeStatus.NO_WITHDRAWAL;
                            }
                    }
                    return MergeStatus.CONTINUE;
                }).buildProcessor(serviceInfo);


        GraphMergePoint<SubscribePayload> trialPeriodCheck = graphBuilder.describeMergePoint()
                .forPayload(SubscribePayload.class)
                .withMerger(
                        "checkTrialPeriod",
                        new String[]{"Checks whether service supports trial period"},
                        payload -> {
                            if (payload.getServiceInfo().isSupportTrialPeriod()) {
                                return MergeStatus.WITHDRAWAL;
                            } else {
                                return MergeStatus.NO_WITHDRAWAL;
                            }
                        })
                .buildMergePoint();

        return graphBuilder.payload(SubscribePayload.class)

                .startPoint()
                .handleBy(gUserProfile)
                .handleBy(gServiceInfo)

                .multiMerge()
                .merge(gUserProfile)
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).merge(gServiceInfo)

                .merge(gServiceInfo)
                .on(MergeStatus.CONTINUE).merge(trialPeriodCheck)
                .on(MergeStatus.STOP).complete()

                .merge(trialPeriodCheck)
                .on(MergeStatus.WITHDRAWAL).handleBy(gBankSubsribe)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gUserLog)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gNotification)

                .singleMerge(gBankSubsribe)
                .onAny()
                .handleBy(gTxLog)

                .singleMerge(gTxLog)
                .onAny()
                .handleBy(gUserLog)

                .singleMerge(gUserLog)
                .onAny()
                .complete()

                .coordinates()
                .start(680, 60)
                .proc("BankProcessor@0", 410, 440)
                .proc("NotificationProcessor@0", 889, 465)
                .proc("ServiceInfoProcessor@0", 480, 120)
                .proc("TransactionLogProcessor@0", 420, 650)
                .proc("UserLogProcessor@0", 680, 820)
                .proc("UserProfileService@0", 770, 120)
                .merge("BankProcessor@0", 480, 550)
                .merge("MergePoint@0", 702, 363)
                .merge("ServiceInfoProcessor@0", 640, 280)
                .merge("TransactionLogProcessor@0", 530, 770)
                .merge("UserLogProcessor@0", 760, 930)
                .merge("UserProfileService@0", 830, 250)
                .complete("ServiceInfoProcessor@0", 480, 310)
                .complete("UserLogProcessor@0", 740, 1020)
                .complete("UserProfileService@0", 920, 300)

                .buildGraph();

    }


}
