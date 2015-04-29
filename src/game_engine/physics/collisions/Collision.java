package game_engine.physics.collisions;

import game_engine.physics.PhysicsObject;
import game_engine.physics.utilities.Vector;
import game_engine.physics.utilities.Constants;

public abstract class Collision {

	protected PhysicsObject myObjectA;
	protected PhysicsObject myObjectB;
	protected Vector myNormal;
	protected double myPenetrationDepth;

	public Collision(PhysicsObject objectA, PhysicsObject objectB) {
		myObjectA = objectA;
		myObjectB = objectB;
		castRigidBodies();
		solve();
	}

	/**
	 * Compute collision normal and penetration depth
	 */
	protected abstract void solve();

	protected abstract void castRigidBodies();

	public void resolve() {
		// return if either object is transparent
		if(myObjectA.isTransparent() || myObjectB.isTransparent()) {
			return;
		}

		// return if objects are moving apart
		if(computeRVProjection() > 0) {
			return;
		}

		applyImpulses();

		applySinkCorrection();
	}

	private void applyImpulses() {
		Vector rVector = computeResolutionVector();
		Vector impulseA = rVector.times(myObjectA.getInvMass()).negate();
		Vector impulseB = rVector.times(myObjectB.getInvMass());
		myObjectA.applyVelocity(impulseA);
		myObjectB.applyVelocity(impulseB);
	}

	private void applySinkCorrection() {
		// return if penetration depth is less than threshold
		if(myPenetrationDepth < Constants.SC_SLOP) {
			return;
		}

		double correctionCoef = Constants.SC_PERCENT * myPenetrationDepth / (myObjectA.getInvMass() + myObjectB.getInvMass());
		Vector correction = myNormal.times(correctionCoef);
		Vector aCorrection = correction.times(myObjectA.getInvMass()).negate();
		Vector bCorrection = correction.times(myObjectB.getInvMass());
		myObjectA.addPosition(aCorrection);
		myObjectB.addPosition(bCorrection);
	}

	protected double computeRestitution() {
		return Math.min(myObjectA.getRestitution(), myObjectB.getRestitution());
	}

	protected Vector computeResolutionVector() {
		double rVectorMag = -(1 + computeRestitution()) * computeRVProjection();
		rVectorMag /= myObjectA.getInvMass() + myObjectB.getInvMass();
		return myNormal.times(rVectorMag);
	}

	protected double computeRVProjection() {
		return getRelativeVelocity().dot(myNormal);
	}

	protected Vector getRelativeVelocity() {
		return myObjectB.getVelocity().minus(myObjectA.getVelocity());
	}

	protected double getSeparationDistance() {
		return getSeparationVector().getMagnitude();
	}

	protected Vector getSeparationVector() {
		return myObjectB.getPositionMeters().minus(myObjectA.getPositionMeters());
	}

	public PhysicsObject getPhysicsObjectA() {
		return myObjectA;
	}

	public PhysicsObject getPhysicsObjectB() {
		return myObjectB;
	}

	public Vector getNormal() {
		return myNormal;
	}

	public double getPenetrationDepth() {
		return myPenetrationDepth;
	}

	public boolean isCollided() {
		return myPenetrationDepth >= 0;
	}

}
