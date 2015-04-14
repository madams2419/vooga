package game_engine.physics;

public class CircleRectCollision extends PhysicsCollision {
	
	//TODO refactor these
	private final static Vector NORTH_NORMAL = new Vector(0, 1);
	private final static Vector WEST_NORMAL = new Vector(-1, 0);
	private final static Vector EAST_NORMAL = new Vector(1, 0);
	private final static Vector SOUTH_NORMAL = new Vector(-1, 0);

	private CircleBody myCircle;
	private RectangleBody myRectangle;
	private double cWidth;
	private double cHeight;
	private CollisionType cType;
	
	private enum CollisionType {
		B_UPPER_LEFT, B_UPPER_RIGHT, B_LOWER_LEFT, B_LOWER_RIGHT, NONE
	}

	public CircleRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		myCircle = (CircleBody) poA.getRigidBody();
		myRectangle = (RectangleBody) poB.getRigidBody();
	}

	//TODO refactor this shit
	protected Vector computeNormal() {
		computeCollisionRegion();
		boolean cHeightGreaterThanWidth = cHeight >= cWidth;
		
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
		return Math.max(cHeight, cWidth);
	}
	
	//TODO duplicated from RR collision
	private void computeCollisionRegion() {
		if(myCircle.containsPoint(myRectangle.getUpperLeft())) {
			cType = CollisionType.B_UPPER_LEFT;
			computeCDimensions(myRectangle.getUpperLeft());
		}
		else if(myCircle.containsPoint(myRectangle.getUpperRight())) {
			cType = CollisionType.B_UPPER_RIGHT;
			computeCDimensions(myRectangle.getUpperRight());
		}
		else if(myCircle.containsPoint(myRectangle.getLowerLeft())) {
			cType = CollisionType.B_LOWER_LEFT;
			computeCDimensions(myRectangle.getLowerLeft());
		}
		else if(myCircle.containsPoint(myRectangle.getLowerRight())) {
			cType = CollisionType.B_LOWER_RIGHT;
			computeCDimensions(myRectangle.getLowerRight());
		} else {
			cType = CollisionType.NONE;
			cWidth = -1;
			cHeight = -1;
		}
	}
	
	private void computeCDimensions(Vector rCorner) {
		Vector cCenter = myObjectA.getPositionMeters();
		Vector rCornerAbs = myObjectB.getPositionMeters().plus(rCorner);
		Vector diff = cCenter.minus(rCornerAbs);
		
		cWidth = myCircle.getRadius() - diff.getX();
		cHeight = myCircle.getRadius() - diff.getY();
	}
		


}


