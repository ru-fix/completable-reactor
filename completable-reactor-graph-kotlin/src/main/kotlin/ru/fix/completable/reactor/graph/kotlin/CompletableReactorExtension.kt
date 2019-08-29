package ru.fix.completable.reactor.graph.kotlin

import ru.fix.completable.reactor.graph.Submit
import ru.fix.completable.reactor.runtime.CompletableReactor
import kotlin.reflect.KClass

inline fun <reified Submit : ru.fix.completable.reactor.graph.Submit<*, *>> CompletableReactor.graph(): Submit =
        this.graph(Submit::class.java)

fun <Request, Response> CompletableReactor.registerGraphImplementation(
        submitType: KClass<out Submit<Request, Response>>,
        implementation: (Request) -> Response) = this.registerGraphImplementation(submitType.java, implementation)
