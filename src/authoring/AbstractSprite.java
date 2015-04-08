package src.authoring;

import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoring.util.ImageEditor;


/**
 * This is the superclass for SpecificSprite and SpriteType. Its goal is to eliminate a lot of
 * duplicated code in the two classes, since they have common methods for their images and image
 * icons as well as for handling when they are clicked on.
 * 
 * @author Natalie Chanfreau
 *
 */

public abstract class AbstractSprite extends ImageView {

    public static final double OPACITY_REDUCTION_RATIO = 0.5;

    final static String X_STRING = "x";
    final static String Y_STRING = "y";
    private boolean selected;

    private int myID;
    private Consumer<AbstractSprite> myOnMouseClickedAction;

    private ImageView myIcon;

    private String myImageURI;
    private final static int MAX_ICON_WIDTH = 100;
    private final static int MAX_ICON_HEIGHT = 100;

    public AbstractSprite (int ID, String imageURI) {
        this();
        this.myID = ID;
        myImageURI = imageURI;
        myIcon = new ImageView();
        changeImage(new Image(getClass().getResourceAsStream(imageURI)));
    }

    public AbstractSprite (int ID, String imageURI, Consumer<AbstractSprite> consumer) {
        this(ID, imageURI);
        myOnMouseClickedAction = consumer;
        onMouseClicked(consumer);
    }

    public AbstractSprite () {
        // TODO Auto-generated constructor stub
    }

    public Consumer<AbstractSprite> getConsumer () {
        return myOnMouseClickedAction;
    }

    public int getID () {
        return myID;
    }

    public ImageView getIcon () {
        return myIcon;
    }

    public void changeImage (Image image) {
        setImage(image);
        setImageIcon(image);
    }

    private void onMouseClicked (Consumer<AbstractSprite> consumer) {
        setOnMouseClicked(e -> consumer.accept(this));
    }

    private void setImageIcon (Image image) {
        myIcon.setImage(image);
        ImageEditor.setToAppropriateWidthAndHeight(myIcon, MAX_ICON_WIDTH, MAX_ICON_HEIGHT);
    }

    public String getImageURI () {
        return myImageURI;
    }

}
