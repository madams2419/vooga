Use Cases
===========

###Authoring
* Allow the user to specify dimensions of game display
* Specify dimensions of game stage. This would be a proportion of the entire game display
* Allow the user to set a certain map as the background for a certain level
* Let game designer insert non movable objects to the game stage
* Allow designers to specify locations of sprites (players and enemies)
* Game designer will be able to set images to actions and behaviors of sprites
* Game designer will be able to insert background music to stages
* Specify behavior when two sprites collide
* Assign animations to behaviors such as collisions
* Defining the type of scrolling as well as the boundaries to begin scrolling
* Linking actions to control keys
* Defining the physics behaviors and constants that govern the world of the game
* Allow users to script interactions, effectively defining new behaviors that are not predefined by the engine
* Users will be able to map the sequence of levels
* When designing, game makers will be able to move to a selected portion of the map by clicking on a mini-map
* When designing, game makers will be able to move to a selected portion of the map by clicking and dragging on a mini-map
* When designing, game makers will be able to move to a selected portion of the map by clicking on a slow scroll buttons that incrementally scroll
* Allow users to name characters in the game
* Have the user be able to provide documentation to define in game controls
* Give the designer the option of creating a game screen
* Allow designers to load previously designed games for further modification.
* Allow designers to load/create infinite maps for use in jetpack joyride

###User Controlling Sprites
* Sprite jumps onto platform
* Sprite collides with an enemy
* Sprite collides with power-up
* Sprite collides with inanimate obstruction
* Sprite completes objective and progresses to the next level
* Sprite runs out of health
* Sprite falls off screen
* Sprite moves past a boundary, causing the screen to scroll
* Sprite enters new region (water), resulting in a physics change

###Gameplay
* Allow potential for wraparound mapping in space invaders when user goes off screen
* Pausing the game at a certain state
* Player wants to view score from Heads Up Display
* User wants to add multiple players on screen at same time (on the same computer)

###Game Player
* Player chooses a specific game to run
* Player wants the option of saving high scores
* Player wants to clear existing high scores
* Player saves progress to return to same point in the middle of a game
* User chooses to play a game or author a game
* User specifies a certain game file to be run
* User wants to switch to a different game file to be run

###Game Data
* Game designer wants to save a game file

###Example Code
package usecases;

import game_engine.CollisionEngine;
import game_engine.Game;
import game_engine.Level;
import game_engine.Objective;
import game_engine.PhysicsEngine;
import game_engine.sprite.Player;
import game_engine.sprite.Sprite;
import game_engine.sprite.Water;

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
		Objective mockObjective = new Objective();
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

