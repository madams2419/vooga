package menu;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * VoogaSaladMain is responsible only for launching the application.
 * 
 * @author Brian Lavallee
 * @since 2 April 2015
 */
public class VoogaSaladMain extends Application {

    /**
     * Initializes the stage, creates the first scene, and displays the
     * application to the use.
     */
    public void start(Stage stage) throws Exception {
	stage.setTitle("VoogaSalad");

	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	Media media = new Media(Paths.get("src/music/opening.mp3").toUri().toString());
	MediaPlayer player = new MediaPlayer(media);
	player.setVolume(100);
	
	stage.setX(primaryScreenBounds.getMinX());
	stage.setY(primaryScreenBounds.getMinY());

	stage.setWidth(primaryScreenBounds.getWidth());
	stage.setHeight(primaryScreenBounds.getHeight());

	stage.setResizable(false);

	VoogaMenu menu = new VoogaMenu(stage.getWidth(), stage.getHeight());
	stage.setScene(menu.initialize());
	player.play();
	stage.show();
    }

    /**
     * Launches the application.
     * 
     * @param args
     *             are generally empty.
     */
    public static void main(String[] args) {
	launch(args);
    }
}