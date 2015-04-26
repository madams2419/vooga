package game_engine.scrolling.scrollfocus;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;

/**
 * Implements IScrollFocus. Focusing on a point (x,y) scrolls such that the center of the camera is
 * (x,y). This class is also used as a superclass for several other classes that also implement
 * IScrollFocus.
 * 
 * @author Tony
 *
 */
public class BasicFocus implements IScrollFocus {
    private ObservableDoubleValue myX;
    private ObservableDoubleValue myY;
    private double myWidth;
    private double myHeight;
    private BoundaryChecker myBoundaryChecker;

    /**
     * Constructor
     * @param width Camera width
     * @param height Camera height
     */
    public BasicFocus (double width, double height) {
        myX = new SimpleDoubleProperty(0.5 * width);
        myY = new SimpleDoubleProperty(0.5 * height);
        myWidth = width;
        myHeight = height;
    }

    public void bind (Group group) {
        myX = group.translateXProperty().negate().add(0.5 * myWidth);
        myY = group.translateYProperty().negate().add(0.5 * myHeight);
    }
  
    public void setBoundaryChecker (double mapWidth, double mapHeight) {
        myBoundaryChecker = new BoundaryChecker(myWidth, myHeight, mapWidth, mapHeight);
    }

    @Override
    public double focusX (double x) {
        double change = getChangeX(x);
        return inBoundsX(change);
    }

    @Override
    public double focusY (double y) {
        double change = getChangeY(y);
        return inBoundsY(change);
    }
    
    private double inBoundsX (double changeX) {
        if (myBoundaryChecker != null) {
            double adjust = myBoundaryChecker.adjustX(myX.get() + changeX);
            changeX += adjust;
        }
        return changeX;
    }
    
    private double inBoundsY (double changeY) {
        if (myBoundaryChecker != null) {
            double adjust = myBoundaryChecker.adjustY(myY.get() + changeY);
            changeY += adjust;
        }
        return changeY;
    }
    
    public Point2D computeChange (double changeX, double changeY) {
        double adjustX = inBoundsX (changeX);
        double adjustY = inBoundsY (changeY);
        return new Point2D (adjustX, adjustY);
    }
    
    /**
     * Amount in the x-direction that needs to be changed to focus on point x.
     * @param x
     * @return
     */
    protected double getChangeX (double x) {
        return x - myX.get();
    }

    /**
     * Amount in the y-direction that needs to be changed to focus on point y.
     * @param y
     * @return
     */
    protected double getChangeY (double y) {
        return y - myY.get();
    }


}
