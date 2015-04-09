package authoring;
//package authoring;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoring.util.ImageEditor;


/***
 * Class that contains information about the sprites for eventually generating xml files
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */
public class Sprite extends ImageView {
    
    public static final double OPACITY_REDUCTION_RATIO = 0.5;

    private final String X_STRING = "x";
    private final String Y_STRING = "y";
    private boolean selected;

    private Map<String, Double> myPosition;
    private Map<String, Double> myVelocity;
    private Map<String, String> myKeyActions;
    private Map<String, String> myCharacteristics;
    private String myImageURI;
    private int myID;
    private Consumer<Sprite> myOnMouseClickedAction;
    
    private ImageView myIcon;
    private final static int MAX_ICON_WIDTH = 100;
    private final static int MAX_ICON_HEIGHT = 100;

    public Sprite (int ID, String imageURI) {
        this();
        this.myID = ID;
        myImageURI = imageURI;
        myIcon = new ImageView();
        changeImage(new Image(getClass().getResourceAsStream(myImageURI)));
    }

    // addKeyAction()

    public Sprite (int iD, String string, Consumer<Sprite> consumer) {
        this(iD, string);
        myOnMouseClickedAction = consumer;
        onMouseClicked(consumer);
    }
    
    public Sprite () {
        myPosition = new HashMap<>();
        myVelocity = new HashMap<>();
        myKeyActions = new HashMap<>();
    }
    
    public Sprite (Sprite sprite, int ID) {
        this(ID, sprite.getImageURI(), sprite.getConsumer());
    }

    public Consumer<Sprite> getConsumer() {
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
    
    private void onMouseClicked(Consumer<Sprite> consumer) {
        setOnMouseClicked(e -> consumer.accept(this));
    }

    private void setImageIcon (Image image) {
        myIcon.setImage(image);
        ImageEditor.setToAppropriateWidthAndHeight(myIcon, MAX_ICON_WIDTH, MAX_ICON_HEIGHT);
    }

    public void setXPosition (double value) {
        myPosition.put(X_STRING, value);
    }

    public void setYPosition (double value) {
        myPosition.put(Y_STRING, value);
    }

    public double getXPosition () {
        return myPosition.get(X_STRING);
    }

    public double getYPosition () {
        return myPosition.get(Y_STRING);
    }

    public void setXVelocity (double value) {
        myVelocity.put(X_STRING, value);
    }

    public void setYVelocity (double value) {
        myVelocity.put(Y_STRING, value);
    }

    public void setKeyControl (String action, String result) {
        myKeyActions.put(action, result);
    }

    public String getImageURI () {
        return myImageURI;
    }

    public void setCharacteristic (String characteristic, String value) {
        myCharacteristics.put(characteristic, value);
    }

    public String getCharacteristic (String characteristic) {
        return myCharacteristics.get(characteristic);
    }
    
}
