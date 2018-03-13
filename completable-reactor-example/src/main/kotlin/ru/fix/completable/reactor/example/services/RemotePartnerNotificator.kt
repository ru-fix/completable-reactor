package ru.fix.completable.reactor.example.services

import kotlinx.coroutines.experimental.delay
import mu.KotlinLogging
import java.util.concurrent.TimeUnit.*

private val log = KotlinLogging.logger {}

class RemotePartnerNotificator {

    suspend fun notifyRemotePartner(notification: String): Boolean {
        log.info { "start sending notification: `$notification`" }

        delay(1, MILLISECONDS)

        log.info { "notification `$notification` was successfully sent to remote partner" }
        return true
    }
}