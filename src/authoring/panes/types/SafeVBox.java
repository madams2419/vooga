package authoring.panes.types;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SafeVBox extends VBox implements ISafePane {

    public void addToChildren(Node ... nodes) {
        this.getChildren().addAll(nodes);
    }
    
}
