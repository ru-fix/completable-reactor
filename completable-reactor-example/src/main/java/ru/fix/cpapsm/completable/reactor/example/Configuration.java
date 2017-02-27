package ru.fix.cpapsm.completable.reactor.example;

import ru.fix.cpapsm.completable.reactor.example.chain.PurchasePayload;
import ru.fix.cpapsm.completable.reactor.example.chain.ServiceInfoPayloadMixin;
import ru.fix.cpapsm.completable.reactor.example.chain.SubscribePayload;
import ru.fix.cpapsm.completable.reactor.example.chain.UserProfilePayloadMixin;
import ru.fix.cpapsm.completable.reactor.example.processors.*;
import ru.fix.cpapsm.completable.reactor.runtime.GraphProcessor;
import ru.fix.cpapsm.completable.reactor.runtime.GraphProcessorDescription;
import ru.fix.cpapsm.completable.reactor.runtime.ReactorGraph;
import ru.fix.cpapsm.completable.reactor.runtime.ReactorGraphBuilder;

import java.math.BigDecimal;

/**
 * Created by Kamil Asfandiyarov
 */
public class Configuration {

    final ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder();


    /**
     * Processors
     */
    UserProfileProcessor userProfile = new UserProfileProcessor();
    ServiceInfoProcessor serviceInfo = new ServiceInfoProcessor();
    BankProcessor bank = new BankProcessor();
    TransactionLogProcessor txLog = new TransactionLogProcessor();
    UserLogProcessor userLog = new UserLogProcessor();
    NotificationProcessor notification1 = new NotificationProcessor();

    /**
     * Graph processors
     */
    GraphProcessor<UserProfileProcessor> gUserProfile = graphBuilder.processor(userProfile);
    GraphProcessorDescription<UserProfileProcessor, UserProfilePayloadMixin> gUserProfileDescription =
            graphBuilder.describe(UserProfileProcessor.class, UserProfilePayloadMixin.class)
                    .passArg(pld -> pld.getUserId())
                    .passArg(pld -> pld.getUserId().toString())
                    .withHandler(UserProfileProcessor::loadUserProfileById)
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
    GraphProcessorDescription<ServiceInfoProcessor, ServiceInfoPayloadMixin> gServiceInfoDescription =
            graphBuilder.describe(ServiceInfoProcessor.class, ServiceInfoPayloadMixin.class)
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
                    });

    GraphProcessor<BankProcessor> gBank = graphBuilder.processor(bank);
    GraphProcessor<TransactionLogProcessor> gTxLog = graphBuilder.processor(txLog);
    GraphProcessor<UserLogProcessor> gUserLog = graphBuilder.processor(userLog);
    GraphProcessor<NotificationProcessor> gNotification = graphBuilder.processor(notification1);

    enum Status {OK}


    ReactorGraph<PurchasePayload> purchaseGraph() throws Exception {

        return graphBuilder.payload(PurchasePayload.class)

                .processedBy(gUserProfile)
                .describe(gUserProfileDescription)

                .processedBy(gServiceInfo)
                .describe(gServiceInfoDescription)

                .processedBy(gBank)
                .passArg(pld -> pld.intermediateData.getUserInfo())
                .passArg(pld -> pld.intermediateData.getServiceInfo())
                .withHandler(BankProcessor::withdrawMoneyWithMinus)
                .withMerger((pld, withdraw) -> {
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
                .proc("UserProfileProcessor@0", 770, 120)
                .merge("UserProfileProcessor@0", 830, 250)
                .merge("ServiceInfoProcessor@0", 640, 280)
                .merge("BankProcessor@0", 480, 550)
                .merge("TransactionLogProcessor@0", 530, 770)
                .merge("UserLogProcessor@0", 760, 930)
                .start(680, 60)
                .complete("UserProfileProcessor@0", 920, 300)
                .complete("ServiceInfoProcessor@0", 480, 310)
                .complete("UserLogProcessor@0", 740, 1020)

                .buildGraph();
    }

    ReactorGraph<SubscribePayload> subscribeGraph() throws Exception {

        return graphBuilder.payload(SubscribePayload.class)

                .processedBy(gUserProfile)
                .describe(gUserProfileDescription)

                .processedBy(gServiceInfo)
                .describe(gServiceInfoDescription)


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
                .proc("UserProfileProcessor@0", 770, 120)
                .merge("UserProfileProcessor@0", 830, 250)
                .merge("ServiceInfoProcessor@0", 640, 280)
                .merge("BankProcessor@0", 480, 550)
                .merge("TransactionLogProcessor@0", 530, 770)
                .merge("UserLogProcessor@0", 760, 930)
                .start(680, 60)
                .complete("UserProfileProcessor@0", 920, 300)
                .complete("ServiceInfoProcessor@0", 480, 310)
                .complete("UserLogProcessor@0", 740, 1020)

                .buildGraph();

    }


}
