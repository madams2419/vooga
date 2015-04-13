package game_engine.physics;

public class RectRectCollision extends PhysicsCollision {

	private RectangleBody rectA;
	private RectangleBody rectB;
	private RectangleBody collisionRegion;
	private CollisionType cType;

	private enum CollisionType {
		B_UPPER_LEFT, B_UPPER_RIGHT, B_LOWER_LEFT, B_LOWER_RIGHT, NONE
	}

	public RectRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		rectA = (RectangleBody) poA.getRigidBody();
		rectB = (RectangleBody) poA.getRigidBody();
	}

	protected Vector computeNormal() {
		//TODO refactor this shit
		computeCollisionRegion();
		
		switch(cType) {
		case B_UPPER_LEFT :
			if(collisionRegion.getHeight())
		}
	}

	protected double computePenetrationDepth() {
		return Math.max(collisionRegion.getHeight(), collisionRegion.getWidth());
	}

	private void computeCollisionRegion() {
		//TODO refactor and also deal with case where rectangle is completely contained in other
		if(rectA.containsPoint(rectB.getUpperLeft())) {
			cType = CollisionType.B_UPPER_LEFT;
			collisionRegion = RectangleBody.rBodyFromCorners(rectB.getUpperLeft(), rectA.getLowerRight());
		}
		else if(rectA.containsPoint(rectB.getUpperRight())) {
			cType = CollisionType.B_UPPER_RIGHT;
			collisionRegion = RectangleBody.rBodyFromCorners(rectB.getUpperRight(), rectA.getLowerLeft());
		}
		else if(rectA.containsPoint(rectB.getLowerLeft())) {
			cType = CollisionType.B_LOWER_LEFT;
			collisionRegion = RectangleBody.rBodyFromCorners(rectA.getUpperRight(), rectB.getLowerLeft());
		}
		else if(rectA.containsPoint(rectB.getLowerRight())) {
			cType = CollisionType.B_LOWER_RIGHT;
			collisionRegion = RectangleBody.rBodyFromCorners(rectA.getUpperLeft(), rectB.getLowerRight());
		}
	}

}


