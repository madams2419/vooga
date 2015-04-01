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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import formerdefault.GameWindow;

/**
 * 
 */

/**
 * @author hojeanniechung
 *
 */
public class AuthoringWindow {

	private Scene myScene;

	public Scene GameCreateUI() {
		VBox root = new VBox();
		root.getChildren().add(menuBar());
		BorderPane canvas = new BorderPane();
		myScene = new Scene(root, 500, 500, Color.ALICEBLUE);

		// Setting up borderPane
		canvas.setBottom(setupBottomPane(myScene.getWidth()));
		canvas.setTop(setupTopPane(myScene.getWidth()));
		canvas.setLeft(setupLeftPane());
		canvas.setRight(setupRightPane());
		canvas.setCenter(setupCenterPane());

		root.getChildren().add(canvas);

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
		HBox bottomPane = new HBox();
		return bottomPane;
	}
	
	public static boolean done = false;

	private HBox setupTopPane(double width) {
		HBox topPane = new HBox();
		TopPane mTopPane = new TopPane();
		Map<String, EventHandler<Event>> mButtons = new HashMap<>();
		mButtons.put("Test", e -> System.out.println(((Button)(e.getSource())).getText()));
		mButtons.put("More", e -> {
			Thread t = new Thread(){
				@Override
				public void run() {
					GameWindow.main(null);
				}
			};
			if(!done){
				t.start();
				done = true;
			}
		});
		mTopPane.addButtons(mButtons);
		topPane.getChildren().add(mTopPane);
		return topPane;
	}

	private VBox setupRightPane() {
		VBox rightPane = new VBox();
		// rightPane.getChildren().addAll();
		return rightPane;
	}

	private VBox setupLeftPane() {
		VBox leftPane = new VBox();
		// leftPane.getChildren().addAll();
		return leftPane;
	}

	private Node setupCenterPane() {
		ScrollPane gameDisplay = new ScrollPane();
		return gameDisplay;
	}

}
