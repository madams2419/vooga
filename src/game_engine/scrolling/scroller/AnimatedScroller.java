package game_engine.scrolling.scroller;

import java.awt.Point;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Implements IScroller. This kind of scroller animates the scrolling action.
 * @author Tony
 *
 */
public class AnimatedScroller extends BasicScroller{
    private static final Duration DEFAULT_DURATION = Duration.millis(10);
    private Duration myDuration;
    private TranslateTransition myTranslate;
    /**
     * Constructor
     * @param group Target of the scrolling.
     * @param duration Time it takes to scroll one pixel.
     */
    public AnimatedScroller(Group group, Duration duration) {
        super(group);
        setDuration(duration);
    }
    
    /**
     * Constructor. Uses a default duration. 
     * @param group
     */
    public AnimatedScroller(Group group) {
        this(group, DEFAULT_DURATION);
    }
    
    /**
     * Sets the duration to scroll 1 pixel.
     * @param duration Time to scroll 1 pixel.
     */
    public void setDuration (Duration duration) {
        myDuration = duration;
    }
    
    @Override
    public void scroll (double changeX, double changeY) {
        if (myTranslate != null) {
            myTranslate.stop();
        }
        double changeDistance = Point.distance(0, 0, changeX, changeY);
        myTranslate = animate(changeDistance);
        myTranslate.setByX(-changeX);
        myTranslate.setByY(-changeY);
        myTranslate.play();
    }
    
    private TranslateTransition animate (double duration) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(getGroup());
        translate.setDuration(myDuration.multiply(duration)); 
        return translate;
    }
}
