package utilities.SocialCenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Rankings {
	private Stage myStage;
	private String ID;
	private Scene scoreScreen;
	private double Width;
	private double Height;
	private StackPane root=new StackPane();
	private Driver db=new Driver();
	
	Rankings(String id, double width, double height){
		ID=id;
		Width=width;
		Height=height;
		initialize(width,height);
		IDList();

		
	}
	
	private void initialize(double w, double h){
		scoreScreen=new Scene(root,w,h);
	
	}
	
	private void gameList(){
		//get the different types of games the user played
		ArrayList<String> gamesPlayed=new ArrayList<>();
		try {
			gamesPlayed=db.get("HighScore",String.format("SELECT GamesPlayed FROM %s",ID),"GamesPlayed");
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
		comboBox.setPromptText("Pick a chatroom by game");
		comboBox.setOnAction(e->getData(comboBox));
		comboBox.setTranslateX(0);
		comboBox.setTranslateY(0);
		
		GridPane grid=new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5,5,5,5));
		grid.add(comboBox, 1, 0);
		root.getChildren().add(grid);
		
	}
	
	private void getData(ComboBox comboBox){
		String game=comboBox.getSelectionModel().getSelectedItem().toString();
		try {		
			db.getScores("HighScores", game, gamesPlayed);
			createFeed(game);
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
	


	

	
	private void createFeed(String game){
		//TEXT FIELD
		TextField textArea=new TextField();	
		int frameRate=2;
		
		KeyFrame frame=start(frameRate,game);
		Timeline animation=new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		nameGUI();
		textArea.setOnKeyPressed(e->AddLine(e,chatName,textArea,game));	
		VBox chatBox = new VBox();
		chatBox.getChildren().addAll(myChat,textArea);
		root.getChildren().addAll(chatBox);
		
		//scrolling
		myChat.scrollTo(myChat.getItems().size()-1);
	}
	
	void getStatsScreen(Stage s){
		myStage=s;
		s.setScene(scoreScreen);
	}
	


}
