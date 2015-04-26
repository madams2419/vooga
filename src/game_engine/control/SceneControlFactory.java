package game_engine.control;

import java.util.*;
import java.util.function.Function;

import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This class serves as the factory class for scenecontrol objects and
 * helps to diffentiate key events and mouse events
 */
public class SceneControlFactory {
	private SceneControlManager myControlManager;
	private final String MOUSE_EVENT = "javafx.scene.input.MouseEvent";
	private final String KEY_EVENT = "javafx.scene.input.KeyEvent";

	/**
	 * Constructor for SceneControlFactory.
	 * @param controlManager SceneControlManager
	 */
	public SceneControlFactory(SceneControlManager controlManager){
		myControlManager = controlManager;
	}
	
	/**
	 * Method addControlType.
	 * @param control Control
	 */
	public void addControlType(Control control){
		if(control.getClass().toString().equals("class game_engine.control.KeyControl")){
			myControlManager.addKeyControl((KeyControl) control); 
		} else if (control.getClass().toString().equals("class game_engine.control.KeyControl")){
			myControlManager.addMouseControl((MouseControl) control); 
		} else {
			System.out.println("Invalid Control Type");
		}
	}

	/**
	 * Method getControlType.
	 * @param event InputEvent
	 * @return SceneControl
	 */
	public SceneControl getControlType(InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return myControlManager.getActiveMouseControl();
		else
			return myControlManager.getActiveKeyControl();
	}

	/**
	 * Method getEventType.
	 * @param event InputEvent
	 * @return InputEvent
	 */
	public InputEvent getEventType(InputEvent event){
		if(event.getClass().equals(MOUSE_EVENT))
			return (MouseEvent) event;
		else
			return (KeyEvent) event;
	}

	/**
	 * Method getKeyEventType.
	 * @param e KeyEvent
	 * @return Function<KeyEvent,Integer>
	 */
	public Function<KeyEvent, Integer> getKeyEventType(KeyEvent e){
		if(e.getEventType().equals(KeyEvent.KEY_PRESSED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executePressed();
		} else if (e.getEventType().equals(KeyEvent.KEY_RELEASED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeReleased();
		} else
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeHeld();
	}

}
