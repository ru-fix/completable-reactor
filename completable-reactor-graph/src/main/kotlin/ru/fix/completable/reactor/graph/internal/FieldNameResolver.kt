package ru.fix.completable.reactor.graph.internal

import mu.KotlinLogging
import java.lang.reflect.Field
import java.util.*

private val log = KotlinLogging.logger {}

class FieldNameResolver(
        private val instance: Any,
        private val fieldTypes: List<Class<out Any>>) {

    private var graphConfigFields = IdentityHashMap<Any, String>()

    private fun scanFields(): IdentityHashMap<Any, String> {

        val configFields = IdentityHashMap<Any, String>()

        var clazz: Class<*>? = instance.javaClass
        while (clazz != null) {

            Arrays.stream<Field>(clazz.declaredFields)
                    .filter { field ->
                        fieldTypes.any { fieldType -> fieldType.isAssignableFrom(field.type) }
                    }
                    .forEach { field ->
                        try {
                            if (!field.isAccessible) {
                                field.isAccessible = true
                            }
                            val value = field.get(instance)
                            configFields.put(value, field.name)
                        } catch (exc: Exception) {
                            log.warn(exc) {
                                "Failed to access field $field of class $clazz in object $instance"
                            }
                        }
                    }
            clazz = clazz.superclass
        }

        return configFields
    }

    fun resolveFieldName(configField: Any): String {

        if (graphConfigFields.containsKey(configField)) {
            return graphConfigFields[configField]!!

        } else {
            graphConfigFields = scanFields()

            return graphConfigFields[configField] ?: throw IllegalArgumentException("""
                You are probably using local variable instead of class field in GraphConfig builder API.
                You should convert Vertex local variable to config field.
                Failed to resolve field within class ${instance.javaClass} that reference given object $configField.
                """.trimIndent())
        }
    }
}