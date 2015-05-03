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
	private Constants constants=new Constants();

	private double WIDTH;
	private double HEIGHT;
	private String ID;
	private ProfilePage pp;
	private StatsPage sp;
	private ChatPage cp;

	public SocialCenterMenu(String id, double width, double height,
			Scene login, Stage stage) {
		myStage = stage;
		myLogin = login;
		ID = id;
		WIDTH = width;
		HEIGHT = height;
		initializeHexPage();
		myRoot.getStyleClass().add(constants.CSS_BACKGROUND);
		myMenu.getStylesheets().add(constants.CSS);
	}

	private void initializeHexPage() {
		myMenu = new Scene(myRoot, WIDTH, HEIGHT);
		myHexPage = new HexPage(WIDTH / 2, HEIGHT / 2, constants.HEX_SIZE, constants.MENU_OFFSET);
		myHexPage.addGroup(myHexGroup);
		myHexPage.addTag(constants.CSS_HEX);

		for (int i = 0; i < myHexPage.getNumberOfHexagons(); i++) {
			int temp = i;
			String id = constants.CSS_HEX + temp;
			myHexPage.getPosition(i).getHexagon().getStyleClass().add(id);

			myHexPage.getPosition(i).setOnMouseEnter(
					e -> handleHoverEnter(temp));
			myHexPage.getPosition(i).setOnMouseExit(e -> handleHoverExit(temp));
		}

		// PROFILE
		myHexPage.getPosition(0).setOnMouseClicked(e -> goProfilePage());
		Image profilePic = new Image(constants.PROFILE);
		myHexPage.getPosition(0).getHexagon()
				.setFill(new ImagePattern(profilePic));

		// STATSPAGE
		myHexPage.getPosition(2).setOnMouseClicked(e -> goScorePage());
		Image score = new Image(constants.STATS);
		myHexPage.getPosition(2).getHexagon().setFill(new ImagePattern(score));

		// BACK TO LOGIN
		myHexPage.getPosition(3).setOnMouseClicked(e -> goLoginPage());
		Image back = new Image(constants.BACK_BUTTON);
		myHexPage.getPosition(3).getHexagon().setFill(new ImagePattern(back));

		// CHATPAGE
		myHexPage.getPosition(6).setOnMouseClicked(e -> goChatPage());
		Image chat = new Image(constants.CHAT);
		myHexPage.getPosition(6).getHexagon().setFill(new ImagePattern(chat));

		myRoot.getChildren().add(myHexGroup);
	}

	private void handleHoverExit(int i) {
		myHexPage.getPosition(i).getHexagon().getStyleClass()
				.removeAll(constants.CSS_HEXHOVER);
	}

	private void handleHoverEnter(int i) {
		myHexPage.getPosition(i).getHexagon().getStyleClass().add(constants.CSS_HEXHOVER);
		RotateTransition rt = new RotateTransition(
				Duration.millis(constants.ROTATION_DURATION), myHexPage.getPosition(i)
						.getHexagon());
		rt.setFromAngle(-constants.ROTATION);
		rt.setByAngle(constants.ROTATION);
		rt.setCycleCount(constants.ROTATION_CYCLE);
		rt.setAutoReverse(true);
		rt.play();

	}

	// CENTER BUTTON
	private void goProfilePage() {
		pp = new ProfilePage(ID, WIDTH, HEIGHT, myMenu);
		pp.getProfileScreen(myStage);
	}

	private void goScorePage() {
		sp = new StatsPage(ID, WIDTH, HEIGHT);
		sp.getStatsScreen(myStage);

	}

	private void goChatPage() {
		cp = new ChatPage(ID, WIDTH, HEIGHT, myMenu);
		cp.getChatScreen(myStage);
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
