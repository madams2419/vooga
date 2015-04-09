package chatroom;

import javafx.application.Application;
import javafx.stage.*;

public class ChatroomMain extends Application{
	ChatroomClient myClient;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		myClient = new ChatroomClient();
		myClient.run();
		View view = new View();
		stage.setResizable(false);
        stage.setTitle("Chat Room");
        stage.setScene(view.getScene());
        stage.show();
		
	}

}