package game_engine;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ScrollTester extends Application{

    @Override
    public void start (Stage stage) throws Exception {
        Group group = new Group();
        IScroller s = new BasicScroller(group);
        DragScrollTracker d = new DragScrollTracker(s, group);
        group.getChildren().addAll(new Rectangle(500, 500), new Rectangle(100, 100, Color.WHITE));
        stage.setScene(new Scene(group, 300, 300));
        stage.show();
    }
    
    public static void main (String[] args) {
        launch(args);
    }
}
