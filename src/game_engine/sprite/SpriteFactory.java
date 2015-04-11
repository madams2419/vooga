package game_engine.sprite;

public class SpriteFactory {
    
    public SpriteFactory(){
        
    }
    
    public Sprite createSprite(String spriteType) throws InstantiationException, IllegalAccessException{
       Class<?> runClass = null;
       Sprite classInstance = null;
       String className =  "game_engine.sprite." + spriteType;
       try {
        runClass = Class.forName(className);
    }
    catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
        return classInstance =  (Sprite) runClass.newInstance();   
    }
}
