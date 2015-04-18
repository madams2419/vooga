package game_engine.sprite;

import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RigidBody.RBodyType;
import game_player.Animation;

public class Enemy extends Character {
	
	public Enemy(String defaultState, String defaultImage, int height, int width, RBodyType rbType,
			PhysicsEngine globalPhysics, Material material, int startX, int startY) {
		super(defaultState, defaultImage, height, width, rbType, globalPhysics, material, startX, startY);
	}
	
    public Enemy(PhysicsObject physics) {
      super(physics);
    }
 
    public Enemy(PhysicsObject physics, String name){
        super(physics, name);
    }
  
    public Enemy(PhysicsObject physics, String name, int id){
        super(physics, name,id);
    }
	@Override
	public void update() {
		// TODO Auto-generated method stub
	    this.getPhysicsObject().update();
	    setChanged();
            notifyObservers();
	}

}
