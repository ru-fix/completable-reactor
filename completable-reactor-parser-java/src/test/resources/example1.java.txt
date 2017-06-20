package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.example.chain.PurchasePayload;
import ru.fix.completable.reactor.example.chain.ServiceInfoPayloadMixin;
import ru.fix.completable.reactor.example.chain.SubscribePayload;
import ru.fix.completable.reactor.example.chain.UserProfilePayloadMixin;
import ru.fix.completable.reactor.example.processors.*;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.dsl.ProcessorDescription;

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
    ProcessorDescription<UserProfilePayloadMixin> gUserProfileDescription =
            graphBuilder.processor()
                    .forPayload(UserProfilePayloadMixin.class)
                    .passArg(pld -> pld.getUserId())
                    .passArg(pld -> pld.getUserId().toString())
                    .withHandler(userProfile::loadUserProfileById)
                    .withMerger(
                            "updateUserProfile",
                            (pld, result) -> {
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

    Processor<UserProfilePayloadMixin> gUserProfile = gUserProfileDescription.buildProcessor();


    Processor<UserProfilePayloadMixin> gTxLog = graphBuilder.processor()
            .forPayload(UserProfilePayloadMixin.class)
            .passArg(pld -> pld.getUserId())
            .withHandler(txLog::logTransactioin)
            .withMerger((pld, any) -> MergeStatus.CONTINUE)
            .buildProcessor();


    Processor<UserProfilePayloadMixin> gUserLog = graphBuilder.processor()
            .forPayload(UserProfilePayloadMixin.class)
            .passArg(pld -> pld.getUserId())
            .passArg(pld -> String.format("Request type: %s", pld.getClass().getSimpleName()))
            .withHandler(userLog::logAction)
            .withMerger((pld, result) -> MergeStatus.CONTINUE)
            .buildProcessor();


    Processor<UserProfilePayloadMixin> gNotification = graphBuilder.processor()
            .forPayload(UserProfilePayloadMixin.class)
            .copyArg(pld -> pld.getUserId())
            .withHandler(notification1::sendPurchaseNotification)
            .withoutMerger()
            .buildProcessor();


    @Reactored({
            "Checks result of withdraw operation",
            "Sets new amount and withdrawal status in payload",
            "Stops in case if operation is failed"})
    static Enum checkWithdrawResult(PurchasePayload pld, BankProcessor.Withdraw withdraw) {
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

    Processor<PurchasePayload> gBankPurchase = graphBuilder.processor()
            .forPayload(PurchasePayload.class)
            .passArg(pld -> pld.intermediateData.getUserInfo())
            .passArg(pld -> pld.intermediateData.getServiceInfo())
            .withHandler(bank::withdrawMoneyWithMinus)
            .withMerger(Configuration::checkWithdrawResult)
            .buildProcessor();

    Processor<SubscribePayload> gBankSubsribe = graphBuilder.processor()
            .forPayload(SubscribePayload.class)
            .passArg(pld -> pld.intermediateData.getUserInfo())
            .passArg(pld -> pld.intermediateData.getServiceInfo())
            .withHandler(bank::withdrawMoney)
            .withMerger(
                    "updateNewAmount",
                    (pld, withdraw) -> {
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
            .buildProcessor();


    ReactorGraph<PurchasePayload> purchaseGraph() throws Exception {

        Processor<ServiceInfoPayloadMixin> gServiceInfo = graphBuilder.processor()
                .forPayload(ServiceInfoPayloadMixin.class)
                .passArg(pld -> pld.getServiceId())
                .withHandler(serviceInfo::loadServiceInformation)
                .withMerger(
                        "updateServiceInfo",
                        (pld, result) -> {
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
                .buildProcessor();


        return graphBuilder.payload(PurchasePayload.class)
                .handle(gUserProfile)
                .handle(gServiceInfo)

                .mergePoint(gUserProfile)
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).merge(gServiceInfo)


                .mergePoint(gServiceInfo)
                .on(MergeStatus.WITHDRAWAL).handle(gBankPurchase)
                .on(MergeStatus.NO_WITHDRAWAL).handle(gUserLog)
                .on(MergeStatus.NO_WITHDRAWAL).handle(gNotification)
                .on(MergeStatus.STOP).complete()

                .mergePoint(gBankPurchase)
                .onAny()
                .handle(gTxLog)

                .mergePoint(gTxLog)
                .onAny()
                .handle(gUserLog)

                .mergePoint(gUserLog)
                .onAny()
                .complete()

                .coordinates()
                .start(680, 60)
                .proc(BankProcessor.class, 0, 410, 440)
                .proc(NotificationProcessor.class, 0, 880, 430)
                .proc(ServiceInfoProcessor.class, 0, 480, 120)
                .proc(TransactionLogProcessor.class, 0, 420, 650)
                .proc(UserLogProcessor.class, 0, 680, 820)
                .proc(UserProfileService.class, 0, 770, 120)
                .merge(BankProcessor.class, 0, 480, 550)
                .merge(ServiceInfoProcessor.class, 0, 640, 280)
                .merge(TransactionLogProcessor.class, 0, 530, 770)
                .merge(UserLogProcessor.class, 0, 760, 930)
                .merge(UserProfileService.class, 0, 806, 201)
                .complete(ServiceInfoProcessor.class, 0, 480, 310)
                .complete(UserLogProcessor.class, 0, 740, 1020)
                .complete(UserProfileService.class, 0, 963, 258)

                .buildGraph();
    }

    ReactorGraph<SubscribePayload> subscribeGraph() throws Exception {

        Processor<ServiceInfoPayloadMixin> gServiceInfo = graphBuilder.processor()
                .forPayload(ServiceInfoPayloadMixin.class)
                .passArg(pld -> pld.getServiceId())
                .withHandler(serviceInfo::loadServiceInformation)
                .withMerger(
                        "updateServiceInfo",
                        (pld, result) -> {
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
                        }).buildProcessor();


        MergePoint<SubscribePayload> trialPeriodCheck = graphBuilder.mergePoint()
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

        return graphBuilder
                .payload(SubscribePayload.class)
                .handle(gUserProfile)
                .handle(gServiceInfo)

                .mergePoint(gUserProfile)
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).merge(gServiceInfo)

                .mergePoint(gServiceInfo)
                .on(MergeStatus.CONTINUE).merge(trialPeriodCheck)
                .on(MergeStatus.STOP).complete()

                .mergePoint(trialPeriodCheck)
                .on(MergeStatus.WITHDRAWAL).handle(gBankSubsribe)
                .on(MergeStatus.NO_WITHDRAWAL).handle(gUserLog)
                .on(MergeStatus.NO_WITHDRAWAL).handle(gNotification)

                .mergePoint(gBankSubsribe)
                .onAny()
                .handle(gTxLog)

                .mergePoint(gTxLog)
                .onAny()
                .handle(gUserLog)

                .mergePoint(gUserLog)
                .onAny()
                .complete()

                .coordinates()
                .start(680, 60)
                .proc(BankProcessor.class, 0, 410, 440)
                .proc(NotificationProcessor.class, 0, 889, 465)
                .proc(ServiceInfoProcessor.class, 0, 480, 120)
                .proc(TransactionLogProcessor.class, 0, 420, 650)
                .proc(UserLogProcessor.class, 0, 680, 820)
                .proc(UserProfileService.class, 0, 770, 120)
                .merge(BankProcessor.class, 0, 480, 550)
                .merge(0, 702, 363)
                .merge(ServiceInfoProcessor.class, 0, 640, 280)
                .merge(TransactionLogProcessor.class, 0, 530, 770)
                .merge(UserLogProcessor.class, 0, 760, 930)
                .merge(UserProfileService.class, 0, 830, 250)
                .complete(ServiceInfoProcessor.class, 0, 480, 310)
                .complete(UserLogProcessor.class, 0, 740, 1020)
                .complete(UserProfileService.class, 0, 920, 300)

                .buildGraph();

    }


}
