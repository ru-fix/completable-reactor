package ru.fix.completable.reactor.graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.fix.completable.reactor.graph.internal.InternalDslAccessor
import ru.fix.completable.reactor.graph.internal.RuntimeEmptyHandler
import ru.fix.completable.reactor.graph.internal.RuntimeRouterMerger

private const val VERTEX_NAME = "vertex"

class VertexTest {

    enum class Foo {
        BAR
    }

    @Test
    fun onElseAndOnAnyAreDefinedBoth_mustThrowException() {
        val onElseTransition = Vertex()
        val onAnyTransition = Vertex()
        val vertex = Vertex()

        InternalDslAccessor.vx(vertex).apply {
            name = VERTEX_NAME
            handler = RuntimeEmptyHandler()
            merger = RuntimeRouterMerger(object : Router<Any?> {
                override fun route(payload: Any?): Enum<*> {
                    return Foo.BAR
                }
            })
        }

        val ex = assertThrows<IllegalArgumentException> {
            vertex
                    .onElse().handleBy(onElseTransition)
                    .onAny().handleBy(onAnyTransition)
        }
        assertEquals(
                "Vertex $VERTEX_NAME is used two incompatible transitions onElse and onAny is same time.", ex.message)
    }

    @Test
    fun onAnyAndOnElseAreDefinedBoth_mustThrowException() {
        val onElseTransition = Vertex()
        val onAnyTransition = Vertex()
        val vertex = Vertex()

        InternalDslAccessor.vx(vertex).apply {
            name = VERTEX_NAME
            handler = RuntimeEmptyHandler()
            merger = RuntimeRouterMerger(object : Router<Any?> {
                override fun route(payload: Any?): Enum<*> {
                    return Foo.BAR
                }
            })
        }

        val ex = assertThrows<IllegalArgumentException> {
            vertex
                    .onAny().handleBy(onAnyTransition)
                    .onElse().handleBy(onElseTransition)
        }
        assertEquals("Vertex $VERTEX_NAME is used two incompatible transitions onElse and onAny is same time.", ex.message)
    }

    @Test
    fun onElse_withoutMergerAndRouter_mustThrowException() {
        val onElseTransition = Vertex()
        val vertex = Vertex()
        InternalDslAccessor.vx(vertex).apply { name = VERTEX_NAME }

        val ex = assertThrows<IllegalArgumentException> {
            vertex.onElse().handleBy(onElseTransition)
        }

        assertEquals(
                "Vertex $VERTEX_NAME is used as source of onElse() transition but does not have merger.",
                ex.message
        )
    }

    @Test
    fun onAny_withoutMergerAndRouter_mustThrowException() {
        val onElseTransition = Vertex()
        val vertex = Vertex()

        InternalDslAccessor.vx(vertex).apply { name = VERTEX_NAME }

        val ex = assertThrows<IllegalArgumentException> {
            vertex.onAny().handleBy(onElseTransition)
        }

        assertEquals(
                "Vertex $VERTEX_NAME is used as source of onAny() transition but does not have merger.",
                ex.message
        )
    }
}