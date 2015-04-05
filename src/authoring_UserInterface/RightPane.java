package authoring_UserInterface;

import authoring_environment.Sprite;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Daniel Luker & Andrew Sun
 *
 */
public class RightPane extends VBox {

	private Scene myScene;
	
	RightPane(Scene scene) {
		myScene = scene;
		this.getStylesheets().add("styles/right_pane.css");
		this.getChildren().add(
				new TextArea(String.format("Information%n"
						+ "when a drop down%n" + "item is selected, or%n"
						+ "a current element on%n" + "the scroll pane is%n"
						+ "selected (up to two%n"
						+ "selections), its (their)%n"
						+ "information will be%n" + "shown here.")));
		ImageView sampleImage = new ImageView("/images/turtle.png");
		Sprite s = new Sprite(100, "/images/turtle.png");
		sampleImage.setOnMouseClicked(e -> imageClicked());
		sampleImage.setOnMouseDragged(e -> imageDragged(e));
		this.getChildren().add(sampleImage);
	}

	private void imageDragged(MouseEvent e) {
		
	}

	private void imageClicked() {
		// need to now set mouse cursor to the sprite image
		myScene.setCursor(new SpriteCursor(new Sprite(100, "/images/turtle.png")));
	}
}
