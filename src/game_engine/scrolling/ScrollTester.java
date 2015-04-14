package game_engine.scrolling;

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
        Rectangle r = new Rectangle(500, 500);
        group.getChildren().addAll(r, new Rectangle(100, 100, Color.WHITE));
        r.setTranslateX(50);
        group.setTranslateX(-100);
        System.out.println(r.getTranslateX());
        stage.setScene(new Scene(group, 300, 300));
        stage.show();
    }
    
    public static void main (String[] args) {
        launch(args);
    }
}
