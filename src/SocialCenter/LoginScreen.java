package SocialCenter;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LoginScreen {
	private static final int TEMPWIDTH = 800;
	private static final int TEMPHEIGHT = 600;

	private Scene loginScreen;
	private StackPane root = new StackPane();



	public LoginScreen() {
		initialize();
		createForm();
		createTitle();
	}

	private void initialize() {
		loginScreen = new Scene(root, TEMPWIDTH, TEMPHEIGHT);
		loginScreen.getStylesheets().add("styles/login.css");
		//		loginScreen.getStylesheets().addAll(this.getClass().getResource("./styles/login.css").toExternalForm());
		//		rect.getStyleClass().add("my-rect"); 
		StackPane background = new StackPane();
		background.setId("pane");
		Rectangle r = makeSmoke(loginScreen);
		//		r.setEffect(new GaussianBlur(1000));
		background.setEffect(new GaussianBlur(6));
		background.getChildren().add(r);
		root.getChildren().add(background);

		//		ImageView background = new ImageView();
		//		background.getStyleClass().add("body");
		//		root.getChildren().add(background);


	}

	private void createForm(){
		VBox form = new VBox();
		TextField LoginBox = new TextField();
		TextField Password = new TextField();
		LoginBox.setStyle("-fx-background-color: transparent; -fx-border-color:black;");
		Password.setStyle("-fx-background-color: transparent; -fx-border-color:black;");


		LoginBox.setText("Login");
		Password.setText("Password");
		form.getChildren().addAll(LoginBox,Password);
		form.setTranslateX(50);
		form.setLayoutY(loginScreen.getHeight()/2);
		form.setMaxSize(125, 15);


		root.getChildren().add(form);

	}

	private void createTitle(){
		VBox TitleBox=new VBox();
		Text Title=new Text("High $crollers");
		Title.setStyle(		
		String.format("-fx-font-size: 15pt;-fx-base: #DFB951;-fx-background: #A78732;-fx-focus-color: #B6A678; -fx-font-family: %s;", "Tahoma"));

		TitleBox.getChildren().add(Title);
		TitleBox.setTranslateX(-75);
		TitleBox.setLayoutY(loginScreen.getHeight()/2);
		TitleBox.setMaxSize(100, 30);
		root.getChildren().add(TitleBox);
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
