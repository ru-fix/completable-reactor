package ru.fix.cpapsm.completable.reactor.graph.viewer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Kamil Asfandiyarov
 */
@RequiredArgsConstructor
public class CoordinateTranslator {

    @NonNull
    Double worldSize;

    Double translateX(Integer x) {
        return worldSize / 2 + x;
    }

    Double translateX(Double x) {
        return worldSize / 2 + x;
    }

    Double translateY(Integer y) {
        return worldSize / 2 + y;
    }

    Double translateY(Double y) {
        return worldSize / 2 + y;
    }

    int reverseTranslateX(Double x){
        return (int)(x - worldSize / 2);
    }

    int reverseTranslateY(Double y){
        return (int)(y - worldSize / 2);
    }
}