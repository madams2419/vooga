package game_engine.control;


import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Defines controls and maps them to behaviors
 * @author 
 *
 */
public class KeyControl extends SceneControl {

//	Map<String, IAction> myControlMap;
//	Map<String, String> myDesignerMap;
//	Map<KeyCode, String> myVirtualKeyboard;

	private Map<KeyCode, IBehavior> myKeyPressedMap;
	private Map<KeyCode, IBehavior> myKeyReleasedMap;
	private Map<KeyCode, IBehavior> myKeyHeldMap;
	private List<KeyCode> myWhilePressedKey;
	private SceneControlFactory myControlFactory;
	private final Integer EXECUTION_RESULT = 0;
	
	public KeyControl(Map<KeyCode, IBehavior> keyPressMap, Map<KeyCode, IBehavior> keyReleaseMap, Map<KeyCode, IBehavior> keyHeldMap, SceneControlFactory controlFactory) {
//		myControlMap = new HashMap<>();
//		myDesignerMap = new HashMap<>();
//		myVirtualKeyboard = new HashMap<>();
		myKeyPressedMap = keyPressMap;
		myKeyReleasedMap = keyReleaseMap;
		myKeyHeldMap = keyHeldMap;
		myWhilePressedKey = new ArrayList<>();
		myControlFactory = controlFactory;
	}



	public void executeKeyEvent(KeyCode keycode, boolean pressed){
		if(myKeyPressedMap.containsKey(keycode)){
			if(pressed){
				//add error checking later
				if(myKeyPressedMap.get(keycode) != null){
					myKeyPressedMap.get(keycode).perform();
					myWhilePressedKey.add(keycode);
				}
			} else {
				if(myKeyReleasedMap.get(keycode) != null){
					myKeyReleasedMap.get(keycode).perform();
					myWhilePressedKey.remove(keycode);
				}
			}
		}
	}


	public void executeEvent(KeyEvent e){
		System.out.println("Start executing keyevent");
		if(myKeyPressedMap.containsKey(e.getCode())){
			myControlFactory.getKeyEventType(e).apply(e);
			System.out.println("Perfect");
		}
	}

	public Function<KeyEvent, Integer> executePressed(){
		Function<KeyEvent, Integer> pressedFunction = t -> {
			myKeyPressedMap.get(t.getCode()).perform();
			myWhilePressedKey.add(t.getCode());
			return EXECUTION_RESULT;
		};
		return pressedFunction;
	}
	
	public Function<KeyEvent, Integer> executeReleased(){
		Function<KeyEvent, Integer> releasedFunction = t -> {
			myKeyReleasedMap.get(t.getCode()).perform();
			myWhilePressedKey.remove(t.getCode());
			return EXECUTION_RESULT;
		};
		return releasedFunction;
	}
	
	public Function<KeyEvent, Integer> executeHeld(){
		Function<KeyEvent, Integer> heldFunction = t -> {
			return EXECUTION_RESULT;
		};
		return heldFunction;
	}

//	/**
//	 * method executeBehavior
//	 * executes a behavior corresponding to a key
//	 * @param keyText the string that maps to the key
//	 */
//	public void executeBehavior(String keyText) {
//		myControlMap.get(myDesignerMap.get(keyText)).execute();
//	}
//	/**
//	 * KeyCode version of execut   eBehavior
//	 * @param keycode
//	 */
//	public void executeBehavior(KeyCode keycode){
//		myControlMap.get(myVirtualKeyboard.get(keycode)).execute();
//	}

	//adds a new behavior to the maps
//	public void addBehavior(String key, String behaviorName){
//		myDesignerMap.put(key, behaviorName);
//		myVirtualKeyboard.put(KeyCode.valueOf(key), behaviorName);
//		addEntryControlMap(behaviorName, ControlTester.selectBehavior(behaviorName));
//	}
//
//	//change the key of a behavior
//	public void modifyKey(String oldKey, String newKey){
//		if(myDesignerMap.containsKey(oldKey)){
//			if(myDesignerMap.containsKey(newKey)){
//				System.out.println("New key is in use. Please try another one.");
//				return;
//			} else {
//				myDesignerMap.put(newKey, myDesignerMap.get(oldKey));
//				myVirtualKeyboard.put(KeyCode.valueOf(newKey), myVirtualKeyboard.get(kcTranslation(oldKey)));
//				addEntryControlMap(newKey, ControlTester.selectBehavior(oldKey));
//				myDesignerMap.remove(oldKey);
//				myVirtualKeyboard.remove(KeyCode.valueOf(oldKey));
//				deleteEntryControlMap(oldKey);
//			}
//		} else {
//			System.out.println("Key modification is aborted");
//		}
//	}
//
//	private void addEntryControlMap(String key, IAction behavior){
//		myControlMap.put(key, behavior);
//	}
//
//	private void deleteEntryControlMap(String key){
//		if(myControlMap.containsKey(key)){
//			myControlMap.remove(key);
//		} else {
//			System.out.println("Nothing to delete in the ControlMap");
//		}
//	}
//
//	//TODO: throw exception if keycode not defined
//	private KeyCode kcTranslation(String index){
//		return null;
//		//return KeycodeFactory.generateKeyCode(index);
//	}

}
