package ru.fix.completable.reactor.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import ru.fix.completable.reactor.example.services.*
import ru.fix.completable.reactor.example.services.UserProfileManager.Status
import ru.fix.completable.reactor.graph.kotlin.*
import ru.fix.completable.reactor.runtime.CompletableReactor
import javax.annotation.PostConstruct

/**
 * # Subscribe user to service and regularly withdraw money
 *
 * Created by Kamil Asfandiyarov
 */
@Configuration
open class SubscribeGraph : Graph<SubscribePayload>() {

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

                if (response.status != null) {
                    Flow.STOP
                }

                when (it.status) {
                    Status.USER_NOT_FOUND, Status.USER_IS_BLOCKED -> {
                        response.status = it.status
                        Flow.STOP
                    }

                    Status.OK -> {
                        intermediateData.userInfo = it.userProfile
                        Flow.CONTINUE
                    }

                    else -> throw IllegalArgumentException("result.status = " + it.status)
                }
            }

    val logTransaction =
            handler {
                txLog.logTransactioin(request.userId)

            }.withMerger {
                Flow.CONTINUE
            }


    val logActionToUserJournal =
            handler {
                userJournal.logAction(
                        request.userId,
                        "Request type: ${javaClass.simpleName}")
            }.withMerger {
                //ignore result of logging
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
                        response.status = result.status
                        Flow.STOP
                    }
                    ServiceRegistry.Status.OK -> {
                        intermediateData.serviceInfo = result.serviceInfo

                        if (result.serviceInfo.isActive) {
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
        if (intermediateData.serviceInfo.isSupportTrialPeriod) {
            Flow.WITHDRAWAL
        } else {
            Flow.NO_WITHDRAWAL
        }
    }

    val bonusPurchaseSubgraph =
            subgraph(PurchasePayload::class) {
                PurchasePayload().apply {
                    request
                            .setServiceId(107L)
                            .setUserId(request.userId)
                }

            }.withMerger {
                response.bonusPurchaseStatus = it.response.status
                Flow.CONTINUE
            }

    init {
        payload()
                .handleBy(loadUserProfile)
                .handleBy(loadServiceInfo)

        loadUserProfile
                .on(Flow.STOP).complete()
                .on(Flow.CONTINUE).mergeBy(loadServiceInfo)

        loadServiceInfo
                .on(Flow.CONTINUE).handleBy(checkTrialPeriod)
                .on(Flow.STOP).complete()

        checkTrialPeriod
                .on(Flow.WITHDRAWAL).handleBy(withdrawMoney)
                .on(Flow.NO_WITHDRAWAL).handleBy(logActionToUserJournal)
                .on(Flow.NO_WITHDRAWAL).handleBy(webNotification)

        withdrawMoney
                .onAny().handleBy(logTransaction)

        logTransaction
                .onAny().handleBy(logActionToUserJournal)
                .onAny().handleBy(bonusPurchaseSubgraph)


        bonusPurchaseSubgraph
                .onAny().mergeBy(logActionToUserJournal)

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
