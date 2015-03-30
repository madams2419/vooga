package game_engine;

import game_engine.sprite.Sprite;

import java.util.Map;

/**
 * Defines controls and maps them to behaviors
 * @author 
 *
 */
public class ControlsManager {
	
	Map<String, Behavior> controlsMap;
	
	public ControlsManager() {
		// TODO
	}
	/**
	 * method executeBehavior
	 * executes a behavior corresponding to a key
	 * @param keyText the string that maps to the key
	 */
	public void executeBehavior(String keyText) {
		controlsMap.get(keyText).execute();
	}

}
