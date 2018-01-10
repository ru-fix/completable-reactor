package ru.fix.completable.reactor.runtime.internal.gl

import ru.fix.completable.reactor.api.ReactorGraphModel
import ru.fix.completable.reactor.runtime.ReactorGraph
import ru.fix.completable.reactor.runtime.gl.GlTransition
import ru.fix.completable.reactor.runtime.gl.Vertex

import java.util.ArrayList

class GlReactorGraph<Payload> : ReactorGraph<Payload> {

    var vertices: List<Vertex> = ArrayList()
    var payloadStartTansitions: List<Vertex> = ArrayList()

    override fun serialize(): ReactorGraphModel {
        throw UnsupportedOperationException("CR GL does not support serialization, it is deprecated.")
    }
}
