package game_engine;

import javafx.scene.Group;

public class DeadZoneScroller extends BasicScroller{
    private DeadZoneCalculator myRight;
    private DeadZoneCalculator myLeft;
    private DeadZoneCalculator myTop;
    private DeadZoneCalculator myBottom;
    
    public DeadZoneScroller (Group group, double x, double y, double right, double left, double top, double bottom) {
        super(group, x, y);
        myRight = new DeadZoneCalculator (Math.abs(right));
        myLeft = new DeadZoneCalculator (- Math.abs(left));
        myTop = new DeadZoneCalculator (Math.abs(top));
        myBottom = new DeadZoneCalculator (- Math.abs(bottom));
    }
    
    public double getNewFocusX (double x) {
        double change = super.getNewFocusX(x);
        return myRight.computeScrollChange(change) + myLeft.computeScrollChange(change);
    }
    
    public double getNewFocusY (double y) {
        double change = super.getNewFocusY(y);
        return myTop.computeScrollChange(change) + myBottom.computeScrollChange(change);
    }
}
