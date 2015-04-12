package game_engine.physics;

public abstract class PhysicsCollision {

	private PhysicsObject myPOA;
	private PhysicsObject myPOB;
	private Vector myNormal;
	private double myPenetrationDepth;

	public PhysicsCollision(PhysicsObject poA, PhysicsObject poB) {
		myPOA = poA;
		myPOB = poB;
	}

	public boolean collide() {
		analyzeCollision();
		return (myPenetrationDepth >= 0);
	}

	protected abstract Vector computeNormal();

	protected abstract double getPenetrationDepth();

	public PhysicsObject getPhysicsObjectA() {
		return myPOA;
	}

	public PhysicsObject getPhysicsObjectB() {
		return myPOB;
	}

	public Vector getNormal() {
		return myNormal;
	}

	public double getPenetrationDepth() {
		return myPenetrationDepth;
	}

}
