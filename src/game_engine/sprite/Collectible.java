package game_engine.sprite;

public class Collectible extends Sprite{
    
    private boolean canCollect;
    
    public Collectible() {
        super();
       
      }

   
      public Collectible(String name){
          super(name);
          
      }
    
      public Collectible(String name, int id){
          super(name,id);
         
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
