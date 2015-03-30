CS 308: High $croller$ Design Document (Vooga Salad)
===================

[Use Cases]
==============
Use cases are listed [here](https://github.com/duke-compsci308-spring2015/voogasalad_HighScrollers/blob/master/USE_CASES.md)

Design Specs
==============

####Introduction
The problem our team is trying to solve is to create a game engine and authoring environment for side scrolling games like Super Mario Brothers, Jetpack Joyride, and SpaceInvaders. However, our problem not only includes developing these components but also ensuring that the two remain as separate as possible and thus similar to a real-world game engine and authoring environment. Furthermore we would like to make our project easy to use for non-programmers while also supporting more powerful game designing for people with coding experience. 

The main goal driving our design and implementation is making the engine and authoring environment as modular and separate as possible to create a realistic game development and running environment. We plan on the engine and authoring environment talking to each other solely through game data files with the assistance of game player. For example, when the authoring environment is launched it would access a data file that specifies the features supported by the game engine and then makes those features available to the user to design their game. Thus, the most flexible part of our design is that the authoring and engine environments are most separate. Furthermore, new features implemented in the engine will easily be made available in the authoring environment with minimal modification of code if any.

The primary architecture of the game engine consists of modules required to build a scrolling game that are then integrated to form the actual game. Parts such as the physics engine and scrolling package are closed as these will not be modified after initial implementation (other than debugging). However, the Sprites() class is open as one will be able to add new sprites as well as specific movements to specific sprites. The game player and game data portions of our project will also be closed as we want to ensure the game data functionality remains the same (as this connects the authoring and engine environments).

The primary architecture of the authoring environment will consist of manipulation of XML files. The game engine will pass the authoring environment an XML file that consists of default available actions and objects. The authoring environment will then add all of these actions and objects to the GUI. The layout of the GUI is closed as once it is created, the user cannot edit it. 

Finally, the scrolling genre is built on the concept of scrolling. Thus, the engine and authoring environment will need to support objects moving across the screen at relative rates and maps that are larger (and much longer) than the given screen size so that scrolling is possible. Furthermore, scrolling games can be linear (as in one level leads directly to another) or can involve more logic (such as particular actions leading to a particular level) which must also be accommodated in our authoring and engine environments.

####Overview
As mentioned earlier, our program is split into four main modules: Game Engine (GE), Authoring Environment (AE), Game Data (GD), and Game Player (GP). The purpose of dividing our project into these four components is (a) to ensure the modularity of our design and (b) to be able to develop the game engine and authoring environment completely separate from each other.

The overall flow of our project is illustrated below. Game data is accessed by game engine and authoring environment for configuration files as well as files that represent the games themselves. Configuration files include features implemented in and parameters needed by Game Engine that provide a framework for the authoring environment (for example “jumping” for a sprite would be included in this file if it was implemented for a sprite in the game engine). 

![UML Model](http://i.imgur.com/6SwRttd.png)


Listed below are the major classes and UML diagrams corresponding to our four project packages--launcer, game_player, game_engine, authoring_environment.

![Launcher UML Model]
(https://github.com/duke-compsci308-spring2015/voogasalad_HighScrollers/blob/master/uml/launcher_uml.png)
 
Main(): The main class of our project extends App and contains a ProgramChooser which allows us to give the user an option to either run a game or create a game. It also contains a method to start the game.

ProgramChooser(): ProgramChooser contains a Scene object and the method initialize() which initializes a Scene.

![Game Engine UML Model]
(https://github.com/duke-compsci308-spring2015/voogasalad_HighScrollers/blob/master/uml/game_engine_uml.png)

The Game Engine itself is also modulated to accommodate the easy implementation of additional features and the many components required to run a game. These modules consist of Level, Layer, Game, Sprite, Animation, GameLoop, Parser, ProgramChooser, Hitbox, Physics, Objective and Main. These classes are described in detail below.

Level(): Level represents a single level of the game. It contains two Lists of active Objectives (see below) and Layers (see below) in the game as well as an update method that updates the level. 

Layer(): Layer represents a layer of the game. Stores a List of Sprites (characters, obstacles, etc) and includes methods to add a sprite to the layer, remove a sprite from the layer, update the layer, and a getter that gets the list of sprites. We call this class “Layer” because a scrolling game consists of objects that are layered on top of each other (so each layer in our game will be represented by a Layer object).

Game(): Game contains the active level as a Level object.

Sprite(): Sprite contains the basic information about a specific sprite such as its name, ID, HitBox (see below), PhysicsEngine (see below) and an update method that updates the sprite’s parameters. A sprite could be anything object placed on a map in a game such as coins, blocks, the player, clouds, and enemies.

Animation(): Animation contains two maps. The first map maps a string to a string path of the image of a particular sprite’s state. The second map maps a Sprite to the standard Image of the sprite.

GameLoop(): This class sets up the loop for the game and contains instances of Game, Scene and Parser. It contains a GameLoop method which takes a File as a parameter (the game file created during the authoring of the game), an initialize method which returns a Scene and a play method which starts the game.

Parser(): Parser contains the method parse which parses the game file (XML) and constructs the GUI accordingly.

Hitbox(): Hitbox defines the representative hit box for a sprite and contains a rectangle object as well as the method intersects which takes in another hit box and returns a boolean that indicates whether these two boxes have collided).

Physics(): Physics is a class which determines how each sprite moves.  It will consider drag, gravity, and kinematics in general.  It also maintains all of the relevant physics fields (constants and locations).  Each sprite has its own instance of physics so that each sprite or type of sprite can have different physical constants to simulate changing gravity, drag under water, or magnetism.

Objective(): The Objective class contains a list of Objectives which represents the sub-objectives of a particular objective (for example, the objective of going to the next level may require fulfilling a variety of other objectives such as obtaining 100 coins) and may also have a condition that must be met (getting a key object). In addition each objective has a action that is executed when the objective is completed. For example, go to the next level. 

![Game Engine Flow](http://i.imgur.com/9VkE1fZ.png)

####User Interface
This section describes how the user will interact with your program (keep it simple to start). It should describe the overall appearance of program's user interface components and how users interact with these components (especially those specific to your program, i.e., means of input other than menus or toolbars). It should also include one or more pictures of the user interface (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a dummy program that serves as a exemplar). Describe how a game is represented to the designer and what support is provided to make it easy to create a game. Finally, it should describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.). This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say.

![Authoring Environment Prototype](http://i.imgur.com/bsdMWDZ.png)


GUI Design
Upon launching the program the user will be presented with a GUI that allows them to create the game environment. The authoring environment will have a game display in the center and a series of drop down boxes at the top (or bottom if necessary). There will be a listener connected with each element in the drop down box that will generate a collection of items in the side pane to be able to put into the game display. We also plan on having a setting up a script function so that the user may be able to have complete control on interactions between objects.
 
####Design Details 
This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). It should describe how each module handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Note, each sub-team should have its own API for others in the overall team or for new team members to write extensions. Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions. This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say.

Game Engine Design Details: 
Main: This is the class that sets the scene of the initial program. It will extend the java application interface. Primarily, it is the starting point of the program, initializing a stage. It also will instantiate the ProgramChooser, which will present a clickable interface to choose whether to play the game or whether to create a new or existing game. 

ProgramChooser: ProgramChooser will create the clickable interface to allow the user to choose whether to create a game or play a game. It will do this by creating a scene that contains JavaFX nodes and event handlers to listen for clicks. If the user clicks to create a game, private methods in ProgramChooser will instantiate the Game Authoring Environment and add the appropriate Authoring Environment scene to the stage. If the user clicks to play a game, the ProgramChooser will pop up a file chooser to pick the appropriate data file to load. Then, the ProgramChooser will send that data path as a String to the GameLoop to parse and create the Game scene.

Game: Will have a reference to the active level. Switch level will replace the active level. The most important aspect of this class is the fact that the Game does not have a Scene object. This is intentional to avoid mixing front-end and backend responsibilities. To display each level, GameLoop will render the active level in the Game. 

Level: Level represents a list of layers and a list of objectives. Level also includes an update method to update itself during the game. When the game needs to update the level, the level update() method should update the list of objectives and the list of layers, which include their own update method. 

Layer: Layer represents the list of sprites. It is intended that each layer should represent a list of similar sprite objects (ie boundaries, characters, enemies, etc.). As a result, Layer is more of an organizational element so as to have an even more compartmentalized organization of sprites. The layer can add sprites and remove sprites from list. On update of the layer, update will likely be applied to each sprite in the layer in order to update its state according to in game changes and events. 

Sprite: Sprite will contain its name, ID, HitBox, and PhysicsEngine. On update, the appropriate physics will be applied to the Sprite to update its position, velocity, acceleration in the game. 

Animation: Animation map images to the sprite’s states. This encapsulates the javaFX/ visual component of the sprites from the model of the sprites themselves. Animation contains two maps. The first map maps a string to a string path of the image of a particular sprite’s state. The second map maps a Sprite to the standard Image of the sprite. Images will then be displayed in the scene in the appropriate locations according to parameters in the Sprites. 

GameLoop: This class sets up the loop for the game and contains instances of Game, Scene and Parser. It contains a GameLoop method which takes a File as a parameter. The GameLoop constructor will apply the parser, and parse the xml data files to construct the game scene and animations, likely with some sort of reflection. There will be an initialize method which returns a Scene and a play method which starts the game. 

Parser: Parser will construct the Game, Sprites, Layers, Levels, Objectives, etc into a Scene that can be added to the stage to play. XML will instruct the parser how to create the objects.

Hitbox: Hitbox defines the representative hit box for a sprite and contains a rectangle object as well as the method intersects, which will return True if Hitbox intersects with any other hitboxes. Other methods will define the behavior upon collision. 

Physics: Physics is a class which determines how each sprite moves.  It will consider drag, gravity, and kinematics in general. Methods in Physics will be called to update each sprite’s position according to certain mathematical physics algorithms. 

Objective: The Objective class has a list of objectives and a condition that must be met in order for the condition to be met. When these subobjectives and conditions are met, a specified action is performed. Objective also has a start method that allows you to start working on the objective.

Front End Design:
Creation of Data Files: The front end decided to render an XML File for the Game Design. We will have a default XML file to give the user a foundation to work off of. When the user has implemented his game he will press a Render button that will fire an event handler that would have a file-saver pop up. The file-saver will give the user the option to create a new game XML or to re-write the existing XML file. 

Interactions:Interactions will have two dropdown objects that will collide and fire a ChangeListener or an EventHandler depending on the object type. The different types of ChangeListener and EventHandler that can be applied will also be a list of choices in a dropdown menu (Seen in Figure.) Upon selection of one action in a dropdown box, there will be another dropdown box of Listener choices dynamically created.

We listed out a general XML file to represent our concept. Please see the game data for it.


####Example games
Three games that we are trying to implement in the scroller genre include Super Mario Bros, Jetpack Joyride, and Space invaders, with Super Mario Bros conceptually being the most extensive. The three games all differ fundamentally in gameplay. Jetpack Joyride implements constant scrolling, requiring users to fly up and down to avoid obstacles with use of a jetpack. In Space invaders, players must shoot and destroy aliens in order to traverse levels, and in Super Mario Bros players traverse maps and interact with various sprites in order to continue through the game. Below are examples of elements of our design that support different features of the different game:

* Scroll direction: All three of these games implement some type of scroller, and our code is extendable to allow for scrolling in any direction.

* Major vs minor objectives: The final objectives of each of the games differ, and behaviors that occur when objectives are complete are different as well. We will have an objective class through which we will extend and create multiple type of objectives (which the user can set to certain levels). Each objective has a method that specifies what to do when it is complete. Our design caters to different types of objectives (mini objectives as well as major ones) in order to cater to the gameplay of different games. For example, in Super Mario Bros our objective class allows for smaller objectives such as collecting coins (which may lead to power ups) that do not result in level changes by setting different isComplete methods. Major objectives will have the functionality to set a new level. With the new level another set of objectives is loaded.

* Physics : Super Mario Bros. has different regions that alter the physics of the game, such as water. We will have a physics engine (it is still being decided where to hold this physics engine) but essentially it will be responsible for changing physics parameters in the sprites. Specific regions can correspond to different physics, supporting a water environment.

Jetpack Joyride also has power ups that allow physics to be changed. In doing this, we can have the complete method of a power up correspond to physics with different parameters, successfully supporting power ups.

* Recurring locations: Jetpack Joyride has a shop feature. In order to support this, we can just use our level class and just create a level that corresponds to the shop. Whenever the shop needs to be accessed, this same level is referenced to return it to the same shop. However, in the case of Super Mario Bros., there are recurring features that need to be updated. Our implementation can also support this. For example, the map in which a player selects the “world” to go into from the first Mario screen can be updated. The properties of the different objects in the level are contained within that level, thus keeping the updates when the level is assessed again.

Other basic features such as adding sprites, levels, and backgrounds can be supported to make each of the different games.

####Design Considerations 

One of the design considerations we discussed about in detail is whether to have the Physics engine in the sprite or have a global physics engine. This design makes it easier to govern physical properties that correspond to specific sprites. For example, a player, when falling, would fall based on a set gravity (that affects its velocity) but a platform would have its internal gravity set to 0, letting it float in the air. Unfortunately, there is a problem with this implementation that a global physics engine solves. When we have a power up that affects physics of the environment (and thus multiple sprites), a global physics engine would be beneficial. In essence, the game world would be operating like the real world and have a global gravity that can be modified and affect all members in the world.

Our group also had extensive discussion regarding the implementation of controls. Features we needed to encapsulate when defining keys include the following:

*User needs to different define actions to be performed for a key
*Keys can perform different actions based on collisions
*How to store a key action pair

Different ways we considered implementing actions include:
Mapping keys to action events
Assigning predicates to implement actions
Using a main controller class to deal with all of the controls

The third option was the implementation we were planning for originally. However, we quickly decided against that because it seemed like implementing the visitor pattern, where a control comes into a sprite, changes certain properties, and then leaves. Mapping still leaves the question of how we are going to assign methods and actions to keys, so predicates seems like the most viable solution.

When dealing with collisions, our original implementation included having an ICollision interface that each separate collision class for collision types implements. The interface was going to label a doCollision method which all of the subclasses would implement, and a Factory class would choose which Collision object to create. We changed this to instead make parameterized types for each collision between two sprites and having a CollisionManager that listens and finds the appropriate collision. This can be used in conjunction with our original idea of a CollisionEngine which listens for collisions between two sprites and knows what to do accordingly.

Design considerations for the authoring environment consisted of how we wanted to display certain aspects of the GUI, and how we wanted to define interactions between different regions within the AuthoringView. For displaying the interactions between two objects, we wanted to decide how we wanted the user to input actions to objects when they collide or interact. For example, Mario could have an interaction with a Coin where if Mario collides with the Coin, Mario adds to his Coin counter and the Coin disappears. The user would simply select Mario and the Coin, and select actions that the user wants for each object (see Figure). However, we want the user to specify multiple actions for the objects. One way is to dynamically create new actions in the GUI. The other is to have an add button with two static action bars. While implementation for the two static action bars is much easier, we decided to dynamically create the action bars for a better user experience as the user could easily see what actions are being associated with the two objects.
The other design consideration was whether we wanted objects to interact different in different “regions” or we wanted them to interact with different “invisible objects”. We decided on the invisible objects because it was easier to determine the action of the object when colliding with an invisible object rather than being in a particular object.

This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. It should include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design. This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say.

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



Code Interfaces
==============
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
	 * USE CASE 1: Player presses spacebar and sprite responds with mapped behavior
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
	 * USE CASE 2: Player completes objective and moves onto new level
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
	 * USE CASE 3: Player enters a new region (water), and the 
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
	
		/*
	 * USE CASE 4: Objective Interface
	 * 1. Objective Interface handles the objectives that the characters achieve
	 * 2. Objective Interface has four methods that invoke different actions at the start and complete of an objective. It also has a boolean to check if the objective is completed and an update method to update the objective.
	 */
	 
	 /*
     *interface for Objectives
     */
 	public interface Objective{
		void onComplete();
		void onStart();
		void isComplete();
		void update();

	class MarioObjective implements Objective{
		public void onComplete(){
			System.out.println("Now do things after completion of objective");
		}
		public void onStart(){
			System.out.println("Object is Started");
		}
		public void isComplete(){
			System.out.println("Object is Completed");
		}
		public void update(){
			System.out.println("update the Objectives");
		}
	}
	
	Objective MarioObjective=new MarioObjective();
	
	 * USE CASE 5: Objective Interface
	 * 1. CharacterMovement Interface handles the CharacterMovement and hopefully the graphics that occur when the character moves
	 * 2. The object where these methods are invoked should be able to translate as well as switch to appropriate images based on their movement.
	 */
	 
	public interface CharacterMovement {
	 void moveLeft(int n);
	 void moveRight(int n);
	 void jump(int n);
	 void crawl(int n);	
	}
	
	class MainPlayer implements CharacterMovement {
	Circle c=new Circle(40,40,50);

	@Override
	public void moveLeft(int currentLocation) {
		// TODO Auto-generated method stub
		c.setTranslateX(currentLocation-1);
	}

	@Override
	public void moveRight(int currentLocation) {
		// TODO Auto-generated method stub
		c.setTranslateX(currentLocation+1);
	}

	@Override
	public void jump(int currentLocation) {
		// TODO Auto-generated method stub
		c.setTranslateY(currentLocation+1);
	}

	@Override
	public void crawl(int currentLocation) {
		// TODO Auto-generated method stub
		c=new Circle(10,10,30);
	}
 }	

XMLParser Interface
* 1. When starting a new game design, the XMLParser Interface parses an XML file generated by the game engine and set up a workspace for the designer. 
* 2. When the user loads an XML file that has been created by the authoring environment, the Parser will parse the information about the Game, Sprites, Layers, Levels, Objectives, etc., and pass the information to a UIBuilder class to create a Scene that can be added to the stage to play. XML will instruct the UIBuilder how to create the objects.
‘’’
public interface XMLParser {

private Map<String,String> createLabels() {		
		// A collection of JavaFX Nodes in a map
		// that allows a builder class to construct
// the GUI 
	}

public Group createGUI(Builder builder) {		
		// A collection of JavaFX Nodes in a map
		// that allows a builder class to construct
// the GUI 
	}
}
‘’’
####Team Responsibilities
Our team is mainly divided up into two sub-teams: game authoring environment and game engine. The game authoring environment team is responsible for creating the GUI for game designers to create games and writing and saving XML files that game players can use to play the games. The game engine team is responsible for creating the framework of the chosen genre. Below is the breakdown of individual members’ responsibilities:

Team Members
- Ryan Lavallee (Salty VP of Reality Cheques and party pooper): Brian will work on the front end side of Engine and then shift to back end classes.
- Emre Sonmez (Expert of female development): Emre will start with working on the core back end engine classes, help with front end, and data.
- Yancheng Zeng (VP of administration): Yancheng will start with working on the core back end engine classes and then help with data/frontend.
- Michael Lee (Red Bull Representative): Michael will work on core back end engine classes.
- Kevin Chang (token Bay Area team member): Kevin will also work on core back end engine classes then transition to front end. 
- Michael Adams (Phrat Star-In-Chief): Mike will work on implementing Game Player and then shift to Game Engine.
- Tony Qiao (Chief Cheerleader): Tony will work on core back end engine classes including the creation of the data file generated by the features in the back end.
- Mengchao Feng (VP of sassy omniscience and constructive? criticism): Mengchao will work on the Game Authoring Environment back end as well as the parsing/data file handling.
- Jeannie Chun (graphic designing and pizza specialist): Jeannie will work mainly on the front end of the Game Authoring Environment.
- Daniel Luker (VP of afternoon tea): Daniel will work on the creation of the authoring environment data files as well as the back end of the authoring environment.
- Andrew Sun (Chief Confrontationist): Andrew will work on the back and front ends of the authoring environment.
- Natalie Chanfreau (Chargé d’affaires): Natalie will work on the back and front ends of the authoring environment.


Example Data
==============
![Example Data](http://i.imgur.com/SVkp5xZ.png)

Example Design Layout
===================

![Example Design Layout](http://i.imgur.com/66fbfl0.png)

![Example Design Layout](http://i.imgur.com/Bl675LH.jpg)

![Example Design Layout](http://i.imgur.com/lgvUnP6.png)
