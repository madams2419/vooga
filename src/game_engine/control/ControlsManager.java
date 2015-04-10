package game_engine.control;


import game_engine.IBehavior;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.KeyCode;

/**
 * Defines controls and maps them to behaviors
 * @author 
 *
 */
public class ControlsManager {
	
	Map<String, IBehavior> myControlMap;
	Map<String, String> myDesignerMap;
	Map<KeyCode, String> myVirtualKeyboard;
	
	public ControlsManager() {
		myControlMap = new HashMap<>();
		myDesignerMap = new HashMap<>();
		myVirtualKeyboard = new HashMap<>();
	}
	
	/**
	 * method executeBehavior
	 * executes a behavior corresponding to a key
	 * @param keyText the string that maps to the key
	 */
	public void executeBehavior(String keyText) {
		myControlMap.get(myDesignerMap.get(keyText)).execute(new String[3]);
	}
	/**
	 * KeyCode version of executeBehavior
	 * @param keycode
	 */
	public void executeBehavior(KeyCode keycode){
		myControlMap.get(myVirtualKeyboard.get(keycode)).execute(new String[3]);
	}
	
	//adds a new behavior to the maps
	public void addBehavior(String key, String behaviorName){
		myDesignerMap.put(key, behaviorName);
		myVirtualKeyboard.put(KeycodeFactory.generateKeyCode(key), behaviorName);
		addEntryControlMap(behaviorName, ControlTester.selectBehavior(behaviorName));
	}
	
	//change the key of a behavior
	public void modifyKey(String oldKey, String newKey){
		if(myDesignerMap.containsKey(oldKey)){
			if(myDesignerMap.containsKey(newKey)){
				System.out.println("New key is in use. Please try another one.");
				return;
			} else {
				myDesignerMap.put(newKey, myDesignerMap.get(oldKey));
				myVirtualKeyboard.put(kcTranslation(newKey), myVirtualKeyboard.get(kcTranslation(oldKey)));
				addEntryControlMap(newKey, ControlTester.selectBehavior(oldKey));
				myDesignerMap.remove(oldKey);
				myVirtualKeyboard.remove(kcTranslation(oldKey));
				deleteEntryControlMap(oldKey);
			}
		} else {
			System.out.println("Key modification is aborted");
		}
	}
	
	private void addEntryControlMap(String key, IBehavior behavior){
		myControlMap.put(key, behavior);
	}
	
	private void deleteEntryControlMap(String key){
		if(myControlMap.containsKey(key)){
			myControlMap.remove(key);
		} else {
			System.out.println("Nothing to delete in the ControlMap");
		}
	}
	
	//TODO: throw exception if keycode not defined
	private KeyCode kcTranslation(String index){
		  return KeycodeFactory.generateKeyCode(index);
	}
	
}
