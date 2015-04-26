package authoring.dialogs;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.DialogGridOrganizer;

/**
 * 
 * @author Daniel
 *
 */
public class PhysicsSettingsDialog extends Dialog<ButtonType> {

	private RightPane myParent;

	private TextField myGravityResponse;

	public PhysicsSettingsDialog(RightPane parent) {
		myParent = parent;

		DialogGridOrganizer grid = new DialogGridOrganizer(2);

		grid.addRowEnd(new Label("Gravity"), myGravityResponse = new TextField());
		this.getDialogPane().setContent(grid);

		this.getDialogPane().getButtonTypes()
				.addAll(ButtonType.OK, ButtonType.CANCEL);

		this.setTitle("Physics Settings");
		showBox();
	}

	public void showBox() {
		this.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(
						response -> {
							myParent.getParent()
									.getCenterPane()
									.getActiveTab()
									.addSetting("gravity",
											myGravityResponse.getText());
						});
	}
}