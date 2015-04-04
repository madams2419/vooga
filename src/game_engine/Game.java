package game_engine;

import game_engine.control.ControlsManager;

/**
 * 
 * @author 
 *
 */
public class Game {
	
	Level activeLevel;
	ControlsManager controlsManager;
	
	public Game() {
		// TODO
	}
	
	public void handleKeyPress(String keyText) {
		controlsManager.executeBehavior(keyText);
	}
	
	public void switchActiveLevel(Level newLevel){
		activeLevel = newLevel;
	}

}
