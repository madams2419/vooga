package game_engine.sprite;


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

            testerScreen menu = new testerScreen((int)primaryScreenBounds.getWidth(), (int)primaryScreenBounds.getHeight());
            stage.setScene(menu.initialize());

            stage.show();
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