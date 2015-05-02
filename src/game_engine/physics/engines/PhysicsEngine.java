// This entire file is part of my masterpiece.
// Brian Lavallee

package game_engine.physics.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics.Vector;
import game_engine.physics.objects.SimplePhysicsObject;

public class PhysicsEngine implements IActor {
	
	private List<Vector> globalAcceleration, globalForce;
	
	public PhysicsEngine() {
		globalAcceleration = new ArrayList<>();
		globalForce = new ArrayList<>();
	}
	
	public Vector getGlobalAccel() {
		return Vector.sum(globalAcceleration);
	}
	
	public Vector getGlobalForce() {
		return Vector.sum(globalForce);
	}
	
	public void addGlobalAccel(Vector accel) {
		globalAcceleration.add(accel);
	}
	
	public void addGlobalForce(Vector force) {
		globalForce.add(force);
	}
	
	public BiFunction<Double, Vector, Vector> getDependentForces() {
		return (a, b) -> new Vector(0, 0);
	}
	
	private IAction setGlobalAccel = (params) -> {
		globalAcceleration = new ArrayList<>();
		globalAcceleration.add(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	private IAction setGlobalForce = (params) -> {
		globalForce = new ArrayList<>();
		globalForce.add(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};

	public IAction getAction(String name) {
		return name.equals("setGlobalAccel") ? setGlobalAccel : name.equals("setGlobalForce") ? setGlobalForce : null;
	}
	
	public void resolveCollision(SimplePhysicsObject spriteA, SimplePhysicsObject spriteB) {
		List<Vector> boundsA = spriteA.getHitbox().getBoundingBox();
		List<Vector> boundsB = spriteB.getHitbox().getBoundingBox();
		
		Vector bottomLeftA = boundsA.get(0).plus(spriteA.getPosition());
		Vector topRightA = boundsA.get(1).plus(spriteA.getPosition());
		Vector bottomLeftB = boundsB.get(0).plus(spriteB.getPosition());
		Vector topRightB = boundsB.get(1).plus(spriteB.getPosition());
		
		int direction = 0;
		if ((direction = checkHorizontal(bottomLeftA.getX(), bottomLeftB.getX(), topRightA.getX(), topRightB.getX())) != 0) {
			if (direction == 1) {
				spriteA.setMaxMajorVelocity(0);
				spriteB.setMinMajorVelocity(0);
			}
			else {
				spriteA.setMinMajorVelocity(0);
				spriteB.setMaxMajorVelocity(0);
			}
		}
		if ((direction = checkVertical(bottomLeftA.getY(), bottomLeftB.getY(), topRightA.getY(), topRightB.getY())) != 0) {
			if (direction == 1) {
				spriteA.setMaxMinorVelocity(0);
				spriteB.setMinMinorVelocity(0);
			}
			else {
				spriteA.setMinMinorVelocity(0);
				spriteB.setMaxMinorVelocity(0);
			}
		}
	}
	
	private int checkHorizontal(double leftA, double leftB, double rightA, double rightB) {
		if (rightA > leftB && rightA < rightB && leftA < leftB) {
			return 1;
		}
		else if (rightB > leftA && rightB < rightA && leftB < leftA) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	private int checkVertical(double bottomA, double bottomB, double topA, double topB) {
		if (bottomA < topB && bottomA > bottomB && topA > topB) {
			return -1;
		}
		else if (bottomB < topA && bottomB > bottomA && topB > topA) {
			return 1;
		}
		else {
			return 0;
		}
	}
}