package authoring.panes.rightPane;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import authoring.dataEditors.Sprite;
import authoring.userInterface.ControlsDialog;
import authoring.util.FrontEndUtils;
import authoring.util.ImageEditor;

/**
 * This will be for when a character already on the screen is clicked on. It
 * will allow the designer to edit the character.
 * 
 * @author Natalie Chanfreau, Daniel Luker, Andrew Sun
 *
 */

class CharacterEditingPane extends EditingPane {
	private static final String imageChooserTitle = "Change Character Image";
	private static final String imageChooserDescription = "Image Files";
	private static final String[] imageChooserExtensions = { "*.png", "*.jpg",
			"*.gif" };

	private List<HBox> myFields = new LinkedList<>();

	CharacterEditingPane(Scene scene, RightPane parent, Sprite sprite) {
		super(scene, parent);
		// ======================== New design in here ===================== //
		setFields(this.getChildren(), sprite.getCharacteristics());
		Button c = new Button("Update");
		
		// TODO: refactor
		Button controls = new Button("Controls");
		controls.setDisable(!sprite.getPlayable());
		CheckBox playable = new CheckBox("Playable");
		playable.setSelected(sprite.getPlayable());
		playable.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue<? extends Boolean> ov,
		             Boolean prev, Boolean curr) {
				sprite.setPlayable(curr);
				controls.setDisable(prev);
			}
		});
		controls.setOnMouseClicked(e -> controlsClicked(sprite));
		c.setOnAction(e -> updateSprite(sprite));
		this.getChildren().addAll(c, playable, controls);
		addButtonToReturnToCreationPane("Back");

		// ================================================================= //
		addSpriteIcon(sprite);
	}

	private void controlsClicked(Sprite s) {
		if (s.getControls() != null){
			s.getControls().showBox(s);
		}
		else{
			ControlsDialog c = new ControlsDialog(s);
			s.setControls(c);
		}
	}

	private void updateSprite(Sprite sprite) {
		myFields.forEach(hbox -> {
			String s, t;
			sprite.setCharacteristic(
					(s = ((Text) hbox.getChildren().get(0)).getText()),
					(t = ((TextField) hbox.getChildren().get(1)).getText()));
			switch(s) {
			case "position":
				sprite.setPosition(FrontEndUtils.stringToMap(t));
				break;
			case "name":
				sprite.setName(t);
				break;
			case Sprite.SCALE:
				sprite.setScale(Double.parseDouble(t));
				break;
			}

		});
	}

	private void addSpriteIcon(Sprite sprite) {
		ImageView spriteIcon = sprite.getIcon();
		spriteIcon.setOnMouseClicked(i -> spriteIconClicked(sprite));
		spriteIcon.setOnMouseEntered(i -> reduceSpriteOpacity(spriteIcon));
		spriteIcon.setOnMouseExited(i -> restoreSpriteOpacity(spriteIcon));
		getChildren().add(spriteIcon);
	}

	private void spriteIconClicked(Sprite sprite) {
		changeCharacterImage(sprite);
	}

	private void reduceSpriteOpacity(ImageView imageView) {
		ImageEditor.reduceOpacity(imageView, Sprite.OPACITY_REDUCTION_RATIO);
	}

	private void restoreSpriteOpacity(ImageView imageView) {
		ImageEditor.restoreOpacity(imageView);
	}

	private void changeCharacterImage(Sprite sprite) {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setTitle(imageChooserTitle);
		imageChooser.getExtensionFilters().add(
				new ExtensionFilter(imageChooserDescription,
						imageChooserExtensions));
		File selectedImageFile = imageChooser.showOpenDialog(null);
		if (selectedImageFile != null) {
			sprite.changeImage(new Image(selectedImageFile.toURI().toString()));
		}
	}

	private void setFields(ObservableList<Node> parent,
			Map<String, String> fields) {
		fields.forEach((label, value) -> {
			HBox h = new HBox(5);
			h.getChildren().addAll(new Text(label),
					new javafx.scene.control.TextField(value));
			parent.add(h);
			myFields.add(h);
		});
	}

}
