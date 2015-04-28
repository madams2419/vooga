package game_engine.physics;

// TODO
// - remove generic helper methods up to RigidBody class (inXRange and inYRange to name two)

public class RectangleBody extends RigidBody {

	private Vector myUpperRight;
	private Vector myLowerLeft;

	public RectangleBody(double height, double width) {
		super();
		myUpperRight = new Vector(height/2, width/2);
		myLowerLeft = myUpperRight.negate();
	}

	public static RectangleBody rBodyFromCorners(Vector corner1, Vector corner2) {
		Vector diagonal = corner1.minus(corner2);
		double height = diagonal.getY();
		double width = diagonal.getX();
		return new RectangleBody(height, width);
	}

	public double getArea() {
		return getHeight() * getWidth();
	}

	public double getCxLength() {
		return getHeight();
	}

	public double getRadius() {
		return Math.sqrt(getHeight() * getHeight() + getWidth() * getWidth());
	}

	public boolean containsPoint(Vector point) {
		return inXRange(point) && inYRange(point);
	}

	public Vector clampPointToEdge(Vector point) {
		double halfWidth = getWidth()/2;
		double halfHeight = getHeight()/2;

		double clampedX = Utilities.clamp(-halfWidth, halfWidth, point.getX());
		double clampedY = Utilities.clamp(-halfHeight, halfHeight, point.getY());
		Vector clampedPoint = new Vector(clampedX, clampedY);

		// handle case where point is completely inside rectangle
		if(clampedPoint.equals(point)) {
			double distToX = halfWidth - Math.abs(clampedX);
			double distToY = halfHeight - Math.abs(clampedY);

			// clamp to closest edge
			if(distToX < distToY) {
				clampedX = (clampedX > 0) ? halfWidth : -halfWidth;
			} else {
				clampedY = (clampedY > 0) ? halfHeight : -halfHeight;
			}
			clampedPoint = new Vector(clampedX, clampedY);
		}

		return clampedPoint;
	}

	private boolean inXRange(Vector point) {
		return point.getX() >= myLowerLeft.getX()
			&& point.getX() <= myUpperRight.getX();
	}

	private boolean inYRange(Vector point) {
		return point.getY() >= myLowerLeft.getY()
			&& point.getY() <= myUpperRight.getY();
	}

	public double getHeight() {
		return myUpperRight.getY() - myLowerLeft.getY();
	}

	public double getWidth() {
		return myUpperRight.getX() - myLowerLeft.getX();
	}

	public Vector getUpperLeft() {
		return myUpperRight.setX(myLowerLeft.getX());
	}

	public Vector getUpperRight() {
		return myUpperRight;
	}

	public Vector getLowerLeft() {
		return myLowerLeft;
	}

	public Vector getLowerRight() {
		return myLowerLeft.setX(myUpperRight.getX());
	}

}
