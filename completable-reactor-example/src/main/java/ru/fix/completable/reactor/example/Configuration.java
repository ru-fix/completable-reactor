package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.example.chain.PurchasePayload;
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
    GraphProcessor<UserProfileService> gUserProfile = graphBuilder.processor(userProfile);
    GraphProcessorDescription<UserProfileService, UserProfilePayloadMixin> gUserProfileDescription =
            graphBuilder.describe(UserProfileService.class, UserProfilePayloadMixin.class)
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


    GraphProcessor<ServiceInfoProcessor> gServiceInfo = graphBuilder.processor(serviceInfo);


    GraphProcessor<BankProcessor> gBank = graphBuilder.processor(bank);
    GraphProcessor<TransactionLogProcessor> gTxLog = graphBuilder.processor(txLog);
    GraphProcessor<UserLogProcessor> gUserLog = graphBuilder.processor(userLog);
    GraphProcessor<NotificationProcessor> gNotification = graphBuilder.processor(notification1);

    enum Status {OK}


    ReactorGraph<PurchasePayload> purchaseGraph() throws Exception {

        return graphBuilder.payload(PurchasePayload.class)

                .processedBy(gUserProfile)
                .describe(gUserProfileDescription)

                /**
                 * Purchase Service Info Merge Point
                 */
                .processedBy(gServiceInfo)
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

                .processedBy(gBank)
                .passArg(pld -> pld.intermediateData.getUserInfo())
                .passArg(pld -> pld.intermediateData.getServiceInfo())
                .withHandler(BankProcessor::withdrawMoneyWithMinus)
                .withMerger(new String[]{
                                "Checks result of withdraw operation",
                                "Sets new amount and withdrawal status in payload",
                                "Stops in case if operation is failed"
                        },
                        (pld, withdraw) -> {
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
                        })

                .processedBy(gTxLog)
                .passArg(pld -> pld.request.getUserId())
                .withHandler(TransactionLogProcessor::logTransactioin)
                .withMerger((pld, any) -> MergeStatus.CONTINUE)

                .processedBy(gUserLog)
                .passArg(pld -> pld.request.getUserId())
                .passArg(pld -> String.format("Request type: %s", pld.request.getClass().getSimpleName()))
                .withHandler(UserLogProcessor::logAction)
                .withMerger((pld, result) -> MergeStatus.CONTINUE)

                .processedBy(gNotification)
                .copyArg(pld -> pld.request.getUserId())
                .withHandler(NotificationProcessor::sendPurchaseNotification)
                .withoutMerger()

                .startPoint()
                .handleBy(gUserProfile, gServiceInfo)

                .multiMerge()
                .merge(gUserProfile)
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).merge(gServiceInfo)


                .merge(gServiceInfo)
                .on(MergeStatus.WITHDRAWAL).handleBy(gBank)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gUserLog, gNotification)
                .on(MergeStatus.STOP).complete()

                .singleMerge(gBank)
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



        GraphMergePoint<SubscribePayload> trialPeriodCheck = graphBuilder.describeMergePoint(SubscribePayload.class)
                .withMerger(new String[]{"Checks whether service supports trial period"},
                        payload -> {
                            if(payload.getServiceInfo().isSupportTrialPeriod()) {
                                return MergeStatus.WITHDRAWAL;
                            } else {
                                return MergeStatus.NO_WITHDRAWAL;
                            }
                        })
                .buildMergePoint();

        return graphBuilder.payload(SubscribePayload.class)

                .processedBy(gUserProfile)
                .describe(gUserProfileDescription)

                .processedBy(gServiceInfo)
                /**
                 * Subscription Service Info Merge Point
                 */
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

                            if(result.getServiceInfo().isActive()){
                                return MergeStatus.CONTINUE;
                            } else {
                                return MergeStatus.NO_WITHDRAWAL;
                            }
                    }
                    return MergeStatus.CONTINUE;
                })

                .processedBy(gBank)
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

                .processedBy(gTxLog)
                .passArg(pld -> pld.request.getUserId())
                .withHandler(TransactionLogProcessor::logTransactioin)
                .withMerger((pld, any) -> MergeStatus.CONTINUE)

                .processedBy(gUserLog)
                .passArg(pld -> pld.request.getUserId())
                .passArg(pld -> String.format("Request type: %s", pld.request.getClass().getSimpleName()))
                .withHandler(UserLogProcessor::logAction)
                .withMerger((pld, result) -> {
                    pld.response.setStatusIfNull(Status.OK);
                    return MergeStatus.CONTINUE;
                })

                .processedBy(gNotification)
                .copyArg(pld -> pld.request.getUserId())
                .withHandler(NotificationProcessor::sendPurchaseNotification)
                .withoutMerger()


                .startPoint()
                .handleBy(gUserProfile, gServiceInfo)

                .multiMerge()
                .merge(gUserProfile)
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).merge(gServiceInfo)

                .merge(gServiceInfo)
                .on(MergeStatus.CONTINUE).merge(trialPeriodCheck)
                .on(MergeStatus.STOP).complete()

                .merge(trialPeriodCheck)
                .on(MergeStatus.WITHDRAWAL).handleBy(gBank)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gUserLog, gNotification)


                .singleMerge(gBank)
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
