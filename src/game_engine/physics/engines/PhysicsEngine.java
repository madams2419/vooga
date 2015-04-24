package game_engine.physics.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics.Vector;
import game_engine.physics.objects.PhysicsObject;

public class PhysicsEngine implements IActor {
	
	private List<Vector> globalAcceleration, globalForce;
	private double lastUpdateTime, timeLapse;
	
	public Vector getGlobalAccel() {
		return Vector.sum(globalAcceleration);
	}
	
	public Vector getGlobalForce() {
		return Vector.sum(globalForce);
	}
	
	public void addGlobalAccel(Vector accel) {
		globalAcceleration.add(accel);
	}
	
	public void addGlobalForce(Vector force) {
		globalForce.add(force);
	}
	
	public BiFunction<Double, Vector, Vector> getDependentForces() {
		return (a, b) -> new Vector(0, 0);
	}
	
	public void update() {
		double currentTime = System.currentTimeMillis();
		double timeLapse = currentTime - lastUpdateTime;
		timeLapse = timeLapse < 0 ? currentTime : timeLapse;
		lastUpdateTime = currentTime;
	}
	
	public double getTimeLapse() {
		return timeLapse;
	}
	
	private IAction setGlobalAccel = (params) -> {
		globalAcceleration = new ArrayList<>();
		globalAcceleration.add(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	private IAction setGlobalForce = (params) -> {
		globalForce = new ArrayList<>();
		globalForce.add(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};

	public IAction getAction(String name) {
		return name.equals("setGlobalAccel") ? setGlobalAccel : name.equals("setGlobalForce") ? setGlobalForce : null;
	}
	
	public void resolveCollision(PhysicsObject spriteA, PhysicsObject spriteB) {
		
	}
}