package game_engine.physics.objects;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Supplier;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;
import game_engine.sprite.Sprite;

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
public abstract class PhysicsObject extends Observable implements Observer {

	private double xPosition, yPosition;
	private Map<String, List<IHitbox>> hitboxes;
	private IHitbox activeHitbox;
	private PhysicsEngine engine;
	private double lastUpdateTime;
	private String currentState;
	
	public PhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> map, Vector position, Animation animation) {
		engine = physEng;
		hitboxes = map;
		xPosition = position.getX();
		yPosition = position.getY();
		hitboxes.keySet().forEach((name) -> hitboxes.get(name).forEach((hitbox) -> hitbox.addPositionSupplier(getPositionSupplier())));
		addObserver(animation);
		animation.addObserver(this);
	}
	
	protected Supplier<Vector> getPositionSupplier() {
		return () -> new Vector(xPosition, yPosition);
	}
	
//	protected double getTimeLapse(double currentTime) {
//		return currentTime < lastUpdateTime ? currentTime : currentTime - lastUpdateTime;
//	}

	/**
	 * Allows for the PhysicsObject to be updated frame by frame.
	 */
	public void update(double currentTime) {
		lastUpdateTime = currentTime;
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
		return activeHitbox;
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
	public void set(Vector amount) {
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
	
	public abstract void applyImpulse(Vector impulse);
	
	public void update(Observable arg0, Object arg1) {
		updateSprite(arg0);
		updateAnimation(arg0);
	}
	
	private void updateSprite(Observable source) {
		try {
			Sprite sprite = (Sprite) source;
			currentState = sprite.getState();
			activeHitbox = hitboxes.get(currentState).get(0);
		}
		catch (Exception e) {
			// do nothing
		}
	}
	
	private void updateAnimation(Observable source) {
		try {
			Animation animation = (Animation) source;
			activeHitbox = hitboxes.get(currentState).get(animation.getIndex());
		}
		catch (Exception e) {
			// do nothing
		}
	}
}