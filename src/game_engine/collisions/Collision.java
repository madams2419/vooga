package game_engine.collisions;

import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.collisions.resolvers.ICollisionResolver;

public class Collision {
	
	private ICollisionDetector detector;
	private ICollisionResolver resolver;
	
	public Collision(ICollisionDetector detect, ICollisionResolver resolve) {
		detector = detect;
		resolver = resolve;
	}
	
	public void update() {
		if (detector.detectCollision()) {
			resolver.resolveCollision();
		}
	}
}