package authoring;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that keeps track of the interactions between sprites. 
 * 
 * @author Natalie
 *
 */
public class InteractionManager {
    private static InteractionManager myInstance;
    private Map<Sprite, Map<Sprite, Interaction>> myInteractions;

    public static InteractionManager getInstance () {
        if (myInstance == null)
            myInstance = new InteractionManager();
        return myInstance;
    }

    public Interaction getInteraction (Sprite sprite1, Sprite sprite2) {
        return myInteractions.get(sprite1).get(sprite2);
    }

    public void setInteraction (Sprite sprite1, Sprite sprite2, Interaction interaction) {
        addMiniSpriteMap(sprite1, sprite2, interaction);
        addMiniSpriteMap(sprite2, sprite1, interaction);
    }

    private void addMiniSpriteMap (Sprite sprite1, Sprite sprite2, Interaction interaction) {
        Map<Sprite, Interaction> sprite1map = myInteractions.get(sprite1);
        if (sprite1map == null) {
            sprite1map = new HashMap<>();
        }
        sprite1map.put(sprite2, interaction);
    }
}
