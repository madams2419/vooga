package authoring;

public class Interaction {
    private Sprite mySprite1, mySprite2;
    private String myAction;

    Interaction (Sprite sprite1, Sprite sprite2) {
        mySprite1 = sprite1;
        mySprite2 = sprite2;
    }

    void setAction (String action) {
        myAction = action;
    }

    String getAction () {
        return myAction;
    }
}
