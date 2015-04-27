/**
 * ToDo: Add profile pic adder
 */
package utilities.SocialCenter;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author hojeanniechung
 *
 */
public class ProfilePage {
	private Scene profilePage;
	private StackPane root = new StackPane();
	private static Driver db = new Driver();
	private static String ID;
	private double myWidth;
	private double myHeight;
	private Stage myStage;
	private Scene myMenu;

	// CSS
	private static final String CSS = "styles/profile.css";
	private static final String FONT = "http://fonts.googleapis.com/css?family=Exo:100,200,400";
	private static final String CSS_BACKGROUND = "background";
	private static final String CSS_FONT = "prof_font";
	
	//PROF PIC
	private static final int PROF_WIDTH = 200;
	private static final int PROF_HEIGHT = 200;
	private static final double HEIGHT_CONSTANT = .10;
	
	//GRID PANE Positioning
	private static final int GRID_X = 350;
	private static final int GRID_Y = 300;
	private static final String SET_PIC = "Set Profile Pic URL";
	
	//POP UP URL DIMENSIONS
	Group URLgroup;
	private static final int POP_WIDTH = 500;
	private static final int POP_HEIGHT = 150;
	
	//BACK BUTTON
	private static final String BACK_BUTTON = "http://eingleses.com/wp-content/uploads/2014/03/duke-footer-logo.png";
	private static final double BACK_X = -225;
	private static final double BACK_Y = -225;
	private static final double BACK_WIDTH = 150;
	private static final double BACK_HEIGHT = 75;
	
	/**
	 * @param menu 
	 * @param args
	 */

	public ProfilePage(String id, double WIDTH, double HEIGHT, Scene menu) {
		myMenu = menu;
		ID = id;
		myWidth = WIDTH;
		myHeight = HEIGHT;
		initialize(WIDTH, HEIGHT);
		createGUI();
		profileImage();
		createStats();
		createBackButton();
	}

	private void createBackButton() {
		ImageView backButton = new ImageView(new Image(BACK_BUTTON));
		backButton.setTranslateX(BACK_X);
		backButton.setTranslateY(BACK_Y);
		backButton.setFitWidth(BACK_WIDTH);
		backButton.setFitHeight(BACK_HEIGHT);
		
		backButton.setOnMouseClicked(e->backMenu());
		root.getChildren().add(backButton);
		
	}

	private void backMenu() {
		myStage.setScene(myMenu);
	}

	private void initialize(double width, double height) {
		profilePage = new Scene(root, width, height);
		profilePage.getStylesheets().add(CSS);
		profilePage.getStylesheets().add(FONT);
		root.getStyleClass().add(CSS_BACKGROUND);
	}

	private void profileImage() {
		HBox hbox = new HBox();
		Image picture;
		ArrayList<String> imageURL = new ArrayList<>();
		Rectangle rect = new Rectangle(PROF_WIDTH, PROF_HEIGHT);
		rect.getStyleClass().add("profile_pic");
		try {
			imageURL=db.get("LoginInfo","SELECT ProfilePic FROM profile WHERE ID = '"+ID+"'", "ProfilePic");
			picture=new Image(imageURL.get(0));
			rect.setFill(new ImagePattern(picture));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		rect.setOnMouseClicked(e -> saveURL());
		hbox.getChildren().add(rect);
		hbox.setTranslateX((myWidth / 2) - rect.getWidth()/2  );
		hbox.setTranslateY(myHeight * HEIGHT_CONSTANT);
		root.getChildren().add(hbox);

	}

	private void saveURL() {
		Stage insert = new Stage();
		URLgroup = new Group();
		VBox stack = new VBox();

		Text t = new Text("Insert the URL of your profile picture");
		Button submit = new Button("Submit");
		Button close = new Button("Close");
		TextField URLinsert = new TextField();
		
		t.getStyleClass().add(CSS_FONT);
		submit.getStyleClass().add(CSS_FONT);
		close.getStyleClass().add(CSS_FONT);
		URLinsert.getStyleClass().add(CSS_FONT);
		
		submit.setOnMouseClicked(e -> insertQuery(URLinsert.getText()));
		close.setOnMouseClicked(e -> insert.close());

		// layout
		stack.getChildren().addAll(t, URLinsert, submit, close);
		URLgroup.getChildren().addAll(stack);
		stack.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		Scene URLscene = new Scene(URLgroup, POP_WIDTH, POP_HEIGHT);

		insert.setScene(URLscene);
		insert.initModality(Modality.WINDOW_MODAL);
		insert.setTitle(SET_PIC);

		insert.show();
	}

	private void insertQuery(String URL){
		try{
			db.updateURL("LoginInfo",ID, URL);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	private void createGUI() {
		GridPane gridpane = new GridPane();
		ArrayList<String> query = new ArrayList<>();
		String[] s = { "ID", "NickName" };
		String command = "SELECT ID,NickName FROM Profile WHERE ID = '" + ID
				+ "'";
		try {
			query=db.get("LoginInfo",command, s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < s.length; i++) {
			setConstraints(gridpane, s[i]+": "+query.get(i), i, 0);
		}
		gridpane.setTranslateX(GRID_X);
		gridpane.setTranslateY(GRID_Y);
		root.getChildren().add(gridpane);

	}

	private void setConstraints(GridPane g, String s, int row, int col) {
		HBox region = new HBox();
		Text temp = new Text(String.format("%s", s));
		temp.getStyleClass().add(CSS_FONT);
		region.getChildren().add(temp);
		g.setConstraints(region, col, row);
		g.getChildren().add(region);
	}

	private static void createStats() {
		String[] request = { "NickName" };
		ArrayList<String> results = new ArrayList<>();
		try {

			//System.out.println(ls.getID());
			results=db.get("LoginInfo","SELECT * FROM profile WHERE ID = '"+ID+"'",request);
			for(String s:results){
				System.out.println(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getProfileScreen(Stage s) {
		myStage = s;
		s.setScene(profilePage);
	}

}
