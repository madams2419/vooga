package authoring;

public class Interaction {
    private Sprite mySprite1, mySprite2;
    private String myAction1, myAction2;

    Interaction (Sprite sprite1, Sprite sprite2) {
        mySprite1 = sprite1;
        mySprite2 = sprite2;
    }

    void setActions (String action1, String action2) {
        myAction1 = action1;
        myAction2 = action2;
    }

    String getAction1 () {
        return myAction1;
    }
    
    String getAction2 () {
        return myAction2;
    }
}
