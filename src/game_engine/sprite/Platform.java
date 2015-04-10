package game_engine.sprite;

import game_engine.physics.PhysicsObject;

public class Platform extends Collectible{
    
    public Platform(PhysicsObject physics) {
        super(physics);
        setCollectible(false);
      }

   
      public Platform(PhysicsObject physics, String name){
          super(physics, name);
          setCollectible(false);
      }
    
      public Platform(PhysicsObject physics, String name, int id){
          super(physics, name,id);
          setCollectible(false);
      }
      

}
