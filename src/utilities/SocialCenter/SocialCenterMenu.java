package utilities.SocialCenter;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SocialCenterMenu {
	private Stage myStage;
	private StackPane myRoot = new StackPane();
	private Scene myMenu;
	private Scene myLogin;
	// hexpage variables
	private HexPage myHexPage;
	private Group myHexGroup = new Group();
	private static final int HEX_SIZE = 90;
	private static final int MENU_OFFSET = 20;
	// menu
	private static final int ROTATION = 3;
	private static final int ROTATION_CYCLE = 7;
	private static final int ROTATION_DURATION = 50;

	// CSS tags
	private static final String CSS = "styles/social_menu.css";
	private static final String CSS_BACKGROUND = "background";
	private static final String CSS_HEX = "hex";
	private static final String CSS_HEXHOVER = "hexHover";

	// image url
	private static final String PROFILE = "http://media.philly.com/images/20080510_kutcher_300.jpg";
	private static final String BACK_BUTTON = "http://th07.deviantart.net/fs70/PRE/f/2014/002/7/1/kr_vector___duke_mark_by_malunis-d6yqn8j.png";

	private double WIDTH;
	private double HEIGHT;
	private String ID;
	private ProfilePage pp;

	public SocialCenterMenu(String id, double width, double height,
			Scene login, Stage stage) {
		myStage = stage;
		myLogin = login;
		ID = id;
		WIDTH = width;
		HEIGHT = height;
		initializeHexPage();
		myRoot.getStyleClass().add(CSS_BACKGROUND);
		myMenu.getStylesheets().add(CSS);
	}

	private void initializeHexPage() {
		myMenu = new Scene(myRoot, WIDTH, HEIGHT);
		myHexPage = new HexPage(WIDTH / 2, HEIGHT / 2, HEX_SIZE, MENU_OFFSET);
		myHexPage.addGroup(myHexGroup);
		myHexPage.addTag(CSS_HEX);

		for (int i = 0; i < myHexPage.getNumberOfHexagons(); i++) {
			int temp = i;
			String id = CSS_HEX + temp;
			myHexPage.getPosition(i).getHexagon().getStyleClass().add(id);

			myHexPage.getPosition(i).setOnMouseEnter(
					e -> handleHoverEnter(temp));
			myHexPage.getPosition(i).setOnMouseExit(e -> handleHoverExit(temp));
		}

		// PROFILE
		myHexPage.getPosition(0).setOnMouseClicked(e -> goProfilePage());
		Image profilePic = new Image(PROFILE);
		myHexPage.getPosition(0).getHexagon()
				.setFill(new ImagePattern(profilePic));

		// BACK TO LOGIN
		myHexPage.getPosition(3).setOnMouseClicked(e -> goLoginPage());
		Image back = new Image(BACK_BUTTON);
		myHexPage.getPosition(3).getHexagon().setFill(new ImagePattern(back));

		myRoot.getChildren().add(myHexGroup);
	}

	private void handleHoverExit(int i) {
		myHexPage.getPosition(i).getHexagon().getStyleClass()
				.removeAll(CSS_HEXHOVER);
	}

	private void handleHoverEnter(int i) {
		myHexPage.getPosition(i).getHexagon().getStyleClass().add(CSS_HEXHOVER);
		RotateTransition rt = new RotateTransition(
				Duration.millis(ROTATION_DURATION), myHexPage.getPosition(i)
						.getHexagon());
		rt.setFromAngle(-ROTATION);
		rt.setByAngle(ROTATION);
		rt.setCycleCount(ROTATION_CYCLE);
		rt.setAutoReverse(true);
		rt.play();

	}

	// CENTER BUTTON
	private void goProfilePage() {
		pp = new ProfilePage(ID, WIDTH, HEIGHT, myMenu);
		pp.getProfileScreen(myStage);
	}

	// 3rd Position
	private void goLoginPage() {
		System.out.println("login");
		myStage.setScene(myLogin);
	}

	public void returnScene(Stage s) {
		myStage = s;
		s.setScene(myMenu);
	}

}
