package game_engine.controls;

import game_engine.controls.key_controls.KeyControlMap;
import game_engine.controls.key_controls.PressedKeyControlMap;
import game_engine.controls.key_controls.ReleasedKeyControlMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ControlScheme {
	
	private KeyControlMap onPressed, onReleased, whilePressed;
	
	private Map<EventType<KeyEvent>, KeyControlMap> controlsMaps;
	
	private Set<KeyCode> pressedKeys;
	
	public ControlScheme() {
		controlsMaps = new HashMap<>();
		pressedKeys = new HashSet<>();
	}
	
	public void addPressedControlMap(PressedKeyControlMap pressed) {
		onPressed = pressed;
		controlsMaps.put(KeyEvent.KEY_PRESSED, onPressed);
		controlsMaps.put(KeyEvent.KEY_TYPED, onPressed);
	}
	
	public void addReleasedControlMap(ReleasedKeyControlMap released) {
		onReleased = released;
		controlsMaps.put(KeyEvent.KEY_RELEASED, onReleased);
	}
	
	public void addControlMap(KeyControlMap frame) {
		whilePressed = frame;
	}
	
	public void update() {
		whilePressed.perform(null, pressedKeys);
	}
	
	public void handleInput(InputEvent input) {
		handleKeyInput(input);
		handleMouseInput(input);
	}
	
	private void handleKeyInput(InputEvent input) {
		try {
			KeyEvent key = (KeyEvent) input;
			controlsMaps.get(key.getEventType()).perform(key.getCode(), pressedKeys);
		}
		catch(Exception e) {
			// do nothing
		}
	}
	
	private void handleMouseInput(InputEvent input) {
		try {
			MouseEvent mouse = (MouseEvent) input;
			System.out.println(mouse.getEventType());
		}
		catch(Exception e) {
			// do nothing
		}
	}
}