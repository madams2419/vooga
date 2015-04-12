package game_engine.physics;

public class RectangleBody extends RigidBody {

	private Vector myUpperRight;
	private Vector myLowerLeft;

	public RectangleBody(Vector upperRight, Vector lowerLeft) {
		this(0, upperRight, lowerLeft);
	}

	public RectangleBody(double depth, Vector upperRight, Vector lowerLeft) {
		super(depth);
		myUpperRight = upperRight;
		myLowerLeft = lowerLeft;
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

}
