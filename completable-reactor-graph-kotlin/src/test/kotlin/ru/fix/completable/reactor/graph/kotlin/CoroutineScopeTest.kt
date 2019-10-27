package ru.fix.completable.reactor.graph.kotlin

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isA
import kotlinx.coroutines.future.future
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class CoroutineScopeTest {

    @Test
    fun `exception in handler`() {

        class UserException: RuntimeException()

        val future = Graph.defaultCoroutineScope.future {
            throw UserException()
        }

        val exc = assertThrows<Exception> {
            future.get(5, TimeUnit.SECONDS)
        }
        assertThat(exc, isA<TimeoutException>().not())
        assertNotNull(exc.cause)
        assertThat(exc.cause!!, isA<UserException>())
    }
}