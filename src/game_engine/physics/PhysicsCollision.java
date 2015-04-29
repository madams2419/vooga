package game_engine.physics;

public abstract class PhysicsCollision {

	//TODO move these to properties file
	private static double SC_PERCENT = 0.5;
	private static double SC_SLOP = 0.01;

	protected PhysicsObject myObjectA;
	protected PhysicsObject myObjectB;
	protected Vector myNormal;
	protected double myPenetrationDepth;

	public PhysicsCollision(PhysicsObject objectA, PhysicsObject objectB) {
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

		// apply impulse
		Vector impulse = computeImpulse();
		//TODO change to an addVelocity() call
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
		double implsMag = -(1 + computeRestitution()) * computeRVProjection();
		implsMag /= myObjectA.getInvMass() + myObjectB.getInvMass();
		return myNormal.times(implsMag);
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
