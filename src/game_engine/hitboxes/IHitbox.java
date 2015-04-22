package game_engine.hitboxes;

import java.util.List;
import java.util.function.Supplier;

import javafx.util.Pair;
import game_engine.physics_engine.Vector;

public interface IHitbox {
	public boolean intersects(IHitbox other);
	
	public List<Pair<Integer, Integer>> getCollisionPairs();
	
	public double getArea();
	
	public double getHeight();
	
	public double getWidth();
	
	public boolean containsPoint(Vector point);
	
	public List<Vector> getPoints();
	
	public Supplier<Vector> getPosition();
	
	
}