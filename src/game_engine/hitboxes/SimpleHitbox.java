package game_engine.hitboxes;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javafx.util.Pair;
import game_engine.physics_engine.Vector;

public class SimpleHitbox implements IHitbox {
	
	private List<Vector> points;
	
	private Supplier<Vector> parentLocation;

	public boolean intersects(IHitbox other) {
		Vector separation = other.getPosition().get().minus(parentLocation.get());
		for (Vector point : other.getPoints()) {
			if (containsPoint(separation.plus(point))) {
				return true;
			}
		}
		return false;
	}

	public double getArea() {
		return getHeight() * getWidth();
	}

	public double getHeight() {
		return getTopLeft().getY() - getBottomRight().getY();
	}

	public double getWidth() {
		return getBottomRight().getX() - getTopLeft().getX();
	}
	
	private Vector getTopLeft() {
		return points.get(0);
	}
	
	private Vector getBottomRight() {
		return points.get(2);
	}
	
	public boolean containsPoint(Vector point) {
		return inXRange(point) && inYRange(point);
	}
	
	private boolean inXRange(Vector point) {
		return point.getX() >= getTopLeft().getX() &&
				point.getX() <= getBottomRight().getX();
	}
	
	private boolean inYRange(Vector point) {
		return point.getY() <= getTopLeft().getY() &&
				point.getY() >= getBottomRight().getY();
	}
	
	public List<Vector> getPoints() {
		return Collections.unmodifiableList(points);
	}

	public Supplier<Vector> getPosition() {
		return parentLocation;
	}

	public List<Pair<Integer, Integer>> getCollisionPairs() {
		
	}
}