/**
 * 
 */
package SocialCenter;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @author hojeanniechung
 *
 */
public class ProfilePage {
	private Scene profilePage;
	private StackPane root = new StackPane();
	private static Driver db = new Driver();
	private static String ID;
	
	/**
	 * @param args
	 */

	public ProfilePage(String id, int width, int height){
		ID=id;
		initialize(width,height);
		createGUI();
		createStats();
	}

	private void initialize(int width, int height){
		profilePage=new Scene(root, width, height);
		StackPane background=new StackPane();		
		profilePage.getStylesheets().add("styles/login.css");
		profilePage.getStylesheets().add("http://fonts.googleapis.com/css?family=Exo:100,200,400");
		background.setId("pane");
		root.getChildren().add(background);
	}


	private void createGUI(){
		GridPane gridpane = new GridPane();
		String[] s={"ID","NickName","Game","HighScore"};
		for(int i=0; i<s.length; i++){
			setConstraints(gridpane,s[i],i);
		}
		gridpane.setTranslateX(350);
		gridpane.setTranslateY(300);
		root.getChildren().add(gridpane);
		
	}
	
	private void setConstraints(GridPane g, String s, int row){
		HBox region=new HBox();
		Text temp=new Text(String.format("%s",s));
		temp.getStyleClass().add("prof_font");
		region.getStyleClass().add("prof_grid");
		region.getChildren().add(temp);
		g.setConstraints(region, 1, row);
		g.getChildren().add(region);
	}

	
	private static void createStats(){		
		String[] request={"NickName","GamesPlayed","HighScore"};
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
	
	public Scene getProfileScreen(){
		return profilePage;
	}

}
