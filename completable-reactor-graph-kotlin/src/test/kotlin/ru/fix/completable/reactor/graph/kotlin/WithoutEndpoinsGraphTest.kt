package ru.fix.completable.reactor.graph.kotlin

import mu.KotlinLogging
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import ru.fix.aggregating.profiler.NoopProfiler
import ru.fix.completable.reactor.model.validation.AtLeastOneEndPointExistValidator
import ru.fix.completable.reactor.model.validation.GraphValidationException
import ru.fix.completable.reactor.model.validation.Validator
import ru.fix.completable.reactor.runtime.CompletableReactor

private val log = KotlinLogging.logger {}

class WithoutEndpoinsGraphTest {
    class WithoutEndpoinsPayload

    class WithoutOutgoingTransitionGraph : Graph<WithoutEndpoinsPayload>() {

        val first = suspendHandler { /* empty vertex*/ }.withoutMerger()
        val second = suspendHandler { /* empty vertex*/ }.withoutMerger()
        val third = suspendHandler { /* empty vertex*/ }.withoutMerger()

        init {
            payload().handleBy(first)
                    .handleBy(second)
            first.onAny().mergeBy(second)
            second.onAny().handleBy(third)
        }
    }

    @Test
    fun `graph without endpoints rise an exception`() {
        try {
            CompletableReactor(NoopProfiler()).registerGraph(WithoutOutgoingTransitionGraph())
            fail("Graph should rise an exception during registration")

        } catch (exc: GraphValidationException) {
            log.info { exc.message }
            MatcherAssert.assertThat(
                    exc.validatorClass,
                    Matchers.equalTo<Class<out Validator>>(AtLeastOneEndPointExistValidator::class.java))
        }
    }
}