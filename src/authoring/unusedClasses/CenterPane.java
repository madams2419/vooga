package authoring.unusedClasses;
// I commented this class out because there is another class with the same name and everything seems
// to work with this class commented out.

// This was previously in the userInterface package.

//package authoring.userInterface;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Side;
//import javafx.scene.Group;
//import javafx.scene.ImageCursor;
//import javafx.scene.Scene;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.image.Image;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.FileChooser;
//import authoring.Sprite;
//import authoring.panes.centerPane.Region;
//import authoring.panes.rightPane.GlobalCreationPane;
//import authoring.util.FrontEndUtils;
//
///**
// * 
// * @author Andrew Sun & Daniel "the fresh code machine of bel-duke" Luker
// *
// */
//public class CenterPane extends WindowPane {
//
//	private List<CenterCanvas> myMaps; 
//
//	private CenterPane(Scene s, AuthoringWindow w) {
//		super(s, new TabPane(), w);
//		myMaps = new ArrayList<>();
//		((TabPane) getContainer()).setSide(Side.BOTTOM);
//		addTab();
//		FrontEndUtils.setKeyActions(this.getContainer());
//		System.out.printf("Instantiated %s%n", this.getClass().getName());
//	}
//
//	public void addTab() {
//		CenterCanvas c;
//		((TabPane) getContainer()).getTabs().add(
//				new Tab("Map", c = new CenterCanvas(myScene)));
//		myMaps.add(c);
//	}
//
//	public CenterCanvas getActiveTab() {
//		return (CenterCanvas) ((TabPane) getContainer())
//				.getTabs()
//				.get(((TabPane) getContainer()).getSelectionModel()
//						.getSelectedIndex()).getContent();
//	}
//
//	@Override
//	public Group generateComponents(
//			ArrayList<Map<String, Map<String, String>>> values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	public Iterator<CenterCanvas> getMaps() {
//		return myMaps.iterator();
//	}
//
//	public class CenterCanvas extends ScrollPane {
//
//		private List<Map<String, String>> myEnvironmentList;
//		private ObservableList<Sprite> myListOfSprites;
//		private Region myCurrentRectangle;
//		private GlobalCreationPane gp;
//		private Group myGroup;
//
//		CenterCanvas(Scene scene) {
//			assert (scene != null);
//			myScene = scene;
//			myGroup = new Group();
//			gp = new GlobalCreationPane(myScene, myParent.getMyRightPane());
//			this.setContent(myGroup);
//
//			//myGroup.setOnMouseClicked(e -> canvasClicked(e));
//
//			myListOfSprites = FXCollections.observableArrayList();
//			myEnvironmentList = new ArrayList<>();
//
//			FrontEndUtils.setKeyActions(this);
//			addMaptoEnvironment(gp.getFields());
//
//		}
//
//		public void addMaptoEnvironment(Map<String, String> m) {
//			myEnvironmentList.add(m);
//		}
//
//		private void canvasClicked(MouseEvent e) {
//			try {
//				if (e.getButton() == MouseButton.SECONDARY){
//					FileChooser fileChooser = new FileChooser();
//			        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
//			        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
//			        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);
//					File imagePath = fileChooser.showOpenDialog(null);
//				    Image image = new Image("file:///" + imagePath.getPath());
//				    myCurrentRectangle.setBackgroundImage(image);
//				}
//
//				Sprite s = ((SpriteCursor) myScene.getCursor())
//						.getCurrentSprite();
//				//s.setOnMouseClicked((a -> keyPressed(a, s));
//				s.setXPosition(e.getX() - s.getImage().getWidth() / 2);
//				s.setYPosition(e.getY() - s.getImage().getHeight() / 2);
//				myGroup.getChildren().add(s);
//				this.myListOfSprites.add(s);
//				myScene.setCursor(ImageCursor.DEFAULT);
//				s.setOnMouseDragged(a -> imageDragged(a, s));
//
//			} catch (ClassCastException a) {
//			} catch (NullPointerException b) {
//			}
//		}
//
//		private void imageDragged(MouseEvent a, Sprite s) {
//			s.setXPosition(a.getSceneX() - (s.getImage().getWidth()/2));
//			s.setYPosition(a.getSceneY() - (s.getImage().getHeight()/2));
//		}
//
//		public Object[] getData() {
//			return new Object[] { myCurrentRectangle, myListOfSprites };
//		}
//
//		public Collection<Sprite> getSprites() {
//			return myListOfSprites;
//		}
//
//		public Collection<Map<String, String>> getEnvironment() {
//			return myEnvironmentList;
//		}
//
//		public void createRegion(double x, double y) {
//			if (myCurrentRectangle != null) {
//				myGroup.getChildren().remove(myCurrentRectangle);
//			}
//			myCurrentRectangle = new Region(x, y, Color.WHITE);
//			myCurrentRectangle.setOnMouseClicked(e -> canvasClicked(e));
//			//myCurrentRectangle.setOnKeyPressed(e -> keyPressed(e));
//			myGroup.getChildren().add(myCurrentRectangle);
//		}
//		
//		private void keyPressed(KeyEvent e, Sprite s) {
//			if (e.getCode() == KeyCode.DELETE){
//				System.out.println("deleting");
//				myGroup.getChildren().remove(s);
//			}
//			System.out.println("keypressed");
//		}
//
//		public Region getRegion(){
//			return myCurrentRectangle;
//		}
//	}
//}