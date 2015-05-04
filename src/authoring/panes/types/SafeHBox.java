package authoring.panes.types;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class SafeHBox extends HBox implements ISafePane {

    public SafeHBox(int spacing) {
        super(spacing);
    }
    
    public void addToChildren(Node ... nodes) {
        this.getChildren().addAll(nodes);
    }
    
}
