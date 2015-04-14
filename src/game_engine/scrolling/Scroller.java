package game_engine.scrolling;

import javafx.scene.Group;

public abstract class Scroller implements IScroller{
    private Group myGroup;
    
    public Scroller (Group group) {
        myGroup = group;
    }
    
    public void scrollX (double amount) {
        myGroup.setTranslateX(myGroup.getTranslateX() - amount);
    }
    
    public void scrollY (double amount) {
        myGroup.setTranslateY(myGroup.getTranslateY() - amount);
    }
}
