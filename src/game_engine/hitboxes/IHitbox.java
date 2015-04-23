package game_engine.hitboxes;

import java.util.List;

import javafx.util.Pair;
import game_engine.physics_engine.Vector;

public interface IHitbox {
	
	public boolean intersects(IHitbox other);
	
	public List<Pair<Integer, Integer>> getCollisionPairs();
	
	public double getArea();
	
	public boolean containsPoint(Vector point);
	
	public List<Vector> getPoints();
	
	public Vector getPosition();
	
	public List<SingleHitbox> getComponents();
}