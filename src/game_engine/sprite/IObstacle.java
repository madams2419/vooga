package game_engine.sprite;

public interface IObstacle {
    
    /**
     * Allows an obstacle to move in a rolling motion
     * @param rotVelocity the velocity to spin a rolling object
     */
    void roll(double rotVelocity);
    
    /**
     * resizes an obstacle a certain amount
     * @param factor the scale by which to resize the object
     */
    void resize(double factor);
    
    /**
     * Determines what to do on a hit 
     */
    void onHit();
}
