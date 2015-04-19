package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;


/**
 * Abstract class for scroll trackers. A scroll tracker is something that tells the IScrollFocus
 * what points should be focused on and then scrolls the necessary amount. A scroll tracker is given
 * an IScrollFocus and an IScroller. When the tracker is enabled, it will listen to events such that
 * when these events happen, it will focus on some point determined by that event.
 * 
 * @author Tony
 *
 */
public abstract class AbstractTracker {
    private boolean myStatus;
    private IScrollFocus myFocuser;
    private IScroller myScroller;

    /**
     * Constructor.
     * 
     * @param focuser Focus method to be used.
     * @param scroller Type of scrolling to be used.
     */
    public AbstractTracker (IScrollFocus focuser, IScroller scroller) {
        setFocuser(focuser);
        setScroller(scroller);
        myFocuser.bind(myScroller.getGroup());
        myStatus = false;
    }

    public void setFocuser (IScrollFocus focuser) {
        myFocuser = focuser;
    }

    public void setScroller (IScroller scroller) {
        myScroller = scroller;
    }

    /**
     * Focuses on (x,y).
     * 
     * @param x
     * @param y
     */
    public void tell (double x, double y) {
        myScroller.scroll(myFocuser.focus(x, y));
    }   

    /**
     * Focuses along the x-direction on x.
     * 
     * @param x
     */
    public void tellX (double x) {
        myScroller.scroll(myFocuser.focusX(x), 0);
    }

    /**
     * Focuses along the y-direction on y.
     * 
     * @param y
     */
    public void tellY (double y) {
        myScroller.scroll(0, myFocuser.focusY(y));
    }

    /**
     * Scrolls by the given
     * 
     * @param changeX
     * @param changeY
     */
    public void tellChange (double changeX, double changeY) {
        myScroller.scroll(changeX, changeY);
    }
    
    

    /**
     * Enables this tracker.
     */
    public void enable () {
        if (!isEnabled()) {
            myStatus = true;
            start();
        }
    }

    /**
     * Disables the tracker.
     */
    public void disable () {
        if (isEnabled()) {
            myStatus = false;
            stop();
        }
    }

    /**
     * 
     * @return Returns true if this tracker is enabled.
     */
    public boolean isEnabled () {
        return myStatus;
    }

    /**
     * Defines what happens when this tracker is enabled. Typically it will start tracking some
     * event such that when this event happens, some point is focused on.
     */
    protected abstract void start ();

    /**
     * Defines what happens when this tracker is disabled. Resets to original values.
     */
    protected abstract void stop ();
}
