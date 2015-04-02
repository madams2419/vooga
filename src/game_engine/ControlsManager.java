package game_engine;


import java.util.Map;

public class ControlsManager {
	
	Map<String, Behavior> controlsMap;
	
	public ControlsManager() {
		// TODO
	}
	
	public void executeBehavior(String keyText) {
		controlsMap.get(keyText).execute();
	}

}
