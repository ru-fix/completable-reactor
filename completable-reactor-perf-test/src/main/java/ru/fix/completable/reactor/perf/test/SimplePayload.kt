package ru.fix.completable.reactor.perf.test

class SimplePayload (val request: Request){
    val intermedium = Intermedium()
    val response = Response()


    class Request(val arg: String)
    class Intermedium() {
        lateinit var smppTransactId: String
        lateinit var serviceInfo: String
        lateinit var smppParameters: String
        lateinit var smsMsisdnHistory : String
        lateinit var maxPushTimeOut : String
    }
    class Response{
        lateinit var result: String
    }

}