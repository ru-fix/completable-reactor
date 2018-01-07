package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.example.chain.PurchasePayload;
import ru.fix.completable.reactor.example.processors.*;
import ru.fix.completable.reactor.runtime.GraphConfig;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.gl.Vertex;

import java.math.BigDecimal;

/**
 * Define purchase process when user buys good in the shop.
 */
public class PurchaseGraphConfig extends GraphConfig<PurchasePayload> {

    Vertex userProfile = new Vertex() {
        UserProfileService userProfile = new UserProfileService();

        {
            handler("load user profile",
                    pld -> userProfile.loadUserProfileById(pld.request.getUserId())
            ).withMerger("check profile state",
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

    Vertex txLog = new Vertex() {
        TransactionLogProcessor txLog = new TransactionLogProcessor();

        {
            handler(
                    pld -> txLog.logTransactioin(pld.request.getUserId())
            ).withMerger(
                    (pld, any) -> MergeStatus.CONTINUE
            );
        }
    };

    Vertex userLog = new Vertex() {
        UserLogProcessor userLog = new UserLogProcessor();

        {
            handler(
                    pld -> userLog.logAction(
                            pld.request.getUserId(),
                            String.format("Request type: %s", pld.getClass().getSimpleName()))
            ).withMerger((pld, result) -> MergeStatus.CONTINUE);
        }
    };

    Notifier notifier = new Notifier();


    Vertex webNotification =
            handler(pld -> notifier.sendHttpPurchaseNotification(pld.request.getUserId()))
                    .withoutMerger();

    Vertex smsNotification =
            handler(pld -> notifier.sendSmsPurchaseNotification(pld.request.getUserId()))
                    .withoutMerger();

    Vertex bank = new Vertex() {
        BankProcessor bank = new BankProcessor();

        {
            handler(
                    pld -> bank.withdrawMoneyWithMinus(
                            pld.intermediateData.getUserInfo(),
                            pld.intermediateData.getServiceInfo())
            ).withMerger(
                    "checkWithdraw",
                    new String[]{
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
                    });
        }
    };

    Vertex serviceInfo = new Vertex() {
        ServiceInfoProcessor serviceInfo = new ServiceInfoProcessor();

        {
            handler(pld -> serviceInfo.loadServiceInformation(pld.request.getServiceId()))
                    .withMerger(
                            "checkServiceState",
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
                                            return MergeStatus.WITHDRAWAL;
                                        } else {
                                            return MergeStatus.NO_WITHDRAWAL;
                                        }
                                }
                                return MergeStatus.CONTINUE;
                            });
        }
    };

    public ReactorGraph<PurchasePayload> purchaseGraph() {

        payload()
                .handleBy(userProfile)
                .handleBy(serviceInfo);

        userProfile
                .on(MergeStatus.STOP).complete()
                .on(MergeStatus.CONTINUE).mergeBy(serviceInfo);

        serviceInfo
                .on(MergeStatus.WITHDRAWAL).handleBy(bank)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(userLog)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(webNotification)
                .on(MergeStatus.NO_WITHDRAWAL).handleBy(smsNotification)
                .on(MergeStatus.STOP).complete();



        bank.onAny().handleBy(txLog);

        txLog.onAny().handleBy(userLog);

        userLog.onAny().complete();

        coordinates()
                .payload(680, 60)
                .handler(bank, 410, 440)
                .handler(webNotification, 880, 430)
                .handler(smsNotification, 850, 450)
                .handler(serviceInfo, 480, 120)
                .handler(txLog, 420, 650)
                .handler(userLog, 680, 820)
                .handler(userProfile, 770, 120)
                .merger(bank, 480, 550)
                .merger(serviceInfo, 640, 280)
                .merger(txLog, 530, 770)
                .merger(userLog, 760, 930)
                .merger(userProfile, 806, 201)
                .complete(serviceInfo, 480, 310)
                .complete(userLog, 740, 1020)
                .complete(userProfile, 963, 258);

        return buildGraph();
    }
}
