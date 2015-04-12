package authoring.userInterface;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import authoring.Sprite;
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
	// private static final int EDIT_MENU = 1;
	// private static final int VIEW_MENU = 2;
	private static final int HELP_MENU = 3;

	private static final int NEW_FILE = 0;
	private static final int OPEN_FILE = 1;
	private static final int CLOSE_GAME = 2;
	private static final int SCENE_WIDTH = 1200;
	private static final int SCENE_HEIGHT = 600;

	private static Sprite currentlySelected;
	private static boolean control;

	public AuthoringWindow() {
		// TODO
		System.out.println("Instantiated AuthoringWindow");
	}

	public Scene GameCreateUI() {

		VBox root = new VBox();

		BorderPane rootContainer = new BorderPane();

		myScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);

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
		MenuBar mBar = new MenuBar();
		String[] menuItems = { "File:New/Load/Close", "Edit:Copy",
				"View:Sreen Options", "Help:Help" };

		// TODO: refactor
		Arrays.asList(menuItems).forEach(
				str -> {
					String a = str.split(":")[0];
					Menu m = new Menu(a);
					String s = str.split(":")[1];
					String[] t = s.split("/");
					Arrays.asList(t).forEach(
							str1 -> m.getItems().add(new MenuItem(str1)));
					mBar.getMenus().add(m);
				});

		mBar.getMenus().get(FILE_MENU).getItems().get(NEW_FILE)
				.setOnAction(e -> {
					new NewRegionDialog(myCenterPane);
				});

		mBar.getMenus()
				.get(0)
				.getItems()
				.get(1)
				.setOnAction(
						e -> {
							FileChooser fileChooser = new FileChooser();
							fileChooser.setTitle("Open Resource File");
							fileChooser.getExtensionFilters().addAll(
									new ExtensionFilter("Text Files", "*.txt"),
									new ExtensionFilter("Image Files", "*.png",
											"*.jpg", "*.gif"),
									new ExtensionFilter("Audio Files", "*.wav",
											"*.mp3", "*.aac"),
									new ExtensionFilter("All Files", "*.*"));
							fileChooser.showOpenDialog(null);
						});
		mBar.getMenus().get(FILE_MENU).getItems().get(NEW_FILE)
				.setOnAction(e -> new NewRegionDialog(myCenterPane));

		mBar.getMenus()
				.get(FILE_MENU)
				.getItems()
				.get(OPEN_FILE)
				.setOnAction(e -> {
					// refactor this into new class
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Open Resource File");
						fileChooser.getExtensionFilters().addAll(
								new ExtensionFilter("Text Files", "*.txt"),
								new ExtensionFilter("Image Files", "*.png",
										"*.jpg", "*.gif"),
								new ExtensionFilter("Audio Files", "*.wav",
										"*.mp3", "*.aac"),
								new ExtensionFilter("All Files", "*.*"));
						fileChooser.showOpenDialog(null);
					});

		mBar.getMenus()
				.get(HELP_MENU)
				.getItems()
				.get(0)
				.setOnAction(
						e -> {

							Media media = new Media(Paths
									.get("src/Resources/help.mp3").toUri()
									.toString());

							MediaPlayer player = new MediaPlayer(media);
							player.setVolume(100);
							player.play();
						});

		mBar.getMenus().get(FILE_MENU).getItems().get(CLOSE_GAME)
				.setOnAction(e -> Platform.exit());

		return mBar;
	}

	private Node setupBottomPane() {
		return (myBottomPane = new BottomPane(myScene, this)).myContainer;
	}

	private Node setupTopPane() {
		return (myTopPane = new TopPane(myScene, this)).myContainer;
	}

	private Node setupRightPane() {
		return (myRightPane = new RightPane(myScene, this)).myContainer;
	}

	private Node setupLeftPane() {
		return (myLeftPane = new LeftPane(myScene, this)).myContainer;
	}

	private Node setupCenterPane() {
		return (myCenterPane = new CenterPane(myScene, this)).myContainer;
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
