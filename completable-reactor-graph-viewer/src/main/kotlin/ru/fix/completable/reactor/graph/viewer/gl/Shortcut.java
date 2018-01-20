package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Value;
import lombok.val;

import java.util.function.Predicate;

/**
 * Created by swarmshine on 23.02.2017.
 */
@Value
public class Shortcut {
    boolean isControl;
    KeyCode keyCode;

    public String getTitle() {
        val result = new StringBuilder();
        if (isControl) {
            result.append("Control+");
        }
        result.append(keyCode);
        return result.toString();
    }

    public Predicate<KeyEvent> getPredicate() {
        return keyEvent -> isControl == keyEvent.isControlDown() && keyEvent.getCode() == keyCode;
    }
}
