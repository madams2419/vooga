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

	private Vector position;
	private Map<String, List<IHitbox>> hitboxes;
	private IHitbox activeHitbox;
	private PhysicsEngine engine;
	private String currentState;
	
	public PhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> map, Vector pos, Animation animation) {
		engine = physEng;
		hitboxes = map;
		position = pos;
		hitboxes.keySet().forEach((name) -> hitboxes.get(name).forEach((hitbox) -> hitbox.addPositionSupplier(getPositionSupplier())));
		addObserver(animation);
		animation.addObserver(this);
	}
	
	protected Supplier<Vector> getPositionSupplier() {
		return () -> position;
	}

	public void ignorePhysics(Observer o) {
		deleteObserver(o);
	}
	
	public void update(double timeLapse) {
		setChanged();
		notifyObservers();
	}
	
	public Vector getPosition() {
		return getPositionSupplier().get();
	}
	
	public IHitbox getHitbox() {
		return activeHitbox;
	}
	
	protected PhysicsEngine getEngine() {
		return engine;
	}
	
	protected void setPosition(Vector pos) {
		position = pos;
	}
	
	protected void incrementPosition(Vector amount) {
		setPosition(position.plus(amount));
	}
	
	public void update(Observable source, Object argument) {
		updateSprite(source);
		updateAnimation(source);
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