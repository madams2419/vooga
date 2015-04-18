package game_engine.sprite;

import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RigidBody.RBodyType;

public class Platform extends Collectible{
    
    public Platform(String defaultState, String defaultImage, int height, int width, RBodyType rbType,
		PhysicsEngine globalPhysics, Material material, int startX, int startY) {
	super(defaultState, defaultImage, height, width, rbType, globalPhysics, material, startX, startY);
}
    
//    public Platform(PhysicsObject physics) {
//        super(physics);
//        setCollectible(false);
//      }
//
//   
//      public Platform(PhysicsObject physics, String name){
//          super(physics, name);
//          setCollectible(false);
//      }
//    
//      public Platform(PhysicsObject physics, String name, int id){
//          super(physics, name,id);
//          setCollectible(false);
//      }
      

}
