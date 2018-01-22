package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.function.Predicate;

/**
 * Created by swarmshine on 23.02.2017.
 */
class Shortcut(
        var isControl: Boolean,
        var keyCode: KeyCode
) {

    fun getTitle(): String {
        val result = StringBuilder()
        if (isControl) {
            result.append("Control+")
        }
        result.append(keyCode)
        return result.toString()
    }

    fun getPredicate(): Predicate<KeyEvent> {
        return Predicate { keyEvent -> isControl == keyEvent.isControlDown && keyEvent.code == keyCode }
    }
}

enum class ShortcutType {
    GOTO_GRAPH_CONFIG
}
