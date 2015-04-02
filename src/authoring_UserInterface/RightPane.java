package authoring_UserInterface;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class RightPane extends VBox {

	RightPane() {
		this.getStylesheets().add("styles/right_pane.css");
		this.getChildren().add(
				new TextArea(String.format("Information%n"
						+ "when a drop down%n" + "item is selected, or%n"
						+ "a current element on%n" + "the scroll pane is%n"
						+ "selected (up to two%n"
						+ "selections), its (their)%n"
						+ "information will be%n" + "shown here.")));
	}
}
