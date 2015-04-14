package game_engine.scrolling;

import javafx.scene.Group;

public class BasicScroller extends Scroller{
    private double myX;
    private double myY;
    
    public BasicScroller (Group group, double x, double y) {
        super(group);
        myX = x;
        myY = y;
        System.out.println("Initial:" +myX +" " + myY);
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
        double change = x - myX;
        return change;
    }

    @Override
    public double getNewFocusY (double y) {
        double change = y - myY;
        return change;
    }

}
