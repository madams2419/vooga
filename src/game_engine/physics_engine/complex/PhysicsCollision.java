package game_engine.physics_engine.complex;

import game_engine.physics_engine.IPhysicsCollision;
import game_engine.physics_engine.Vector;
import game_engine.physics_engine.physics_object.complex_physics_object.ComplexPhysicsObject;

public abstract class PhysicsCollision implements IPhysicsCollision {

	//TODO move these to properties file
	private static double SC_PERCENT = 0.5;
	private static double SC_SLOP = 0.01;

	protected ComplexPhysicsObject myObjectA;
	protected ComplexPhysicsObject myObjectB;
	protected Vector myNormal;
	protected double myPenetrationDepth;

	public PhysicsCollision(ComplexPhysicsObject objectA, ComplexPhysicsObject objectB) {
		myObjectA = objectA;
		myObjectB = objectB;
	}

	public boolean collide() {
		solve();
		return (myPenetrationDepth >= 0);
	}

	/**
	 * Compute collision normal and penetration depth
	 */
	protected abstract void solve();

	public void resolve() {
		// return if objects are moving apart
		if(rvProjOnNorm() > 0) {
			return;
		}

		// apply impulse
		Vector impulse = computeImpulse();
		myObjectA.applyImpulse(impulse.negate());
		myObjectB.applyImpulse(impulse);

		// apply sink correction
		applySinkCorrection();
	}

	private void applySinkCorrection() {
		// return if penetration depth is less than threshold
		if(myPenetrationDepth < SC_SLOP) {
			return;
		}

		double correctionCoef = SC_PERCENT * myPenetrationDepth / (myObjectA.getInvMass() + myObjectB.getInvMass());
		Vector correction = myNormal.times(correctionCoef);
		Vector aCorrection = correction.times(myObjectA.getInvMass()).negate();
		Vector bCorrection = correction.times(myObjectB.getInvMass());
		myObjectA.addPosition(aCorrection);
		myObjectB.addPosition(bCorrection);
	}

	protected double computeRestitution() {
		return Math.min(myObjectA.getRestitution(), myObjectB.getRestitution());
	}

	protected Vector computeImpulse() {
		double implsMag = -(1 + computeRestitution()) * rvProjOnNorm();
		implsMag /= myObjectA.getInvMass() + myObjectB.getInvMass();
		return myNormal.times(implsMag);
	}

	protected double rvProjOnNorm() {
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

	public ComplexPhysicsObject getPhysicsObjectA() {
		return myObjectA;
	}

	public ComplexPhysicsObject getPhysicsObjectB() {
		return myObjectB;
	}

	public Vector getNormal() {
		return myNormal;
	}

	public double getPenetrationDepth() {
		return myPenetrationDepth;
	}

}
