package game_engine.physics;

public class RectangleBody extends RigidBody {

	private Vector myUpperRight;
	private Vector myLowerLeft;

	public RectangleBody(Vector center, double height, double width) {
		super(center);
		Vector cTranslate = new Vector(height/2, width/2);
		myUpperRight = center.plus(cTranslate);
		myLowerLeft = center.minus(cTranslate);
	}

	public static RectangleBody rBodyFromCorners(Vector corner1, Vector corner2) {
		Vector translate = corner1.minus(corner2).times(0.5);
		Vector center = corner2.plus(translate);
		double height = Math.abs(corner1.getY() - corner2.getY());
		double width = Math.abs(corner1.getX() - corner2.getX());
		return new RectangleBody(center, height, width);
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
