package authoring_RightEditingPane;

import javafx.scene.control.TextArea;


/**
 * 
 * @author Natalie Chanfreau
 *
 */

public class InteractionEditingPane extends EditingPane {

    InteractionEditingPane () {
        super();
        this.getChildren().add(
                               new TextArea(String
                                       .format("This will contain the interactions")));
    }
}
