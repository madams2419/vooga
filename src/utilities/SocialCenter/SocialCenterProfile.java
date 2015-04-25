package utilities.SocialCenter;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class SocialCenterProfile {
	private static final int TEMPWIDTH = 1000;
	private static final int TEMPHEIGHT = 600;
	private Scene profile;
	private StackPane root = new StackPane();
	private Driver db = new Driver();

	public SocialCenterProfile() {
		initialize();
	}

	private void initialize() {
		profile = new Scene(root, TEMPWIDTH, TEMPHEIGHT);
		
		
	}

}
