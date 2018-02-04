package ru.fix.crudility.engine

import mu.KotlinLogging
import java.io.Closeable
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

private val log = KotlinLogging.logger {}

class FileWatcher(private val filePath: Path,
                  private val callListenerAfterConstruction: Boolean = true,
                  private val listener: (Path) -> Any) : Closeable {

    private val watchThread = Executors.newSingleThreadExecutor { Thread(it, javaClass.canonicalName) }

    private val shutdownFlag = AtomicBoolean(false)

    fun start(): CompletableFuture<Boolean> {

        val feature = CompletableFuture<Boolean>()

        watchThread.submit {
            try {
                FileSystems.getDefault().newWatchService().use {

                    log.info { "Watching for $filePath" }

                    filePath.parent.register(it, StandardWatchEventKinds.ENTRY_MODIFY)

                    if (callListenerAfterConstruction) {
                        listener(filePath)
                    }

                    feature.complete(true)

                    while (!shutdownFlag.get() || Thread.currentThread().isInterrupted) {

                        val watchKey = it.poll(5, TimeUnit.SECONDS) ?: continue

                        for (event in watchKey.pollEvents()) {

                            val entryFileNamePath = event.context() as Path

                            if (entryFileNamePath.fileName == filePath.fileName) {

                                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {

                                    log.trace { "Detected file modification: $filePath" }
                                    listener.invoke(filePath)
                                }
                            }

                        }

                        if (!watchKey.reset()) {
                            log.warn { "maybe directory does not exist" }
                            break
                        }
                    }
                }
            } catch (exc: Exception) {
                log.error(exc) { "Failed to watch for $filePath" }
                feature.completeExceptionally(exc)
            } finally {
                feature.complete(false)
            }
        }

        return feature
    }

    override fun close() {
        shutdownFlag.set(true)

        watchThread.shutdown()
        if (!watchThread.awaitTermination(5, TimeUnit.SECONDS)) {
            watchThread.shutdownNow()
        }
    }
}