package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import java.util.Collection;
import javafx.scene.Group;
import javafx.scene.Node;

public class ButtonTracker extends AbstractTracker {
    private Group myGroup;
    private Collection<Node> myNodes;
    
    public ButtonTracker (IScrollFocus focuser, IScroller scroller, Group group) {
        super(focuser, scroller);
        myGroup = group;
    }
    
    public void addNode (Node node, double scrollX, double scrollY) {
        myNodes.add(node);
        node.setOnMousePressed(e -> {
            if (isEnabled()) {
                tellChange (scrollX, scrollY);
            }
        });
    }

    @Override
    protected void start () {
        myGroup.getChildren().clear();
        myGroup.getChildren().addAll(myNodes);
    }

    @Override
    protected void stop () {
        myGroup.getChildren().clear();
    }

}
