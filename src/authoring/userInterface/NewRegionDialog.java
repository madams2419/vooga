package authoring.userInterface;

import java.util.regex.Pattern;

import authoring.centerPane.CenterCanvas;
import authoring.centerPane.CenterPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * 
 * @author Andrew
 *
 */
public class NewRegionDialog {
	
	private TextField xField;
	private TextField yField;
	
	private static final int NUM_COLS = 2;
	private static final int GRID_HGAP = 10;
	private static final int GRID_VGAP = 10;
	private static final String DEFAULT_SIZE_HEIGHT = "400";
	private static final String DEFAULT_SIZE_WIDTH = "1000";
	
	private static final String X_lABEL = "xSize";
	private static final String Y_lABEL = "ySize";
	
	public NewRegionDialog(CenterPane c){
		// Refactor this into new class/method
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Create New Game Scene");
		
		//GridPane grid = new GridPane();
		DialogGridOrganizer grid = new DialogGridOrganizer(NUM_COLS);
		grid.setHgap(GRID_HGAP);
		grid.setVgap(GRID_VGAP);
		
		xField = new TextField(DEFAULT_SIZE_WIDTH);
		yField = new TextField(DEFAULT_SIZE_HEIGHT);
		
		grid.addRowEnd(new Label(X_lABEL), new Label(Y_lABEL));
		grid.addRowEnd(xField, yField);
		
		
		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		dialog.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> okPressed(c));
	}
	
	private void okPressed(CenterPane c) {
		if (Pattern.matches("^\\d+(\\.\\d+)?$", xField.getText())
			&& Pattern.matches("^\\d+(\\.\\d+)?$", yField.getText())) {
			CenterCanvas d = c.getActiveTab();
			
				d.createRegion(Double.parseDouble(xField.getText()),
					Double.parseDouble(yField.getText()));
		} else {
			System.out.println("error");
		}
	}
}
