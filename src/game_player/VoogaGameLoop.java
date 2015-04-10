package game_player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The VoogaGameLoop manages the game and all of its components
 * from frame to frame.  It both forces the model to update and also
 * instructs the view how to display the data for the user.
 * 
 * @author Brian Lavallee
 * @since 7 April 2015
 */
public class VoogaGameLoop {
    
    private static final int FRAME_RATE = 1000/60;
    
    private Timeline gameLoop;
    
    public VoogaGameLoop() {
	gameLoop = new Timeline();
	gameLoop.setCycleCount(Timeline.INDEFINITE);
	gameLoop.getKeyFrames().add(new KeyFrame(Duration.millis(FRAME_RATE), (frame) -> update()));
    }
    
    public void start() {
	
    }
    
    private void update() {
	System.out.println("frame");
    }
}