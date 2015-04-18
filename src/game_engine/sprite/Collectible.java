package game_engine.sprite;

import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RigidBody.RBodyType;



public class Collectible extends Sprite{
    
    private int myCount;
    private boolean canCollect;
    
    public Collectible(String defaultState, String defaultImage, int height, int width, RBodyType rbType,
		PhysicsEngine globalPhysics, Material material, int startX, int startY) {
	super(defaultState, defaultImage, height, width, rbType, globalPhysics, material, startX, startY);
}
    
//    public Collectible(PhysicsObject physics) {
//        super(physics);
//        myCount =0;
//       
//      }
//
//   
//      public Collectible(PhysicsObject physics, String name){
//          super(physics,name);
//          myCount =0;
//      }
//    
//      public Collectible(PhysicsObject physics,String name, int id){
//          super(physics,name,id);
//          myCount = 0;
//      }
      
      public void collect(int amount){
          myCount+=amount;
      }
      
      public void decrement(int amount){
          myCount -= amount;
      }
      
      public int getCount(){
          return myCount;
      }
      
      public void resetCount(){
          myCount = 0;
         
      }
      
      public void setCollectible(boolean canCollect){
          this.canCollect = canCollect;
      }
      
      public boolean isCollectible(){
          return canCollect;
      }
      
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
