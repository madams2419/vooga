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
    
    public ScrollTracker(IScroller scroller, ObservableDoubleValue x, ObservableDoubleValue y){
        myScroller = scroller;
        myX = x;
        myY = y;
        myListener = makeListener();
        
    }
    
    private void setTracker (ObservableDoubleValue original, ObservableDoubleValue newValue) {
        Optional.ofNullable(original).ifPresent(this::disableTracking);
        
        newValue.addListener(myListener);
    }
    
    public void setXTracker (ObservableDoubleValue observableX ) {
        Optional.ofNullable(myX).ifPresent(obs -> obs.removeListener(myListener));
        Optional.ofNullable(myY).ifPresent(obs -> obs.removeListener(myListener));
        myX = observableX;
        myListener = makeListener();
        Optional.ofNullable(myX).ifPresent(obs -> obs.addListener(myListener));
        Optional.ofNullable(myY).ifPresent(obs -> obs.addListener(myListener));
    }
    
    public void setYTracker (ObservableDoubleValue observableY) {
        Optional.ofNullable(myX).ifPresent(obs -> obs.removeListener(myListener));
        Optional.ofNullable(myY).ifPresent(obs -> obs.removeListener(myListener));
        myY = observableY;
        myListener = makeListener();
        Optional.ofNullable(myX).ifPresent(obs -> obs.addListener(myListener));
        Optional.ofNullable(myY).ifPresent(obs -> obs.addListener(myListener));
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
        if (myX != null && myY != null) {
            return (change) -> {
                myScroller.focus(myX.get(), myY.get());
            };
        }
        
        return (change) -> {System.out.println("fail");};
    }
}
