/**
 * 
 */
package utilities.SocialCenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author hojeanniechung
 *
 */
public class ChatPage {
	private Stage myStage;
	private Scene myMenu;
	private String ID;
	private Scene chatScreen;
	private ListView<String> myChat=new ListView<>();
	private double Width;
	private double Height;
	private StackPane root=new StackPane();
	private Driver db=new Driver();
	
	ChatPage(String id, double width, double height, Scene menu){
		myMenu = menu;
		ID=id;
		Width=width;
		Height=height;
		initialize(width,height);
		gameList();
	}
	
	private void initialize(double w, double h){
		chatScreen=new Scene(root,w,h);
	
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
		comboBox.setOnAction(e->chatData(comboBox));
		comboBox.setTranslateX(0);
		comboBox.setTranslateY(0);
		
		GridPane grid=new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5,5,5,5));
		grid.add(comboBox, 1, 0);
		root.getChildren().add(grid);
		
	}
	
	private void chatData(ComboBox comboBox){
		String game=comboBox.getSelectionModel().getSelectedItem().toString();
		try {		
			db.createChat("Chat",game);
			createFeed(game);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void createFeed(String game){
		TextField textArea=new TextField();	
		int frameRate=1;
		KeyFrame frame=start(frameRate,game);
		Timeline animation=new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		Scanner s=new Scanner(System.in);
		System.out.println("Please insert your chatname");
		String chatname=s.next();
		textArea.setOnKeyPressed(e->AddLine(e,chatname,textArea,game));	
		root.getChildren().addAll(myChat,textArea);
	}
	
	private void AddLine(KeyEvent k, String chatname,TextField textArea, String game){
		if(k.getCode()==KeyCode.ENTER){
			try {
				db.addLine("Chat",game,String.format("%s: ",chatname)+textArea.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textArea.clear();
		}
	}
	
	private KeyFrame start (int frameRate, String game) {
		return new KeyFrame(Duration.millis(1000 / frameRate), e -> updateList(game));
	}
	
	private void updateList(String game){
		try {
			//First get what is in the database
			ArrayList<String> prev=new ArrayList<>();
			prev=db.get("Chat", String.format("SELECT * FROM %s",game), "CHATLINE");
			ObservableList<String> listViewData=FXCollections.observableArrayList(prev);
			myChat.setItems(listViewData);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	void getChatScreen(Stage s){
		myStage=s;
		s.setScene(chatScreen);
	}
}
