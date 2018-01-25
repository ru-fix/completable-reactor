package ru.fix.completable.reactor.example

import org.springframework.beans.factory.annotation.Autowired
import ru.fix.completable.reactor.example.services.*
import ru.fix.completable.reactor.example.services.UserProfileManager.Status
import ru.fix.completable.reactor.runtime.ReactorGraph
import ru.fix.completable.reactor.runtime.gl.GraphConfig
import ru.fix.completable.reactor.runtime.gl.Vertex

/**
 * Created by Kamil Asfandiyarov
 */
class SubscribeGraphConfig : GraphConfig<SubscribePayload>() {

    val userProfile = object : Vertex() {
        lateinit var userProfile: UserProfileManager

        init {
            handler { pld ->
                userProfile.loadUserProfileById(pld.request.userId)
            }.withMerger(
                    //check profile status
                    { pld, result ->
                        if (pld.response.status != null) {
                            Flow.STOP
                        }

                        when (result.status) {
                            Status.USER_NOT_FOUND, Status.USER_IS_BLOCKED -> {
                                pld.response.status = result.status
                                Flow.STOP
                            }

                            Status.OK -> {
                                pld.intermediateData.userInfo = result.userProfile
                                Flow.CONTINUE
                            }

                            else -> throw IllegalArgumentException("result.status = " + result.status)
                        }
                    })
        }
    }

    val txLog = object : Vertex() {
        lateinit var txLog: TransactionLog

        init {
            handler { pld ->
                txLog.logTransactioin(pld.request.userId)
            }.withMerger { pld, any -> Flow.CONTINUE }
        }
    }

    val userJournal = object : Vertex() {
        lateinit var userJournal: UserJournal

        init {
            handler { pld ->
                userJournal.logAction(
                        pld.request.userId,
                        String.format("Request type: %s", pld.javaClass.getSimpleName()))
            }.withMerger { pld, result -> Flow.CONTINUE }
        }
    }

    @Autowired
    lateinit var notifier: Notifier

    val webNotification =
            handler { pld ->
                notifier.sendHttpPurchaseNotification(pld.request.userId)
            }.withoutMerger()


    val bank = object : Vertex() {
        lateinit var bank: Bank

        init {
            handler { pld ->
                bank.withdrawMoney(
                        pld.intermediateData.userInfo,
                        pld.intermediateData.serviceInfo)
            }.withMerger(
                    //update new amount
                    { pld, withdraw ->
                        when (withdraw.getStatus()) {
                            Bank.Withdraw.Status.WALLET_NOT_FOUND,
                            Bank.Withdraw.Status.USER_IS_BLOCKED -> {
                                pld.response.status = withdraw.status
                                Flow.STOP
                            }
                            Bank.Withdraw.Status.OK -> {
                                pld.response.newAmount = withdraw.getNewAmount()
                                pld.response.status = Bank.Withdraw.Status.OK
                                Flow.CONTINUE
                            }

                            else -> throw IllegalArgumentException("Status: " + withdraw.getStatus());
                        }
                    })
        }
    }

    val serviceInfo = object : Vertex() {
        lateinit var serviceInfo: ServiceRegistry

        init {
            handler { pld ->
                serviceInfo.loadServiceInformation(pld.request.serviceId)
            }.withMerger { pld, result ->
                if (pld.response.status != null) {
                    Flow.STOP
                }

                when (result.status!!) {
                    ServiceRegistry.Status.SERVICE_NOT_FOUND -> {
                        pld.response.status = result.getStatus()
                        Flow.STOP
                    }
                    ServiceRegistry.Status.OK -> {
                        pld.intermediateData.serviceInfo = result.getServiceInfo()

                        if (result.getServiceInfo().isActive()) {
                            Flow.CONTINUE
                        } else {
                            Flow.NO_WITHDRAWAL
                        }
                    }
                }

                Flow.CONTINUE
            }
        }

    }

    val checkTrialPeriod = router(
            //checkTrialPeriod
            //Checks whether service supports trial period
            { payload ->

                if (payload.intermediateData.serviceInfo!!.isSupportTrialPeriod) {
                    Flow.WITHDRAWAL
                } else {
                    Flow.NO_WITHDRAWAL
                }
            })

    fun subscribeGraph(): ReactorGraph<SubscribePayload> {
        payload()
                .handleBy(userProfile)
                .handleBy(serviceInfo)

        userProfile
                .on(Flow.STOP).complete()
                .on(Flow.CONTINUE).mergeBy(serviceInfo)

        serviceInfo
                .on(Flow.CONTINUE).mergeBy(checkTrialPeriod)
                .on(Flow.STOP).complete()

        checkTrialPeriod
                .on(Flow.WITHDRAWAL).handleBy(bank)
                .on(Flow.NO_WITHDRAWAL).handleBy(userJournal)
                .on(Flow.NO_WITHDRAWAL).handleBy(webNotification)

        bank
                .onAny()
                .handleBy(txLog)

        txLog
                .onAny()
                .handleBy(userJournal)

        userJournal
                .onAny().complete()

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
                .complete(userProfile, 920, 300)

        return buildGraph()
    }
}
