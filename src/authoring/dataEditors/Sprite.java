package authoring.dataEditors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoring.dialogs.AnimationsDialog;
import authoring.dialogs.CharacterPhysicsDialog;
import authoring.dialogs.ControlsDialog;
import authoring.panes.centerPane.CenterPane;
import authoring.panes.rightPane.RightPane;
import authoring.userInterface.ClickHandler;
import authoring.util.FrontEndUtils;
import authoring.util.ImageEditor;


/***
 * Class that contains information about the sprites for eventually generating
 * xml files
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */
public class Sprite extends ImageView {

    private static final String NAME = "Name";
    private static final String X_STRING = "x";
    private static final String Y_STRING = "y";
    private static final String VELOCITY = "velocity";
    private static final String POSITION = "position";
    private static final String KEY_ACTIONS = "key_actions";
    public static final String SCALE = "Scale";
    private static final String IMAGE_URI = "imageURI";
    private static final String SWITCH_PANE_METHOD = "switchPane";
    private static final int MAX_ICON_WIDTH = 100;
    private static final int MAX_ICON_HEIGHT = 100;

    public static final double OPACITY_REDUCTION_RATIO = 0.5;

    private Map<String, String> myPosition;
    private Map<String, String> myVelocity;
    private Map<String, String> myKeyActions;
    private Map<String, String> myCharacteristics;

    private int myID;
    private int myCurrentScore;
    private double myScale;
    private boolean isPlayable = false;
    private String myName;
    private ImageView myIcon;
    private ControlsDialog myControls;
    private AnimationsDialog myAnimations;
    private CharacterPhysicsDialog myPhysics;
    private String myType;
    private String myMaterial;
    private Map<Sprite, Map<Action, String>> mySpriteInteractionMap;
    private Map<Sprite, Interaction> myInteractions;
    private Consumer<Sprite> myOnMouseClickedAction;

    private CenterPane myParent;

    // private final double initialScale = 1.0;

    /***
     * 
     * @param ID
     * @param imageURI
     * @param parent
     *        container, instance of the CenterPane.
     */
    public Sprite (int ID, String imageURI, CenterPane parent) {
        this(parent);
        this.myID = ID;
        myScale = 1.0;
        myCharacteristics.put(IMAGE_URI, imageURI);
        myCharacteristics.put(SCALE, String.valueOf(myScale));
        myIcon = new ImageView();
        changeImage(imageURI);
        myCurrentScore = 0;
    }

    public Sprite (CenterPane parent) {
        myParent = parent;
        myInteractions = new HashMap<>(); // TODO: not needed?
        myPosition = new HashMap<>();
        myVelocity = new HashMap<>();
        mySpriteInteractionMap = new HashMap<>();
        myVelocity.put(X_STRING, "0.0");
        myVelocity.put(Y_STRING, "0.0");
        myKeyActions = new HashMap<>();
        myCharacteristics = new HashMap<>();
        addDefaultCharacteristics(Arrays.asList(new String[] { NAME }));
        onMouseClicked();
    }

    public Sprite (Sprite sprite, int ID, CenterPane parent) {
        this(ID, sprite.getImageURI(), parent);
    }

    
    public Map<Sprite, Map<Action, String>> getInteractionMap() {
    	return mySpriteInteractionMap;
    }

    public void addDefaultCharacteristics (List<String> characteristics) {
        characteristics.forEach(characteristic -> myCharacteristics.put(
                                                                        characteristic, ""));
    }

    public void setName (String name) {
        this.myName = name;
    }

    public String getName () {
        return this.myName;
    }

    public Consumer<Sprite> getConsumer () {
        return myOnMouseClickedAction;
    }

    public int getID () {
        return myID;
    }

    public ImageView getIcon () {
        return myIcon;
    }

    private void changeImage (Image image) {
        setImage(image);
        setImageIcon(image);
    }
    
    public void changeImage (String imageURL) {
        myCharacteristics.put(IMAGE_URI, imageURL);
        changeImage (new Image(imageURL));
    }

