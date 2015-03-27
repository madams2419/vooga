package usecases;

import game_engine.Game;

public class UseCases {
	
	/* 
	 * USE CASE: Player presses spacebar and sprite responds with mapped behavior
	 * 1. Private method gameLoop.handleKeyInput(KeyEvent event) is called when the spacebar is pressed. See GameLoop.java for implementation of this method.
	 * 2. Public method game.handleKeyPress(String keyText) is called. See Game.java for implementation details.
	 * 3. Public method controlsManager.executeBehavior(String keyText) is called. See ControlsManager.java for implementation details.
	 */
	public void useCase1() {
		Game mockGame = new Game();
		String keyText = "SPACEBAR";
		mockGame.handleKeyPress(keyText);
	}
	
	/*
	 * USE CASE: Player completes objective and moves onto new level
	 * 
	 * 1. Listener in Objective class detects 
	 */
	
	

}
