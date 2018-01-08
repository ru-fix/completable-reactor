package ru.fix.completable.reactor.runtime.gl;

import ru.fix.completable.reactor.runtime.internal.gl.ConfigContext;

public class Vertex {

    {
        ConfigContext.get().setVertex(this);
    }

    public TransitionBuilder on(Enum<?>... mergeStatuses){
        throw new UnsupportedOperationException();
    }

    public TransitionBuilder onAny(){
        throw new UnsupportedOperationException();
    }

}
