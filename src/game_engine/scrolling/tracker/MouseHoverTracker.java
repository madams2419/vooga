package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;


public class MouseHoverTracker extends MouseTracker {
    private Polygon myArrow;
    private Point2D myChange;
    private double myRatio;
    private PauseTransition myPause;

    public MouseHoverTracker (IScrollFocus focuser, IScroller scroller, double ratio) {
        super(focuser, scroller);
        myArrow = new Polygon();
        myArrow.getPoints().addAll(new Double[] {
                                                 0.0, -5.0,
                                                 20.0, 0.0,
                                                 0.0, 5.0 });
    }

    @Override
    protected ObjectProperty<EventHandler<? super MouseEvent>> getHandlerProperty (Group group) {
        return group.onMouseMovedProperty();
    }

    @Override
    protected void handle (MouseEvent event, Group group) {
        
    }
}
