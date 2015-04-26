package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


public class ContinuousTracker extends AbstractTracker {
    private Animation myAnimation;

    public ContinuousTracker (IScrollFocus focuser,
                              IScroller scroller,
                              double time,
                              double amountX,
                              double amountY) {
        super(focuser, scroller);
        makeAnimation(time, amountX, amountY);
    }

    @Override
    protected void start () {
        myAnimation.play();
    }

    @Override
    protected void stop () {
        myAnimation.stop();
    }

    private void makeAnimation (double time, double amountX, double amountY) {
        myAnimation = new PauseTransition(Duration.millis(time));
        myAnimation.setOnFinished(end -> {
            tellChange(amountX, amountY);
            myAnimation.playFromStart();
        });
    }

}
