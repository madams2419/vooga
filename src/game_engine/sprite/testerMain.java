package game_engine.sprite;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class testerMain extends Application {

    /**
     * Initializes the stage, creates the first scene, and displays the
     * application to the use.
     */
    public void start(Stage stage) throws Exception {
            stage.setTitle("VoogaSalad");
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());
            stage.setWidth(primaryScreenBounds.getWidth());
            stage.setHeight(primaryScreenBounds.getHeight());
            stage.setResizable(false);
            
            Timeline animation = new Timeline();
            testerScreen menu = new testerScreen((int)primaryScreenBounds.getWidth(), (int)primaryScreenBounds.getHeight());
            stage.setScene(menu.initialize());
            KeyFrame frame = menu.start(60);

            stage.show();
            animation.setCycleCount(Animation.INDEFINITE);
            animation.getKeyFrames().add(frame);
            animation.play();
    }

    /**
     * Launches the application.
     * 
     * @param args
     *            are generally empty.
     */
    public static void main(String[] args) {
            launch(args);
    }
}