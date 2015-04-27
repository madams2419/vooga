package game_engine.sprite;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
public class PauseTransitionTask extends TimerTask {
    private PathTransition transition;
    private int pauseDuration;
    
    public PauseTransitionTask(PathTransition transition, int duration){
        this.transition = transition;
        pauseDuration = duration;
    }
    
    @Override
    public void run () {
        // TODO Auto-generated method stub
        transition.pause();
        Timer timer = new Timer();
        try {
            timer.wait(pauseDuration);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        transition.play();
        
    }

}
