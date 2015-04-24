package game_engine.scrolling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * This class allows the creation for nodes that wrap around the scene.
 * 
 * @author Tony
 *
 */
public class WrapAround{
    private Image myImage;
    private Collection<ImageView> myNodes;
    private Group myGroup;
    private boolean myXRepeat;
    private boolean myYRepeat;
    private double myWidth;
    private double myHeight;

    /**
     * Constructor.
     * 
     * @param image The image of the node to be wrapped around.
     * @param width Screen width
     * @param height Screen height
     */
    public WrapAround (Image image, double width, double height) {
        myGroup = new Group();
        myImage = image;
        myWidth = width;
        myHeight = height;
        myNodes = new ArrayList<>();
        myXRepeat = false;
        myYRepeat = false;
        update();
    }

    /**
     * Convenience constructor.
     * 
     * @param image String representing the location of the image.
     * @param width Screen width
     * @param height Screen height
     */
    public WrapAround (String image, double width, double height) {
        this(new Image(image), width, height);
    }

    /**
     * @return The node that is wrapped around. Since wrapping involves the creation of multiple
     * versions of the same sprite, this returns the group holding all versions of the sprite.
     */
    public Node getGroup () {
        return myGroup;
    }
    
    public Collection<ImageView> getNodesOnScreen() {
        return myNodes.stream().filter(SceneUtil::isOnScreen).collect(Collectors.toList());
    }
    
    public void setImage (Image image) {
        myImage = image;
        myNodes.forEach(view -> view.setImage(image));
    }
    
    public Image getImage () {
        return myImage;
    }

    /**
     * Allows the node to be wrapped around horizontally. Should be done before any translations occur.
     */
    public void repeatHorizontal () {
        myXRepeat = true;
        update();
        myGroup.translateXProperty().addListener( (change) -> updateX(myNodes));
    }

    /**
     * Allows the node to be wrapped vertically. Should be done before any translations occur.
     */
    public void repeatVertical () {
        myYRepeat = true;
        update();
        myGroup.translateYProperty().addListener( (change) -> updateY(myNodes));
    }

    private ImageView makeImageCopy (double translateX, double translateY) {
        ImageView imageView = new ImageView(myImage);
        imageView.setTranslateX(translateX);
        imageView.setTranslateY(translateY);
        return imageView;
    }

    private void update () {
        myGroup.getChildren().removeAll(myNodes);
        updateNodes();
        myGroup.getChildren().addAll(myNodes);
    }

    private void updateNodes () {
        myNodes.clear();
        int x = myXRepeat ? 1 : 0;
        int y = myYRepeat ? 1 : 0;
        for (int i = -x; i <= x; i++) {
            for (int j = -y; j <= y; j++) {
                myNodes.add(makeImageCopy(myWidth * i, myHeight * j));
            }
        }
    }

    private void updateX (Collection<ImageView> myNodes2) {
        for (Node node : myNodes2) {
            double newPos = node.getTranslateX();
            double distance = node.getTranslateX() + myGroup.getTranslateX();
            if (Math.abs(distance) >= 2 * myWidth) {
                newPos -= 3 * myWidth * Math.signum(distance);
            }
            node.setTranslateX(newPos);
        }
    }

    private void updateY (Collection<ImageView> myNodes2) {
        for (Node node : myNodes2) {
            double newPos = node.getTranslateY();
            double distance = node.getTranslateY() + myGroup.getTranslateY();
            if (Math.abs(distance) >= 2 * myHeight) {
                newPos -= 3 * myHeight * Math.signum(distance);
            }
            node.setTranslateY(newPos);
        }
    }
}
