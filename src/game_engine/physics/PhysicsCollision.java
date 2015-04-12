package game_engine.physics;

public abstract class PhysicsCollision {

	protected PhysicsObject myObjectA;
	protected PhysicsObject myObjectB;
	protected Vector myNormal;
	protected double myPenetrationDepth;

	public PhysicsCollision(PhysicsObject objectA, PhysicsObject objectB) {
		myObjectA = objectA;
		myObjectB = objectB;
		computeNormal();
		computePenetrationDepth();
	}

	public boolean collide() {
		return (myPenetrationDepth >= 0);
	}

	protected abstract Vector computeNormal();

	protected abstract double computePenetrationDepth();

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

}
