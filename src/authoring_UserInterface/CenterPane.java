package authoring_UserInterface;

import java.awt.Cursor;
import java.util.Stack;

import authoring_environment.Sprite;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Andrew Sun
 *
 */
public class CenterPane extends ScrollPane {
	
	private Stack<Sprite> myStack;
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
		myStack = new Stack<>();
		
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
		Sprite s = ((SpriteCursor) myScene.getCursor()).getCurrentSprite();
		s.setOnMouseClicked(p -> spriteClicked(p, s));
		s.setX(e.getX() - s.getImage().getWidth()/2);
		s.setY(e.getY() - s.getImage().getHeight()/2);
		
		//s.getClip().setClip(myMask);
		myGroup.getChildren().add(s);
		
		System.out.println(s.getID());
		myStack.add(s);
		myScene.setCursor(ImageCursor.DEFAULT);
		

		}
		catch (ClassCastException a){
			
		}
		catch (NullPointerException b){
			
		}	
	}

	
	private void spriteClicked(MouseEvent p, Sprite s) {
		myGroup.getChildren().remove(s);
		//s.setTranslateX(100);
		System.out.println("Removing");
		// TODO: Show sprite data in information pane
		// TODO: Allow stacked sprites
	}
	
	public void createRegion(double x, double y){
		// TODO: dynamically change size of rectangle here		
		myCurrentRectangle = new Rectangle (x, y, Color.WHITE);
		myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
		myGroup.getChildren().add(myCurrentRectangle);
	}
	
	
	
}
