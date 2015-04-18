package game_engine.sprite;

import game_engine.behaviors.IAction;
import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics.RigidBody.RBodyType;



public class Region extends Sprite implements IRegion{

    
    public Region(String defaultState, String defaultImage, int height, int width, RBodyType rbType,
		PhysicsEngine globalPhysics, Material material, int startX, int startY) {
	super(defaultState, defaultImage, height, width, rbType, globalPhysics, material, startX, startY);
}
//    public Region(PhysicsObject physics) {
//        super(physics);
//      }
//
//      public Region(PhysicsObject physics, String name){
//          super(physics, name);
//      }
//    
//      public Region(PhysicsObject physics, String name, int id){
//          super(physics,name,id);
//      }
      
      private IAction changePhysics = (params) ->{
          //Change corresponding physics params
      };
      
      public IAction getChangePhysics(){
          return this.changePhysics;
      }
      
      private IAction negatePhysics = (params) ->{
          
      };
      
      public IAction getNegatePhysics(){
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