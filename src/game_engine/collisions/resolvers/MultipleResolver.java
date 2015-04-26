package game_engine.collisions.resolvers;

import java.util.ArrayList;
import java.util.List;

import game_engine.sprite.Sprite;

public class MultipleResolver implements ICollisionResolver {
	
	private List<ICollisionResolver> resolvers;
	
	public MultipleResolver() {
		resolvers = new ArrayList<>();
	}
	
	public MultipleResolver(List<ICollisionResolver> list) {
		resolvers = list;
	}
	
	public void addResolver(ICollisionResolver resolver) {
		resolvers.add(resolver);
	}
	
	public void resolveCollision(Sprite spriteA, Sprite spriteB) {
		for (ICollisionResolver resolver : resolvers) {
			resolver.resolveCollision(spriteA, spriteB);
		}
	}
}