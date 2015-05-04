// This entire file is part of my masterpiece.
// Natalie Chanfreau

package authoring.panes.types;

import javafx.scene.Node;


/**
 * This interface limits the access to the children of a pane such that the list can only have
 * children added to it.
 * 
 * @author Natalie Chanfreau
 *
 */
public interface ISafePane {
    void addToChildren (Node ... nodes);
}
