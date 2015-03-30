package game_engine;
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

}
