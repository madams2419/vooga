package game_engine;

import javafx.scene.Group;

public class BasicScroller extends Scroller{
    private double myX;
    private double myY;
    
    public BasicScroller (Group group) {
        super(group);
    }

    public void scrollX (double amount) {
        myX += amount;
        super.scrollX(amount);
    }
    
    public void scrollY (double amount) {
        myY += amount;
        super.scrollY(amount);
    }
    
    @Override
    public double getNewFocusX (double x) {
        double change = myX - x;
        return change;
    }

    @Override
    public double getNewFocusY (double y) {
        double change = myY - y;
        return change;
    }

}
