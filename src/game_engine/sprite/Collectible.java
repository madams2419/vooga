package game_engine.sprite;

import game_engine.physics.PhysicsObject;

public class Collectible extends Sprite{
    
    private int myCount;
    private boolean canCollect;
    
    public Collectible(PhysicsObject physics) {
        super(physics);
       
      }

   
      public Collectible(PhysicsObject physics, String name){
          super(physics,name);
          
      }
    
      public Collectible(PhysicsObject physics,String name, int id){
          super(physics,name,id);
      }
      
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
