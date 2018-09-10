package ru.fix.completable.reactor.example

class OrElsePayload(val flow: Flow) {
    val response = Response()

    class Response {
        var result: Flow ? = null
    }

    enum class Flow {
        FIRST, SECOND, THIRD, FOURTH
    }
}
