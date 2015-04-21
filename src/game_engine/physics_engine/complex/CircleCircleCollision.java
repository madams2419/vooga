package game_engine.physics_engine.complex;

import game_engine.physics_engine.physics_object.complex_physics_object.ComplexPhysicsObject;

public class CircleCircleCollision extends PhysicsCollision {

	private CircleBody myCircleA;
	private CircleBody myCircleB;

	public CircleCircleCollision(ComplexPhysicsObject poA, ComplexPhysicsObject poB) {
		super(poA, poB);
		myCircleA = (CircleBody) poA.getRigidBody();
		myCircleB = (CircleBody) poB.getRigidBody();
	}

	protected Vector computeNormal() {
		return getSeparationVector().normalize();
	}

	protected double computePenetrationDepth() {
		double radiiSum = myCircleA.getRadius() + myCircleB.getRadius();
		double test =  radiiSum - getSeparationDistance();
		getSeparationDistance();
		return test;
	}

}


