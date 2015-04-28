* Physics is now a completely self contained package--that is, it's
  independent of all classes in the rest of the project
* Physics is accessed through three interfaces: IPhysicsEngine,
  IPhysicsObject and IRigidBody
	> These interfaces allow the physics engine to be easily swapped with
	a different engine
* PhysicsCollisions no longer need to be defined
	> all pairs of physics objects are checked each iteration
	> "transparency" field will be added to physics objects
		- if this field is set, the physics object will not induce a
		collision
* The collisions package is purely for behavioral collisions
	> all behavioral collisions must still be defined
* A single call to PhysicsEngine.update() will check and resolve all
  collisions
* Not sure how to deal with paths yet
	> maybe pass a path supplier function into physics object
	> have path-constrained physics object
* For now, just using animation height and width to build RigidBodies
	> if time, extend to take extra parameters from hitbox definition

TODO IN MORNING
* make it so all physics object construction must go through physics
  engine
* make physics engine update function that checks all collisions and
  then updates shit
* change update sequence
	> PhysicsEngine.update()
		- check collisions
		- update positions
	> CollisionsManager.update()
	> Sprite.update()
		- update position to reflect that of physics object
		- update animation

TODO
* make animation update physics state
* Add interfaces for physics engine and physics object
* Add ComplexResolver which takes in collision normal and does direction
  based resolution
* Change velocity and force handling to reset every timestep

