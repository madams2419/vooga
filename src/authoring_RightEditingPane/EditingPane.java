package authoring_RightEditingPane;

import javafx.scene.layout.VBox;

/**
 * 
 * @author Natalie Chanfreau, Daniel Luker
 *
 */
public class EditingPane extends VBox {
    private final String CSS = "styles/right_pane.css";

    EditingPane () {
        this.getStylesheets().add(CSS);
    }
    
}
