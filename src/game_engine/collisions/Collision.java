// This entire file is part of my masterpiece.
// Tony Qiao
package game_engine.collisions;

import game_engine.collisions.detectors.ICollisionDetector;
import game_engine.collisions.resolvers.ICollisionResolver;

public class Collision <T> {
	private T a, b;
	
	private ICollisionDetector<T> detector;
	private ICollisionResolver<T> resolver;
	
	public Collision(ICollisionDetector<T> detect, ICollisionResolver<T> resolve, T a, T b) {
		detector = detect;
		resolver = resolve;
		this.a = a;
		this.b = b;
	}
	
	public void update() {
		if (detector.detectCollision(a, b)) {
			resolver.resolveCollision(a,b );
		}
	}
	
	public boolean involves (T t) {
	    return a == t || b == t;
	}
}