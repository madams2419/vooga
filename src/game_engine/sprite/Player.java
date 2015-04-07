package game_engine.sprite;

import game_engine.Behavior;

import java.util.Observable;

public class Player extends Sprite {

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	// movement
	private Behavior moveForward = (params) -> {
		myPhysicsParams.setX(myPhysicsParams.getX() + params[0]);
	};
	p
}
