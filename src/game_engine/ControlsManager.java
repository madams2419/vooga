package game_engine;

import game_engine.sprite.Sprite;

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
