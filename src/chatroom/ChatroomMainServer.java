//package chatroom;
//
//import java.io.IOException;
//
//import javafx.application.Application;
//import javafx.stage.*;
//
//public class ChatroomMainServer extends Application {
//	public static void main(String[] args) {
//		launch(args);
//	}
//	
//	public void start(Stage stage) throws Exception {
//		View view = new View();
//		
//		int port = Integer.parseInt("6059");
//		try{
////			Thread t = new ChatroomServer(port,view);
////			t.start();
////		}catch(IOException e){
////			e.printStackTrace();
////		}
//		stage.setResizable(false);
//        stage.setTitle("Chat Room");
//        stage.setScene(view.getScene());
//        stage.show();
//		
////	}
////
////}