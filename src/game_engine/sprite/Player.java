package game_engine.sprite;

import game_engine.IBehavior;
import game_engine.physics.Vector;

import java.util.Observable;

public class Player extends Sprite {
	private Vector myPosition = myPhysicsObject.getPosition();
	
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
	
	// params[0] is pixels to move forward
	private IBehavior moveForward = (params) -> { // movement
		myPosition.setX(params[0]);
	};

	// params[0] is upward acceleration change
	private IBehavior Jump = (params) -> { // movement
		Vector myVector = new Vector()
		myPhysicsObject.applyImpulse();;
	};
}
