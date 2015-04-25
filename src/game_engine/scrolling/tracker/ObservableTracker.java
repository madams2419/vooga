package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.DoubleSupplier;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


/**
 * This class tracks when specified observables change. When this observables change, some point
 * will be focused on.
 * 
 * @author Tony
 *
 */
public class ObservableTracker extends AbstractTracker {

    private Collection<Observable> myObservables;
    private DoubleSupplier myX;
    private DoubleSupplier myY;
    private InvalidationListener myListener;

    public ObservableTracker (IScrollFocus focuser, IScroller scroller) {
        super(focuser, scroller);
        reset();
    }

    /**
     * Resets the observables being tracked as well as what happens when tracker observables change.
     */
    public void reset () {
        if (myObservables == null) {
            myObservables = new HashSet<>();
        }
        myObservables.forEach(this::disableTracking);
        myObservables.clear();
        myX = null;
        myY = null;
        myListener = (change) -> {
        };
    }

    /**
     * 
     * @param supplier Returns a DoubleSupplier that supplies the x-coordinate to be focused on
     *        whenever the observables change.
     */
    public void setXSupplier (DoubleSupplier supplier) {
        myX = supplier;
        updateListener();
    }

    /**
     * 
     * @param supplier Returns a DoubleSupplier that supplies the y-coordinate to be focused on
     *        whenever the observables change.
     */
    public void setYSupplier (DoubleSupplier supplier) {
        myY = supplier;
        updateListener();
    }

    /**
     * 
     * @param observables Starts trackiing these observable objects.
     */
    public void enableTracking (Observable ... observables) {
        for (Observable observable : observables) {
            myObservables.add(observable);
            if (isEnabled()){
                observable.addListener(myListener);
            }
        }
    }

    /**
     * 
     * @param observables No longer listens to changes of these objects.
     */
    public void disableTracking (Observable ... observables) {
        for (Observable observable : observables) {
            myObservables.remove(observable);
            observable.removeListener(myListener);
        }
    }

    private void updateListener () {
        disable();
        myListener = (change) -> tell(myX, myY);
        enable();
    }

    private void tell (DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
        if (xSupplier != null && ySupplier != null) {
            System.out.println(xSupplier.getAsDouble() + " " + ySupplier.getAsDouble());
            tell(xSupplier.getAsDouble(), ySupplier.getAsDouble());
        }
        else if (xSupplier != null) {
            tellX(xSupplier.getAsDouble());
        }
        else if (ySupplier != null) {
            tellY(ySupplier.getAsDouble());
        }
    }

    /**
     * When started, this class will listen to changes within the observables.
     */
    @Override
    protected void start () {
        myObservables.forEach(this::enableTracking);
    }

    /**
     * No longer listens to changes within this class's observables.
     */
    @Override
    protected void stop () {
        myObservables.forEach(obs -> obs.removeListener(myListener));
    }
}
