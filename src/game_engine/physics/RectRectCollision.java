package game_engine.physics;

public class RectRectCollision extends PhysicsCollision {
	
	//TODO refactor these
	private final static Vector NORTH_NORMAL = new Vector(0, 1);
	private final static Vector WEST_NORMAL = new Vector(-1, 0);
	private final static Vector EAST_NORMAL = new Vector(1, 0);
	private final static Vector SOUTH_NORMAL = new Vector(-1, 0);

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
	
	//TODO refactor this shit
	protected Vector computeNormal() {
		computeCollisionRegion();
		boolean cHeightGreaterThanWidth = collisionRegion.getHeight() > collisionRegion.getWidth();
		
		switch(cType) {
		case B_UPPER_LEFT :
			return (cHeightGreaterThanWidth) ? WEST_NORMAL : SOUTH_NORMAL;
			
		case B_UPPER_RIGHT :
			return (cHeightGreaterThanWidth) ? EAST_NORMAL : SOUTH_NORMAL;

		case B_LOWER_LEFT :
			return (cHeightGreaterThanWidth) ? WEST_NORMAL : NORTH_NORMAL;

		case B_LOWER_RIGHT :
			return (cHeightGreaterThanWidth) ? EAST_NORMAL : SOUTH_NORMAL;
			
		default :
			return new Vector(0, 0);
		}
	}

	protected double computePenetrationDepth() {
		return Math.max(collisionRegion.getHeight(), collisionRegion.getWidth());
	}

	//TODO refactor and also deal with case where rectangle is completely contained in other
	private void computeCollisionRegion() {
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
		} else {
			cType = CollisionType.NONE;
		}
	}

}


