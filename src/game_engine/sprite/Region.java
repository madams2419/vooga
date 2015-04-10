package game_engine.sprite;

import game_engine.IBehavior;
import game_engine.physics.PhysicsObject;

public class Region extends Sprite implements IRegion{

    public Region(PhysicsObject physics) {
        super(physics);
      }

      public Region(PhysicsObject physics, String name){
          super(physics, name);
      }
    
      public Region(PhysicsObject physics, String name, int id){
          super(physics,name,id);
      }
      
      private IBehavior changePhysics = (params) ->{
          //Change corresponding physics params
      };
      
      public IBehavior getChangePhysics(){
          return this.changePhysics;
      }
      
      private IBehavior negatePhysics = (params) ->{
          
      };
      
      public IBehavior getNegatePhysics(){
          return this.negatePhysics;
      }
      
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void setGravity (double gravity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getGravity () {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setDrag (double drag) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getDrag () {
        // TODO Auto-generated method stub
        return 0;
    }

}
