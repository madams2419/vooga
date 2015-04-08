package src.authoring;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


/***
 * Class that contains information about a specific sprite for eventually generating xml files
 * A specific sprite contains specific information about a sprite, such as its position. It does not
 * contain general information such as its velocity.
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */
public class SpecificSprite extends AbstractSprite {

    private Map<String, Double> myPosition;
    private String myType;

    public SpecificSprite () {
        myPosition = new HashMap<>();
    }

    public SpecificSprite (SpriteType sprite, int ID, Consumer<AbstractSprite> spriteClicked) {
        super(ID, sprite.getImageURI(), spriteClicked);
    }

    public void setXPosition (double value) {
        myPosition.put(X_STRING, value);
    }

    public void setYPosition (double value) {
        myPosition.put(Y_STRING, value);
    }

    public double getXPosition () {
        return myPosition.get(X_STRING);
    }

    public double getYPosition () {
        return myPosition.get(Y_STRING);
    }

}
