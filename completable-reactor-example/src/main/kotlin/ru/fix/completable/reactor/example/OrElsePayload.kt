package ru.fix.completable.reactor.example

class OrElsePayload(val flow: Flow) {

    enum class Flow {
        FIRST, SECOND, THIRD, FOURTH
    }
}
