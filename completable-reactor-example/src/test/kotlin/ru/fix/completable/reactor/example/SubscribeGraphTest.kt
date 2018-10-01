package ru.fix.completable.reactor.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.fix.aggregating.profiler.AggregatingProfiler
import ru.fix.completable.reactor.example.services.*
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.util.concurrent.TimeUnit

/**
 * @author Kamil Asfandiyarov
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [SubscribeGraphTest.ServicesConfig::class, SubscribeGraph::class])
open class SubscribeGraphTest {

    @Configuration
    open class ServicesConfig {

        @Bean
        open fun bank() = Bank()

        @Bean
        open fun marketingService() = MarketingService()

        @Bean
        open fun notifier() = Notifier ()

        @Bean
        open fun serviceRegistry() = ServiceRegistry()

        @Bean
        open fun txLog() = TransactionLog()


        @Bean
        open fun userJournal() = UserJournal()

        @Bean
        open fun userProfile() = UserProfileManager()


        @Bean
        open fun remotePartnerNotificator() = RemotePartnerNotificator()

        @Bean
        open fun reactor(): CompletableReactor {
            val reactor = CompletableReactor(AggregatingProfiler())

            //For debug purpose to resolve blocked vertices that does not completed correctly
            reactor.setDebugProcessingVertexGraphState(true)

            return reactor
        }
    }

    @Autowired
    lateinit var reactor: CompletableReactor

    @BeforeEach
    fun before(){
        reactor.registerGraphSync(PurchasePayload::class.java){
            it.apply{
                response.status = Bank.Withdraw.Status.OK
            }
        }
    }


    @Test
    fun invalid_user_subscribes_on_invalid_service() {

        val payload = SubscribePayload(SubscribePayload.Request(
                userId = UserProfileManager.USER_ID_INVALID,
                serviceId = ServiceRegistry.SERVICE_ID_INVALID
        ))

        val result = reactor.submit(payload).resultFuture.get(5, TimeUnit.SECONDS)

        assertEquals(UserProfileManager.Status.USER_NOT_FOUND, result.response.status)

    }

    @Test
    fun john_subscribes_on_car_wash() {

        val payload = SubscribePayload(SubscribePayload.Request(
                userId = UserProfileManager.USER_ID_JOHN,
                serviceId = ServiceRegistry.SERVICE_ID_CAR_WASH
        ))

        val execution = reactor.submit(payload)

        val result = execution.resultFuture.get(5, TimeUnit.SECONDS)

        assertEquals(Bank.Withdraw.Status.OK, result.response.status)
    }

    @Test
    fun bob_wallet_not_found() {

        val payload = SubscribePayload(SubscribePayload.Request(
                userId = UserProfileManager.USER_WITH_NO_WALLET,
                serviceId = ServiceRegistry.SERVICE_ID_CAR_WASH
        ))

        val execution = reactor.submit(payload)

        val result = execution.resultFuture.get(5, TimeUnit.SECONDS)

        assertEquals(Bank.Withdraw.Status.WALLET_NOT_FOUND, result.response.status)
    }
}