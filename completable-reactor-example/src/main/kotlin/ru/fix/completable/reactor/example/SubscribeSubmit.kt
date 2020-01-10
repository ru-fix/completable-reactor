package ru.fix.completable.reactor.example;

import ru.fix.completable.reactor.graph.Submit
import java.math.BigDecimal

/**
 * Created by swarmshine on 16.10.2016.
 */
interface SubscribeSubmit: Submit<SubscribeSubmit.Request, SubscribeSubmit.Response> {
    data class Request(
            val userId: Long,
            val serviceId: Long
    )

    data class Response(
            var status: Enum<*>? = null,
            var moneyWithdrawed: Int? = null,
            var newAmount: BigDecimal? = null,
            //TODO replace Enum with internal status enum
            var bonusPurchaseStatus: Enum<*>? = null,
            var remoteServiceNotificationResult: Boolean = false
    )
}
