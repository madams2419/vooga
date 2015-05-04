/**
 * 
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author hojeanniechung
 *
 */
public class ChatPage {
	private static final double TRANSLATE_Y = .75;
	private Stage myStage;
	private String ID;
	private Scene chatScreen;
	private ListView<String> myChat=new ListView<>();
	private StackPane root=new StackPane();
	private Driver db=new Driver();
	private String chatName;
	private Scene Menu;
	private double myWidth;
	private double myHeight;
	private Button myBack;
	
	ChatPage(String id, double width, double height, Scene menu){
		Menu=menu;
		ID=id;
		initialize(width,height);
		myWidth = width;
		myHeight = height;
		gameList();
	}
	
	private void initialize(double w, double h){
		chatScreen=new Scene(root,w,h);
	
	}
	
	private void addBack(){
		myBack=new Button("Back");
		myBack.setTranslateY(TRANSLATE_Y*myHeight/2);
		root.getChildren().add(myBack);
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
	
	private void createComboBox(Set<String> g){
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
	
	private void chatData(ComboBox<String> comboBox){
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
	
	private void nameGUI(){
		Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(myStage);
        HBox dialogHbox = new HBox(20);
		Label instr=new Label("Add me a chat name");
		TextField name=new TextField();
		Button submit=new Button("Set Name");
        dialogHbox.getChildren().addAll(instr,name,submit);
        Scene dialogScene = new Scene(dialogHbox, 500, 50);
        dialog.setScene(dialogScene);
        dialog.show();
		submit.setOnMouseClicked(e->getChatID(dialog,name));
	}
	
	private void getChatID(Stage dialog,TextField t){
		chatName=t.getText();
		dialog.close();
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
			addBack();
			myBack.setOnMouseClicked(e->goBack());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	void getChatScreen(Stage s){
		myStage=s;
		s.setScene(chatScreen);
	}
	
	void goBack(){	
		System.out.println("back");
		myStage.setScene(Menu);
	}
}
