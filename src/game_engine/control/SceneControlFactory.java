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

	public SceneControl getControlType(InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return myControlManager.getActiveMouseControl();
		else
			System.out.println("First get keycontrol");
			return myControlManager.getActiveKeyControl();
	}

	public InputEvent getEventType(InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return (MouseEvent) event;
		else
			return (KeyEvent) event;
	}

	public Function<KeyEvent, Integer> getKeyEventType(KeyEvent e){
		KeyControl keyControl = (KeyControl) myControlManager.getActiveKeyControl();
		if(e.getEventType().equals(KeyEvent.KEY_PRESSED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executePressed();
		} else if (e.getEventType().equals(KeyEvent.KEY_RELEASED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeReleased();
		} else
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeHeld();
	}

}
