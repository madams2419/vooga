package game_engine.scrolling.scroller;

import javafx.scene.Group;
import javafx.scene.Node;


/**
 * 
 * Implements IScroller. Scrolls by translating group in the opposite direction. Scrolling is
 * instantaneous.
 * 
 * @author Tony
 *
 */
public class BasicScroller implements IScroller {
    private Group myGroup;
    
    /**
     * Constructor
     * @param group Target of the scrolling.
     */
    public BasicScroller (Group group) {
        myGroup = group;
    }
    
    @Override
    public void scroll (double changeX, double changeY) {
        myGroup.setTranslateX(myGroup.getTranslateX() - changeX);
        myGroup.setTranslateY(myGroup.getTranslateY() - changeY);
    }

    @Override
    public Group getGroup () {
        return myGroup;
    }

    @Override
    public void addBackgroundImage (Node node, double ratio) {
        node.translateXProperty().bind(myGroup.translateXProperty().multiply(ratio));
        node.translateYProperty().bind(myGroup.translateYProperty().multiply(ratio));
    }


}
