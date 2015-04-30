package game_engine.physics.collisions;

import game_engine.physics.PhysicsObject;
import game_engine.physics.utilities.Vector;
import game_engine.physics.utilities.Constants;
import game_engine.physics.utilities.Utilities;

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

		applyCollisionImpulses();

		applySinkCorrection();
		
		applyFrictionModification();
	}

	private void applyCollisionImpulses() {
		double rvScale = computeResolutionScalar();
		Vector rVector = myNormal.times(rvScale);
		applyImpulses(rVector);
	}
	
	private void applyFrictionModification() {
		Vector relVel = getRelativeVelocity();
		Vector tangent = relVel.minus(myNormal.times(computeRVProjection())).normalize();
		double fricMag = -relVel.dot(tangent) / (myObjectA.getInvMass() + myObjectB.getInvMass());
		double staticFriction = computeStaticFriction();
		double rvScale = computeResolutionScalar();
		
		Vector fVector = Vector.ZERO;
		if(Math.abs(fricMag) < Math.abs(rvScale * staticFriction)) {
  			fVector = tangent.times(fricMag);
		} else {
			double dynamicFriction = computeKineticFriction();
			fVector = tangent.times(rvScale * dynamicFriction);
		}
		applyImpulses(fVector);
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
	
	private void applyImpulses(Vector raw) {
		Vector impulseA = raw.times(myObjectA.getInvMass()).negate();
		Vector impulseB = raw.times(myObjectB.getInvMass());
		myObjectA.applyVelocity(impulseA);
		myObjectB.applyVelocity(impulseB);
	}
	
	protected double computeStaticFriction() {
		return Utilities.solvePythagorean(myObjectA.getStaticFriction(), myObjectB.getStaticFriction());
	}
	
	protected double computeKineticFriction() {
		return Utilities.solvePythagorean(myObjectA.getKineticFriction(), myObjectB.getKineticFriction());
	}
	
	private double computeResolutionScalar() {
		double rvMag = -(1 + computeRestitution()) * computeRVProjection();
		rvMag /= myObjectA.getInvMass() + myObjectB.getInvMass();
		return rvMag;
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
