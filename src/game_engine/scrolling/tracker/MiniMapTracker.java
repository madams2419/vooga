package game_engine.scrolling.tracker;

import game_engine.scrolling.scroller.IScroller;
import game_engine.scrolling.scrollfocus.IScrollFocus;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//TODO: May want to add a square representing where the camera would be when the mouse hovers over the minimap.

/**
 * Class that allows for scrolling by clicking on a mini-map. Scrolls to the position in the map
 * corresponding to the position in the mini-map.
 * 
 * @author Tony
 *
 */
public class MiniMapTracker extends AbstractTracker {
    private Node myMiniMap;
    private double myRatio;

    /**
     * Constructor.
     * @param focuser See AbstractTracker.
     * @param scroller See AbstractTracker.
     * @param miniMap Node representing the mini-map. Mini-map should not be in the same group as the scroll target group.
     * @param ratio Ratio of size of the mini-map to the full size map.
     */
    public MiniMapTracker (IScrollFocus focuser, IScroller scroller, double ratio) {
        this(focuser, scroller, scroller.getGroup(), ratio);
    }
    
    public MiniMapTracker (IScrollFocus focuser, IScroller scroller, Node node, double ratio) {
        super(focuser, scroller);
        myRatio = ratio;
        myMiniMap = makeMiniMap(node, ratio);
    }
    
    private Node makeMiniMap (Node node, double ratio) {
        double width = node.getBoundsInParent().getWidth();
        double height = node.getBoundsInParent().getHeight();
        Image image = node.snapshot(new SnapshotParameters(), null);
        ImageView view = new ImageView(image);
        view.setFitHeight(height * ratio);
        view.setFitWidth(width * ratio);
        return view;
    }

    @Override
    protected void start () {
        myMiniMap.setOnMousePressed(e -> {
            System.out.println(e.getSource());
            tell(e.getX() / myRatio, e.getY() / myRatio);
        });
    }

    @Override
    protected void stop () {
        myMiniMap.setOnMousePressed(null);
    }
    
    public Node getMiniMap() {
        return myMiniMap;
    }

}
