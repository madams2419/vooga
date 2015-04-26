package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class MouseDragTracker extends MouseTracker {
    
    public MouseDragTracker (IScrollFocus focuser, IScroller scroller) {
        super(focuser, scroller);
    }

    @Override
    protected ObjectProperty<EventHandler<? super MouseEvent>> getHandlerProperty (Group group) {
        return group.onMousePressedProperty();
    }

    @Override
    protected void handle (MouseEvent event, Group group) {
        double pressedX = event.getX();
        double pressedY = event.getY();
        group.setOnMouseDragged(e -> handleMouseDragged(e, group, pressedX, pressedY));
        group.setOnMouseReleased(e -> group.setCursor(Cursor.DEFAULT));
    }
    
    private void handleMouseDragged(MouseEvent event, Group group, double x, double y) {
        group.setCursor(Cursor.MOVE);;
        double amountX = -event.getX() + x;
        double amountY = -event.getY() + y;
        tellChange(amountX, amountY);
    }

}
