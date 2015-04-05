package game_engine.control;


import game_engine.Behavior;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines controls and maps them to behaviors
 * @author 
 *
 */
public class ControlsManager {
	
	Map<String, Behavior> controlMap;
	Map<String, String> designerMap;
	VirtualKeyboard virtualKeyboard;
	
	public ControlsManager() {
		controlMap = new HashMap<>();
		designerMap = new HashMap<>();
	}
	
	/**
	 * method executeBehavior
	 * executes a behavior corresponding to a key
	 * @param keyText the string that maps to the key
	 */
	public void executeBehavior(String keyText) {
		controlMap.get(designerMap.get(keyText)).execute();
	}
	
	public void addBehavior(String key, String behaviorString){
		designerMap.put(key, behaviorString);
		addEntryControlMap(behaviorString, ControlTester.selectBehavior(behaviorString));
	}
	
	public void modifyKey(String oldKey, String newKey){
		if(designerMap.containsKey(oldKey)){
			if(designerMap.containsKey(newKey)){
				System.out.println("New key is in use. Please try another one.");
				return;
			} else {
				designerMap.put(newKey, designerMap.get(oldKey));
				addEntryControlMap(newKey, ControlTester.selectBehavior(oldKey));
				designerMap.remove(oldKey);
				deleteEntryControlMap(oldKey);
			}
		} else {
			System.out.println("Key modification is aborted");
		}
	}
	
	private void addEntryControlMap(String key, Behavior behavior){
		controlMap.put(key, behavior);
	}
	
	private void deleteEntryControlMap(String key){
		if(controlMap.containsKey(key)){
			controlMap.remove(key);
		} else {
			System.out.println("Nothing to delete in the ControlMap");
		}
	}
	
	public void mapTranslation(){
		  
	}
	
}
