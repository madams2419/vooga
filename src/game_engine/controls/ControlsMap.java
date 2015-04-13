package game_engine.controls;

import game_engine.behaviors.IBehavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.KeyCode;

public class ControlsMap {

    private Map<KeyCode, IBehavior> controls;
    
    public ControlsMap() {
	controls = new HashMap<>();
    }
    
    public void executeBehaviors(KeyCode code) {
	if (controls.containsKey(code)) {
	    controls.get(code).perform();
	}
    }
    
    public void addBehavior(KeyCode code, IBehavior behavior) {controls.put(code, behavior);
    }
    
    public void handleInput(KeyCode input, List<KeyCode> pressedKeys) {
	// Empty, sub-classes override for their own functionality
    }
    
    public void update(double time, List<KeyCode> pressedKeys) {
	// TODO: implement this
    }
}