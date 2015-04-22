package game_engine.hitboxes;

import game_engine.physics_engine.Vector;

import java.util.List;
import java.util.function.Supplier;

public class MultipleHitboxes implements IHitbox {
	
	private List<IHitbox> hitboxes;

	@Override
	public boolean intersects(IHitbox other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean containsPoint(Vector point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Vector> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier<Vector> getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
}