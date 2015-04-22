package game_engine.controls.key_controls;

import java.util.Set;

import javafx.scene.input.KeyCode;

public class ReleasedKeyControlsMap extends KeyControlsMap {
	
	public void perform(KeyCode key, Set<KeyCode> pressedKeys) {
		if (pressedKeys.contains(key)) {
			super.perform(key);
		}
		pressedKeys.remove(key);
	}
}