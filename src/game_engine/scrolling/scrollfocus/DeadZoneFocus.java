package game_engine.scrolling.scrollfocus;

/**
 * Class used for scrolling with a dead zone. This dead zone is a rectangular region such that if
 * the focused point falls in this region no scrolling occurs. Furthermore, if the focused point is
 * outside the region, the amount scrolled is the shortest distance such that the scrolled focused
 * point now lies within the deadzone region.
 * 
 * @author Tony
 *
 */
public class DeadZoneFocus extends BasicFocus {
    private DeadZoneCalculator myRight;
    private DeadZoneCalculator myLeft;
    private DeadZoneCalculator myTop;
    private DeadZoneCalculator myBottom;
    
    /**
     * Constructor.
     * @param width Camera width.
     * @param height Camera height.
     * @param left
     * @param right
     * @param top
     * @param bottom
     */
    public DeadZoneFocus (double width,
                          double height,
                          double left,
                          double right,
                          double top,
                          double bottom,
                          double centerX,
                          double centerY) {
        super(width, height, centerX, centerY);
        myRight = new DeadZoneCalculator(right);
        myLeft = new DeadZoneCalculator(-left);
        myTop = new DeadZoneCalculator(top);
        myBottom = new DeadZoneCalculator(-bottom);
    }

    public DeadZoneFocus (double width,
                          double height,
                          double horizontal,
                          double vertical,
                          double centerX,
                          double centerY) {
        this(width, height, horizontal, horizontal, vertical, vertical, centerX, centerY);
    }

    public DeadZoneFocus (double width,
                          double height,
                          double centerX,
                          double centerY,
                          double ratio) {
        this(width, height, ratio * width * 0.5, ratio * height * 0.5, centerX, centerY);
    }

    @Override
    public double getChangeX (double x) {
        double changeX = super.getChangeX(x);
        double scrollChange = myRight.compute(changeX) + myLeft.compute(changeX);
        return scrollChange;
    }

    @Override
    public double getChangeY (double y) {
        double changeY = super.getChangeY(y);
        double scrollChange = myTop.compute(changeY) + myBottom.compute(changeY);
        return scrollChange;
    }

}
