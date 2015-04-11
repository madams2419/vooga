package authoring.userInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoring.Sprite;
import authoring.rightPane.GlobalCreationPane;
import authoring.util.FrontEndUtils;

/**
 * 
 * @author Andrew Sun & Daniel "the fresh code machine of bel-duke" Luker
 *
 */
public class CenterPane extends TabPane {


	private Scene myScene;
	private Group myGroup;
	
	public static CenterPane mInstance;
	
	public static CenterPane getInstance(Scene scene) {
		if (mInstance == null)
			mInstance = new CenterPane(scene);
		return mInstance;
	}
	
	private CenterPane(Scene s) {
		myScene = s;
		this.setSide(Side.BOTTOM);
		addTab();
	}
	
	public void addTab() {
		this.getTabs().add(new Tab("", new CenterCanvas(myScene)));
	}
	
	public CenterCanvas getActiveTab() {
		return (CenterCanvas) this.getTabs().get(this.getSelectionModel().getSelectedIndex()).getContent();
	}
	
	
	class CenterCanvas extends ScrollPane {

		private List<Map<String, String>> myEnvironmentList;
		private ObservableList<Sprite> myListOfSprites;
		private Rectangle myCurrentRectangle;
		public GlobalCreationPane gp;
		
		CenterCanvas(Scene scene) {
			assert(scene!=null);
			myScene = scene;
			myGroup = new Group();
			this.setContent(myGroup);

			myGroup.setOnMouseClicked(e -> canvasClicked(e));

			myListOfSprites = FXCollections.observableArrayList();
			myEnvironmentList = new ArrayList<>();

			FrontEndUtils.setKeyActions(this);
			addMaptoEnvironment(gp.getInstance(scene).fields);

		}


		public void addMaptoEnvironment(Map<String, String> m) {
			myEnvironmentList.add(m);
			System.out.println(m.toString());
		}

		private void canvasClicked(MouseEvent e) {
			try {
				Sprite s = ((SpriteCursor) myScene.getCursor())
						.getCurrentSprite();

				s.setXPosition(e.getX() - s.getImage().getWidth() / 2);
				s.setYPosition(e.getY() - s.getImage().getHeight() / 2);
				myGroup.getChildren().add(s);

				System.out.println(s.getID());
				this.myListOfSprites.add(s);
				myScene.setCursor(ImageCursor.DEFAULT);

			} catch (ClassCastException a) {
			} catch (NullPointerException b) {
			}

		}

		public Object[] getData() {
			return new Object[] { myCurrentRectangle, myListOfSprites };
		}

		public Collection<Sprite> getSprites() {
			return myListOfSprites;
		}

		public Collection<Map<String, String>> getEnvironment() {
			return myEnvironmentList;
		}

		public void createRegion(double x, double y) {
			if (myCurrentRectangle != null) {
				myGroup.getChildren().remove(myCurrentRectangle);
				System.out.println("removing rectangle");
			}
			myCurrentRectangle = new Rectangle(x, y, Color.WHITE);
			myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
			myGroup.getChildren().add(myCurrentRectangle);
		}
	}
}
