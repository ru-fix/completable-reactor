package ru.fix.completable.reactor.runtime.internal.gl

import ru.fix.completable.reactor.api.ReactorGraphModel
import ru.fix.completable.reactor.runtime.ReactorGraph
import ru.fix.completable.reactor.runtime.gl.Vertex
import java.util.*


class GlReactorGraph<Payload> : ReactorGraph<Payload> {

    var vertices: List<Vertex<Payload>> = ArrayList()
    var start: MutableList<Vertex<Payload>> = ArrayList()

    override fun serialize(): ReactorGraphModel {
        throw UnsupportedOperationException("CR GL does not support serialization, it is deprecated.")
    }
}
