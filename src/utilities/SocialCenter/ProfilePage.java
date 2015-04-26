/**
 * ToDo: Add profile pic adder
 */
package utilities.SocialCenter;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
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
	private static double W;
	private static double H;
	private Stage myStage;

	/**
	 * @param args
	 */

	public ProfilePage(String id, double WIDTH, double HEIGHT){
		ID=id;
		W=WIDTH;
		H=HEIGHT;
		initialize(WIDTH,HEIGHT);
		createGUI();
		profileImage();
		createStats();
	}


	private void initialize(double width, double height){
		profilePage=new Scene(root, width, height);
		StackPane background=new StackPane();		
		profilePage.getStylesheets().add("styles/login.css");
		profilePage.getStylesheets().add("http://fonts.googleapis.com/css?family=Exo:100,200,400");
		background.setId("pane");
		root.getChildren().add(background);
	}

	private void profileImage(){
		HBox hbox=new HBox();
		Image picture;
		ArrayList<String> imageURL=new ArrayList<>();
		Rectangle rect=new Rectangle(200,200);
		try {
			imageURL=db.get("SELECT ProfilePic FROM profile WHERE ID = '"+ID+"'", "ProfilePic");
			picture=new Image(imageURL.get(0));
			rect.setFill(new ImagePattern(picture));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		rect.setOnMouseClicked(e->saveURL());
		hbox.getChildren().add(rect);
		hbox.setTranslateX(W/2-85);
		hbox.setTranslateY(H/6);
		root.getChildren().add(hbox);

	}
	private void saveURL(){
		Stage insert= new Stage();
		Group URLgroup=new Group();
		StackPane stack=new StackPane();
		
		Text t=new Text("Insert the URL of your profile picture");
		Button submit=new Button("Submit");
		Button close=new Button("Close");
		TextField URLinsert=new TextField();
		submit.setOnMouseClicked(e->insertQuery(URLinsert.getText()));
		close.setOnMouseClicked(e->insert.close());
		
		//layout
		stack.getChildren().addAll(t,URLinsert,submit,close);
		URLgroup.getChildren().addAll(stack);
		stack.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		
		
		Scene URLscene=new Scene(URLgroup,500,50);
		
	    insert.setScene(URLscene);
	    insert.initModality(Modality.WINDOW_MODAL);
	    insert.setTitle("Pop up window");
	  
		insert.show();
	}


	private void insertQuery(String URL){
		try{
			db.updateURL(ID, URL);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	private void createGUI(){
		GridPane gridpane = new GridPane();
		ArrayList<String> query=new ArrayList<>();
		String[] s={"ID","NickName"};
		String command="SELECT ID,NickName FROM Profile WHERE ID = '"+ID+"'";
		try {
			query=db.get(command, s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<s.length; i++){
			setConstraints(gridpane,s[i],i,1);
			setConstraints(gridpane,query.get(i),i,2);
		}
		gridpane.setTranslateX(350);
		gridpane.setTranslateY(300);
		root.getChildren().add(gridpane);

	}

	private void setConstraints(GridPane g, String s, int row, int col){
		HBox region=new HBox();
		Text temp=new Text(String.format("%s",s));
		temp.getStyleClass().add("prof_font");
		region.getStyleClass().add("prof_grid");
		region.getChildren().add(temp);
		g.setConstraints(region, col, row);
		g.getChildren().add(region);
	}


	private static void createStats(){		
		String[] request={"NickName"};
		ArrayList<String> results=new ArrayList<>();
		try {
			//System.out.println(ls.getID());
			results=db.get("SELECT * FROM profile WHERE ID = '"+ID+"'",request);
			for(String s:results){
				System.out.println(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}	
	public void getProfileScreen(Stage s){
		myStage=s;
		s.setScene(profilePage);
	}

}
