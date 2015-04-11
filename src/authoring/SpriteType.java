package authoring;

import java.util.HashMap;
import java.util.Map;


/***
 * Class that contains information about a sprite type for eventually generating xml files.
 * A sprite type is a kind of sprite. It does not contain specific information such as the position
 * on a level.
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */
public class SpriteType extends AbstractSprite {

    private Map<String, Double> myVelocity;
    private Map<String, String> myKeyActions;
    private Map<String, String> myCharacteristics;

    public SpriteType (int ID, String imageURI) {
        super(ID, imageURI);
    }

    public SpriteType () {
        myVelocity = new HashMap<>();
        myKeyActions = new HashMap<>();
    }

    public SpriteType (SpriteType sprite, int ID) {
        super(ID, sprite.getImageURI(), sprite.getConsumer());
    }

    public void setXVelocity (double value) {
        myVelocity.put(AbstractSprite.X_STRING, value);
    }

    public void setYVelocity (double value) {
        myVelocity.put(AbstractSprite.Y_STRING, value);
    }

    public void setKeyControl (String action, String result) {
        myKeyActions.put(action, result);
    }

    public void setCharacteristic (String characteristic, String value) {
        myCharacteristics.put(characteristic, value);
    }

    public String getCharacteristic (String characteristic) {
        return myCharacteristics.get(characteristic);
    }

}
