package authoring.userInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
public class CenterPane extends WindowPane {

	private List<CenterCanvas> myMaps; 

	CenterPane(Scene s, AuthoringWindow w) {
		super(s, new TabPane(), w);
		myMaps = new ArrayList<>();
		((TabPane) myContainer).setSide(Side.BOTTOM);
		addTab();
		FrontEndUtils.setKeyActions(this.myContainer);
		System.out.printf("Instantiated %s%n", this.getClass().getName());
	}

	public void addTab() {
		CenterCanvas c;
		((TabPane) myContainer).getTabs().add(
				new Tab("Map", c = new CenterCanvas(myScene)));
		myMaps.add(c);
	}

	public CenterCanvas getActiveTab() {
		System.out.printf("Changing tabs to tab %d%n", ((TabPane) myContainer)
				.getSelectionModel().getSelectedIndex());
		return (CenterCanvas) ((TabPane) myContainer)
				.getTabs()
				.get(((TabPane) myContainer).getSelectionModel()
						.getSelectedIndex()).getContent();
	}

	@Override
	public Group generateComponents(
			ArrayList<Map<String, Map<String, String>>> values) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Iterator<CenterCanvas> getMaps() {
		return myMaps.iterator();
	}

	public class CenterCanvas extends ScrollPane {

		private List<Map<String, String>> myEnvironmentList;
		private ObservableList<Sprite> myListOfSprites;
		private Rectangle myCurrentRectangle;
		private GlobalCreationPane gp;
		private Group myGroup;

		CenterCanvas(Scene scene) {
			assert (scene != null);
			myScene = scene;
			myGroup = new Group();
			gp = new GlobalCreationPane(myScene, myParent.getMyRightPane());
			this.setContent(myGroup);

			//myGroup.setOnMouseClicked(e -> canvasClicked(e));

			myListOfSprites = FXCollections.observableArrayList();
			myEnvironmentList = new ArrayList<>();

			FrontEndUtils.setKeyActions(this);
			addMaptoEnvironment(gp.fields);

		}

		public void addMaptoEnvironment(Map<String, String> m) {
			myEnvironmentList.add(m);
		}

		private void canvasClicked(MouseEvent e) {
			try {
				Sprite s = ((SpriteCursor) myScene.getCursor())
						.getCurrentSprite();

				s.setXPosition(e.getX() - s.getImage().getWidth() / 2);
				s.setYPosition(e.getY() - s.getImage().getHeight() / 2);
				myGroup.getChildren().add(s);
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
			}
			myCurrentRectangle = new Rectangle(x, y, Color.WHITE);
			myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
			myGroup.getChildren().add(myCurrentRectangle);
		}
	}
}
