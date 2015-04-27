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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
	private StackPane root=new StackPane();
	private Driver db=new Driver();
	
	StatsPage(String id, double width, double height){
		ID=id;
		Width=width;
		Height=height;
		initialize(width,height);
		IDList();

		
	}
	
	private void initialize(double w, double h){
		scoreScreen=new Scene(root,w,h);
	
	}
	

	private void IDList(){
		//get the different types of games the user played
		ArrayList<String> gamesPlayed=new ArrayList<>();
		ArrayList<String> highScore =new ArrayList<>();
		try {
			gamesPlayed=db.get("HighScore","SELECT * FROM "+ID+"","GamesPlayed");
			highScore=db.get("HighScore", "SELECT * FROM "+ID+"", "HighScore");
			createGrid(gamesPlayed,highScore);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createGrid(ArrayList<String> g, ArrayList<String> h){
		GridPane grid=new GridPane();
		for(int i=0; i<g.size(); i++){
			gridCreate(grid, g.get(i),i,0);
			gridCreate(grid, h.get(i),i,1);
		}
		root.getChildren().add(grid);
	}
	
	private void gridCreate(GridPane g, String s, int row, int col) {
		HBox region = new HBox();
		Text temp = new Text(String.format("%s", s));
//		temp.getStyleClass().add(CSS_FONT);
		region.getChildren().add(temp);
		g.setConstraints(region, col, row);
		g.getChildren().add(region);
	}
	
	
	void getStatsScreen(Stage s){
		myStage=s;
		s.setScene(scoreScreen);
	}
	

}
