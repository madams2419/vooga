package game_engine.control;


import game_engine.Behavior;

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
	
	Map<String, Behavior> myControlMap;
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
		myControlMap.get(myDesignerMap.get(keyText)).execute();
	}
	
	public void addBehavior(String key, String behaviorString){
		myDesignerMap.put(key, behaviorString);
		addEntryControlMap(behaviorString, ControlTester.selectBehavior(behaviorString));
	}
	
	public void modifyKey(String oldKey, String newKey){
		if(myDesignerMap.containsKey(oldKey)){
			if(myDesignerMap.containsKey(newKey)){
				System.out.println("New key is in use. Please try another one.");
				return;
			} else {
				myDesignerMap.put(newKey, myDesignerMap.get(oldKey));
				addEntryControlMap(newKey, ControlTester.selectBehavior(oldKey));
				myDesignerMap.remove(oldKey);
				deleteEntryControlMap(oldKey);
			}
		} else {
			System.out.println("Key modification is aborted");
		}
	}
	
	private void addEntryControlMap(String key, Behavior behavior){
		myControlMap.put(key, behavior);
	}
	
	private void deleteEntryControlMap(String key){
		if(myControlMap.containsKey(key)){
			myControlMap.remove(key);
		} else {
			System.out.println("Nothing to delete in the ControlMap");
		}
	}
	
	public void mapTranslation(){
		  
	}
	
}
