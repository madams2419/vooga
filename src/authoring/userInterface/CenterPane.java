package authoring.userInterface;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import authoring.rightPane.GlobalCreationPane;
import authoring.rightPane.RightPane;

/**
 * 
 * @author Andrew Sun
 *
 */
public class CenterPane extends ScrollPane {

	private List<Sprite> mySpriteList;
	private List<Map> myEnvironmentList;
	private Scene myScene;
	private Canvas myCanvas;
	private Group myGroup;
	private Rectangle myCurrentRectangle;
	public static CenterPane mInstance;
	public GlobalCreationPane gp;

	private static final int TOP_PANE_HEIGHT = 50;
	private static final int RIGHT_PANE_WIDTH = 300;

	private int width;
	private int height;

	public CenterPane(Scene scene) {
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
		mySpriteList = new ArrayList<Sprite>();
		myEnvironmentList=new ArrayList<Map>();
		
		addMaptoEnvironment(gp.getInstance().fields);
		
		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.CONTROL)
				AuthoringWindow.setControlOn();
		});
		this.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.SHIFT)
				AuthoringWindow.setControlOff();
		});

	}
	
	public CenterPane getInstance(){
		if (mInstance == null)
			mInstance = new CenterPane(myScene);
		return mInstance;

	}

	public void addMaptoEnvironment(Map m){
		myEnvironmentList.add(m);
		System.out.println(m.toString());
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
			System.out.println("error");
		} catch (NullPointerException b) {
			System.out.println("error");
		}

	}

	//	private void spriteClicked(MouseEvent p, Sprite s) {
	//		myGroup.getChildren().remove(s);
	//		System.out.println("Removing");
	//		// TODO: Show sprite data in information pane
	//		// TODO: Allow stacked sprites
	//	}
	//	
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
