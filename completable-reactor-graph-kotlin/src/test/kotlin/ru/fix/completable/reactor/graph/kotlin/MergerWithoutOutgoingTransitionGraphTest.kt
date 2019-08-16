package ru.fix.completable.reactor.graph.kotlin

import mu.KotlinLogging
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import ru.fix.aggregating.profiler.NoopProfiler
import ru.fix.completable.reactor.model.validation.GraphValidationException
import ru.fix.completable.reactor.model.validation.MergerShouldHaveOutgoingOrCompleteTransitionValidator
import ru.fix.completable.reactor.model.validation.Validator
import ru.fix.completable.reactor.runtime.CompletableReactor

private val log = KotlinLogging.logger {}

class MergerWithoutOutgoingTransitionGraphTest {
    class MergerWithoutOutgoingPayload

    class WithoutOutgoingTransitionGraph : Graph<MergerWithoutOutgoingPayload>() {

        val first = suspendHandler { /* empty vertex*/ }.withoutMerger()
        val second = suspendHandler { /* empty vertex*/ }.withoutMerger()
        val third = suspendHandler { /* empty vertex*/ }.withoutMerger()
        val fourth = suspendHandler { /* empty vertex*/ }.withoutMerger()


        init {
            payload().handleBy(first)
                    .handleBy(second)
                    .handleBy(third)
                    .handleBy(fourth)
            first.onAny().mergeBy(second)
            third.onAny().mergeBy(second)
            fourth.onAny().complete()
        }
    }

    @Test
    fun `merger without outgoing transition rise an exception`() {
        try {
            CompletableReactor(NoopProfiler()).registerGraph(WithoutOutgoingTransitionGraph())
            fail("Graph should rise an exception during registration")

        } catch (exc: GraphValidationException) {
            log.info { exc.message }

            MatcherAssert.assertThat(
                    exc.validatorClass,
                    equalTo<Class<out Validator>>(MergerShouldHaveOutgoingOrCompleteTransitionValidator::class.java))
        }
    }
}