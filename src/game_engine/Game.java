package game_engine;

import game_engine.control.KeyControl;

/**
 * 
 * @author 
 *
 */
public class Game {
	
	Level activeLevel;
	KeyControl controlsManager;
	
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