    @SuppressWarnings("unchecked")
    private void onMouseClicked () {
        try {
            setOnMouseClicked(new ClickHandler(RightPane.class.getMethod(SWITCH_PANE_METHOD, Sprite.class),
                                               myParent.getParent().getRightPane(), this));
        }
        catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void setImageIcon (Image image) {
        myIcon.setImage(image);
        ImageEditor.setToAppropriateWidthAndHeight(myIcon, MAX_ICON_WIDTH, MAX_ICON_HEIGHT);
    }

    public void setXPosition (double value) {
        this.setX(value);
        myPosition.put(X_STRING, Double.toString(value));
    }

    public void setXPosition (String value) {
        setXPosition(Double.parseDouble(value));
    }

    public void setYPosition (double value) {
        this.setY(value);
        myPosition.put(Y_STRING, Double.toString(value));
    }

    public void setYPosition (String value) {
        setYPosition(Double.parseDouble(value));
    }

    public void setPosition (Map<String, String> newPosition) {
        myPosition = new HashMap<>();
        newPosition.forEach( (s1, s2) -> myPosition.put(s1, s2));
        setXPosition(myPosition.get(X_STRING));
        setYPosition(myPosition.get(Y_STRING));
    }

    public void setScore (int score) {
        myCurrentScore = score;
    }

    public int getScore () {
        return myCurrentScore;
    }

    public void setScale (double scale) {

        this.setScaleX(scale);
        this.setScaleY(scale);
        // myCharacteristics.put(SCALE, scale);
        // double newScale = Double.parseDouble(scale);
    }

    public double getXPosition () {
        return Double.parseDouble(myPosition.get(X_STRING));
    }

    public double getYPosition () {
        return Double.parseDouble(myPosition.get(Y_STRING));
    }

    public void setVelocity (Map<String, String> newVelocity) {
        myVelocity = new HashMap<>();
        newVelocity.forEach( (s1, s2) -> myVelocity.put(s1, s2));
    }

    public void setXVelocity (double value) {
        setXVelocity(Double.toString(value));
    }

    public void setXVelocity (String value) {
        myVelocity.put(X_STRING, value);
    }

    public void setYVelocity (double value) {
        setYVelocity(Double.toString(value));
    }

    public void setYVelocity (String value) {
        myVelocity.put(Y_STRING, value);
    }

    public void setKeyActions (Map<String, String> keyActions) {
        myKeyActions = keyActions;
    }

    public Map<String, String> getKeyActions () {
        return myKeyActions;
    }

    public String getImageURI () {
        return myCharacteristics.get(IMAGE_URI);
    }

    public void setCharacteristic (String characteristic, String value) {
        if (characteristic.equals(POSITION))
            setPosition(FrontEndUtils.stringToMap(value));
        else if (characteristic.equals(VELOCITY))
            setVelocity(FrontEndUtils.stringToMap(value));
        else if (characteristic.equals(KEY_ACTIONS))
            setKeyActions(FrontEndUtils.stringToMap(value));
        else
            myCharacteristics.put(characteristic, value);
    }

    public String getCharacteristic (String characteristic) {
        return myCharacteristics.get(characteristic);
    }

    public void addInteraction (Sprite otherSprite, Map<Action, String> interaction) {
    	//mySpriteInteractionMap.getOrDefault(otherSprite, interaction);
    	mySpriteInteractionMap.putIfAbsent(otherSprite, interaction);
        mySpriteInteractionMap.replace(otherSprite, interaction);
        String s = "buffer";
    }

    @SuppressWarnings("unchecked")
    /***
     * pls make sure all instance variables are string,string maps
     * @return
     */
    public Map<String, String> getCharacteristics () {
        Object[] mapCharacteristics = { POSITION, myPosition, VELOCITY,
                                       myVelocity };
        for (int i = 0; i < mapCharacteristics.length; i += 2) {
            myCharacteristics
                    .put((String) mapCharacteristics[i],
                         ((Map<String, String>) mapCharacteristics[i + 1]).toString()
                                 .substring(1,((Map<String, String>) mapCharacteristics[i + 1])
                                            .toString().length() - 1));
        }
        return this.myCharacteristics;
    }

    public void setControls (ControlsDialog c) {
        myControls = c;
    }

    public ControlsDialog getControls () {
        return myControls;
    }

    public void setAnimations (AnimationsDialog animations) {
        myAnimations = animations;
    }

    public AnimationsDialog getAnimations () {
        if (myAnimations != null) {
            return myAnimations.update();
        }
        return myAnimations = AnimationsDialog.defaultAnimations(this);
    }

    public void setPhysics (CharacterPhysicsDialog physics) {
        myPhysics = physics;
    }
    
    public CharacterPhysicsDialog getPhysics () {
        if (myPhysics != null) {
            return myPhysics;
        }
        return myPhysics;
    }
    
    public Boolean getPlayable () {
        return isPlayable;
    }

    public void setPlayable (Boolean b) {
        isPlayable = b;
    }

    public double getWidth () {
        return this.getFitWidth();
    }

    public double getHeight () {
        return this.getFitHeight();
    }

    public String getMyType () {
        return myType;
    }

    public void setMyType (String myType) {
        this.myType = myType;
    }

    public String getMyMaterial () {
        return myMaterial;
    }

    public void setMyMaterial (String myMaterial) {
        this.myMaterial = myMaterial;
    }

    @Override
    public String toString () {
        return String.format("%s, %s, %s", this.myName, this.myID, this.getImageURI());
    }

}
