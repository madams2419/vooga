package authoring.panes.centerPane;

import game_engine.objective.Objective;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import authoring.dataEditors.Sprite;
import authoring.dialogs.FileChooserDialog;
import authoring.dialogs.NewRegionDialog;
import authoring.panes.rightPane.GlobalCreationPane;
import authoring.userInterface.AuthoringWindow;
import authoring.userInterface.SpriteCursor;
import authoring.util.FrontEndUtils;

public class CenterCanvas extends ScrollPane {

    private static final String INITIAL_LABEL_CONTENT = "\n\tClick on the screen to add a canvas!";
    private static final int INITIAL_REGION_Y = 600;
    private static final int INITIAL_REGION_X = 1000;
    
    private List<Map<String, String>> myEnvironmentList;
    private ObservableList<Sprite> myListOfSprites;
    private ObservableList<String> myListOfObjectives;
    
    private Region myCurrentRectangle;
    private GlobalCreationPane gp;
    private Group myGroup;
    private Scene myScene;
    private AuthoringWindow myParent;
    private Label myInitialLabel;

    CenterCanvas(Scene scene, AuthoringWindow parent) {
        assert (scene != null);
        assert (parent != null);
        myScene = scene;
        myParent = parent;
        myGroup = new Group();
        gp = new GlobalCreationPane(myScene, myParent.getRightPane());
        this.setContent(myGroup);

        //myGroup.setOnMouseClicked(e -> canvasClicked(e));

        myListOfSprites = FXCollections.observableArrayList();
        myListOfObjectives = FXCollections.observableArrayList();
        myEnvironmentList = new ArrayList<>();

        FrontEndUtils.setKeyActions(this);
        addMaptoEnvironment(gp.getFields());
        
        setupInitialRegion();
    }
    
    private void setupInitialRegion() {
        myCurrentRectangle = new Region(INITIAL_REGION_X, INITIAL_REGION_Y, Color.TRANSPARENT);
        myCurrentRectangle.setOnMouseClicked(e -> new NewRegionDialog(this));
        myInitialLabel = new Label(INITIAL_LABEL_CONTENT);
        myGroup.getChildren().addAll(myInitialLabel, myCurrentRectangle);
    }
    
    public void addMaptoEnvironment(Map<String, String> m) {
        myEnvironmentList.add(m);
    }

    private void canvasClicked(MouseEvent e) {
        try {
            if (e.getButton() == MouseButton.SECONDARY){     	
            	FileChooserDialog chooser = new FileChooserDialog(
            			new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG"),
            			new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG"));            	
                myCurrentRectangle.setBackgroundImage(chooser.grabImage());
            }
            Sprite s = ((SpriteCursor) myScene.getCursor())
                    .getCurrentSprite();

            s.setXPosition(e.getX() - s.getImage().getWidth() / 2);
            s.setYPosition(e.getY() - s.getImage().getHeight() / 2);
            myGroup.getChildren().add(s);
            this.myListOfSprites.add(s);
            myScene.setCursor(ImageCursor.DEFAULT);
            s.setOnMouseDragged(a -> imageDragged(a, s));

        } catch (ClassCastException a) {
        } catch (NullPointerException b) {
        }
    }
    
    public void removeSprite(Sprite s) {
        myListOfSprites.remove(s);
        myGroup.getChildren().remove(s);
    }

    private void imageDragged(MouseEvent a, Sprite s) {
        s.setXPosition(a.getSceneX() - (s.getImage().getWidth()/2));
        s.setYPosition(a.getSceneY() - (s.getImage().getHeight()/2));
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
    
    public Region getRegion(){
        return myCurrentRectangle;
    }
    
    public void addObjective(Map<String,String> params) {
    	
    }
    
}
