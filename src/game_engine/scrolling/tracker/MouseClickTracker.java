package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MouseClickTracker extends MouseTracker{
    private static final double INITIAL_CIRCLE_SIZE = 2;
    private static final double CIRCLE_STROKE_WIDTH = 0.4;
    private static final Duration TIME = Duration.seconds(0.5);
    private static final double SCALE_FACTOR = 3;
    
    public MouseClickTracker (IScrollFocus focuser, IScroller scroller) {
        super(focuser, scroller);
    }


    private void animateClick(double x, double y, Group group) {
        ScaleTransition scale = new ScaleTransition();
        Circle circle = new Circle(x, y, INITIAL_CIRCLE_SIZE);
        circle.setStroke(Color.GREEN);
        circle.setStrokeWidth(CIRCLE_STROKE_WIDTH);
        circle.setFill(Color.TRANSPARENT);
        group.getChildren().add(circle);
        scale.setNode(circle);
        scale.setDuration(TIME);
        scale.setByX(SCALE_FACTOR);
        scale.setByY(SCALE_FACTOR);
        scale.setByZ(SCALE_FACTOR);
        
        FadeTransition fade = new FadeTransition(TIME, circle);
        fade.setOnFinished(e -> group.getChildren().remove(circle));
        fade.setFromValue(1);
        fade.setToValue(0);
        scale.setOnFinished(e -> fade.play());
        scale.play();
    }
    

    @Override
    protected ObjectProperty<EventHandler<? super MouseEvent>> getHandlerProperty (Group group) {
        return group.onMousePressedProperty();
    }

    @Override
    protected void handle (MouseEvent event, Group group) {
        tell(event.getX(), event.getY());
        animateClick(event.getX(), event.getY(), group);
    }

}
