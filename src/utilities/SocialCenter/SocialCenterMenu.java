//This entire file is part of my masterpiece
//Michael Lee

package utilities.SocialCenter;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.SocialCenter.shape_pages.IShapePage;

public class SocialCenterMenu {
	private Stage myStage;
	private StackPane myRoot = new StackPane();
	private Scene myMenu;
	private Scene myLogin;
	// hexpage variables
	private double[] params = {400, 300, 90, 20};
	private IShapePage myPage;
	private Group myPageGroup = new Group();
	// menu
	private static final int ROTATION = 3;
	private static final int ROTATION_CYCLE = 7;
	private static final int ROTATION_DURATION = 50;

	// CSS tags
	private static final String CSS = "styles/social_menu.css";
	private static final String CSS_BACKGROUND = "background";
	private static final String CSS_TILE = "hex";
	private static final String CSS_HOVER = "hexHover";

	// image url
	private static final String PROFILE = "http://upload.wikimedia.org/wikipedia/en/a/a5/StickFigurePortrait.jpg";
	private static final String BACK_BUTTON = "http://th07.deviantart.net/fs70/PRE/f/2014/002/7/1/kr_vector___duke_mark_by_malunis-d6yqn8j.png";
	private static final String CHAT = "https://lh3.googleusercontent.com/-HBPhOisn6-k/VT1TjYgdssI/AAAAAAAAAGQ/BqPCkScWecU/w346-h461/11082549_886228631419332_9053992119777199589_n.jpg";
	private static final String STATS = "http://www3.amherst.edu/~jcook15/binary.jpg";

	private double WIDTH;
	private double HEIGHT;
	private String ID;
	private ProfilePage pp;
	private StatsPage sp;
	private ChatPage cp;
	
	private static final String PAGE_TYPE = "utilities.SocialCenter.shape_pages.HexPage";
	private static final String TILE_TYPE = "utilities.SocialCenter.shape_pages.HexTile";
	

	public SocialCenterMenu(String id, double width, double height,
			Scene login, Stage stage) {
		myStage = stage;
		myLogin = login;
		ID = id;
		WIDTH = width;
		HEIGHT = height;
//		initializeHexPage();

		initializeMenu(PAGE_TYPE, TILE_TYPE);
		myRoot.getStyleClass().add(CSS_BACKGROUND);
		myMenu.getStylesheets().add(CSS);
	}

	private void initializeMenu(String pageType, String tileType){
		
		myMenu = new Scene(myRoot, WIDTH, HEIGHT);
	
			try {
				myPage = (IShapePage) Class.forName(pageType).newInstance();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			myPage.defineMatrix(params[0], params[1], params[2], params[3], tileType);
			myPage.addGroup(myPageGroup);
			myPage.addTag(CSS_TILE);
			
			//apply unique css id
			//apply a animation?
			for (int i = 0; i < myPage.getNumberOfTiles(); i++) {
				int temp = i;
				String id = CSS_TILE + temp;
				myPage.getPosition(i).getTile().getStyleClass().add(id);
				myPage.getPosition(i).getTile().setOnMouseEntered(e -> handleHoverEnter(temp));
				myPage.getPosition(i).getTile().setOnMouseExited(e -> handleHoverExit(temp));
			}
			
			// PROFILE
			myPage.getPosition(0).getTile().setOnMouseClicked(e -> goProfilePage());
			Image profilePic = new Image(PROFILE);
			myPage.getPosition(0).getTile()
					.setFill(new ImagePattern(profilePic));
	
			// STATSPAGE
			myPage.getPosition(2).getTile().setOnMouseClicked(e -> goScorePage());
			Image score = new Image(STATS);
			myPage.getPosition(2).getTile().setFill(new ImagePattern(score));
	
			// BACK TO LOGIN
			myPage.getPosition(3).getTile().setOnMouseClicked(e -> goLoginPage());
			Image back = new Image(BACK_BUTTON);
			myPage.getPosition(3).getTile().setFill(new ImagePattern(back));
	
			// CHATPAGE
			myPage.getPosition(6).getTile().setOnMouseClicked(e -> goChatPage());
			Image chat = new Image(CHAT);
			myPage.getPosition(6).getTile().setFill(new ImagePattern(chat));

			myRoot.getChildren().add(myPageGroup);
		
	};

	private void handleHoverExit(int i) {
		myPage.getPosition(i).getTile().getStyleClass()
				.removeAll(CSS_HOVER);
	}

	private void handleHoverEnter(int i) {
		myPage.getPosition(i).getTile().getStyleClass().add(CSS_HOVER);
		RotateTransition rt = new RotateTransition(
				Duration.millis(ROTATION_DURATION), myPage.getPosition(i)
						.getTile());
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
