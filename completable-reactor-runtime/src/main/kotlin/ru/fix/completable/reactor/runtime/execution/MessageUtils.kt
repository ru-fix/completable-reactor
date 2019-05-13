package ru.fix.completable.reactor.runtime.execution

class MessageUtils {

    companion object {

        fun formatErrorMessage(message: String, throwable: Throwable): String {

            val rootCause = rootCause(throwable)

            return "[Root cause: ${rootCause.javaClass.simpleName} - ${rootCause.message}] $message"
        }


        private fun rootCause(t: Throwable): Throwable {
            var current = t
            var cause: Throwable? = null

            while (current !== cause) {
                cause = current
                val nextCause = current.cause
                if (nextCause !== null) {
                    current = nextCause
                }
            }

            return cause
        }

    }
}