package ru.fix.completable.reactor.runtime.internal.gl;

import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.gl.Vertex;

import java.util.ArrayList;
import java.util.List;

public class GlReactorGraph<Payload> implements ReactorGraph<Payload> {

    List<Vertex> vertices = new ArrayList<>();







    @Override
    public ReactorGraphModel serialize() {
        throw new UnsupportedOperationException("CR GL does not support serialization, it is deprecated.");
    }

}
