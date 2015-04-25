package authoring.dataEditors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoring.dialogs.AnimationsDialog;
import authoring.dialogs.ControlsDialog;
import authoring.dialogs.StatesDialog;
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

    public static final double OPACITY_REDUCTION_RATIO = 0.5;

    private final String X_STRING = "x";
    private final String Y_STRING = "y";
    // private boolean selected;

    private Map<String, String> myPosition;
    private Map<String, String> myVelocity;
    private Map<String, String> myKeyActions;
    private Map<String, String> myCharacteristics;

    private String myName;
    private int myID;
    private double myScale;
    private ImageView myIcon;
    private ControlsDialog myControls;
    private AnimationsDialog myAnimations;
    private Map<String, String> myAnimationsMap;
    private ObservableList<String> myStates;
    private int myCurrentScore;

    private Map<Sprite, Interaction> myInteractions;

    private Consumer<Sprite> myOnMouseClickedAction;

    private final static int MAX_ICON_WIDTH = 100;
    private final static int MAX_ICON_HEIGHT = 100;

    public final static String VELOCITY = "velocity";
    public final static String POSITION = "position";
    public static final String KEY_ACTIONS = "key_actions";
    public final static String SCALE = "Scale";

    private Boolean isPlayable = false;

    // private final double initialScale = 1.0;

    private CenterPane myParent;

    private StatesDialog myStatesDialog;

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
        myCharacteristics.put("imageURI", imageURI);
        myCharacteristics.put(SCALE, String.valueOf(myScale));
        myIcon = new ImageView();
        changeImage(new Image(imageURI));
        myCurrentScore = 0;
    }

    public Sprite (CenterPane parent) {
        myParent = parent;
        myInteractions = new HashMap<>();
        myPosition = new HashMap<>();
        myVelocity = new HashMap<>();
        myVelocity.put(X_STRING, "0.0");
        myVelocity.put(Y_STRING, "0.0");
        myStates = FXCollections.observableArrayList();
        myKeyActions = new HashMap<>();
        myCharacteristics = new HashMap<>();
        addDefaultCharacteristics(Arrays.asList(new String[] { "Name" }));
        onMouseClicked();
    }

    public Sprite (Sprite sprite, int ID, CenterPane parent) {
        this(ID, sprite.getImageURI(), parent);
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

    public void changeImage (Image image) {
        setImage(image);
        setImageIcon(image);
    }

    @SuppressWarnings("unchecked")
    private void onMouseClicked () {
        try {
            setOnMouseClicked(new ClickHandler(
                                               RightPane.class
                                                       .getMethod(
                                                                  "switchPane", Sprite.class),
                                               myParent.getParent()
                                                       .getRightPane(), this));
        }
        catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void setImageIcon (Image image) {
        myIcon.setImage(image);
        ImageEditor.setToAppropriateWidthAndHeight(myIcon, MAX_ICON_WIDTH,
                                                   MAX_ICON_HEIGHT);
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
    
    public void setScore(int score){
    	myCurrentScore = score;
    }
    
    public int getScore(){
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

    public String getImageURI () {
        return myCharacteristics.get("imageURI");
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

    public void setInteraction (Sprite otherSprite, Interaction interaction) {
        myInteractions.put(otherSprite, interaction);
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
                    .put(
                         (String) mapCharacteristics[i],
                         ((Map<String, String>) mapCharacteristics[i + 1]).toString()
                                 .substring(1,
                                            ((Map<String, String>) mapCharacteristics[i + 1])
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

    public void setAnimations (AnimationsDialog c) {
        System.out.println("Setting animations");
        myAnimations = c;
    }
    
    public void setAnimations (Map<String, String> animations) {
        myAnimationsMap = animations;
    }

    public AnimationsDialog getAnimations () {
        if (myAnimations != null) {
            return myAnimations.update(getStates());
        }
        return myAnimations;
    }

    public Boolean getPlayable () {
        return isPlayable;
    }

    public void setPlayable (Boolean b) {
        isPlayable = b;
    }

    public ObservableList<String> getStates () {
        return myStates;
    }
    
    public StatesDialog getStatesDialog () {
        return myStatesDialog;
    }
    
    public void setStates (ObservableList<String> states) {
        myStates = states;
    }

    public void setStates (StatesDialog states) {
        myStatesDialog = states;
    }

    @Override
    public String toString(){
    	return String.format("%s, %s, %s", this.myName, this.myID, this.getImageURI());
    }
    
}
