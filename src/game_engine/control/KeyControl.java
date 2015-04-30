package game_engine.control;


import game_engine.behaviors.IBehavior;

import java.util.*;
import java.util.function.Function;

import javafx.scene.input.*;

/**
 * This class defines the functionalities of a keycontrol object
 * and extends the scenecontrol class
 * @author Yancheng Zeng
 *
 */
public class KeyControl extends SceneControl {

	private Map<KeyCode, IBehavior> myKeyPressedMap;
	private Map<KeyCode, IBehavior> myKeyReleasedMap;
	private Map<KeyCode, IBehavior> myKeyHeldMap;
	private List<KeyCode> myWhilePressedKey;
	

	public KeyControl(Map<KeyCode, IBehavior> keyPressMap, Map<KeyCode, IBehavior> keyReleaseMap, Map<KeyCode, IBehavior> keyHeldMap) {
		myKeyPressedMap = keyPressMap;
		myKeyReleasedMap = keyReleaseMap;
		myKeyHeldMap = keyHeldMap;
		myWhilePressedKey = new ArrayList<>();
	}

	@Override
	public void executeEvent(InputEvent e){
		executeKeyEvent((KeyEvent) e);
	}

	public void executeKeyEvent(KeyEvent e){
		if(myKeyPressedMap.containsKey(e.getCode())){
			SceneControlFactory.getKeyEventType(e).apply(e);
		}
	}

	public Function<KeyEvent, Integer> executePressed(){
		Function<KeyEvent, Integer> pressedFunction = t -> {
			if(myKeyPressedMap.get(t.getCode()) != null){
				//System.out.println(t.getCode().toString() + " key pressed ");
				myKeyPressedMap.get(t.getCode()).perform();
			}
			myWhilePressedKey.add(t.getCode());
			return ControlConstants.EXECUTION_RESULT;
		};
		return pressedFunction;
	}

	public Function<KeyEvent, Integer> executeReleased(){
		Function<KeyEvent, Integer> releasedFunction = t -> {
			if(myKeyReleasedMap.get(t.getCode()) != null){
				//System.out.println(t.getCode().toString() + " key released");
				myKeyReleasedMap.get(t.getCode()).perform();
			}
			myWhilePressedKey.remove(t.getCode());
			return ControlConstants.EXECUTION_RESULT;
		};
		return releasedFunction;
	}

	public Function<KeyEvent, Integer> executeHeld(){
		Function<KeyEvent, Integer> heldFunction = t -> {
			return ControlConstants.EXECUTION_RESULT;
		};
		return heldFunction;
	}
}
