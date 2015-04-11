package game_engine.sprite;

import game_engine.physics.PhysicsObject;

public class Obstacle extends Sprite {

	public Obstacle (PhysicsObject physics) {
        super(physics);
        // TODO Auto-generated constructor stub
    }
	public Obstacle(PhysicsObject physics, String name){
            super(physics, name);
    }

    public Obstacle(PhysicsObject physics, String name, int id){
            super(physics, name,id);
    }

    @Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
