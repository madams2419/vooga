package game_engine.scrolling.scrollfocus;

import javafx.geometry.Point2D;
import javafx.scene.Group;


/**
 * Interface that is linked with the IScroller. It tells the IScroller the amount to scroll by in
 * order to focus on a point.
 * 
 * @author Tony
 *
 */
public interface IScrollFocus {

    /**
     * Focuses on a point. 
     * @param x x-coordinate of the point to be focused on.
     * @param y y-coordinate of the point to be focused on.
     * @return A vector representing the directions along the x-axis and y-axis to be scrolled along.
     */
    public default Point2D focus (double x, double y) {
        return new Point2D(focusX(x), focusY(y));
    }

    /**
     * Focuses in the x-direction on the point x.
     * @param x 
     * @return Amount of scrolling in the x-direction needed to focus on x.
     */
    public double focusX (double x);

    /**
     * Focuses in the y-direction on the point y.
     * @param y
     * @return Amount of scrolling in the y-direction needed to focus on y.
     */
    public double focusY (double y);

    public Point2D computeChange (double x, double y) ;
    /**
     * Setting up a boundary checker prevents you from scrolling past the boundary of the map.
     * @param mapWidth
     * @param MapHeight
     */
    public void setBoundaryChecker (double mapWidth, double MapHeight);

    public void bind (Group group);
}
