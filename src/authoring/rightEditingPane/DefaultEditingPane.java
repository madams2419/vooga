package authoring.rightEditingPane;

import javafx.scene.control.TextArea;


/**
 * 
 * @author Natalie Chanfreau
 *
 */
public class DefaultEditingPane extends EditingPane {

    DefaultEditingPane () {
        super();
        this.getChildren().add(
                               new TextArea(String.format("Right Pane when nothing is selected")));
    }
}
