##Code Masterpiece

To start, I will note that all of the code in this branch is actually taken from an old commit before our physics engine changed.  I intended to just copy the relevant physics engine code back into master, but since the physics engine is so directly tied to collisions and hitboxes, I found it easier to simply revert to the commit of interest so that you could see what I define as well designed code without having to spend an unreasonable amount of time relatively.  As a result of this, a number of features that we implemented after do not yet work or aren't even incorporated, so actually running the game is much different from our final version.  That being said, it does still work so feel free to run it to verify that the well designed physics engine I'm trying to describe does indeed work, just without some aspects like friction which were implemented after the group decided to switch to the another approach in order to attain functionality.  I labeled a large number of files as part of my masterpiece.  These are the files whose design and implementation depend directly on the physics engine and thus should be looked at as part of determining good design.  All other files should be disregarded since they have drastically changed since this commit was first made.

The key difference between my code masterpiece and the existing code is the inclusion of the hitbox package which serves as a connection between the physics engine and the collision engine.  Besides using the composite pattern to allow for multiple hitboxes to behave as one, hitboxes can also take on complex polygon shapes allowing the designer of a game to precisely outline their characters.  Hitboxes are used by the collision engine for both detection and resolution.  Detection is very simple, but the `HitboxResolver` allows for very powerful and precise collision resolution.  In the authoring environment when definig collisions and their behaviors, the designer can prioritize the collision of certain hitbox pairs before others to trigger specific behavior depending on how the two sprites collides.  This allows for Mario to die when he hits a goomba from the side but to kill the goomba when he hits them from the top.  Additionally, because of the composite pattern used for resolvers, there can be a general behavior for a collision regardless of orientation by coupling a `SimpleResolver` with a `HitboxResolver`.  Collisions can also be resolved physically using a `PhysicalResolver` which directs the `PhysicsEngine` to calculate the new parameters of the collided objects.

This brings us to the physics package where good design is most highlighted.  The physics is broken up into two aspects, physics objects and physics engines.  The engines handle collision resolution between two physics objects as well as any global forces or accelerations.  Too implement a new physics engine, the programmer only has to write a new class which extends `PhysicsEngine` and override the public methods.  The `ComplexPhysicsEngine` is an example of this and adds drag forces as an additional global force.

Physics objects are equally extensible.  The current implementation has all of the functionality of a physics object in one class, but my code masterpiece breaks this up into an inheritance chain.  Thus, position is handled in one class, velocity in another, acceleration in a third, and finally mass and force in the `SimplePhysicsEngine`.  Not only does separate responsibilities among multiple classes, it also makes the design of physics objects more extensible.  If a future programmer wanted to implement a physics object with only velocity, they could simply extend the `MovingPhysicsObject` class instead of having to entirely rewrite all of the behavior already implemented to handle position and velocity.  In this way, physics objects become highly open for diverse extension and closed to modification.  The `ComplexPhysicsObject` is an example of this which incorporates `Material` to allow for more complex calculation of mass with density and more detailed resolution of physical collisions using restitution.