package authoring.panes.menuBar;

import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Plays a song
 * @author Andrew Sun, Daniel Luker
 *
 */
public class HelpMediaPlayer {
	
	private static final int MAX_VOLUME = 100;
	
	public HelpMediaPlayer(String path){
		Media media = new Media(Paths
				.get(path).toUri()
				.toString());
		MediaPlayer player = new MediaPlayer(media);
		player.setVolume(MAX_VOLUME);
		player.play();
	}
	
}
