package game_player;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import game_engine.Game;
/**
 * Loop in which the game to be played will be run
 * @author 
 *
 */
public class GameLoop {
	
	Game game;
	Scene scene;
	Parser parser;
	
	public GameLoop(File gameData) {
		// TODO
	}
	/**
	 * initialize the scene
	 * @return Scene to be run
	 */
	public Scene initialize() {
		// TODO
		return null;
	}
	/**
	 * 
	 */
	public void play() {
		
	}
	/**
	 * Method to deal with user inputs
	 * @param event key pressed
	 */
	private void handleKeyPress(KeyEvent event) {
		game.handleKeyPress(event.getText());
	}

}
