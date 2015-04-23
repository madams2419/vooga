package game_engine.physics_engine.physics_objects;

import java.util.Observable;
import java.util.function.Supplier;

import game_engine.hitboxes.IHitbox;
import game_engine.physics_engine.PhysicsEngine;
import game_engine.physics_engine.Vector;
import game_engine.sprite.Animation;

/**
 * PhysicsObject represents the bare minimum traits that a designer may way to
 * include in their game. All PhysicsObjects must have a position so that Sprite
 * and Animation can accurately render the environment for the player.
 * 
 * @author Brian Lavallee
 * @author Emre Sonmez
 * @author Mike Adams
 * 
 * @since 23 April 2015
 */
public class PhysicsObject extends Observable {

	private double xPosition, yPosition;
	private IHitbox hitbox;
	private PhysicsEngine engine;
	
	public PhysicsObject(PhysicsEngine physEng, IHitbox hb, Vector position, Animation animation) {
		engine = physEng;
		hitbox = hb;
		xPosition = position.getX();
		yPosition = position.getY();
		hitbox.addPositionSupplier(getPositionSupplier());
		addObserver(animation);
	}
	
	protected Supplier<Vector> getPositionSupplier() {
		return () -> new Vector(xPosition, yPosition);
	}

	/**
	 * Allows for the PhysicsObject to be updated frame by frame.
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}

	public double getXPosition() {
		return xPosition;
	}

	public double getYPosition() {
		return yPosition;
	}
	
	public IHitbox getHitbox() {
		return hitbox;
	}
	
	protected PhysicsEngine getEngine() {
		return engine;
	}

	/**
	 * Allows for the position of the PhysicsObject to be changed in some way.
	 * This is different depending on the implementation.
	 * 
	 * @param amount
	 *            Potentially a displacement, but may also be implemented as a
	 *            velocity, acceleration, force, etcetera.
	 */
	public void move(Vector amount) {
		xPosition = amount.getX();
		yPosition = amount.getY();
	}
	
	/**
	 * Allows for the position of the PhysicsObject to be changed in some way.
	 * Differs depending on implementation.  Suggested that increment adds to the
	 * existing variable amount while move completely resets it.
	 * 
	 * @param amount
	 * 			Potentially a displacement, but may also be implemented as a
	 *            velocity, acceleration, force, etcetera.
	 */
	public void increment(Vector amount) {
		xPosition += amount.getX();
		yPosition += amount.getY();
	}
}