package chatroom;

import javafx.application.Application;
import javafx.stage.*;

public class ChatroomMain extends Application{
	ChatroomClient myClient;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		View view = new View();
		myClient = new ChatroomClient(view);
		myClient.run();
		stage.setResizable(false);
        stage.setTitle("Chat Room");
        stage.setScene(view.getScene());
        stage.show();
		
	}

}