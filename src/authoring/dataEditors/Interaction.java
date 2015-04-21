package authoring.dataEditors;


/**
 * A class to represent the interaction between two sprites.
 * 
 * @author Natalie
 *
 */
public class Interaction {
    private Sprite mySprite1, mySprite2;
    private String myAction1, myAction2;

    public Interaction (Sprite sprite1, Sprite sprite2) {
        mySprite1 = sprite1;
        mySprite2 = sprite2;
    }
    
    public Interaction (Sprite sprite1, Sprite sprite2, String action1, String action2) {
        this(sprite1, sprite2);
        setActions(action1, action2);
    }

    public void setActions (String action1, String action2) {
        myAction1 = action1;
        myAction2 = action2;
    }

    public String getAction1 () {
        return myAction1;
    }
    
    public String getAction2 () {
        return myAction2;
    }

    public Interaction getOpposite () {
        return new Interaction(mySprite2, mySprite1, myAction2, myAction1);
    }
}
