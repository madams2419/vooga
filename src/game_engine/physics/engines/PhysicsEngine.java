package game_engine.physics.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics.Vector;
import game_engine.physics.objects.SimplePhysicsObject;

public class PhysicsEngine implements IActor {
	
	private List<Vector> globalAcceleration, globalForce;
	
	public PhysicsEngine() {
		globalAcceleration = new ArrayList<>();
		globalForce = new ArrayList<>();
	}
	
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
	
	public void resolveCollision(SimplePhysicsObject spriteA, SimplePhysicsObject spriteB) {
		if(verticalCollision(spriteA,spriteB)){
			setVerticalEdgeVelocity(spriteA);
			setVerticalEdgeVelocity(spriteB);
		}else{
			setHorizontalEdgeVelocity(spriteA);
			setHorizontalEdgeVelocity(spriteB);
		}
	}
	
	private void setVerticalEdgeVelocity(SimplePhysicsObject sprite){
		if(sprite.getVelocity().getMagnitude() > 0){
			sprite.setMinMinorVelocity(0);
		}else{
			sprite.setMaxMinorVelocity(0);
		}
	}
	private void setHorizontalEdgeVelocity(SimplePhysicsObject sprite){
		if(sprite.getVelocity().getMagnitude() > 0){
			sprite.setMinMajorVelocity(0);
		}else{
			sprite.setMaxMajorVelocity(0);
		}
	}
	
	/**
	 * returns true if collision is vertical
	 * @param spriteA
	 * @param spriteB
	 * @return
	 */
	private boolean verticalCollision(SimplePhysicsObject spriteA, SimplePhysicsObject spriteB){
		List<Vector> boundingBoxA =  spriteA.getHitbox().getBoundingBox(); // lower left upper right
		Vector lowerLeftA = boundingBoxA.get(0);
		Vector upperRightA = boundingBoxA.get(1);
		List<Vector> boundingBoxB = spriteB.getHitbox().getBoundingBox();
		Vector lowerLeftB = boundingBoxB.get(0);
		Vector upperRightB = boundingBoxB.get(1);;
//		if(((lowerLeftA.getY() < upperRightB.getY()) && (upperRightA.getY() > upperRightB.getY())) | 
//				((lowerLeftB.getY() < upperRightA.getY()) && (upperRightB.getY() > upperRightA.getY()))){
		if(((lowerLeftA.getY() < upperRightB.getY()) | (upperRightB.getY() > upperRightA.getY()))){
			System.out.println("here" + lowerLeftA.getY());
			return true;
		}else{
			System.out.println("false");
			return false;
		}
	}
	
	/**
	 * returns whether sprite is moving vertically or horizontally
	 * @param incidentVector
	 * @return
	 */
	private boolean movingHorizontal(Vector incidentVector){
		double difference = Math.abs(incidentVector.getX()) - Math.abs(incidentVector.getY());
		if(difference > 0){ // moving in x direction
			return true;
		}
		return false;
	}
	
	private boolean movingUp(Vector incidentVector){
		return(incidentVector.getY() > 0);
	}
	
	private Vector reverseVector(Vector incidentVector){
		double x = incidentVector.getX();
		double y = incidentVector.getY();
		double magnitude = incidentVector.getMagnitude();
		double angle = incidentVector.getAngleDegrees();
		System.out.println(angle);
		if(x*y < 0){
			angle -= 90; // assuming counter clockwise coordinate system
		}else{
			angle += 90;
		}
		double newX = magnitude*Math.cos(Math.toRadians(angle));
		double newY = magnitude*Math.sin(Math.toRadians(angle));
		return new Vector(newX,newY);
	}
}