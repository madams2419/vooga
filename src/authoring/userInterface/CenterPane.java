package authoring.userInterface;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoring.SpecificSprite;

/**
 * 
 * @author Andrew Sun
 *
 */
public class CenterPane extends ScrollPane {
	
	private List<SpecificSprite> mySpriteList;
	private Scene myScene;
	private Group myGroup;
	private Rectangle myCurrentRectangle;
	

	CenterPane(Scene scene) {
		myScene = scene;
		myGroup = new Group();
//		myMask = new Rectangle(1000, 1000);
//		myMask.setOpacity(0);
//		myGroup.getChildren().add(myMask);
		this.setContent(myGroup);
		
		// TODO: change to list
		mySpriteList = new ArrayList<>();
		
	}

	// TODO: dynamically change size of sprites
	// Update: may not be possible due difficulty. Could possibly change in our information pane
	// but does cannot dynamically resize directly.
	
	// TODO: dynamically add to or change size of region
	public void changeRectangleSize(double x, double y){
		myCurrentRectangle.setX(myCurrentRectangle.getX() + x);
		myCurrentRectangle.setY(myCurrentRectangle.getY() + y);
	}
	
	private void canvasClicked(MouseEvent e) {

		try{
		SpecificSprite s = ((SpriteCursor) myScene.getCursor()).getCurrentSprite();
		
		// Hey Andrew! I commented this next line out because I decided to pass in 
		// a Consumer in the creation of the Sprite (in CharacterCreationPane), and
		// this line was resetting the setOnMouseClicked method that I had used in 
		// order to switch between panes (although in the end it should probably be 
		// sent to some intermediate class rather than directly switch panes so that  
		// the logic of how many sprites are currently clicked is done before 
		// deciding what will happen).
		
//		s.setOnMouseClicked(p -> spriteClicked(p, s));
		
		s.setX(e.getX() - s.getImage().getWidth()/2);
		s.setY(e.getY() - s.getImage().getHeight()/2);
		
		//s.getClip().setClip(myMask);
		myGroup.getChildren().add(s);
		
		System.out.println(s.getID());
		mySpriteList.add(s);
		myScene.setCursor(ImageCursor.DEFAULT);
		

		}
		catch (ClassCastException a){
			
		}
		catch (NullPointerException b){
			
		}	
	}

	
	private void spriteClicked(MouseEvent p, SpecificSprite s) {
		myGroup.getChildren().remove(s);
		//s.setTranslateX(100);
		System.out.println("Removing");
		// TODO: Show sprite data in information pane
		// TODO: Allow stacked sprites
	}
	
	public void createRegion(double x, double y){
		myCurrentRectangle = new Rectangle (x, y, Color.WHITE);
		myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
		myGroup.getChildren().add(myCurrentRectangle);
	}
	
	
	
}
