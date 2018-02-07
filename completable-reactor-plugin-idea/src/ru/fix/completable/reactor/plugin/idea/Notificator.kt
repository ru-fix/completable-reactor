package ru.fix.completable.reactor.plugin.idea

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import java.io.PrintWriter
import java.io.StringWriter

class Notificator {
    companion object {
        private const val NOTIFICATION_GROUP_ID = "ru.fix.completable.reactor"
    }

    fun info(msg: String) = notify(msg, NotificationType.INFORMATION)
    fun warn(msg: String) = notify(msg, NotificationType.WARNING)
    fun error(msg: String) = notify(msg, NotificationType.ERROR)
    fun error(msg: String, exc: Exception) = error(
            exc.run {
                StringWriter().let {
                    printStackTrace(PrintWriter(it))
                    "$msg\n$it"
                }
            })

    fun notify(msg: String, notificationType: NotificationType) {
        val notification = Notification(
                NOTIFICATION_GROUP_ID,
                "Reactor graph",
                msg,
                notificationType)
        Notifications.Bus.notify(notification)
    }
}