package authoring.rightPane;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import authoring.Sprite;
import authoring.userInterface.BufferedImageLoader;
import authoring.userInterface.SpriteCursor;
import authoring.userInterface.SpriteSheet;
import authoring.util.ImageEditor;


/**
 * This will allow the user to select from a number of images (or insert his/her
 * own image) to create a character that can be placed on the canvas.
 * 
 * @author Daniel Luker, Andrew Sun, Natalie Chanfreau
 *
 */

public class CharacterCreationPane extends EditingPane {
	private static final int WIDTH = 320;
    private static final int HEIGHT=WIDTH/12*9;
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet=null;
	
    CharacterCreationPane (Scene scene, RightPane parent, List<String> availableCharacterTypeURIs) {
        super(scene, parent);
        this.getChildren().add(
                               new TextArea(String
                                       .format("Information%n"
                                               + "when a drop down%n" + "item is selected, or%n"
                                               + "a current element on%n" + "the scroll pane is%n"
                                               + "selected (up to two%n"
                                               + "selections), its (their)%n"
                                               + "information will be%n" + "shown here.")));

        addSpritesToPane(availableCharacterTypeURIs);
    }
    //temp
    private BufferedImage player;
    
    private void init(){
    	BufferedImageLoader loader=new BufferedImageLoader();
    	try{
    		spriteSheet=loader.loadImage("/sprite_sheet.png");
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	SpriteSheet ss = new SpriteSheet(spriteSheet);
    	player=ss.grabImage(1,1,32, 32); 
    
    }
    
    private void addSpritesToPane (List<String> availableCharacterTypeURIs) {
        VBox v = new VBox(20);
        ScrollPane s = new ScrollPane(v);
        this.getChildren().add(s);
        for (int i = 0; i < availableCharacterTypeURIs.size(); i++) {
            addSpriteToPane(i, availableCharacterTypeURIs.get(i), v);
        }
    }

    private void addSpriteToPane (int id, String imageURI, VBox v) {
        Sprite sampleImage = new Sprite(id, imageURI, myParent.getParent()
                .getMyCenterPane());

        // these two aren't working for now when the copy is made in
        // imageClicked (Consumer<Sprite> spriteClicked, Sprite sampleImage, int
        // ID):
        // sampleImage.setOnMouseEntered(i ->
        // ImageEditor.reduceOpacity(sampleImage,
        // Sprite.OPACITY_REDUCTION_RATIO));
        // sampleImage.setOnMouseExited(i ->
        // ImageEditor.restoreOpacity(sampleImage,
        // Sprite.OPACITY_REDUCTION_RATIO));
        
        int ID = 100; // for now, it doesn't change, but this should eventually
                      // be unique for each sprite

        ImageView sampleImageIcon = sampleImage.getIcon();
        sampleImageIcon.setOnMouseClicked(e -> imageClicked(sampleImage, ID));
        sampleImageIcon.setOnMouseDragged(e -> imageDragged(e));
        sampleImageIcon.setOnMouseEntered(i -> ImageEditor
                .reduceOpacity(
                               sampleImageIcon, Sprite.OPACITY_REDUCTION_RATIO));
        sampleImageIcon.setOnMouseExited(i -> ImageEditor
                .restoreOpacity(sampleImageIcon));
        v.getChildren().add(sampleImageIcon);
//        myScrollPaneContent.getChildren().add(sampleImageIcon);
    }

    private void imageDragged (MouseEvent e) {

    }

    // an image in the right pane is clicked to be moved to the center pane
    private void imageClicked (Sprite sampleImage, int ID) {
        // need to now set mouse cursor to the sprite image
        // getMyScene().setCursor(new SpriteCursor(new Sprite(sampleImage, ID,
        // spriteClicked)));
        getMyScene().setCursor(
                               new SpriteCursor(new Sprite(sampleImage, ID, myParent
                                       .getParent().getMyCenterPane())));

    }
}
