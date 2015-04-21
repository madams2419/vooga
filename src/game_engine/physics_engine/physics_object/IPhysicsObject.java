package game_engine.physics_engine.physics_object;

import java.util.Observable;

import game_engine.physics_engine.Vector;

/**
 * physics object interface
 * note: we have included multiple implementations of IPhysicsObjects (simple and complex)
 * to illustrate the flexibility of our game engine
 */
public abstract class IPhysicsObject extends Observable {
    private double xPosition, yPosition;
	public abstract void update();
	public double getXPosition() {
	    return xPosition;
	}
	public double getYPosition() {
	    return yPosition;
	}
	
	public abstract void move(Vector amount);
}
