package game_engine.scrolling;

import game_engine.scrolling.scroller.AnimatedScroller;
import game_engine.scrolling.scrollfocus.DeadZoneFocus;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import game_engine.scrolling.tracker.MiniMapTracker;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class AnotherTester extends Application {

    @Override
    public void start (Stage stage) throws Exception {
        Group group = new Group();
        Group group2 = new Group();
        Scene scene = new Scene(group2, 300, 300);
        stage.setScene(scene);
        stage.show();
        group.requestFocus();

        IScrollFocus focus = new DeadZoneFocus(300, 300, .2);
        focus.setBoundaryChecker(1000, 1000);

        Rectangle r7 = new Rectangle(-25, -25, 50, 50);
        r7.setFill(Color.GREEN);
        r7.setTranslateX(150);
        r7.setTranslateY(150);
        Rectangle r = new Rectangle(0, 0, 100, 100);
        r.setFill(Color.RED);
        Rectangle r2 = new Rectangle(100, 0, 100, 100);
        Rectangle r3 = new Rectangle(0, 100, 100, 100);
        Rectangle r4 = new Rectangle(100, 100, 100, 100);
        Rectangle r5 = new Rectangle(0, 0, 60, 60);
        r5.setTranslateX(120);
        r5.setTranslateY(120);
        Rectangle r6 = new Rectangle(1000, 1000, Color.YELLOW);
        
        r5.setFill(Color.TRANSPARENT);
        r5.setStroke(Color.BLUE);
        r4.setFill(Color.RED);
        
        scene.setOnKeyPressed(e -> handleKeyPressed(e, r7));
        group.getChildren().addAll(r6, r, r2, r3, r4, r7);
        MiniMapTracker t = new MiniMapTracker(focus, new AnimatedScroller(group), 0.8);
        t.enable();
        group2.getChildren().addAll(group, r5, t.getMiniMap());
    }

    private void handleKeyPressed (KeyEvent e, Rectangle r7) {
        if (e.getCode() == KeyCode.UP) {
            r7.setTranslateY(r7.getTranslateY() - 10);
        }
        else if (e.getCode() == KeyCode.DOWN) {
            r7.setTranslateY(r7.getTranslateY() + 10);
        }
        else if (e.getCode() == KeyCode.LEFT) {
            r7.setTranslateX(r7.getTranslateX() - 10);
        }
        else if (e.getCode() == KeyCode.RIGHT) {
            r7.setTranslateX(r7.getTranslateX() + 10);
        }
    }

    public static void main (String[] args) {
        launch(args);
    }

}
