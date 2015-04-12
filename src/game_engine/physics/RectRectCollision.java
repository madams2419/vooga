package game_engine.physics;

public class RectRectCollision extends PhysicsCollision {

	private RectangleBody rectA;
	private RectangleBody rectB;
	private RectangleBody collisionRegion;

	private enum CollisionType {
		B_UPPER_LEFT, B_UPPER_RIGHT, B_LOWER_LEFT, B_LOWER_RIGHT, NONE
	}

	public RectRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		rectA = (RectangleBody) poA.getRigidBody();
		rectB = (RectangleBody) poA.getRigidBody();
	}

	protected Vector computeNormal() {
		//TODO
	}

	protected double computePenetrationDepth() {
		return Math.max(collisionRegion.getHeight(), collisionRegion.getWidth());
	}

	private RectangleBody computeCollisionRegion() {
		double cRegionDepth = 0;

		//TODO refactor and also deal with case where rectangle is completely contained in other
		if(rectA.containsPoint(rectB.getUpperLeft())) {
			return RectangleBody.getRectBodyULLR(cRegionDepth, rectB.getUpperLeft(), rectA.getLowerRight());
		}
		else if(rectA.containsPoint(rectB.getUpperRight())) {
			return RectangleBody.getRectBodyURLL(cRegionDepth, rectB.getUpperRight(), rectA.getLowerLeft());
		}
		else if(rectA.containsPoint(rectB.getLowerLeft())) {
			return RectangleBody.getRectBodyURLL(cRegionDepth, rectA.getUpperRight(), rectB.getLowerLeft());
		}
		else if(rectA.containsPoint(rectB.getLowerRight())) {
			return RectangleBody.getRectBodyULLR(cRegionDepth, rectA.getUpperLeft(), rectB.getLowerRight());
		}
		else {
			return null;
		}

	}

}


