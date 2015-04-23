package game_engine.hitboxes;

import game_engine.physics_engine.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javafx.util.Pair;

public class SingleHitbox implements IHitbox {
	
	private Supplier<Vector> parentPosition;
	
	private List<Vector> orderedPoints;
	
	private List<Pair<Integer, Integer>> collisionPairs;
	
	public List<SingleHitbox> getComponents() {
		List<SingleHitbox> components = new ArrayList<>();
		components.add(this);
		return Collections.unmodifiableList(components);
	}
	
	public Vector getPosition() {
		return parentPosition.get();
	}
	
	public List<Vector> getPoints() {
		return Collections.unmodifiableList(orderedPoints);
	}
	
	public double getArea() {
		return getArea(orderedPoints);
	}
	
	private double getArea(List<Vector> points) {
		if (points.size() == 3) {
			return calculateArea(points.get(0), points.get(1), points.get(2));
		}
		else if(points.size() < 3) {
			return 0;
		}
		
		points.add(points.get(0));
		
		List<Vector> nextShape = new ArrayList<>();
		nextShape.add(points.get(0));
		
		double area = 0;
		for (int i = 0; i + 2 < points.size(); i += 2) {
			area += calculateArea(points.get(i), points.get(i + 1), points.get(i + 2));
			nextShape.add(points.get(i + 2));
		}
		
		if (points.size() % 2 == 1) {
			nextShape.remove(nextShape.size() - 1);
		}
		
		area += getArea(nextShape);
		
		points.remove(0);
		
		return area;
	}
	
	private double calculateArea(Vector pointA, Vector pointB, Vector pointC) {
		double base = pointC.minus(pointA).getMagnitude();
		Vector sideAC = pointC.minus(pointA);
		Vector sideAB = pointB.minus(pointA);
		Vector projection = sideAC.times(sideAC.dot(sideAB) / Math.pow(sideAC.getMagnitude(), 2));
		double height = projection.minus(sideAB).getMagnitude();
		
		Vector sideBC = pointC.minus(pointB);
		
		List<Double> signList = new ArrayList<>();
		signList.add(sideAC.getAngleDegrees());
		signList.add(sideAB.getAngleDegrees());
		signList.add(sideBC.getAngleDegrees());
		Collections.sort(signList);
		signList.add(signList.get(0));
		
		int index = 0;
		for (int i = 0; i < signList.size(); i++) {
			if (signList.get(i) == sideAC.getAngleDegrees()) {
				index = i;
				break;
			}
		}
		
		double sign = 0;
		if (signList.get(index + 1) == sideAB.getAngleDegrees()) {
			sign = 1;
		}
		else {
			sign = -1;
		}
		
		return base * height * 0.5 * sign;
	}
	
	public List<Pair<Integer, Integer>> getCollisionPairs() {
		return collisionPairs;
	}
	
	public boolean intersects(IHitbox other) {
		collisionPairs = new ArrayList<>();
		for (int i = 0; i < other.getComponents().size(); i++) {
			if (intersects(other.getComponents().get(i))) {
				collisionPairs.add(new Pair<>(0, i));
			}
		}
		return !collisionPairs.isEmpty();
	}
	
	private boolean intersects(SingleHitbox other) {
		Vector separation = other.getPosition().minus(getPosition());
		for (Vector point : other.getPoints()) {
			if (containsPoint(separation.plus(point))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsPoint(Vector point) {
		orderedPoints.add(orderedPoints.get(0));
		
		Vector adjustment = new Vector(0, 0);
		
		int crossings = 0;
		for (int i = 0; i + 1 < orderedPoints.size(); i++) {
			Vector compare = point.plus(adjustment);
			if (orderedPoints.get(i).getY() == compare.getY() || orderedPoints.get(i + 1).getY() == compare.getY()) {
				adjustment = adjustment.plus(new Vector(0, .01));
			}
			compare = point.plus(adjustment);
			if (orderedPoints.get(i).getX() > compare.getX() || orderedPoints.get(i + 1).getX() > compare.getX()) {
				if ((orderedPoints.get(i).getY() > compare.getY() && orderedPoints.get(i + 1).getY() < compare.getY()) ||
						(orderedPoints.get(i).getY() < compare.getY() && orderedPoints.get(i + 1).getY() > compare.getY())) {
					crossings++;
				}
			}
		}
		
		orderedPoints.remove(0);
		
		return crossings % 2 == 1;
	}
}