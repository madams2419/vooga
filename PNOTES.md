* Physics is now a completely self contained package--that is, it's
  independent of all classes in the rest of the project
* Hitbox specifications are no longer used...the image bounds are used
  as the hitbox. Because of this, the image must have very little
  whitespace on the edges, or it will not look good
* Physics is accessed through three interfaces: IPhysicsEngine,
  IPhysicsObject and IRigidBody (not implemented yet)
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
  physical collisions
* Observer/Observable removed from Sprite and Animation b/c it was
  causing some sychronization problems

TODO
* implement friction
* implement sub hitboxes
* add support for non-collidable sprites (transparent material)
* polish up control parameters (sprite is very hard to control now)
* Add interfaces for physics engine and physics object
* Add ComplexResolver which takes in collision normal and does direction
  based resolution
* (maybe) Change velocity and force handling to reset every timestep
