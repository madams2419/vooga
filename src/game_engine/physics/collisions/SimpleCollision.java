package game_engine.physics.collisions;

import game_engine.physics.PhysicsObject;
import game_engine.physics.utilities.Vector;
import game_engine.physics.utilities.Constants;
import game_engine.physics.utilities.Utilities;

public abstract class SimpleCollision {

	protected PhysicsObject myObjectA;
	protected PhysicsObject myObjectB;
	protected Vector myNormal;
	protected double myPenetrationDepth;

	public SimpleCollision(PhysicsObject objectA, PhysicsObject objectB) {
		myObjectA = objectA;
		myObjectB = objectB;
		solve();
	}

	/**
	 * Compute collision normal and penetration depth
	 */
	protected abstract void solve();

	public void resolve() {
		if(myObjectA.isTransparent() || myObjectB.isTransparent()) {
			return;
		}

		if(computeRVProjection() > 0) {
			return;
		}

		applySinkCorrection();
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
	
	protected double computeStaticFriction() {
		return Utilities.solvePythagorean(myObjectA.getStaticFriction(), myObjectB.getStaticFriction());
	}
	
	protected double computeKineticFriction() {
		return Utilities.solvePythagorean(myObjectA.getKineticFriction(), myObjectB.getKineticFriction());
	}

	protected double computeRVProjection() {
		return getRelativeVelocity().dot(myNormal);
	}

	protected Vector getRelativeVelocity() {
		return myObjectB.getVelocity().minus(myObjectA.getVelocity());
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
