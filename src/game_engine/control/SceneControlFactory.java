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
	private static SceneControlManager myControlManager;
	private static final String MOUSE_EVENT = "javafx.scene.input.MouseEvent";
	private static final String KEY_EVENT = "javafx.scene.input.KeyEvent";
	private static final String KEY_CONTROL = "game_engine.control.KeyControl";
	private static final String MOUSE_CONTROL = "game_engine.control.MouseControl";

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
	public static void addControlType(Control control){
		if(control.getClass().getName().toString().equals(KEY_CONTROL)){
			myControlManager.addKeyControl((KeyControl) control); 
		} else if (control.getClass().getName().toString().equals(MOUSE_CONTROL)){
			myControlManager.addMouseControl((MouseControl) control); 
		} else {
			System.out.println(PrintMessage.INVALID_CONTROL_TYPE.getVal());
		}
	}

	/**
	 * Method getControlType.
	 * @param event InputEvent
	 * @return SceneControl
	 */
	public static SceneControl getControlType(InputEvent event){
		if(event.getClass().getName().toString().equals(MOUSE_EVENT))
			return myControlManager.getActiveMouseControl();
		else
			return myControlManager.getActiveKeyControl();
	}

	/**
	 * Method getEventType.
	 * @param event InputEvent
	 * @return InputEvent
	 */
	public static InputEvent getEventType(InputEvent event){
		if(event.getClass().getName().toString().equals(MOUSE_EVENT)){
			//System.out.println("it's mouseevent " + event.getClass());
			return (MouseEvent) event;
		} else{
			//System.out.println("it's keyevent " + event.getClass());
			return (KeyEvent) event;
		}
	}

	/**
	 * Method getKeyEventType.
	 * @param e KeyEvent
	 * @return Function<KeyEvent,Integer>
	 */
	public static Function<KeyEvent, Integer> getKeyEventType(KeyEvent e){
		if(e.getEventType().equals(KeyEvent.KEY_PRESSED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executePressed();
		} else if (e.getEventType().equals(KeyEvent.KEY_RELEASED)){
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeReleased();
		} else
			return ((KeyControl) myControlManager.getActiveKeyControl()).executeHeld();
	}

	public static Function<MouseEvent, Integer> getMouseEventType(MouseEvent e){
		if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
			return ((MouseControl) myControlManager.getActiveMouseControl()).executeClicked();
		} else if (e.getEventType().equals(MouseEvent.MOUSE_MOVED)){
			return ((MouseControl) myControlManager.getActiveMouseControl()).executeMoved();
		} else
			return ((MouseControl) myControlManager.getActiveMouseControl()).executeReleased();
	}
}
