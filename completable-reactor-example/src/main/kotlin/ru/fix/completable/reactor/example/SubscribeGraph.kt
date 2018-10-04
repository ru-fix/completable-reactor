package ru.fix.completable.reactor.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import ru.fix.completable.reactor.example.services.*
import ru.fix.completable.reactor.example.services.UserProfileManager.Status
import ru.fix.completable.reactor.graph.kotlin.Graph
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

    val loadUserProfile =
            handler {
                userProfile.loadUserProfileById(request.userId)

            }.withRoutingMerger {
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

    private fun logTransaction() =
            handler {
                txLog.logTransactioin(request.userId)
            }.withoutMerger()

    val logTransaction = logTransaction()

    val logTransaction2 = logTransaction()

    val logActionToUserJournal =
            handler {
                userJournal.logAction(
                        request.userId,
                        "Request type: ${javaClass.simpleName}")
            }.withoutMerger()


    val webNotification =
            handler {
                notifier.sendHttpPurchaseNotification(request.userId)

            }.withoutMerger()

    val notifyRemotePartner =
            suspendHandler {
                remotePartnerNotificator.notifyRemotePartner("subscribe ${request.userId} to ${request.serviceId}")

            }.withMerger {
                response.remoteServiceNotificationResult = it
            }

    val notifyRemotePartnerAboutWithdrawProblem =
            suspendHandler {
                remotePartnerNotificator.notifyRemotePartner(
                        "withdraw problem with user ${request.userId}, status ${response.status}"
                )
            }.withMerger {
                response.remoteServiceNotificationResult = it
            }

    val withdrawMoney =
            handler {
                bank.withdrawMoney(
                        intermediateData.userInfo,
                        intermediateData.serviceInfo)

            }.withRoutingMerger {
                //update new amount
                withdraw ->

                response.status = withdraw.status
                if (withdraw.status == Bank.Withdraw.Status.OK) {
                    response.newAmount = withdraw.newAmount
                    response.status = Bank.Withdraw.Status.OK
                }

                return@withRoutingMerger withdraw.status
            }

    val loadServiceInfo =
            handler {
                serviceRegistry.loadServiceInformation(request.serviceId)

            }.withRoutingMerger { result ->
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
                .on(Bank.Withdraw.Status.OK).handleBy(logTransaction)
                .onElse().handleBy(notifyRemotePartnerAboutWithdrawProblem)

        notifyRemotePartnerAboutWithdrawProblem
                .onAny().handleBy(logActionToUserJournal)

        logTransaction
                .onAny().handleBy(logActionToUserJournal)

        logTransaction2
                .onAny().handleBy(logActionToUserJournal)

        logActionToUserJournal
                .onAny().handleBy(bonusPurchaseSubgraph)

        bonusPurchaseSubgraph
                .onAny().complete()

        coordinates()
                .pd(556, 19)
                .vx(checkTrialPeriod, 711, 326)
                .vx(webNotification, 1115, 511)
                .vx(bonusPurchaseSubgraph, 698, 889, 866, 951)
                .vx(loadServiceInfo, 548, 122, 693, 252)
                .vx(loadUserProfile, 811, 118, 814, 189)
                .vx(logActionToUserJournal, 787, 749, 867, 828)
                .vx(logTransaction, 701, 590, 807, 677)
                .vx(logTransaction2, 962, 592, 1023, 661)
                .vx(notifyRemotePartner, 340, 460, 455, 520)
                .vx(notifyRemotePartnerAboutWithdrawProblem, 191, 618, 363, 766)
                .vx(withdrawMoney, 576, 477, 582, 557)
                .ct(bonusPurchaseSubgraph, 863, 1015)
                .ct(loadServiceInfo, 565, 281)
                .ct(loadUserProfile, 924, 257)
    }
}
