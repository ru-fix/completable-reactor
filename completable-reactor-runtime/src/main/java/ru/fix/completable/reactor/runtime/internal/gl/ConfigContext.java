package ru.fix.completable.reactor.runtime.internal.gl;

import ru.fix.completable.reactor.runtime.gl.Vertex;

public class ConfigContext {
    private static final ThreadLocal<ConfigContext> configContext = new ThreadLocal<>();

    private Vertex vertex;

    enum State {
        VERTEX_INITIALIZATION
    }

    private State state;


    public static ConfigContext get(){
        ConfigContext context = ConfigContext.configContext.get();
        if(context == null){
            context = new ConfigContext();
            configContext.set(context);
        }
        return context;
    }

    public void vertexInitialization(Vertex vertex) {
        this.vertex = vertex;
        this.state = State.VERTEX_INITIALIZATION;
    }
}
