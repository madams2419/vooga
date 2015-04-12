package game_engine.sprite;


import game_engine.physics.PhysicsObject;

public class Enemy extends Character {
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
