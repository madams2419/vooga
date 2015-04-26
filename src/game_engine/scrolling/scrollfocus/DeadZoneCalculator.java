package game_engine.scrolling.scrollfocus;

/**
 * Helper class for DeadZoneFocus. Used to compute the amount needed to be changed along a single
 * value. See DeadZoneFocus class for more details.
 * 
 * @author Tony
 *
 */
public class DeadZoneCalculator {
    private double myValue;

    /**
     * Constructor
     * @param value If the new focus point is past this value, then scrolling should occur.
     */
    public DeadZoneCalculator (double value) {
        myValue = value;
    }
    
    /**
     * 
     * @param change Distance from the new focus point to the center of the camera along a certain direction.
     * @return Returns true if the camera should be scrolled.
     */
    private boolean check (double change) {
        return change / myValue > 1;
    }

    /**
     * 
     * @param change Distance from the new focus point to the center of the camera along a certain direction.
     * @return The scroll amount needed to include the focused point in the deadzone. 
     */
    public double compute (double change) {
        return check(change) ? change - myValue : 0;
    }
}
