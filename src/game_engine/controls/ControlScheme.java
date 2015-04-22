package game_engine.controls;

import game_engine.controls.key_controls.KeyControlsMap;
import game_engine.controls.key_controls.PressedKeyControlsMap;
import game_engine.controls.key_controls.ReleasedKeyControlsMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ControlScheme {
	
	private KeyControlsMap onPressed, onReleased, whilePressed;
	
	private Map<EventType<KeyEvent>, KeyControlsMap> controlsMaps;
	
	private Set<KeyCode> pressedKeys;
	
	public ControlScheme(PressedKeyControlsMap pressed, ReleasedKeyControlsMap released, KeyControlsMap frame) {
		onPressed = pressed;
		onReleased = released;
		whilePressed = frame;
		controlsMaps = new HashMap<>();
		controlsMaps.put(KeyEvent.KEY_PRESSED, onPressed);
		controlsMaps.put(KeyEvent.KEY_TYPED, onPressed);
		controlsMaps.put(KeyEvent.KEY_RELEASED, onReleased);
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