package game_engine.controls;

import game_engine.Behavior;

import java.util.*;

import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controls {
    
    private Map<EventType<KeyEvent>, ControlsMap> eventType;
    
    private PressedControlsMap pressedControls;
    private ReleasedControlsMap releasedControls;
    private ControlsMap whilePressedControls;
    
    private List<KeyCode> pressedKeys;
    
    public Controls() {
	pressedKeys = new ArrayList<>();
	
	pressedControls = new PressedControlsMap();
	releasedControls = new ReleasedControlsMap();
	whilePressedControls = new ControlsMap();
	
	eventType = new HashMap<>();
	eventType.put(KeyEvent.KEY_PRESSED, pressedControls);
	eventType.put(KeyEvent.KEY_RELEASED, releasedControls);
    }
    
    public void handleKeyEvent(KeyEvent input) {
	ControlsMap controlSet = eventType.get(input.getEventType());
	controlSet.handleInput(input.getCode(), pressedKeys);
    }
    
    public void update(double time) {
	whilePressedControls.update(time, pressedKeys);
    }
    
    public void addControl(KeyCode code, EventType<KeyEvent> type, List<Behavior> behaviors, List<String[]> params) {
	ControlsMap controls = eventType.get(type);
	for (int i = 0; i < behaviors.size(); i++) {
	    controls.addBehavior(code, behaviors.get(i), params.get(i));
	}
    }
}