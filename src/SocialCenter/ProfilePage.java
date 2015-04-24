/**
 * 
 */
package SocialCenter;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author hojeanniechung
 *
 */
public class ProfilePage {
	private Scene profilePage;
	private StackPane root = new StackPane();
	private static General_Driver db = new General_Driver();

	/**
	 * @param args
	 */

	public ProfilePage(){
		initialize(800,600);
		createStats();
	}

	private void initialize(int width, int height){
		profilePage=new Scene(root, width, height);
		StackPane background=new StackPane();
	}

//	private void getInfo(){
//
//	}
//	
//	private void createGUI(){
//		VBox profileBox=new VBox();
//		HBox photoBox=new HBox();
//		Text ID=new Text(ls.getID());
//		Text NickName=new Text("Jeannie");
//		Text GamesPlayed=new Text("Mario");
//		Text HighScore=new Text("100");
//		profileBox.getChildren().addAll(photoBox,ID,NickName,GamesPlayed,HighScore);
//	}

	
	private static void createStats(){	
	ArrayList<String> result = new ArrayList<>();	
		try {
			result=db.get("SELECT Nickname FROM profile WHERE ID = '"+ls.getID()+"'","Nickname");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.contains("none")){
			Scanner s=new Scanner(System.in);
			String ID=ls.getID();
			System.out.println("What is your nickname?");
			String Nickname=s.next();
			System.out.println("What Game did you play?");
			String GamesPlayed=s.next();
			System.out.println("What was your High Score?");
			String HighScore=s.next();
			try {
				db.post("INSERT INTO Profile (ID,NICKNAME,GAMESPLAYED,HIGHSCORE) VALUES ('"+ID+"','"+Nickname+"','"+GamesPlayed+"','"+HighScore+"')");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Scene getProfileScreen(){
		return profilePage;
	}

}
