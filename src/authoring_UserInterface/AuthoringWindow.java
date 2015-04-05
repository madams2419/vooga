package authoring_UserInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author hojeanniechung & Daniel Luker
 *
 */
public class AuthoringWindow {

	private Scene myScene;
	private ButtonFactory mbuttonList;
	private String mFileSelector="src/Resources/FilestoParse.xml";

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

		root.getChildren().add(canvas);
		//root.getChildren().add(menuBar());
		// create a place to display the shapes and react to input

		return myScene;
	}

	private MenuBar menuBar() {
		MenuBar mBar = new MenuBar();
		String[] menuItems = { "File", "Edit", "View", "Help" };
		Arrays.asList(menuItems).forEach(
				str -> mBar.getMenus().add(new Menu(str)));
		return mBar;
	}

	private HBox setupBottomPane(double width) {
		HBox buttonBox = new HBox();
		for(Button B:mbuttonList.getSharedInstace(mFileSelector, "Button").generateButtonBoxes()){
			buttonBox.getChildren().add(B);
		}
		System.out.println(mbuttonList.getSharedInstace(mFileSelector, "Button").GetAttributes().toString());
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
