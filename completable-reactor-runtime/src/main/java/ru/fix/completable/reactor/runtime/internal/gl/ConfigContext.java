package ru.fix.completable.reactor.runtime.internal.gl;

import ru.fix.completable.reactor.runtime.gl.Vertex;

public class ConfigContext {
    private static final ThreadLocal<ConfigContext> configContext = new ThreadLocal<>();

    private Vertex vertex;

    enum State {
        INSIDE_VERTEX_INIT
    }


    public static ConfigContext get(){
        ConfigContext context = ConfigContext.configContext.get();
        if(context == null){
            context = new ConfigContext();
            configContext.set(context);
        }
        return context;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
}
