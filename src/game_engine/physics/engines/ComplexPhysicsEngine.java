package game_engine.physics.engines;

import java.util.function.BiFunction;

import game_engine.physics.Vector;
import game_engine.physics.objects.PhysicsObject;

public class ComplexPhysicsEngine extends PhysicsEngine {
	
	private double dragCoefficient;
	
	public ComplexPhysicsEngine(double drag) {
		dragCoefficient = drag;
	}
	
//	public void resolveCollision(PhysicsObject spriteA, PhysicsObject spriteB) {
//		
//	}
	
	public BiFunction<Double, Vector, Vector> getDependentForces() {
		return (area, velocity) -> {
			double scale = Math.pow(velocity.getMagnitude(), 2) * 0.5 * dragCoefficient * area;
			return velocity.times(scale).negate();
		};
	}
}