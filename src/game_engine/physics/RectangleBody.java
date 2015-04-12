package game_engine.physics;

public class RectangleBody extends RigidBody {

	private Vector myUpperRight;
	private Vector myLowerLeft;

	public RectangleBody(double depth, Vector upperLeft, double height, double width) {
		this(depth, upperLeft.incrementX(width), upperLeft.incrementY(-height));
	}

	public RectangleBody(double depth, Vector upperRight, Vector lowerLeft) {
		super(depth);
		myUpperRight = upperRight;
		myLowerLeft = lowerLeft;
	}

	public static RectangleBody getRectBodyULLR(double depth, Vector upperLeft, Vector lowerRight) {
		Vector upperRight = upperLeft.setX(lowerRight.getX());
		Vector lowerLeft = lowerRight.setX(upperLeft.getX());
		return new RectangleBody(depth, upperRight, lowerLeft);
	}

	public static RectangleBody getRectBodyURLL(double depth, Vector upperRight, Vector lowerLeft) {
		return new RectangleBody(depth, upperRight, lowerLeft);
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
