package SocialCenter;

import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LoginScreen {
	private static final int TEMPWIDTH = 800;
	private static final int TEMPHEIGHT = 600;

	private Scene loginScreen;
	private StackPane root = new StackPane();



	public LoginScreen() {
		initialize();
	}

	private void initialize() {
		loginScreen = new Scene(root, TEMPWIDTH, TEMPHEIGHT);
		loginScreen.getStylesheets().add("styles/login.css");
		//		loginScreen.getStylesheets().addAll(this.getClass().getResource("./styles/login.css").toExternalForm());
		//		rect.getStyleClass().add("my-rect"); 
		root.setId("pane");
		Rectangle r = makeSmoke(loginScreen);
//		r.setEffect(new GaussianBlur(1000));
		root.setEffect(new GaussianBlur(6));
		root.getChildren().add(r);

		//		ImageView background = new ImageView();
		//		background.getStyleClass().add("body");
		//		root.getChildren().add(background);


	}
	
    private Rectangle makeSmoke(Scene s) {
        return new javafx.scene.shape.Rectangle(
                s.getWidth(),
                s.getHeight(),
                Color.WHITESMOKE.deriveColor(
                        0, 1, 1, 0.20
                )
        );
    }

	public Scene getLoginScreen(){
		return loginScreen;
	}

}
