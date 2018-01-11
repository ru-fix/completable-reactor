package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.example.services.*;
import ru.fix.completable.reactor.runtime.gl.GraphConfig;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.gl.Vertex;

/**
 * Created by Kamil Asfandiyarov
 */
public class SubscribeGraphConfig extends GraphConfig<SubscribePayload> {


    Vertex userProfile = new Vertex() {
        UserProfileManager userProfile = new UserProfileManager();

        {
            handler(
                    pld -> userProfile.loadUserProfileById(pld.request.getUserId())
            ).withMerger(
                    "check profile status",
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
        }
    };

    Vertex txLog = new Vertex() {
        TransactionLog txLog = new TransactionLog();

        {
            handler(pld -> txLog.logTransactioin(pld.request.getUserId())
            ).withMerger((pld, any) -> Flow.CONTINUE);
        }
    };

    Vertex userJournal = new Vertex() {
        UserJournal userJournal = new UserJournal();

        {
            handler(
                    pld -> userJournal.logAction(
                            pld.request.getUserId(),
                            String.format("Request type: %s", pld.getClass().getSimpleName()))
            ).withMerger((pld, result) -> Flow.CONTINUE);
        }
    };

    Notifier notifier = new Notifier();

    Vertex webNotification =
            handler(
                    pld -> notifier.sendHttpPurchaseNotification(pld.request.getUserId())
            ).withoutMerger();


    Vertex bank = new Vertex() {
        Bank bank = new Bank();

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
                                return Flow.STOP;
                            case OK:
                                pld.response.setNewAmount(withdraw.getNewAmount());
                                pld.response.setStatusIfNull(Bank.Withdraw.Status.OK);
                                return Flow.CONTINUE;

                            default:
                                throw new IllegalArgumentException("Status: " + withdraw.getStatus());
                        }
                    });
        }
    };


    Vertex serviceInfo = new Vertex() {
        ServiceRegistry serviceInfo = new ServiceRegistry();

        {
            handler(
                    pld -> serviceInfo.loadServiceInformation(pld.request.getServiceId())
            ).withMerger(
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
                                    return Flow.CONTINUE;
                                } else {
                                    return Flow.NO_WITHDRAWAL;
                                }
                        }
                        return Flow.CONTINUE;
                    });
        }
    };

    Vertex checkTrialPeriod =
            router(
                    "checkTrialPeriod",
                    new String[]{"Checks whether service supports trial period"},
                    payload -> {
                        if (payload.intermediateData.getServiceInfo().isSupportTrialPeriod()) {
                            return Flow.WITHDRAWAL;
                        } else {
                            return Flow.NO_WITHDRAWAL;
                        }
                    });

    public ReactorGraph<SubscribePayload> subscribeGraph() {
        payload()
                .handleBy(userProfile)
                .handleBy(serviceInfo);

        userProfile
                .on(Flow.STOP).complete()
                .on(Flow.CONTINUE).mergeBy(serviceInfo);

        serviceInfo
                .on(Flow.CONTINUE).mergeBy(checkTrialPeriod)
                .on(Flow.STOP).complete();

        checkTrialPeriod
                .on(Flow.WITHDRAWAL).handleBy(bank)
                .on(Flow.NO_WITHDRAWAL).handleBy(userJournal)
                .on(Flow.NO_WITHDRAWAL).handleBy(webNotification);

        bank
                .onAny()
                .handleBy(txLog);

        txLog
                .onAny()
                .handleBy(userJournal);

        userJournal
                .onAny().complete();

        coordinates()
                .payload(680, 60)
                .handler(bank, 410, 440)
                .handler(webNotification, 889, 465)
                .handler(serviceInfo, 480, 120)
                .handler(txLog, 420, 650)
                .handler(userJournal, 680, 820)
                .handler(userProfile, 770, 120)
                .merger(bank, 480, 550)
                .merger(checkTrialPeriod, 702, 363)
                .merger(serviceInfo, 640, 280)
                .merger(txLog, 530, 770)
                .merger(userJournal, 760, 930)
                .merger(userProfile, 830, 250)
                .complete(serviceInfo, 480, 310)
                .complete(userJournal, 740, 1020)
                .complete(userProfile, 920, 300);

        return buildGraph();
    }
}
