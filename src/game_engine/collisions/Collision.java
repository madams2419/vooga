package game_engine.collisions;

import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.collisions.resolvers.ICollisionResolver;
import game_engine.sprite.Sprite;

public class Collision {
	
	private Sprite spriteA, spriteB;
	
	private ICollisionDetector detector;
	private ICollisionResolver resolver;
	
	public Collision(ICollisionDetector detect, ICollisionResolver resolve, Sprite a, Sprite b) {
		detector = detect;
		resolver = resolve;
		spriteA = a;
		spriteB = b;
	}
	
	public void update() {
		if (detector.detectCollision(spriteA, spriteB)) {
			resolver.resolveCollision(spriteA, spriteB);
		}
	}
	
	public boolean involves (Sprite sprite) {
	    return spriteA == sprite || spriteB == sprite;
	}
}