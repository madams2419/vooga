/**
 * 
 */
package utilities.SocialCenter;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author hojeanniechung
 *
 */
public class StatsPage {
	private String ID;
	private Scene scoreScreen;
	private double Width;
	private StackPane root=new StackPane();
	private Driver db=new Driver();
	
	StatsPage(String id, double width, double height){
		ID=id;
		Width=width;
		initialize(width,height);
		IDList();

		
	}
	
	private void initialize(double w, double h){
		Text title = new Text("STATS");
		title.getStyleClass().add("title");
		title.setTranslateX(-200);
		title.setTranslateY(0);
		root.getChildren().add(title);
		scoreScreen=new Scene(root,w,h);
		root.getStyleClass().add("background");
		scoreScreen.getStylesheets().add("styles/stats.css");
		scoreScreen.getStylesheets().add("http://fonts.googleapis.com/css?family=Exo:100,200,400");
	
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
		ScrollPane grid=new ScrollPane();
		grid.getStyleClass().add("scrollPane");
//		grid.setHbarPolicy(ScrollBarPolicy.NEVER);
//		grid.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		grid.setMaxWidth(200);
		grid.setMaxHeight(400);
		VBox textList = new VBox();
		grid.setTranslateX(Width*.15);
//		grid.setTranslateY(Height*.1);
		for(int i=0; i<g.size(); i++){
			gridCreate(textList, g.get(i) + ": "+ h.get(i),i,0);
//			gridCreate(grid, ,i,1);
		}
		System.out.println("hello");
		grid.setContent(textList);
		root.getChildren().add(grid);
	}
	
	private void gridCreate(VBox g, String s, int row, int col) {
		Text temp = new Text(String.format("%s", s));
		temp.getStyleClass().add("font");
//		temp.getStyleClass().add(CSS_FONT);
//		region.getChildren().add(temp);
//		g.setConstraints(region, col, row);
		g.getChildren().add(temp);
//		g.getChildren().add(region);
	}
	
	
	void getStatsScreen(Stage s){
		s.setScene(scoreScreen);
	}
	

}
