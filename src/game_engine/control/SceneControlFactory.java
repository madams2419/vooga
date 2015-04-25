	package game_engine.control;

import java.util.*;

import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SceneControlFactory {
	private SceneControlManager myControlManager;
	private final String MOUSE_EVENT = "javafx.scene.input.MouseEvent";
	private final String KEY_EVENT = "javafx.scene.input.KeyEvent";
	
	public SceneControlFactory(SceneControlManager controlManager){
		myControlManager = controlManager;
	}
	
	public SceneControl getControlType(int mouseActiveIndex, int keyActiveIndex, InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return myControlManager.getActiveMouseControl(mouseActiveIndex);
		else
			return myControlManager.getActiveKeyControl(keyActiveIndex);
	}
	
	public InputEvent getEventType(InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return (MouseEvent) event;
		else
			return (KeyEvent) event;
	}
}
