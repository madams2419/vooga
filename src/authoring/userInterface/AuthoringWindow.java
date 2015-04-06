package authoring.userInterface;

//import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import authoring.rightPane.RightPane;

/**
 * @author hojeanniechung & Daniel Luker
 *
 */
public class AuthoringWindow {

	private Scene myScene;
//	private ButtonFactory mbuttonList;
//	private String mFileSelector = "src/Resources/FilestoParse.xml";

	public Scene GameCreateUI() {

		VBox root = new VBox();

		BorderPane canvas = new BorderPane();

		myScene = new Scene(root, 1000, 1000, Color.WHITE);

		canvas.setPrefHeight(myScene.getHeight());
		canvas.setPrefWidth(myScene.getWidth());
		// Setting up borderPane
		canvas.setBottom(setupBottomPane(myScene.getWidth()));
		canvas.setTop(setupTopPane(myScene.getWidth()));
		canvas.setLeft(setupLeftPane());
		canvas.setRight(setupRightPane());
		canvas.setCenter(setupCenterPane());

		root.getChildren().add(menuBar());
		root.getChildren().add(canvas);
		// create a place to display the shapes and react to input

		return myScene;
	}

	private MenuBar menuBar() {
		MenuBar mBar = new MenuBar();
		String[] menuItems = { "File:New/Load/Close", "Edit:Copy",
				"View:Sreen Options", "Help:Lol there is no help" };
		
		Arrays.asList(menuItems).forEach(
				str -> {
					String a = str.split(":")[0];
					System.out.println(a);
					Menu m = new Menu(a);
					String s = str.split(":")[1];
					String[] t = s.split("/");
					Arrays.asList(t).forEach(
							str1 -> m.getItems().add(new MenuItem(str1)));
					mBar.getMenus().add(m);
				});
		
		
		mBar.getMenus().get(0).getItems().get(1).setOnAction(e -> {
			 FileChooser fileChooser = new FileChooser();
			 fileChooser.setTitle("Open Resource File");
			 fileChooser.getExtensionFilters().addAll(
			         new ExtensionFilter("Text Files", "*.txt"),
			         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
			         new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
			         new ExtensionFilter("All Files", "*.*"));
			 fileChooser.showOpenDialog(null);
		});
		mBar.getMenus().get(0).getItems().get(2).setOnAction(e -> Platform.exit());
		
		
		return mBar;
	}

	private HBox setupBottomPane(double width) {
		HBox buttonBox = new HBox();
//		for (Button B : mbuttonList.getSharedInstace(mFileSelector, "Button")
//				.generateButtonBoxes()) {
//			buttonBox.getChildren().add(B);
//		}
//		System.out.println(mbuttonList
//				.getSharedInstace(mFileSelector, "Button").GetAttributes()
//				.toString());
		return buttonBox;
	}

	private HBox setupTopPane(double width) {
		Map<String, EventHandler<Event>> mButtons = new HashMap<>();

		mButtons.put("Global Settings", null);
		mButtons.put("Map Settings", null);
		mButtons.put("Interactions List", null);
		mButtons.put("Characters", null);
		mButtons.put("Blocks", null);
		mButtons.put("Decorations", null);
		mButtons.put("UI Controls", null);

		TopPane mTopPane = new TopPane();
		mTopPane.addButtons(mButtons);
		return mTopPane;
	}

	private VBox setupRightPane() {
		return new RightPane(myScene);
	}

	private VBox setupLeftPane() {
		return new LeftPane();
	}

	private Node setupCenterPane() {
		return new CenterPane(myScene);
	}

}
