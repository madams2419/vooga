package authoring.rightPane;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


/**
 * This is the superclass for any specific pane on the right side of the screen.
 * 
 * @author Natalie Chanfreau
 *
 */
public class EditingPane {
    private ObservableList<Node> myChildren;
    private Scene myScene;

    EditingPane (Scene scene) {
        myChildren = new VBox().getChildren();
        myScene = scene;
    }

    public ObservableList<Node> getChildren () {
        return myChildren;
    }

    public Scene getScene () {
        return myScene;
    }

}
