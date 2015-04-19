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
    
    public MouseClickTracker (IScrollFocus focuser, IScroller scroller) {
        super(focuser, scroller);
    }


    private void animateClick(double x, double y, Group group) {
        ScaleTransition scale = new ScaleTransition();
        Circle circle = new Circle(x, y, 2);
        circle.setStroke(Color.GREEN);
        circle.setStrokeWidth(.4);
        circle.setFill(Color.TRANSPARENT);
        group.getChildren().add(circle);
        scale.setNode(circle);
        scale.setDuration(Duration.seconds(0.5));
        scale.setByX(3);
        scale.setByY(3);
        scale.setByZ(3);
        
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), circle);
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
