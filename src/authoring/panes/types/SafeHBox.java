// This entire file is part of my masterpiece.
// Natalie Chanfreau

package authoring.panes.types;

import javafx.scene.Node;
import javafx.scene.layout.HBox;


/**
 * Allows an HBox to implement ISafePane.
 * 
 * @author Natalie Chanfreau
 *
 */
public class SafeHBox extends HBox implements ISafePane {

    public SafeHBox (int spacing) {
        super(spacing);
    }

    public void addToChildren (Node ... nodes) {
        this.getChildren().addAll(nodes);
    }

}
