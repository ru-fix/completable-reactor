package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.example.chain.PurchasePayload;
import ru.fix.completable.reactor.example.chain.ServiceInfoPayloadMixin;
import ru.fix.completable.reactor.example.chain.SubscribePayload;
import ru.fix.completable.reactor.example.processors.*;
import ru.fix.completable.reactor.runtime.GraphConfig;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.gl.Vertex;

import java.math.BigDecimal;

/**
 * Created by Kamil Asfandiyarov
 */
public class PurchaseGraphConfig extends GraphConfig<PurchasePayload> {

    /**
     * Services
     */
    UserProfileService userProfile = new UserProfileService();
    ServiceInfoProcessor serviceInfo = new ServiceInfoProcessor();
    BankProcessor bank = new BankProcessor();

    UserLogProcessor userLog = new UserLogProcessor();
    NotificationProcessor notification1 = new NotificationProcessor();


    /**
     * Graph processors
     */
    Vertex gUserProfile =
            handler("load user profile",
                    pld -> userProfile.loadUserProfileById(pld.getUserId(), pld.getUserId().toString())
            ).withMerger("check profile state",
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


    Vertex txLog = new Vertex() {
        TransactionLogProcessor txLog = new TransactionLogProcessor();

        {
            handler(
                    pld -> txLog.logTransactioin(pld.getUserId())
            ).withMerger(
                    (pld, any) -> MergeStatus.CONTINUE
            );
        }
    };


    Vertex gUserLog =
            handler(
                    pld -> userLog.logAction(
                            pld.getUserId(),
                            String.format("Request type: %s", pld.getClass().getSimpleName()))
            ).withMerger((pld, result) -> MergeStatus.CONTINUE);


    Vertex gNotification =
            handler(pld -> notification1.sendPurchaseNotification(pld.getUserId()))
                    .withoutMerger();


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

    Vertex gBankPurchase =
            handler(
                    pld -> bank.withdrawMoneyWithMinus(
                            pld.intermediateData.getUserInfo(),
                            pld.intermediateData.getServiceInfo())
            ).withMerger(PurchaseGraphConfig::checkWithdrawResult);

    Vertex gBankSubsribe =
            handler(pld -> bank.withdrawMoney(
                    pld.intermediateData.getUserInfo(),
                    pld.intermediateData.getServiceInfo())
            ).withMerger(
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
                    });


    Vertex gPurchaseServiceInfo =
            handler(pld -> serviceInfo.loadServiceInformation(pld.getServiceId()))
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
                            });

    public ReactorGraph<PurchasePayload> purchaseGraph() {

        payload()
                .handleBy(gUserProfile)
                .handleBy(gPurchaseServiceInfo);

        gUserProfile
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).mergeBy(gPurchaseServiceInfo);


        gPurchaseServiceInfo
                .on(MergeStatus.WITHDRAWAL).handleBy(gBankPurchase)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gUserLog)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(gNotification)
                .on(MergeStatus.STOP).complete();

        gBankPurchase.onAny().handleBy(txLog);

        txLog.onAny().handleBy(gUserLog);

        gUserLog.onAny().complete();


        coordinates()
                .payload(680, 60)
                .handler(gBankPurchase, 410, 440)
                .handler(gNotification, 880, 430)
                .handler(gPurchaseServiceInfo, 480, 120)
                .handler(txLog, 420, 650)
                .handler(gUserLog, 680, 820)
                .handler(gUserProfile, 770, 120)
                .merger(gBankPurchase, 480, 550)
                .merger(gPurchaseServiceInfo, 640, 280)
                .merger(txLog, 530, 770)
                .merger(gUserLog, 760, 930)
                .merger(gUserProfile, 806, 201)
                .complete(gPurchaseServiceInfo, 480, 310)
                .complete(gUserLog, 740, 1020)
                .complete(gUserProfile, 963, 258);

        return buildGraph();
    }
}
