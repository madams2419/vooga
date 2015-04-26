/**
 * 
 */
package utilities.SocialCenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author hojeanniechung
 *
 */
public class StatsPage {
	private Stage myStage;
	private String ID;
	private Scene scoreScreen;
	private double Width;
	private double Height;
	private Group root;
	private Driver db=new Driver();
	
	StatsPage(String id, double width, double height){
		ID=id;
		Width=width;
		Height=height;
		initialize(width,height);
		gameList();
		
	}
	
	private void initialize(double w, double h){
		scoreScreen=new Scene(root,w,h);
	
	}
	

	private void gameList(){
		//get the different types of games the user played
		ArrayList<String> gamesPlayed=new ArrayList<>();
		try {
			gamesPlayed=db.get("HighScore","SELECT gamesplayed FROM '"+ID+"'","GamesPlayed");
			Set<String> gamesSet=new HashSet<String>(gamesPlayed);
			createComboBox(gamesSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createComboBox(Set g){
		ArrayList<String> games=new ArrayList<>(g);
		ObservableList<String> observable=FXCollections.observableArrayList(games);
		ComboBox<String> comboBox=new ComboBox<>(observable);
		comboBox.setPromptText("Pick a game you want to see");
		GridPane grid=new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5,5,5,5));
		grid.add(comboBox, 1, 0);
		root.getChildren().add(grid);
		
	}
	
	
	
	void getStatsScreen(Stage s){
		myStage=s;
		s.setScene(scoreScreen);
	}
	

}
