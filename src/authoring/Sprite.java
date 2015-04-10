package authoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import authoring.rightPane.RightPane;
import authoring.userInterface.AuthoringWindow;
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

	private Map<String, Double> myPosition;
	private Map<String, Double> myVelocity;
	private Map<String, String> myKeyActions;
	private Map<String, String> myCharacteristics;

	private String myImageURI;
	private int myID;
	private ImageView myIcon;

	private Consumer<Sprite> myOnMouseClickedAction;

	private final static int MAX_ICON_WIDTH = 100;
	private final static int MAX_ICON_HEIGHT = 100;
	final String VELOCITY = "velocity";
	public final String POSITION = "position";

	public Sprite(int ID, String imageURI) {
		this();
		this.myID = ID;
		myImageURI = imageURI;
		myCharacteristics.put("imageURI", myImageURI);
		myCharacteristics.put("ID", String.valueOf(ID));
		myIcon = new ImageView();
		changeImage(new Image(getClass().getResourceAsStream(myImageURI)));
	}

	public Sprite() {
		myPosition = new HashMap<>();
		myVelocity = new HashMap<>();
		myVelocity.put(X_STRING, 0.0);
		myVelocity.put(Y_STRING, 0.0);
		myKeyActions = new HashMap<>();
		myCharacteristics = new HashMap<>();
		addDefaultCharacteristics(Arrays.asList(new String[] { "Name" }));
		onMouseClicked();
		FrontEndUtils.setKeyActions(this);
	}

	public Sprite(Sprite sprite, int ID) {
		this(ID, sprite.getImageURI());
	}

	public void addDefaultCharacteristics(List<String> characteristics) {
		characteristics.forEach(characteristic -> myCharacteristics.put(
				characteristic, ""));
	}

	public String getName() {
		return myCharacteristics.get("Name");
	}

	public Consumer<Sprite> getConsumer() {
		return myOnMouseClickedAction;
	}

	public int getID() {
		return myID;
	}

	public ImageView getIcon() {
		return myIcon;
	}

	public void changeImage(Image image) {
		setImage(image);
		setImageIcon(image);
	}

	private void onMouseClicked() {
		try {
			setOnMouseClicked(new ClickHandler(RightPane.class.getMethod(
					"switchPane", Sprite.class), RightPane.getInstance(), this));
			System.out.println(AuthoringWindow.getControl());
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setImageIcon(Image image) {
		myIcon.setImage(image);
		ImageEditor.setToAppropriateWidthAndHeight(myIcon, MAX_ICON_WIDTH,
				MAX_ICON_HEIGHT);
	}

	public void setXPosition(double value) {
		this.setX(value);
		myPosition.put(X_STRING, value);
	}

	public void setYPosition(double value) {
		this.setY(value);
		myPosition.put(Y_STRING, value);
	}

	public void setPosition(Map<String, String> newPosition) {
		myPosition = new HashMap<>();
		newPosition.forEach((s1, s2) -> myPosition.put(s1,
				Double.parseDouble(s2)));
		setXPosition(myPosition.get(X_STRING));
		setYPosition(myPosition.get(Y_STRING));
	}

	public double getXPosition() {
		return myPosition.get(X_STRING);
	}

	public double getYPosition() {
		return myPosition.get(Y_STRING);
	}

	public void setVelocity(Map<String, String> newVelocity) {
		myVelocity = new HashMap<>();
		newVelocity.forEach((s1, s2) -> myVelocity.put(s1,
				Double.parseDouble(s2)));
	}

	public void setXVelocity(double value) {
		myVelocity.put(X_STRING, value);
	}

	public void setYVelocity(double value) {
		myVelocity.put(Y_STRING, value);
	}

	public void setKeyControl(String action, String result) {
		myKeyActions.put(action, result);
	}

	public String getImageURI() {
		return myImageURI;
	}

	public void setCharacteristic(String characteristic, String value) {
		if (characteristic.equals(this.POSITION))
			setPosition(FrontEndUtils.stringToMap(value));
		else if (characteristic.equals(this.VELOCITY))
			setVelocity(FrontEndUtils.stringToMap(value));
		else
			myCharacteristics.put(characteristic, value);
	}

	public String getCharacteristic(String characteristic) {
		return myCharacteristics.get(characteristic);
	}

	public Map<String, String> getCharacteristics() {
		myCharacteristics.put(
				POSITION,
				myPosition.toString().substring(1,
						myPosition.toString().length() - 1));
		myCharacteristics.put(
				VELOCITY,
				myVelocity.toString().substring(1,
						myVelocity.toString().length() - 1));
		return this.myCharacteristics;
	}

}
