package authoring.userInterface;

//import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
//import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import authoring.XMLBuilder;
import authoring.rightPane.RightPane;

/**
 * @author hojeanniechung & Daniel Luker & Andrew Sun
 *
 */
public class AuthoringWindow {

	private Scene myScene;
	// private ButtonFactory mbuttonList;
	// private String mFileSelector = "src/Resources/FilestoParse.xml";

	private static final int FILE_MENU = 0;
	private static final int EDIT_MENU = 1;
	private static final int VIEW_MENU = 2;
	private static final int HELP_MENU = 3;

	private static final int NEW_FILE = 0;
	private static final int OPEN_FILE = 1;
	private static final int CLOSE_GAME = 2;

	private CenterPane myCenterPane;

	private static Object currentlySelected;
	private static boolean control;

	public AuthoringWindow() {
		// TODO
	}

	public Scene GameCreateUI() {

		VBox root = new VBox();

		BorderPane canvas = new BorderPane();

		myScene = new Scene(root, 1000, 1000, Color.WHITE);

		canvas.setPrefHeight(myScene.getHeight());
		canvas.setPrefWidth(myScene.getWidth());

		UIElementDistributer ud = new UIElementDistributer();
		ud.ElementDistributer();

		// Setting up borderPane
		// canvas.setBottom(setupBottomPane(myScene.getWidth()));
		canvas.setTop(setupTopPane(myScene.getWidth()));
		canvas.setLeft(setupLeftPane());
		canvas.setRight(setupRightPane());
		canvas.setCenter(setupCenterPane());
		canvas.setBottom(setupBottomPane(myScene.getWidth()));

		root.getChildren().add(menuBar());
		root.getChildren().add(canvas);
		// create a place to display the shapes and react to input

		return myScene;
	}

	private MenuBar menuBar() {
		MenuBar mBar = new MenuBar();
		String[] menuItems = { "File:New/Load/Close", "Edit:Copy",
				"View:Sreen Options", "Help:Help" };

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

		/*
		 * @author Andrew
		 */
		mBar.getMenus()
				.get(FILE_MENU)
				.getItems()
				.get(NEW_FILE)
				.setOnAction(e -> {
					// Refactor this into new class/method
						Dialog<ButtonType> dialog = new Dialog<>();
						dialog.setTitle("Create New Game Scene");

						GridPane grid = new GridPane();
						grid.setHgap(10);
						grid.setVgap(10);

						grid.add(new Label("xSize"), 0, 0);
						TextField textBox1 = new TextField("400");
						grid.add(textBox1, 0, 1);
						grid.add(new Label("ySize"), 1, 0);
						TextField textBox2 = new TextField("400");
						grid.add(textBox2, 1, 1);

						dialog.getDialogPane().setContent(grid);
						dialog.getDialogPane().getButtonTypes()
								.addAll(ButtonType.OK, ButtonType.CANCEL);

						Optional<ButtonType> result = dialog.showAndWait();
						// Refactor? Is it possible to get rid of this if
						// statement?
						if (result.get() == ButtonType.OK) {
							// TODO: check to make sure user entered numbers
//							myCenterPane.createRegion(
//									Double.parseDouble(textBox1.getText()),
//									Double.parseDouble(textBox1.getText()));

						}
					});

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
							Media media = new Media(
//									"http://uyeshare.com/music/down/12930540/2068304"
//									+ "/YWZiMjAwNCs3OGhkUzN5TU52VkRPM0JmL3FzaXF1ZUpz"
//									+ "angyaEdkekowS2lLcUNEL1E=/The+Beatles+-+01+Help%21.mp3"
									"http://a.tumblr.com/tumblr_mc06mtQ9V81qefr98o1.mp3"
									);
							System.out.println(media.toString());
							MediaPlayer player = new MediaPlayer(media);
							player.setVolume(100);
							player.play();
						});

		mBar.getMenus().get(FILE_MENU).getItems().get(CLOSE_GAME)
				.setOnAction(e -> Platform.exit());
		return mBar;
	}

	private HBox setupBottomPane(double width) {
		HBox buttonBox = new HBox();
		// UIElementDistributer ud = new UIElementDistributer();
		// ud.ElementDistributer();
		buttonBox.getChildren().addAll(BottomPane.mButtonList);
		System.out.println("Button Pane is: "
				+ BottomPane.mButtonList.toString());

		Button c = new Button("Output xml");
		c.setOnAction(e -> {
			XMLBuilder.getInstance("game").streamFile("lib/test.xml",
					XMLBuilder.getInstance("game").getRoot());
		});
		buttonBox.getChildren().add(c);
		return buttonBox;
	}

	private HBox setupTopPane(double width) {
		HBox buttonBox = new HBox();
		// UIElementDistributer ud = new UIElementDistributer();
		// ud.ElementDistributer();
		buttonBox.getChildren().addAll(TopPane.mButtonList);
		System.out.println(TopPane.mButtonList.toString());
		return buttonBox;
	}

	private VBox setupRightPane() {
		RightPane r = RightPane.getInstance();
		r.setScene(myScene);
		return r;
	}


	private VBox setupLeftPane() {
		VBox buttonBox = new VBox();
		buttonBox.getChildren().addAll(LeftPane.mButtonList);
		System.out.println("Left Pane is: " + LeftPane.mButtonList.toString());
		return buttonBox;
	}

	private Node setupCenterPane() {
		myCenterPane = new CenterPane(myScene);
		return myCenterPane;
	}

	public static void setCurrentlySelected(Object o) {
		currentlySelected = o;
	}

	public static Object getCurrentlySelected() {
		return currentlySelected;
	}

	public static void setControlOn() {
		control = true;
	}

	public static void setControlOff() {
		control = false;
	}

	public static boolean getControl() {
		return control;
	}

}
