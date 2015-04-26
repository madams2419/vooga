package authoring.panes.rightPane;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;



/**
 * This will be the pane that shows up when nothing has been clicked on.
 * It might be deleted if we want another pane to be the default one.
 * 
 * @author Natalie Chanfreau
 *
 */
public class DefaultEditingPane extends EditingPane {

    DefaultEditingPane (Scene scene, RightPane parent) {
        super(scene, parent);
        this.getChildren().add(
                               new TextArea(String
                                       .format("Default pane")));
    }

}
