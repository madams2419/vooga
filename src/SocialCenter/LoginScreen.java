package SocialCenter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
		StackPane background = new StackPane();
		background.setId("pane");
		Rectangle r = makeSmoke(loginScreen);
		background.setEffect(new GaussianBlur(6));
		background.getChildren().add(r);
		root.getChildren().add(background);

	}

	private void createForm() {
		VBox form = new VBox(5);
		TextField loginBox = new TextField();
		TextField password = new TextField();
		Button submit = new Button("SUBMIT");
		loginBox.getStyleClass().add("login");
		password.getStyleClass().add("login");
		submit.getStyleClass().add("submit");
		submit.setMinWidth(125);
		loginBox.setText("Login");
		password.setText("Password");
		form.getChildren().addAll(loginBox, password, submit);
		form.setTranslateX(50);
		form.setLayoutY(loginScreen.getHeight() / 2);
		form.setMaxSize(125, 15);

		root.getChildren().add(form);

	}

	private void createTitle() {
		VBox TitleBox = new VBox();
		Label Title = new Label("High $crollers");
		Title.getStyleClass().add("title");
		TitleBox.getChildren().add(Title);
		TitleBox.setTranslateX(-125);
		TitleBox.setLayoutY(loginScreen.getHeight() / 2);
		TitleBox.setMaxSize(200, 30);
		root.getChildren().add(TitleBox);
	}

	private Rectangle makeSmoke(Scene s) {
		return new javafx.scene.shape.Rectangle(s.getWidth(), s.getHeight(),
				Color.WHITESMOKE.deriveColor(0, 1, 1, 0.20));
	}

	public Scene getLoginScreen() {
		return loginScreen;
	}

}
