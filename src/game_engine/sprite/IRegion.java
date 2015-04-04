package game_engine.sprite;

public interface IRegion{
    
    /**
     * Sets the gravity of a region
     * by altering the PhysicsEngine
     * @param gravity the value of gravity
     */
    void setGravity(double gravity);
    
    /**
     * @return gravity the Gravity of the region
     */
    double getGravity();
    
    /**
     * Sets the drag coefficient of the region
     * @param drag 
     */
    void setDrag(double drag);
    
    /**
     * @return the drag coefficient of the region
     */
    double getDrag();

}
