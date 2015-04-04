package game_engine.objective;

public class GameTimer {
    private long myDuration;
    private long myStart;
    
    public GameTimer(long duration){
        myDuration = duration;
        myStart = Long.MAX_VALUE;
    }
    
    public void start(long now){
        myStart = now;
    }
    
    public boolean isFinished(long now){
        return now - myStart >= myDuration;
    }
}
