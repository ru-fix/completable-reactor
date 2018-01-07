package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.example.chain.PurchasePayload;
import ru.fix.completable.reactor.example.chain.SubscribePayload;
import ru.fix.completable.reactor.example.processors.*;
import ru.fix.completable.reactor.runtime.GraphConfig;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.gl.Vertex;

import java.math.BigDecimal;

/**
 * Created by Kamil Asfandiyarov
 */
public class SubscribeGraphConfig extends GraphConfig<SubscribePayload> {

    TransactionLogProcessor txLog = new TransactionLogProcessor();

    Notifier notification1 = new Notifier();


    Vertex userProfile = new Vertex() {
        UserProfileService userProfile = new UserProfileService();

        {
            handler(
                    pld -> userProfile.loadUserProfileById(pld.request.getUserId())
            ).withMerger(
                    "check profile status",
                    (pld, result) -> {
                        if (pld.response.getStatus() != null) {
                            return MergeStatus.STOP;
                        }

                        switch (result.status) {
                            case USER_NOT_FOUND:
                            case USER_IS_BLOCKED:
                                pld.response.setStatus(result.status);
                                return MergeStatus.STOP;

                            case OK:
                                pld.intermediateData.setUserInfo(result.userProfile);
                                return MergeStatus.CONTINUE;
                        }
                        throw new IllegalArgumentException("result.status = " + result.status);
                    });
        }
    };

    Processor<UserProfilePayloadMixin> gTxLog = graphBuilder.processor()
            .forPayload(UserProfilePayloadMixin.class)
            .passArg(pld -> pld.getUserId())
            .withHandler(txLog::logTransactioin)
            .withMerger((pld, any) -> MergeStatus.CONTINUE)
            .buildProcessor();


    Vertex gUserLog = new Vertex() {
        UserLogProcessor userLog = new UserLogProcessor();

        {
            handler(
                    pld -> userLog.logAction(
                            pld.request.getUserId(),
                            String.format("Request type: %s", pld.getClass().getSimpleName()))
            ).withMerger((pld, result) -> MergeStatus.CONTINUE);
        }
    };

    Processor<UserProfilePayloadMixin> gNotification = graphBuilder.processor()
            .forPayload(UserProfilePayloadMixin.class)
            .copyArg(pld -> pld.getUserId())
            .withHandler(notification1::sendHttpPurchaseNotification)
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

    Vertex bank = new Vertex() {
        BankProcessor bank = new BankProcessor();

        {
            handler(pld -> bank.withdrawMoney(
                    pld.intermediateData.getUserInfo(),
                    pld.intermediateData.getServiceInfo())
            ).withMerger(
                    "update new amount",
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
                    });
        }
    }


    Processor<ServiceInfoPayloadMixin> gPurchaseServiceInfo = graphBuilder.processor()
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


    Vertex serviceInfo = new Vertex() {
        ServiceInfoProcessor serviceInfo = new ServiceInfoProcessor();

        {
            handler(
                    pld -> serviceInfo.loadServiceInformation(pld.request.getServiceId())
            ).withMerger(
                    (pld, result) -> {
                        if (pld.response.getStatus() != null) {
                            return MergeStatus.STOP;
                        }

                        switch (result.getStatus()) {
                            case SERVICE_NOT_FOUND:
                                pld.response.setStatus(result.getStatus());
                                return MergeStatus.STOP;
                            case OK:
                                pld.intermediateData.setServiceInfo(result.getServiceInfo());

                                if (result.getServiceInfo().isActive()) {
                                    return MergeStatus.CONTINUE;
                                } else {
                                    return MergeStatus.NO_WITHDRAWAL;
                                }
                        }
                        return MergeStatus.CONTINUE;
                    });
        }
    };

    Vertex checkTrialPeriod =
            router(
                    "checkTrialPeriod",
                    new String[]{"Checks whether service supports trial period"},
                    payload -> {
                        if (payload.intermediateData.getServiceInfo().isSupportTrialPeriod()) {
                            return MergeStatus.WITHDRAWAL;
                        } else {
                            return MergeStatus.NO_WITHDRAWAL;
                        }
                    });

    public ReactorGraph<SubscribePayload> subscribeGraph() {
        payload()
                .handleBy(userProfile)
                .handleBy(serviceInfo);

        userProfile
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).mergeBy(serviceInfo);

        serviceInfo
                .on(MergeStatus.CONTINUE).mergeBy(checkTrialPeriod)
                .on(MergeStatus.STOP).complete();

        checkTrialPeriod
                .on(MergeStatus.WITHDRAWAL).handleBy(bank)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gUserLog)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gNotification);

                .mergePoint(bank)
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
                .proc(bank, 410, 440)
                .proc(gNotification, 889, 465)
                .proc(serviceInfo, 480, 120)
                .proc(gTxLog, 420, 650)
                .proc(gUserLog, 680, 820)
                .proc(userProfile, 770, 120)
                .merge(bank, 480, 550)
                .merge(checkTrialPeriod, 702, 363)
                .merge(serviceInfo, 640, 280)
                .merge(gTxLog, 530, 770)
                .merge(gUserLog, 760, 930)
                .merge(userProfile, 830, 250)
                .complete(serviceInfo, 480, 310)
                .complete(gUserLog, 740, 1020)
                .complete(userProfile, 920, 300);

        return buildGraph();
    }
}
