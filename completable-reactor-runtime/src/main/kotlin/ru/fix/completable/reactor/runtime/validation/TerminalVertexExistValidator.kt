package ru.fix.completable.reactor.runtime.validation

import ru.fix.completable.reactor.runtime.internal.gl.GlReactorGraph

class TerminalVertexExistValidator: Validator {

    override fun validate(graph: GlReactorGraph<*>) {

        graph.vertices
                .asIterable()
                .any { it.tran }


        if (graph.getMergePoints().stream()
                        .flatMap({ mergePoint -> mergePoint.getTransitions().stream() })
                        .noneMatch(Predicate<ReactorGraphModel.Transition> { it.isComplete() })) {

            throw ValidationException("Graph should contain at least one terminal vertex." + " Add complete step to at least one merge point.")
        }
    }

    //TODO: fix injector
}