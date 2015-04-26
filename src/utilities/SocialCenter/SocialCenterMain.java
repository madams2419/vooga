package utilities.SocialCenter;

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

		stage.setWidth(800);
		stage.setHeight(600);
		LoginScreen login = new LoginScreen(stage,stage.getWidth(),stage.getHeight());
		login.getLoginScreen(stage);
//		SocialCenterMenu menu = new SocialCenterMenu(null, 800, 600);
//		menu.returnScene(stage);
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
