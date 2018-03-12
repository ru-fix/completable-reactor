package ru.fix.completable.reactor.example

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import ru.fix.commons.profiler.impl.SimpleProfiler
import ru.fix.completable.reactor.example.services.*
import ru.fix.completable.reactor.runtime.CompletableReactor

import java.util.concurrent.TimeUnit

import org.junit.Assert.assertEquals
import org.junit.Before
import ru.fix.completable.reactor.runtime.DependencyInjector

/**
 * @author Kamil Asfandiyarov
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = [SubscribeGraphTest.ServicesConfig::class, SubscribeGraph::class])
open class SubscribeGraphTest {

    @Configuration
    open class ServicesConfig {

        @Autowired
        lateinit var applicationContext: ApplicationContext

        @Bean
        open fun bank(): Bank {
            return Bank()
        }

        @Bean
        open fun marketingService(): MarketingService {
            return MarketingService()
        }

        @Bean
        open fun notifier(): Notifier {
            return Notifier()
        }

        @Bean
        open fun serviceRegistry(): ServiceRegistry {
            return ServiceRegistry()
        }

        @Bean
        open fun txLog(): TransactionLog {
            return TransactionLog()
        }

        @Bean
        open fun userJournal(): UserJournal {
            return UserJournal()
        }

        @Bean
        open fun userProfile(): UserProfileManager {
            return UserProfileManager()
        }

        @Bean
        open fun reactor(): CompletableReactor {
            val reactor = CompletableReactor(SimpleProfiler())

            reactor.registerDependecnyInjector(object : DependencyInjector {
                override fun <T : Any?> resolve(name: String?, type: Class<T>?) =
                        applicationContext.getBean(name, type)

            })

            //For debug purpose to resolve blocked vertices that does not completed correctly
            reactor.setDebugProcessingVertexGraphState(true)

            return reactor
        }
    }

    @Autowired
    lateinit var reactor: CompletableReactor

    @Before
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
}