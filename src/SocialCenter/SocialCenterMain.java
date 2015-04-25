package SocialCenter;

import menu.VoogaMenu;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SocialCenterMain extends Application {
	private LoginScreen login;
	@Override
	
	
	public void start(Stage stage) throws Exception {
		
		//idk if we want to initialize the size itself
		stage.setTitle("Social Center");
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//		stage.setX(primaryScreenBounds.getMinX());
//		stage.setY(primaryScreenBounds.getMinY());
		
//		stage.setWidth(primaryScreenBounds.getWidth());
//		stage.setHeight(primaryScreenBounds.getHeight());
		stage.setResizable(false);
		//ProfilePage profile=new ProfilePage();

		
		LoginScreen login = new LoginScreen();
		SocialCenterMenu menu = new SocialCenterMenu(stage);
//		login.getLoginScreen(stage);
		login.getLoginScreen(stage);
		stage.show();

	}

	
	/**
	 * Launches the application.
	 * 
	 * @param args
	 *            are generally empty.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
