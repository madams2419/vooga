package authoring;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Natalie
 *
 */

public class SpriteManager {
    Map<String, SpriteElement> mySprites;
    SpriteElement currentSprite;

    SpriteManager () {
        mySprites = new HashMap<>();
    }

    SpriteElement get (String spriteName) {
        SpriteElement sprite = mySprites.get(spriteName);
        if (sprite == null) {
            sprite = new SpriteElement();
            mySprites.put(spriteName, sprite);
        }
        return sprite;
    }
    
    void setCurrentSprite (String sprite) {
        currentSprite = mySprites.get(sprite);
    }
    
    SpriteElement getCurrentSprite () {
        return currentSprite;
    }
}
