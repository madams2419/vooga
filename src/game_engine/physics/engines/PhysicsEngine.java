package game_engine.physics.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.objects.PhysicsObject;

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
	
	public void resolveCollision(PhysicsObject spriteA, PhysicsObject spriteB) {
		Vector normal;
		double pDepth;
		
		IHitbox hbA = spriteA.getHitbox();
		IHitbox hbB = spriteB.getHitbox();
		Vector bboxA = hbA.getBoundingBox().get(1);
		Vector bboxB = hbB.getBoundingBox().get(1);
		Vector posA = hbA.getPosition();
		Vector posB = hbB.getPosition();
		
		Vector collisionDelta = posB.minus(posA);

		double halfWidthA = bboxA.getX() / 2;
		double halfWidthB = bboxB.getY() / 2;

		double x_overlap = halfWidthA + halfWidthB - Math.abs(collisionDelta.getX());

		double halfHeightA = bboxA.getY() / 2;
		double halfHeightB = bboxB.getY() / 2;

		double y_overlap = halfHeightA + halfHeightB - Math.abs(collisionDelta.getY());

		if(x_overlap < y_overlap) {
			normal = (collisionDelta.getX() < 0) ? Vector.WEST : Vector.EAST;
			pDepth = x_overlap;
		} else {
			normal = (collisionDelta.getY() < 0) ? Vector.SOUTH : Vector.NORTH;
			pDepth = y_overlap;
		}
		
		/* apply resolution */
		double rvProjNorm = rvProjOnNorm(spriteA, spriteB, normal);
		if(rvProjNorm > 0) {
			return;
		}

		Vector impulse = computeImpulse(spriteA, spriteB, rvProjNorm, normal);
		
		spriteA.applyImpulse(impulse.times(getInvArea(spriteA)).negate());
		spriteB.applyImpulse(impulse.times(getInvArea(spriteB)));

		/* apply sink correction */
		double SC_SLOP = 0.01;
		double SC_PERCENT = 0.005;
		if(pDepth < SC_SLOP) {
			return;
		}

		double correctionCoef = SC_PERCENT * pDepth / (getInvArea(spriteA) + getInvArea(spriteB));
		Vector correction = normal.times(correctionCoef);
		Vector aCorrection = correction.times(getInvArea(spriteA)).negate();
		Vector bCorrection = correction.times(getInvArea(spriteB));
		
		spriteA.addPosition(aCorrection);
		spriteB.addPosition(bCorrection);

	}
	
	private double rvProjOnNorm(PhysicsObject spriteA, PhysicsObject spriteB, Vector normal) {
		return getRelativeVelocity(spriteA, spriteB).dot(normal);
	}
	
	private Vector getRelativeVelocity(PhysicsObject spriteA, PhysicsObject spriteB) {
		return spriteB.getVelocity().minus(spriteA.getVelocity());
	}
	
	private double getInvArea(PhysicsObject sprite) {
		return 1 / sprite.getHitbox().getArea();
	}
	
	protected Vector computeImpulse(PhysicsObject spriteA, PhysicsObject spriteB, double rvProjNorm, Vector normal) {
		double implsMag = -(1 + computeRestitution(spriteA, spriteB)) * rvProjNorm;
		implsMag /= getInvArea(spriteA) + getInvArea(spriteB);
		return normal.times(implsMag);
	}
	
	protected double computeRestitution(PhysicsObject spriteA, PhysicsObject spriteB) {
		return Math.min(spriteA.getRestitution(), spriteB.getRestitution());
	}
}