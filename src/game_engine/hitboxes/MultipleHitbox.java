package game_engine.hitboxes;

import game_engine.physics.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javafx.util.Pair;

public class MultipleHitbox implements IHitbox {
	
	private List<SingleHitbox> hitboxes;
	
	private List<Pair<Integer, Integer>> collisionPairs;
	
	public MultipleHitbox() {
		hitboxes = new ArrayList<>();
	}

	public boolean intersects(IHitbox other) {
		collisionPairs = new ArrayList<>();
		for (int i = 0; i < hitboxes.size(); i++) {
			if (hitboxes.get(i).intersects(other)) {
				for (Pair<Integer, Integer> pair : hitboxes.get(i).getCollisionPairs()) {
					collisionPairs.add(new Pair<>(i, pair.getValue()));
				}
			}
		}
		return !collisionPairs.isEmpty();
	}

	public List<Pair<Integer, Integer>> getCollisionPairs() {
		return collisionPairs;
	}

	public double getArea() {
		double sum = 0;
		for (SingleHitbox hitbox : hitboxes) {
			sum += hitbox.getArea();
		}
		return sum;
	}

	public boolean containsPoint(Vector point) {
		for (SingleHitbox hitbox : hitboxes) {
			if (hitbox.containsPoint(point)) {
				return true;
			}
		}
		return false;
	}

	public List<Vector> getPoints() {
		List<Vector> points = new ArrayList<>();
		for (SingleHitbox hitbox : hitboxes) {
			points.addAll(hitbox.getPoints());
		}
		return points;
	}

	public Vector getPosition() {
		return hitboxes.get(0).getPosition();
	}

	public List<SingleHitbox> getComponents() {
		return Collections.unmodifiableList(hitboxes);
	}

	public void addPositionSupplier(Supplier<Vector> position) {
		for (SingleHitbox hitbox : hitboxes) {
			hitbox.addPositionSupplier(position);
		}
	}
	
	public void addHitbox(SingleHitbox hitbox) {
		hitboxes.add(hitbox);
	}
	
	public List<Vector> getBoundingBox() {
		List<Vector> boundingBox = new ArrayList<>();
		Vector lowerLeft, upperRight;
		double minX = 0, minY = 0, maxX = 0, maxY = 0;
		
		for(IHitbox hitbox : hitboxes) {
			List<Vector> bBox  = hitbox.getBoundingBox();
			Vector lLeft = bBox.get(0);
			Vector uRight = bBox.get(1);
			
			minX = Math.min(minX, lLeft.getX());
			minY = Math.min(minY, lLeft.getY());
			maxX = Math.max(maxX, uRight.getX());
			maxY = Math.max(maxY, uRight.getY());
		}
		
		lowerLeft = new Vector(minX, minY);
		upperRight = new Vector(maxX, maxY);
		
		boundingBox.add(lowerLeft);
		boundingBox.add(upperRight);
		
		return boundingBox;
	}
}