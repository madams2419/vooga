package game_engine.scrolling.scrollfocus;

/**
 * This class is used to ensure that the scrolling does not go past a certain specified boundary.
 * 
 * @author Tony
 *
 */
public class BoundaryChecker {
    private double myWidth;
    private double myHeight;
    private double myMapWidth;
    private double myMapHeight;

    /**
     * Constructor
     * 
     * @param width Width of the camera
     * @param height Height of the camera
     * @param mapWidth Width of the map
     * @param mapHeight Height of the map
     */
    public BoundaryChecker (double width, double height, double mapWidth, double mapHeight) {
        myWidth = width;
        myHeight = height;
        myMapWidth = mapWidth;
        myMapHeight = mapHeight;
    }

    /**
     * 
     * @param value The new x-coordinate of the center of the camera.
     * @return The scroll amount needed to ensure the camera stays within the map boundaries along
     *         the x-direction.
     */
    public double adjustX (double value) {
        return adjust(value, myWidth * 0.5, myMapWidth - myWidth * 0.5);
    }

    /**
     * 
     * @param value The new y-coordinate of the center of the camera.
     * @return The scroll amount needed to ensure the camera stays within the map boundaries along
     *         the y-direction.
     */
    public double adjustY (double value) {
        return adjust(value, myHeight * 0.5, myMapHeight - myHeight * 0.5);
    }
    
    /**
     * 
     * @param value
     * @param min
     * @param max
     * @return The amount needed to be added to value to keep it within the min and max values.
     */
    private double adjust (double value, double min, double max) {
        return Math.max(Math.min(value, max), min) - value;
    }

}
