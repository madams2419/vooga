// This entire file is part of my masterpiece.
// Tony Qiao
package game_engine.collisions.resolvers;

import java.util.ArrayList;
import java.util.List;

public class MultipleResolver<T> implements ICollisionResolver<T> {
	
	private List<ICollisionResolver<T>> resolvers;
	
	public MultipleResolver() {
		resolvers = new ArrayList<>();
	}
	
	public MultipleResolver(List<ICollisionResolver<T>> list) {
		resolvers = list;
	}
	
	public void addResolver(ICollisionResolver<T> resolver) {
		resolvers.add(resolver);
	}
	
	public void resolveCollision(T spriteA, T spriteB) {
		for (ICollisionResolver<T> resolver : resolvers) {
			resolver.resolveCollision(spriteA, spriteB);
		}
	}
}