package game_engine.physics_engine.complex;

import game_engine.physics_engine.physics_object.complex_physics_object.ComplexPhysicsObject;

public abstract class PhysicsCollision {

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
		myNormal = computeNormal();
		myPenetrationDepth = computePenetrationDepth();
		return (myPenetrationDepth >= 0);
	}

	protected abstract Vector computeNormal();

	protected abstract double computePenetrationDepth();

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
		//TODO this doesnt' work yet...
		// return if penetration depth is less than threshold
		if(myPenetrationDepth < SC_SLOP) {
			return;
		}

		double correctionCoef = SC_PERCENT * myPenetrationDepth / (myObjectA.getInvMass() + myObjectB.getInvMass());
		Vector correction = myNormal.times(correctionCoef);
		myObjectA.applyVelocity(correction.negate());
		myObjectB.applyVelocity(correction);
	}

	protected double collisionRestitution() {
		return Math.min(myObjectA.getRestitution(), myObjectB.getRestitution());
	}

	protected Vector computeImpulse() {
		double implsMag = -(1 + collisionRestitution()) * rvProjOnNorm();
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
