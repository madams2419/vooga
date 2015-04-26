/**
 * 
 */
package utilities.SocialCenter;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
	
	/**
	 * @param args
	 */

	public ProfilePage(String id, double WIDTH, double HEIGHT){
		ID=id;
		initialize(WIDTH,HEIGHT);
		createGUI();
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
		s.setScene(profilePage);
	}

}
