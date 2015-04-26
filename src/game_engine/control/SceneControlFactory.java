package game_engine.control;

import java.util.*;
import java.util.function.Function;

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
	
	public void addControlType(Control control){
		if(control.getClass().toString().equals("class game_engine.control.KeyControl")){
			myControlManager.addKeyControl((KeyControl) control); 
		} else if (control.getClass().toString().equals("class game_engine.control.KeyControl")){
			myControlManager.addMouseControl((MouseControl) control); 
		} else {
			System.out.println("Invalid Control Type");
		}
	}

	public SceneControl getControlType(InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return myControlManager.getActiveMouseControl();
		else
			return myControlManager.getActiveKeyControl();
	}

	public InputEvent getEventType(InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return (MouseEvent) event;
		else
			return (KeyEvent) event;
	}

	public Function<KeyEvent, Integer> getKeyEventType(KeyEvent e){
		if(e.getEventType().equals(KeyEvent.KEY_PRESSED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executePressed();
		} else if (e.getEventType().equals(KeyEvent.KEY_RELEASED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeReleased();
		} else
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeHeld();
	}

}
