package game_engine.objectives;


/**
 * 
 * @author Tony
 *
 */
public class GameTimer {
    private static final long SECOND = 1000000000;
    private double myDuration;
    private double myStart;
    
    /***
     * Constructs a timer with the given duration.
     * @param duration Duration of the timer, measured in seconds.
     */
    public GameTimer(double duration){
        myDuration = duration * SECOND;
        myStart = Long.MAX_VALUE;
    }
    
    /**
     * @param now Time when timer is started
     */
    public void start(double now){
        myStart = now;
    }
    
    /**
     * 
     * @param now Current time
     * @return Time that has passed for the timer.
     */
    public double getTimePassed(double now){
        return now - myStart;
    }
    
    /**
     * 
     * @param now Current time.
     * @return True if timer has expired and false otherwise.
     */
    public boolean isFinished(double now){
        return getTimePassed(now) >= myDuration;
    }
}
