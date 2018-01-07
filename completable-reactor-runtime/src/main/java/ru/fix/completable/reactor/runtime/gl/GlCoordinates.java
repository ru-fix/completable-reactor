package ru.fix.completable.reactor.runtime.gl;

public interface GlCoordinates {
    /**
     * Payload start point coordinates
     */
    GlCoordinates payload(int x, int y);

    GlCoordinates handler(Vertex vertex, int x, int y);

    GlCoordinates merger(Vertex vertex, int x, int y);

    GlCoordinates router(Vertex vertex, int x, int y);

    GlCoordinates complete(Vertex vertex, int x, int y);
}