package game_engine.controls.key_controls;

import game_engine.behaviors.IBehavior;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.input.KeyCode;

public class KeyControlMap {
	
	private Map<KeyCode, IBehavior> behaviors;
	
	public KeyControlMap() {
		behaviors = new HashMap<>();
	}
	
	public void addBehavior(KeyCode key, IBehavior value) {
		behaviors.put(key, value);
		System.out.println(behaviors);
	}
	
	public void perform(KeyCode key) {
		if (behaviors.containsKey(key)) {
			behaviors.get(key).perform();
		}
	}
	
	public void perform(KeyCode key, Set<KeyCode> pressedKeys) {
		for (KeyCode input : pressedKeys) {
			perform(input);
		}
	}
}