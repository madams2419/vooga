/**
 * 
 */
package utilities.SocialCenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.stage.Stage;

/**
 * @author hojeanniechung
 *
 */
public class ChatPage {
	private Stage myStage;
	private String ID;
	private Scene chatScreen;
	private ListView<String> myChat=new ListView<>();
	private double Width;
	private double Height;
	private Group root=new Group();
	private Driver db=new Driver();
	private Timer timer;
	
	ChatPage(String id, double width, double height){
		ID=id;
		Width=width;
		Height=height;
		initialize(width,height);
		gameList();
	}
	
//	public class Updater extends TimerTask{
//		private String myGame;
//		
//		public Updater(String game){
//			myGame = game;
//		}
//		
//		@Override
//		public void run() {
//			updateList(myGame);
//			System.out.println("updating");
//		}
//		
//		private void updateList(String game){
//			try {
//				//First get what is in the database
//				ArrayList<String> prev=new ArrayList<>();
//				prev=db.get("Chat", String.format("SELECT * FROM %s",game), "CHATLINE");
//				ObservableList<String> listViewData=FXCollections.observableArrayList(prev);
//				myChat.setItems(listViewData);
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//	}
	
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
//			timer = new Timer();
//			timer.schedule(new Updater(game), 100);			
			db.createChat("Chat",game);
			createFeed(game);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void createFeed(String game){
		TextField textArea=new TextField();		
//		updateList(game);
		textArea.setOnKeyPressed(e->AddLine(e,textArea,game));	
		root.getChildren().addAll(myChat,textArea);
	}
	
	private void AddLine(KeyEvent k, TextField textArea, String game){
		if(k.getCode()==KeyCode.ENTER){
			try {
				db.addLine("Chat",game,textArea.getText());
				updateList(game);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textArea.clear();
		}
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
