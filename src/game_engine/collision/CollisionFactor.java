package game_engine.collision;

public class CollisionFactor {

	private PhysicsEngine myPhysics;

	public CollisionFactor(PhysicsEngine physics) {
		myPhysics = physics;
	}

	public Collision getCollision(Sprite spriteA, Sprite spriteB, IBehavior behavior, CollisionDirection direction, 
