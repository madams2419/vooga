package authoring.userInterface;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
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
	private Group myGroup;
	private Rectangle myCurrentRectangle;

	CenterPane(Scene scene) {
		myScene = scene;
		myGroup = new Group();
		this.setContent(myGroup);
		mySpriteList = new ArrayList<Sprite>();
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
		
		s.setX(e.getX() - s.getImage().getWidth()/2);
		s.setY(e.getY() - s.getImage().getHeight()/2);
		myGroup.getChildren().add(s);
		
		System.out.println(s.getID());
		mySpriteList.add(s);
		myScene.setCursor(ImageCursor.DEFAULT);
		
		} catch (ClassCastException a) {
		} catch (NullPointerException b) {
		}
		
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
