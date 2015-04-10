package authoring.userInterface;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoring.Sprite;

/**
 * 
 * @author Andrew Sun
 *
 */
public class CenterPane extends ScrollPane {
	
	private List<Sprite> mySpriteList;
	private Scene myScene;
	private Canvas myCanvas;
	private Group myGroup;
	private Rectangle myCurrentRectangle;

	private static final int TOP_PANE_HEIGHT = 50;
	private static final int RIGHT_PANE_WIDTH = 300;

	private int width;
	private int height;

	CenterPane(Scene scene) {
		myScene = scene;
		myGroup = new Group();
//		myCanvas = new Canvas(
//				(width = (int) (scene.getWidth() - this.RIGHT_PANE_WIDTH)),
//				(height = (int) (scene.getHeight() - this.TOP_PANE_HEIGHT)));
//		myCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
		// myCanvas.getGraphicsContext2D().strokeLine(0, 0, 400, 400);

		this.setContent(myGroup);
		//myGroup.getChildren().add(new Rectangle(width, height, Color.WHITE));
		//myGroup.setOnMouseClicked(e -> canvasClicked(e));


		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.CONTROL)
				AuthoringWindow.setControlOn();
		});
		this.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.SHIFT)
				AuthoringWindow.setControlOff();
		});

	}

	private void canvasClicked(MouseEvent e) {

		try{
		Sprite s = ((SpriteCursor) myScene.getCursor()).getCurrentSprite();
		
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
		
		} catch (ClassCastException a) {
			System.out.println("error");
		} catch (NullPointerException b) {
			System.out.println("error");
		}
		
	}

	private void spriteClicked(MouseEvent p, Sprite s) {
		myGroup.getChildren().remove(s);
		System.out.println("Removing");
		// TODO: Show sprite data in information pane
		// TODO: Allow stacked sprites
	}
	
	public void createRegion(double x, double y){
		if (myCurrentRectangle != null){
			myGroup.getChildren().remove(myCurrentRectangle);
			System.out.println("removing rectangle");
		}
		myCurrentRectangle = new Rectangle(x, y, Color.WHITE);
		myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
		myGroup.getChildren().add(myCurrentRectangle);
	}

}
