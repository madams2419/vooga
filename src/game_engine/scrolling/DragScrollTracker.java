package game_engine.scrolling;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;

public class DragScrollTracker{
    private IScroller myScroller;
    private double myStartX;
    private double myStartY;
    
    public DragScrollTracker (IScroller scroller, Group group) {
        myScroller = scroller;
        setUpGroup(group);
    }
    
    public void setUpGroup (Group group ){
        group.setOnMousePressed(e -> {
            myStartX = e.getSceneX();
            myStartY = e.getSceneY();
        });
        group.setOnMouseReleased(e -> {
            System.out.println("here");
            myScroller.scrollX(myStartX - e.getSceneX());
            myScroller.scrollY(myStartY - e.getSceneY());
        });
        group.setOnScroll(e -> {
           myScroller.scrollX(-e.getDeltaX());
           myScroller.scrollY(-e.getDeltaY());
        });
    }
    

    
}
