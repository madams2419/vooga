package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import java.util.Collection;
import java.util.HashSet;
import javafx.scene.Group;
import javafx.scene.Node;


/**
 * This class tracks when specified nodes are pressed. When these nodes are pressed, a specified
 * amount is scrolled.
 * 
 * @author Tony
 *
 */
public class ButtonTracker extends AbstractTracker {
    private Group myGroup;
    private Collection<Node> myNodes;

    /**
     * Constructor.
     * 
     * @param focuser See AbstractTracker.
     * @param scroller See AbstractTracker
     * @param group Group that contains the nodes that act as buttons. Will probably not want these
     *        in the same group as scrolling so that they remain in the same position.
     */
    public ButtonTracker (IScrollFocus focuser, IScroller scroller, Group group) {
        super(focuser, scroller);
        myNodes = new HashSet<>();
        myGroup = group;
    }

    /**
     * Adds a node as scrolling button.
     * @param node Node acting as button.
     * @param scrollX Amount along x-direction scrolled when node is pushed.
     * @param scrollY Amount along y-direction scrolled when node is pushed
     */
    public void addNode (Node node, double scrollX, double scrollY) {
        myNodes.add(node);
        if (isEnabled()) {
            myGroup.getChildren().add(node);
        }
        node.setOnMousePressed(e -> {
            if (isEnabled()) {
                tellChange(scrollX, scrollY);
            }
        });
    }

    /**
     * Adds all the buttons to the screen.
     */
    @Override
    protected void start () {
        myGroup.getChildren().clear();
        myGroup.getChildren().addAll(myNodes);
    }

    /**
     * Removes the buttons from the screen.
     */
    @Override
    protected void stop () {
        myGroup.getChildren().clear();
    }

}
