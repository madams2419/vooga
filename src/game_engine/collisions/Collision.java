package game_engine.collisions;

import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.collisions.resolvers.ICollisionResolver;
import game_engine.sprite.Sprite;

public class Collision {
	
	private Sprite spriteA, spriteB;
	
	private ICollisionDetector detector;
	private ICollisionResolver resolver;
	
	public Collision(ICollisionDetector detect, ICollisionResolver resolve) {
		detector = detect;
		resolver = resolve;
	}
	
	public void update() {
		if (detector.detectCollision(spriteA, spriteB)) {
			resolver.resolveCollision(spriteA, spriteB);
		}
	}
}