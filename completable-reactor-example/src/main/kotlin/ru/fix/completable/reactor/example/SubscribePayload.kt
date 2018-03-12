package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.example.services.ServiceInfo;
import ru.fix.completable.reactor.example.services.UserProfile;
import ru.fix.completable.reactor.example.services.AccountInfo;
import java.math.BigDecimal

/**
 * Created by swarmshine on 16.10.2016.
 */
data class SubscribePayload(val request: Request) {

    val intermediateData = IntermediateData()
    val response = Response()

    data class Request(
            val userId: Long,
            val serviceId: Long
    )

    data class Response(
            var status: Enum<*>? = null,
            var moneyWithdrawed: Int? = null,
            var newAmount: BigDecimal? = null,
            //TODO replace Enum with internal status enum
            var bonusPurchaseStatus: Enum<*>? = null
    )

    class IntermediateData {
        lateinit var serviceInfo: ServiceInfo
        lateinit var accountInfo: AccountInfo
        lateinit var userInfo: UserProfile
    }
}
