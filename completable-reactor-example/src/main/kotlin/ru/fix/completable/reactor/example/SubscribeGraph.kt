package ru.fix.completable.reactor.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import ru.fix.completable.reactor.example.services.*
import ru.fix.completable.reactor.example.services.UserProfileManager.Status
import ru.fix.completable.reactor.graph.KGraph
import ru.fix.completable.reactor.runtime.CompletableReactor
import javax.annotation.PostConstruct

/**
 * Created by Kamil Asfandiyarov
 */
@Configuration
class SubscribeGraph : KGraph<SubscribePayload>() {

    @Autowired
    lateinit var userProfile: UserProfileManager

    @Autowired
    lateinit var txLog: TransactionLog

    @Autowired
    lateinit var completableReactor: CompletableReactor

    @Autowired
    lateinit var userJournal: UserJournal

    @Autowired
    lateinit var notifier: Notifier

    @Autowired
    lateinit var bank: Bank

    @Autowired
    lateinit var serviceRegistry: ServiceRegistry


    @PostConstruct
    fun initialize() {
        completableReactor.registerGraphIfAbsent(this)
    }

    val loadUserProfile =
            handler {
                userProfile.loadUserProfileById(request.userId)

            }.withMerger {
                //check profile status
                result ->
                if (response.status != null) {
                    Flow.STOP
                }

                when (result.status) {
                    Status.USER_NOT_FOUND, Status.USER_IS_BLOCKED -> {
                        response.status = result.status
                        Flow.STOP
                    }

                    Status.OK -> {
                        intermediateData.userInfo = result.userProfile
                        Flow.CONTINUE
                    }

                    else -> throw IllegalArgumentException("result.status = " + result.status)
                }
            }

    val logTransaction =
            handler {
                txLog.logTransactioin(request.userId)

            }.withMerger { any ->
                Flow.CONTINUE
            }


    val logActionToUserJournal =
            handler {
                userJournal.logAction(
                        request.userId,
                        String.format("Request type: %s", javaClass.simpleName))
            }.withMerger {
                //ignore result of logging
                result ->
                Flow.CONTINUE
            }


    val webNotification =
            handler {
                notifier.sendHttpPurchaseNotification(request.userId)

            }.withoutMerger()


    val withdrawMoney =
            handler {
                bank.withdrawMoney(
                        intermediateData.userInfo,
                        intermediateData.serviceInfo)

            }.withMerger {
                //update new amount
                withdraw ->
                when (withdraw.getStatus()) {
                    Bank.Withdraw.Status.WALLET_NOT_FOUND,
                    Bank.Withdraw.Status.USER_IS_BLOCKED -> {
                        response.status = withdraw.status
                        Flow.STOP
                    }
                    Bank.Withdraw.Status.OK -> {
                        response.newAmount = withdraw.getNewAmount()
                        response.status = Bank.Withdraw.Status.OK
                        Flow.CONTINUE
                    }

                    else -> throw IllegalArgumentException("Status: " + withdraw.status)
                }
            }

    val loadServiceInfo =
            handler {
                serviceRegistry.loadServiceInformation(request.serviceId)

            }.withMerger { result ->
                if (response.status != null) {
                    Flow.STOP
                }

                when (result.status!!) {
                    ServiceRegistry.Status.SERVICE_NOT_FOUND -> {
                        response.status = result.getStatus()
                        Flow.STOP
                    }
                    ServiceRegistry.Status.OK -> {
                        intermediateData.serviceInfo = result.getServiceInfo()

                        if (result.getServiceInfo().isActive()) {
                            Flow.CONTINUE
                        } else {
                            Flow.NO_WITHDRAWAL
                        }
                    }
                }

                Flow.CONTINUE
            }

    val checkTrialPeriod = router {
        // Checks whether service supports trial period
        payload ->
        if (payload.intermediateData.serviceInfo!!.isSupportTrialPeriod) {
            Flow.WITHDRAWAL
        } else {
            Flow.NO_WITHDRAWAL
        }
    }

    init {
        payload()
                .handleBy(loadUserProfile)
                .handleBy(loadServiceInfo)

        loadUserProfile
                .on(Flow.STOP).complete()
                .on(Flow.CONTINUE).mergeBy(loadServiceInfo)

        loadServiceInfo
                .on(Flow.CONTINUE).mergeBy(checkTrialPeriod)
                .on(Flow.STOP).complete()

        checkTrialPeriod
                .on(Flow.WITHDRAWAL).handleBy(withdrawMoney)
                .on(Flow.NO_WITHDRAWAL).handleBy(logActionToUserJournal)
                .on(Flow.NO_WITHDRAWAL).handleBy(webNotification)

        withdrawMoney
                .onAny()
                .handleBy(logTransaction)

        logTransaction
                .onAny()
                .handleBy(logActionToUserJournal)

        logActionToUserJournal
                .onAny().complete()

        coordinates()
                .payload(680, 60)
                .handler(withdrawMoney, 410, 440)
                .handler(webNotification, 889, 465)
                .handler(loadServiceInfo, 480, 120)
                .handler(logTransaction, 420, 650)
                .handler(logActionToUserJournal, 680, 820)
                .handler(loadUserProfile, 770, 120)
                .merger(withdrawMoney, 480, 550)
                .merger(checkTrialPeriod, 702, 363)
                .merger(loadServiceInfo, 640, 280)
                .merger(logTransaction, 530, 770)
                .merger(logActionToUserJournal, 760, 930)
                .merger(loadUserProfile, 830, 250)
                .complete(loadServiceInfo, 480, 310)
                .complete(logActionToUserJournal, 740, 1020)
                .complete(loadUserProfile, 920, 300)
    }
}
