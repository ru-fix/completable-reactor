package ru.fix.completable.reactor.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import ru.fix.completable.reactor.api.Reactored
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

    @Autowired
    lateinit var remotePartnerNotificator: RemotePartnerNotificator


    @PostConstruct
    fun initialize() {
        completableReactor.registerGraphIfAbsent(this)
    }

    enum class Flow {
        /**
         * Continue processing
         */
        CONTINUE,
        /**
         * Stop processing
         */
        STOP,
        /**
         * Withdraw money required
         */
        WITHDRAWAL,
        /**
         * There is extra service available to user as a bonus
         */
        BONUS_EXIST,
        /**
         * There is no bonus available for given purchase.
         */
        NO_BONUS,
        /**
         * No withdrawal required
         */
        NO_WITHDRAWAL
    }


    val loadUserProfile =
            handler {
                userProfile.loadUserProfileById(request.userId)

            }.withMerger {
                //# check profile status
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

    val logTransaction2 = logTransaction.clone()

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

    val notifyRemotePartner =
            suspendHandler {
                remotePartnerNotificator.notifyRemotePartner("subscribe ${request.userId} to ${request.serviceId}")

            }.withMerger {
                response.remoteServiceNotificationResult = it
                Flow.CONTINUE
            }

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
            Flow.NO_WITHDRAWAL
        } else {
            Flow.WITHDRAWAL
        }
    }

    val bonusPurchaseSubgraph =
            subgraph(PurchasePayload::class) {
                //# Make bonus purchase
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
                // Withdraw money required
                .on(Flow.WITHDRAWAL).handleBy(withdrawMoney)
                // Withdraw money required
                .on(Flow.WITHDRAWAL).handleBy(notifyRemotePartner)

                // No withdrawal required
                .on(Flow.NO_WITHDRAWAL).handleBy(webNotification)
                // No withdrawal required
                .on(Flow.NO_WITHDRAWAL).handleBy(logTransaction2)

        notifyRemotePartner
                .onAny().mergeBy(withdrawMoney)

        withdrawMoney
                .onAny().handleBy(logTransaction)

        logTransaction
                .onAny().handleBy(logActionToUserJournal)

        logTransaction2
                .onAny().handleBy(logActionToUserJournal)

        logActionToUserJournal
                .onAny().handleBy(bonusPurchaseSubgraph)

        bonusPurchaseSubgraph
                .onAny().complete()

        coordinates()
                .payload(554, 59)
                .handler(loadServiceInfo, 548, 122)
                .handler(loadUserProfile, 811, 118)
                .handler(logActionToUserJournal, 787, 749)
                .handler(logTransaction, 536, 622)
                .handler(logTransaction2, 962, 592)
                .handler(notifyRemotePartner, 340, 460)
                .handler(webNotification, 1115, 511)
                .handler(withdrawMoney, 576, 477)
                .subgraph(bonusPurchaseSubgraph, 698, 889)
                .router(checkTrialPeriod, 711, 326)
                .merger(bonusPurchaseSubgraph, 866, 951)
                .merger(loadServiceInfo, 693, 252)
                .merger(loadUserProfile, 814, 189)
                .merger(logActionToUserJournal, 867, 828)
                .merger(logTransaction, 585, 697)
                .merger(logTransaction2, 1023, 661)
                .merger(notifyRemotePartner, 455, 520)
                .merger(withdrawMoney, 582, 557)
                .complete(bonusPurchaseSubgraph, 863, 1015)
                .complete(loadServiceInfo, 565, 281)
                .complete(loadUserProfile, 924, 257);

    }
}
