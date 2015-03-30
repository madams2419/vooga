package game_engine;

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
	
	public void executeBehavior(String keyText) {
		controlsMap.get(keyText).execute();
	}

}
