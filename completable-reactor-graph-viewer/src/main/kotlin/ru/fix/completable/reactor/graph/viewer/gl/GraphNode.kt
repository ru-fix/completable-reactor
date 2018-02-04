package ru.fix.completable.reactor.graph.viewer.gl

interface GraphNode {
    val graphChildren: List<GraphNode>

}