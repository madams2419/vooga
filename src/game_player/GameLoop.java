package game_player;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import game_engine.Game;

public class GameLoop {
	
	Game game;
	Scene scene;
	Parser parser;
	
	public GameLoop(File gameData) {
		// TODO
	}
	
	public Scene initialize() {
		// TODO
		return null;
	}
	
	public void play() {
		
	}
	
	private void handleKeyPress(KeyEvent event) {
		game.handleKeyPress(event.getText());
	}

}
