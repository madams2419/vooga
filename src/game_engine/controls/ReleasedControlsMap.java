package game_engine.controls;

import java.util.List;

import javafx.scene.input.KeyCode;

public class ReleasedControlsMap extends ControlsMap {

    public void handleInput(KeyCode input, List<KeyCode> pressedKeys) {
	if (pressedKeys.contains(input)) {
	    executeBehaviors(input);
	}
	pressedKeys.remove(input);
    }
}