package authoring.userInterface;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import authoring.Sprite;
import authoring.centerPane.CenterPane;
import authoring.rightPane.RightPane;

/**
 * @author hojeanniechung & Daniel Luker & Andrew Sun
 *
 */
public class AuthoringWindow {

	private Scene myScene;

	private TopPane myTopPane;
	private CenterPane myCenterPane;
	private BottomPane myBottomPane;
	private RightPane myRightPane;
	private LeftPane myLeftPane;
	private Map<String, WindowPane> myPanes = new HashMap<String, WindowPane>();

	private static final int FILE_MENU = 0;
	private static final int EDIT_MENU = 1;
	private static final int VIEW_MENU = 2;
	private static final int HELP_MENU = 3;

	private static Sprite currentlySelected;
	private static boolean control;

	public AuthoringWindow() {
		// TODO
		System.out.println("Instantiated AuthoringWindow");
	}

	public Scene GameCreateUI(Scene parentScene) {

		VBox root = new VBox();

		BorderPane rootContainer = new BorderPane();

		parentScene.setRoot(root);
		
		myScene = parentScene;

		rootContainer.setPrefHeight(myScene.getHeight());
		rootContainer.setPrefWidth(myScene.getWidth());

		// Setting up borderPane
		// canvas.setBottom(setupBottomPane(myScene.getWidth()));

		rootContainer.setTop(setupTopPane());
		rootContainer.setLeft(setupLeftPane());
		rootContainer.setRight(setupRightPane());
		rootContainer.setCenter(setupCenterPane());
		rootContainer.setBottom(setupBottomPane());

		root.getChildren().add(menuBar());
		root.getChildren().add(rootContainer);

		myPanes.put(myBottomPane.getClass().getName(), myBottomPane);
		myPanes.put(myTopPane.getClass().getName(), myTopPane);
		myPanes.put(myCenterPane.getClass().getName(), myCenterPane);
		myPanes.put(myLeftPane.getClass().getName(), myLeftPane);
		myPanes.put(myRightPane.getClass().getName(), myRightPane);

		UIElementDistributer.ElementDistributer(myScene, this);
		
		return myScene;
	}

	private MenuBar menuBar() {		
		GameMenu menu = new GameMenu(new String[]{"File", "Edit", "View", "Help"});
		menu.addItemToMenu(FILE_MENU, "New", e -> new NewRegionDialog(myCenterPane));
		menu.addItemToMenu(FILE_MENU, "Load", e -> new FileChooserDialog());
		menu.addItemToMenu(FILE_MENU, "Close", e -> Platform.exit());
		
		menu.addItemToMenu(EDIT_MENU, "Copy", e -> {});
		menu.addItemToMenu(VIEW_MENU, "Screen Options", e -> {});
		menu.addItemToMenu(HELP_MENU, "Help", e -> new HelpMediaPlayer("src/Resources/help.mp3"));
		
		return menu;
	}

	private Node setupBottomPane() {
		return (myBottomPane = new BottomPane(myScene, this)).getContainer();
	}

	private Node setupTopPane() {
		return (myTopPane = new TopPane(myScene, this)).getContainer();
	}

	private Node setupRightPane() {
		return (myRightPane = new RightPane(myScene, this)).getContainer();
	}

	private Node setupLeftPane() {
		return (myLeftPane = new LeftPane(myScene, this)).getContainer();
	}

	private Node setupCenterPane() {
		return (myCenterPane = new CenterPane(myScene, this)).getContainer();
	}

	public static void setCurrentlySelected(Sprite s) {
		currentlySelected = s;
	}

	public static Object getCurrentlySelected() {
		return currentlySelected;
	}

	public WindowPane getPane(String name) {
		return myPanes.get(name);
	}

	public static void setControlOn() {
		System.out.println("Control on");
		control = true;
	}

	public static void setControlOff() {
		System.out.println("Control off");
		control = false;
	}

	public static boolean getControl() {
		return control;
	}

	public TopPane getMyTopPane() {
		return myTopPane;
	}

	public RightPane getMyRightPane() {
		return myRightPane;
	}

	public LeftPane getMyLeftPane() {
		return myLeftPane;
	}

	public CenterPane getMyCenterPane() {
		return myCenterPane;
	}

	public BottomPane getMyBottomPane() {
		return myBottomPane;
	}
}