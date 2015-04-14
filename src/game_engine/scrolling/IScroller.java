package game_engine.scrolling;

public interface IScroller {
    
    public default void focus (double x, double y) {
        scrollX (getNewFocusX(x));
        scrollY (getNewFocusY(y));
    }
    
    public void scrollX (double amount);
    
    public void scrollY (double amount);
    
    public double getNewFocusX (double x);
    
    public double getNewFocusY (double y);
}
