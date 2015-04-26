package authoring.panes.centerPane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import authoring.dataEditors.Sprite;
import authoring.dialogs.NewRegionDialog;
import authoring.fileBuilders.KeyAction_XML;
import authoring.fileBuilders.Objective_XML;
import authoring.fileBuilders.PhysicsEngine_XML;
import authoring.panes.centerPane.modes.Mode;
import authoring.panes.rightPane.GlobalCreationPane;
import authoring.userInterface.AuthoringWindow;
import authoring.util.FrontEndUtils;

public class CenterCanvas extends ScrollPane {

	private static final String INITIAL_LABEL_CONTENT = "\n\tClick on the screen to add a canvas!";
	private static final int INITIAL_REGION_Y = 600;
	private static final int INITIAL_REGION_X = 1000;

	private List<Map<String, String>> myEnvironmentList = new ArrayList<>();
	private ObservableList<Sprite> myListOfSprites = FXCollections
			.observableArrayList();
	private List<Objective_XML> myListOfObjectives = new ArrayList<>();
	private Map<String, KeyAction_XML> myKeyActions = new HashMap<>();
	private Mode myMode;

	private Region myCurrentRectangle;
	private GlobalCreationPane gp;
	private Group myGroup;
	private Scene myScene;
	private AuthoringWindow myParent;
	private Label myInitialLabel;
	private float myGravity = 10;
	private PhysicsEngine_XML myEngine = PhysicsEngine_XML
			.defaultPhysicsEngine(); // default physics engine

	CenterCanvas(Scene scene, AuthoringWindow parent) {
		assert (scene != null);
		assert (parent != null);
		myMode = new Mode();
		myScene = scene;
		myParent = parent;
		myGroup = new Group();
		gp = new GlobalCreationPane(myScene, myParent.getRightPane());
		this.setContent(myGroup);

		// myGroup.setOnMouseClicked(e -> canvasClicked(e));

		myListOfSprites = FXCollections.observableArrayList();
		myListOfObjectives = new ArrayList<>();
		myEnvironmentList = new ArrayList<>();

		FrontEndUtils.setKeyActions(this);
		addMaptoEnvironment(gp.getFields());

		setupInitialRegion();
	}

	private void setupInitialRegion() {
		myCurrentRectangle = new Region(INITIAL_REGION_X, INITIAL_REGION_Y,
				Color.TRANSPARENT);
		myCurrentRectangle.setOnMouseClicked(e -> new NewRegionDialog(this));
		myInitialLabel = new Label(INITIAL_LABEL_CONTENT);
		myGroup.getChildren().addAll(myInitialLabel, myCurrentRectangle);
	}

	public void addMaptoEnvironment(Map<String, String> m) {
		myEnvironmentList.add(m);
	}

	private void canvasClicked(MouseEvent e) {
	    myMode.canvasClicked(e, myCurrentRectangle, myScene, myGroup, myListOfSprites);
	}

	public void removeSprite(Sprite s) {
		myListOfSprites.remove(s);
		myGroup.getChildren().remove(s);
	}

	public Object[] getData() {
		return new Object[] { myCurrentRectangle, myListOfSprites };
	}

	public List<Sprite> getSprites() {
		return myListOfSprites;
	}

	public Collection<Map<String, String>> getEnvironment() {
		return myEnvironmentList;
	}

	public void createRegion(double x, double y) {
		myGroup.getChildren().remove(myInitialLabel);
		if (myCurrentRectangle != null) {
			myGroup.getChildren().remove(myCurrentRectangle);
		}
		myCurrentRectangle = new Region(x, y, Color.WHITE);
		myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
		myGroup.getChildren().add(myCurrentRectangle);
	}

	public Region getRegion() {
		return myCurrentRectangle;
	}

	public void addObjective(Objective_XML e) {
		myListOfObjectives.add(e);
	}

	public List<Objective_XML> getObjectives() {
		return myListOfObjectives;
	}

	public void addSetting(String type, String value) {
		if (type.equals("gravity"))
			myGravity = Float.parseFloat(value);
	}

	public String getSetting(String type) {
		if (type.equals("gravity"))
			return Float.toString(myGravity);
		return ""; // or null?
	}

	public void addPhysics(PhysicsEngine_XML p) {
		myEngine = p;
	}

	public PhysicsEngine_XML getPhysics() {
		return myEngine;
	}

	public List<KeyAction_XML> getKeyActions() {
		return myKeyActions.values().stream().map(s -> {
			return s;
		}).collect(Collectors.toList());
	}

	public void addKeyAction(String key, KeyAction_XML action) {
		if (myKeyActions.containsKey(key))
			myKeyActions.get(key).appendBehaviours(action);
		else
			myKeyActions.put(key, action);
	}

}
