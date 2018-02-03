package ru.fix.completable.reactor.graph.internal

import mu.KotlinLogging
import java.lang.reflect.Field
import java.util.*

private val log = KotlinLogging.logger {}

class FieldNameResolver(
        private val graphConfig: Any,
        private val fieldTypes: List<Class<out Any>>) {

    private lateinit var graphConfigFields: IdentityHashMap<Any, String>

    fun resolveFieldName(configField: Any): String {

        if (!this::graphConfigFields.isInitialized) {
            graphConfigFields = IdentityHashMap()

            var clazz: Class<*>? = graphConfig.javaClass
            while (clazz != null) {

                Arrays.stream<Field>(clazz.declaredFields)
                        .filter { field ->
                            fieldTypes.stream().anyMatch(
                                    { fieldType -> fieldType.isAssignableFrom(field.type) })
                        }
                        .forEach { field ->
                            try {
                                if (!field.isAccessible()) {
                                    field.isAccessible = true
                                }
                                val value = field.get(graphConfig)
                                graphConfigFields.put(value, field.name)
                            } catch (exc: Exception) {
                                log.warn(exc) {
                                    "Failed to access field $field of class ${configField.javaClass.name}" +
                                            " in object $configField"
                                }
                            }
                        }
                clazz = clazz.superclass
            }
        }

        return graphConfigFields[configField] ?: throw IllegalArgumentException("""
            You are probably using local variable instead of class field in GraphConfig builder API.
            You should convert Vertex local variable to config field.
            Failed to resolve field within class ${graphConfig.javaClass} that reference given object $configField.
            """.trimIndent())
    }
}