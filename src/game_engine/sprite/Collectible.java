package game_engine.sprite;

import game_engine.IAction;

public class Collectible extends Sprite{
    
    private int myCount;
    private boolean canCollect;

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
