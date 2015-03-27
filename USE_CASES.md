Use Cases
===========

### Authoring Environment
* Allow the user to specify dimensions of game display(o)
* currently have it so that the window is resizable, and the listener listens to the changes in dimensions.
* Specify dimensions of game stage(o)
* Choose background image for stage(o)
* Just adding an imageview to the scene before setting the scene, adding an observable that switches out the background might be a good idea. No finesse applied.
* Add obstacle to game stage
* Add enemy sprite at specific location
* Adding images to sprite behaviors
* Adding sounds to maps
* Specify behavior when two sprites collide
* Assign animations to behaviors such as collisions
* Defining scroll bounds/scroll type
* Setting actions to controls
* Defining game physics
* Scripting interactions: Define new behavior not defined by engine
* Define order of levels
* Move selected portion of map by clicking on mini-map
* Move selected portion of map by clicking and dragging
* Move selected portion of map by clicking slow-scroll buttons
* Add names for the game/characters
* Set the game instructions
* Add a splash screen
* Load a previously created game

### Game Engine
* Player jumps onto platform
* Main detects that a key was pressed
* Main tells control that a key was pressed, passes the key event
* control determines which key was pressed (up arrow)
* gets the list of predicates to execute from map of keys to predicates (player jump)
* executes predicates
* eventually, CollisionEngine detects a collision between the platform and the player
* CollisionFactory creates a PlayerPlatformCollision object
* PlayerPlatformCollision object contains a list of predicates to execute
* executes list
* Player collides with an enemy
* Player collides with power-up
* Player collides with inanimate obstruction
* Player completes objective & leads it to next level
* Player runs out of health
* Player falls off screen
* Player moves past a boundary, scrolls
* Player enters new region (water), changes physics
* Wraparound Map
* Infinite Map
* Pausing
* Heads up display: display score
* Multiple players on screen at same time (on same computer)

### Game Player
* Choose game file to run
* Saving high scores
* Clear the high scores
* Save progress mid-game
* Choose whether to author a game or play a game
* Choose development file to edit
* Switch to another game
* Set game preferences

### Game Data
* Saving a game/dev file
* Loading a game
* Loading a dev file
