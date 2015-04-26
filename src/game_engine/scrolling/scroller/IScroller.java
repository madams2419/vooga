package game_engine.scrolling.scroller;

import game_engine.scrolling.WrapAround;
import javafx.geometry.Point2D;
import javafx.scene.Group;


/**
 * Represents an object that has the capability of scrolling. Typically scrolling will be
 * implemented by translating a group.
 * 
 * @author Tony
 *
 */
public interface IScroller {

    /**
     * Scrolls by the given amounts
     * 
     * @param changeX Scroll amount in the x-direction
     * @param changeY Scroll amount in the y-direction
     */
    public void scroll (double changeX, double changeY);

    /**
     * Scrolls based on a change vector.
     * 
     * @param point Vector representing direction and amount of scrolling.
     */
    public default void scroll (Point2D point) {
        scroll(point.getX(), point.getY());
    }
    
    /**
     * Adds parallax scrolling.
     * @param WrapAround
     * @param ratio Ratio of speed of the background image to the normal group. Should be less than 1.
     */
    public void addBackground (WrapAround background, double ratio);
    
    /**
     * 
     * @return Group that is the target of the scrolling.
     */
    public Group getGroup ();
    
    
}
