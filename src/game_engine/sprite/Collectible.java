package game_engine.sprite;

import game_engine.physics.PhysicsObject;

public class Collectible extends Sprite{
    
    private int myCount;
    private boolean canCollect;
<<<<<<< HEAD
    
    public Collectible(PhysicsObject physics) {
        super(physics);
       
      }

   
      public Collectible(PhysicsObject physics, String name){
          super(physics,name);
          
      }
    
      public Collectible(PhysicsObject physics,String name, int id){
          super(physics,name,id);
=======

    public Collectible() {
        super();
        myCount = 0;
    }

   
      public Collectible(String name){
          super(name);
          myCount = 0;
      }
    
      public Collectible(String name, int id){
          super(name,id);
          myCount = 0;
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
>>>>>>> 7d5e946f9ea3ce34ddf23ca286c9b20436d7254d
         
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
