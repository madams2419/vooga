// This entire file is part of my masterpiece.
// Natalie Chanfreau

package authoring.panes.types;

import javafx.scene.Node;
import javafx.scene.layout.VBox;


/**
 * Allows a VBox to implement ISafePane.
 * 
 * @author Natalie Chanfreau
 *
 */
public class SafeVBox extends VBox implements ISafePane {

    public void addToChildren (Node ... nodes) {
        this.getChildren().addAll(nodes);
    }

}
