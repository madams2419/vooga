package game_engine.objectives;


/**
 * 
 * @author Tony
 *
 */
public class GameTimer {
    private double myDuration;
    private double myCurrent;
    
    /***
     * Constructs a timer with the given duration.
     * @param duration Duration of the timer, measured in seconds.
     */
    public GameTimer(double duration){
        myDuration = duration;
        myCurrent = 0;
    }
    
    /**
     * @param now Time when timer is started
     */
    public void start(){
        myCurrent = 0;
    }
    
    /**
     * 
     * @param now Current time
     * @return Time that has passed for the timer.
     */
    public double getTimePassed(){
        return myCurrent;
    }
    
    /**
     * 
     * @param now Current time.
     * @return True if timer has expired and false otherwise.
     */
    public boolean isFinished(){
        return getTimePassed() >= myDuration;
    }
    
    public void update (double now) {
        myCurrent += now;
    }
}
