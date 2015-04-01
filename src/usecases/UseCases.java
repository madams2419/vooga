package usecases;

import game_engine.CollisionEngine;
import game_engine.Game;
import game_engine.Level;
import game_engine.PhysicsEngine;
import game_engine.objective.Objective;
import game_engine.sprite.Player;
import game_engine.sprite.Sprite;
import game_engine.sprite.Water;
import game_engine.GameObjective;

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
	 * 1. Listener in Objective class detects the completeness of an objective
	 * 2. Private method onComplete() is called to execute the correspondent predicates
	 * 3. The predicate triggers the next level and switches activeLevel in Game.java
	 */
	
	public void useCase2(){
		Game mockGame = new Game();
		GameObjective mockObjective = new GameObjective();
		mockObjective.onComplete();
		Level newLevel = new Level();
		mockGame.switchActiveLevel(newLevel);
	}
	
	
	/*
	 * USE CASE: Player enters a new region (water), and the 
	 * 1. CollisionEngine detects the collision between the Player and the water
	 * 2. CollisionEngine calls CollisionManager with the two sprites and the correspondent collision object (PlayerWaterCollision) is retrieved
	 * 3. PlayerWaterCollision executes its configured predicates and changes the gravity parameter in the playerPhysicsEngine
	 */
	
	public void useCase3(){
		CollisionEngine collisionEngine = new CollisionEngine();
		PhysicsEngine physicsEngine = new PhysicsEngine();
		Sprite water = new Water();
		Sprite player = new Player();
		collisionEngine.executeCollision(player, water);
		double acceleration = 9.8;
		physicsEngine.setAcceleration(acceleration);
	}
	

}
