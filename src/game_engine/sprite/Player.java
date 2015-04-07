package game_engine.sprite;

import game_engine.IBehavior;

import java.util.Observable;

public class Player extends Sprite {

    public Player() {
        super();
      }

   
      public Player(String name){
          super(name);
      }
    
      public Player(String name, int id){
          super(name,id);
      }
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	// movement
	private IBehavior moveForward = (params) -> {
		myPhysicsObject.setX(myPhysicsObject.getX() + params[0]);
	};
}
