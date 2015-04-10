package game_engine.collision;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CollisionTester extends Application{

	public CollisionTester() {
		
	}

	@Override
	public void start(Stage s) throws Exception {
		// TODO Auto-generated method stub
		 	s.setTitle("PPC");
	        // create your own game here
	        // attach game to the stage and display it
		 	Group root = new Group();
	        Scene scene = new Scene(root, 400,400);
	        s.setScene(scene);
	        s.show();

	        // setup the game's loop
	        KeyFrame frame = new KeyFrame(Duration.millis(1000 / 60), e -> updatePosition());
	        Timeline animation = new Timeline();
	        animation.setCycleCount(Animation.INDEFINITE);
	        animation.getKeyFrames().add(frame);
	        animation.play();
	}

	private void updatePosition() {
		
		return;
	}
	
	public static void main (String[] args) {
        launch(args);
    }

}
