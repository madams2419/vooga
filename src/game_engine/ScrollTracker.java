package game_engine;

import java.util.Optional;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableDoubleValue;

public class ScrollTracker {
    private IScroller myScroller;
    private ObservableDoubleValue myX;
    private ObservableDoubleValue myY;
    private InvalidationListener myListener;
    
    public ScrollTracker (IScroller scroller) {
        myScroller = scroller;
        myListener = makeListener();
    }
    
    private void setTracker (ObservableDoubleValue original, ObservableDoubleValue newValue) {
        Optional.ofNullable(original).ifPresent(this::disableTracking);
        newValue.addListener(myListener);
    }
    
    public void setXTracker (ObservableDoubleValue observableX ) {
        setTracker(myX, observableX);
        myX = observableX;
    }
    
    public void setYTracker (ObservableDoubleValue observableY) {
        setTracker(myY, observableY);
        myY = observableY;
    }
    
    public void disableTracking (ObservableDoubleValue observable) {
        if (isTracking(observable)) {
            observable.removeListener(myListener);
        }
    }
    
    public boolean isTracking (ObservableDoubleValue observable) {
        return (myX == observable) || myY == observable;
    }
    
    private InvalidationListener makeListener () {
        return (change) -> myScroller.focus(myX.get(), myY.get());
    }
}
