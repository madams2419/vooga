package game_engine.sprite;

public class Platform extends Collectible{
    
    public Platform() {
        super();
        setCollectible(false);
      }

   
      public Platform(String name){
          super(name);
          setCollectible(false);
      }
    
      public Platform(String name, int id){
          super(name,id);
          setCollectible(false);
      }
      

}
