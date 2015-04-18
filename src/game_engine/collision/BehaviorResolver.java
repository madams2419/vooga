package game_engine.collision;

import game_engine.behaviors.IBehavior;


public class BehaviorResolver implements ICollisionResolver{
	
	private IBehavior behavior;

	public BehaviorResolver(IBehavior b) {
		behavior = b;
	}

	public void execute() {
		behavior.perform();
		
	}

}
