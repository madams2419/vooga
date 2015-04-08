package src.authoring.rightPane;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * This is the superclass for any specific pane on the right side of the screen.
 * 
 * @author Natalie Chanfreau
 *
 */
public class EditingPane extends VBox {

	private Scene myScene;

	EditingPane(Scene scene) {
		myScene = scene;
	}

	public Scene getMyScene() {
		return myScene;
	}

}
