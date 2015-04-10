package game_engine.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import game_engine.Layer;
import game_engine.sprite.Sprite;

// TODO
// - todo implement IBehavior
// - implement regions (regions are sprites)
// - optimize net force computation

public class PhysicsEngine {

	//TODO move these to properties file
	private static double SCALE_FACTOR = 0.01; // pixel to meter scaling
	private static String GRAV_STRING = "gravity";
	private static double GRAV_DIRECTION = -90;
	private static double GRAV_MAGNITUDE = 9.8 / SCALE_FACTOR;
	private static double SC_PERCENT = 0.2;
	private static double SC_SLOP = 0.01;

	private double myGround;
	private double myTimeStep;
	private Map<String, Vector> myGlobalForces;
	private Map<String, Vector> myGlobalAccels;
	private Vector myNetGlobalAccel;
	private Vector myNetGlobalForce;

	public PhysicsEngine(double ground, double timeStep, double gravity) {
		myGround = ground;
		myTimeStep = timeStep;
		myGlobalForces = new HashMap<>();
		myGlobalAccels = new HashMap<>();
		myNetGlobalForce = new Vector();
		myNetGlobalAccel = new Vector();
		setGravity(gravity);
	}

	public PhysicsEngine(double ground, double timeStep) {
		this(ground, timeStep, GRAV_MAGNITUDE);
	}

	public void setGravity(double gravity) {
		myGlobalAccels.put(GRAV_STRING, Vector.getPolarVector(GRAV_DIRECTION, GRAV_MAGNITUDE));
		computeNetGlobalAccel();
	}

	public void update(Layer layer) {
		List<Sprite> sprites = layer.getSprites();

		handleCollisions(sprites);

		updatePhysicsObjects(sprites);
	}

	private void updatePhysicsObjects(List<Sprite> sprites) {
		for(Sprite sprite : sprites) {
			sprite.getPhysicsObject().update();
		}
	}

	private void handleCollisions(List<Sprite> sprites) {
		/* check for collisions on unique sprite pairs */
		for(int i = 0; i < sprites.size(); ++i) {
			PhysicsObject a = sprites.get(i).getPhysicsObject();

			for(int j = i + 1; j < sprites.size(); ++j) {
				PhysicsObject b = sprites.get(j).getPhysicsObject();

				if(checkCircleCollision(a, b)) {
					resolveCollision(a, b);
				}
			}
		}
	}

	private boolean checkCircleCollision(PhysicsObject a, PhysicsObject b) {
		double sepDistance = b.getPositionMeters().minus(a.getPositionMeters()).getMagnitude();
		double radiiSum = ((CircleBody)b.getShape()).getRadius() + ((CircleBody)a.getShape()).getRadius();
		return sepDistance <= radiiSum;
	}

	public void resolveCollision(Sprite a, Sprite b, Vector normal, double pDepth) {
		resolveCollision(a.getPhysicsObject(), b.getPhysicsObject(), normal, pDepth);
	}

	public void resolveCollision(PhysicsObject a, PhysicsObject b) {
		Vector normal = getCollisionNormal(a, b);

		resolveCollision(a, b, normal, 0);
	}
	
	/* implement as IBehavior */
	public void resolveCollision(PhysicsObject a, PhysicsObject b, Vector normal, double pDepth) {
		// compute relative velocity
		Vector relVel = b.getVelocity().minus(b.getVelocity());

		// project relative velocity along collision normal
		double projOnNorm = relVel.dot(normal);

		// return if objects are moving apart
		if(projOnNorm > 0) {
			return;
		}

		// get minimum restitution
		double restitution = Math.min(a.getRestitution(), b.getRestitution());

		// calculate impulse
		double implsMag = -(1 + restitution) * projOnNorm;
		implsMag /= a.getInvMass() + b.getInvMass();
		Vector impulse = normal.times(implsMag);

		// apply impulse
		a.applyImpulse(impulse.negate());
		b.applyImpulse(impulse);

		// apply sink correction
		applySinkCorrection(a, b, normal, pDepth);
	}

	private Vector getCollisionNormal(PhysicsObject a, PhysicsObject b) {
		Vector delta = b.getPositionMeters().minus(a.getPositionMeters());
		return delta.normalize();
	}

	private void applySinkCorrection(PhysicsObject a, PhysicsObject b, Vector normal, double pDepth) {
		// return if penetration depth is less than threshold
		if(pDepth < SC_SLOP) {
			return;
		}

		Vector correction = normal.times(SC_PERCENT * pDepth / (a.getInvMass() + b.getInvMass()));
		a.applyImpulse(correction.negate());
		b.applyImpulse(correction);
	}

	private Vector computeNetGlobalForce() {
		myNetGlobalForce = Vector.sum(getGlobalForces());
		return myNetGlobalForce;
	}

	private Vector computeNetGlobalAccel() {
		myNetGlobalAccel = Vector.sum(getGlobalAccels());
		return myNetGlobalAccel;
	}

	public Vector getNetGlobalForce() {
		return myNetGlobalForce;
	}

	public Vector getNetGlobalAccel() {
		return myNetGlobalAccel;
	}

	public List<Vector> getGlobalAccels() {
		return new ArrayList<>(myGlobalAccels.values());
	}

	public List<Vector> getGlobalForces() {
		return new ArrayList<>(myGlobalForces.values());
	}

	public void setGlobalForce(String name, Vector force) {
		myGlobalForces.put(name, force);
		computeNetGlobalForce();
	}

	public void setGlobalAccel(String name, Vector accel) {
		myGlobalAccels.put(name, accel);
		computeNetGlobalAccel();
	}

	public void removeGlobalForce(String name) {
		myGlobalForces.remove(name);
		computeNetGlobalForce();
	}

	public void removeGlobalAccel(String name) {
		myGlobalAccels.remove(name);
		computeNetGlobalAccel();
	}

	public double getTimeStep() {
		return myTimeStep;
	}

	public void setTimeStep(double timeStep) {
		myTimeStep = timeStep;
	}

	public double getGround() {
		return myGround;
	}
	
	/* Scaling utility functions */
	
	public double pixelsToMeters(double pixels) {
		return pixels * SCALE_FACTOR;
	}
	
	public double metersToPixels(double meters) {
		return meters * SCALE_FACTOR;
	}
	
	public Vector vectorPixelsToMeters(Vector vector) {
		return vectorPixelsToMeters(vector.getX(), vector.getY());
	}
	
	public Vector vectorPixelsToMeters(double xPixels, double yPixels) {
		return new Vector(metersToPixels(xPixels), metersToPixels(yPixels));
	}
	
	public Vector vectorMetersToPixels(Vector vector) {
		return vectorMetersToPixels(vector.getX(), vector.getY());
	}

	public Vector vectorMetersToPixels(double xMeters, double yMeters) {
		return new Vector(metersToPixels(xMeters), metersToPixels(yMeters));
	}
}
