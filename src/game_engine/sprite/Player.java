package game_engine.sprite;

import game_engine.IBehavior;
import game_engine.physics.Vector;
import java.util.Observable;

public class Player extends Character {
	public Player(String name) {
		super(name);
	}
	
	public Player(String name, int id){
		super(name,id);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}	
	
}
