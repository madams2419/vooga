package game_engine.physics;

import game_engine.HitBox;
import java.util.ArrayList;
import java.util.List;

// TODO
// - ability to run back time...some mechanism to do that

public class PhysicsObject {

	private double myInvMass;
	private Material myMaterial;
	private HitBox myHitBox;
	private String myState;
	private Vector myPosition;
	private Vector myVelocity;
	private Vector myAccel;
	private Vector myNetInternalForce;
	private double myDirForceMagnitude;
	private List<Joint> myJoints;
	private PhysicsEngine myPhysics;
	
	public PhysicsObject(PhysicsEngine physics, Material material, HitBox hitBox, String state, Vector position, Vector velocity) {
	
	private Shape myShape;

	public PhysicsObject(PhysicsEngine physics, Shape shape, Material material, HitBox hitBox, String state, Vector position, Vector velocity) {
		setMaterial(material);
		setHitBox(hitBox);
		setState(state);
		setPosition(position);
		setVelocity(velocity);
		myInvMass = computeInvMass();
		myAccel = computeAccel();
		myShape = shape;
	}

	public PhysicsObject(PhysicsEngine physics, Shape shape, Material material, HitBox hitBox, String state, int xPos, int yPos) {
		this(physics, shape, material, hitBox, state, new Vector(xPos, yPos), new Vector());
	}

	public void update() {
		double dt = myPhysics.getTimeStep();
		myAccel = computeAccel();
		myVelocity = myVelocity.plus(myAccel).times(dt);
		myPosition = myPosition.plus(myVelocity).times(dt);
	}

	private double computeInvMass() {
		double mass = myMaterial.getDensity() * myHitBox.getVolume();
		return 1/mass;
	}

	private Vector computeAccel() {
		Vector netForce = computeNetForce();
		return netForce.times(myInvMass);
	}

	private Vector computeNetForce() {
		// compute internal directional force
		Vector dirForce = computeDirectionalForce();
		// get net global force
		Vector extSum = myPhysics.getNetGlobalForce();

		return myNetInternalForce.plus(dirForce).plus(extSum);
	}

	private Vector computeDirectionalForce() {
		Vector direction = myVelocity.normalize();
		return direction.times(myDirForceMagnitude);
	}

	public void addForce(Vector force) {
		myNetInternalForce.plus(force);
	}

	public void removeForce(Vector force) {
		myNetInternalForce.minus(force);
	}

	public void addDirectionalForce(double magnitude) {
		myDirForceMagnitude += magnitude;
	}

	public void removeDirectionalForce(double magnitude) {
		myDirForceMagnitude -= magnitude;
	}

	public void applyImpulse(Vector impulse) {
		Vector newVelocity = myVelocity.plus(impulse.times(myInvMass));
		setVelocity(newVelocity);
	}

	public Vector getPosition() {
		return myPosition;
	}

	public void setPosition(Vector position) {
		myPosition = position;
	}
	
	public double getX() {
		return myPosition.getX();
	}
	
	public double getY() {
		return myPosition.getY();
	}
	
	public void setX(double x) {
		myPosition.setX(x);
	}
	
	public void setY(double y) {
		myPosition.setY(y);
	}

	public Vector getVelocity() {
		return myVelocity;
	}

	public void setVelocity(Vector velocity) {
		myVelocity = velocity;
	}

	public Vector getAccel() {
		return myAccel;
	}

	public double getMass() {
		return 1/myInvMass;
	}

	public double getInvMass() {
		return myInvMass;
	}

	public void setRestitution(double restitution) {
		myMaterial.setRestitution(restitution);
	}

	public double getRestitution() {
		return myMaterial.getRestitution();
	}

	public void setMaterial(Material material) {
		myMaterial = material;
		myInvMass = computeInvMass();
	}

	public void setHitBox(HitBox hitBox) {
		myHitBox = hitBox;
		myInvMass = computeInvMass();
	}

	public void setState(String state) {
		myState = state;
	}

	public String getState(String state) {
		return myState;
	}
	
	public Shape getShape() {
		return myShape;
	}
	
	public void setShape(Shape shape) {
		myShape = shape;
		myInvMass = computeInvMass();
	}

}
