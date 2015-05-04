package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import game_engine.sprite.Sprite;
import javafx.scene.Node;


/**
 * This class that listens to changes in a sprite's movement. The camera scrolls to follow the
 * sprite.
 * 
 * @author Tony
 *
 */
public class SpriteTracker extends ObservableTracker {
    private Node myNode;

    public SpriteTracker (IScrollFocus focuser, IScroller scroller) {
        super(focuser, scroller);
    }

    /**
     * Sets the sprite to be followed by the camera.
     * @param sprite
     */
    public void setPlayer (Sprite sprite, boolean x, boolean y) {
        removePlayer();
        Node node = sprite.getImageView();
        enableTracking(node.translateXProperty(), node.translateYProperty());
        if (x) {
            setXSupplier( () -> node.getTranslateX());
        }
        if (y) {
//            setYSupplier( () -> node.getTranslateY());
        }
    }

    private void removePlayer () {
        if (myNode != null) {
            disableTracking(myNode.translateXProperty(), myNode.translateYProperty());
        }
    }
}
