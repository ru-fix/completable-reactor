package ru.fix.completable.reactor.runtime.gl;

final class GlCoordinatesImpl implements GlCoordinates {
    @Override
    public GlCoordinates payload(int x, int y) {
        return this;
    }

    @Override
    public GlCoordinates handler(Vertex vertex, int x, int y) {
        return this;
    }

    @Override
    public GlCoordinates merger(Vertex vertex, int x, int y) {
        return this;
    }

    @Override
    public GlCoordinates router(Vertex vertex, int x, int y) {
        return this;
    }

    @Override
    public GlCoordinates complete(Vertex vertex, int x, int y) {
        return this;
    }
}
