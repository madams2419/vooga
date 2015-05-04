package launcher;

import game_player.VoogaGame;
import game_player.VoogaGameBuilder;
import game_player.XMLParser;

import java.util.ArrayList;
import java.util.List;

import utilities.SocialCenter.LoginScreen;
import authoring.userInterface.AuthoringWindow;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VoogaMenu {
	
	private static final int PADDING = 20;
	
	private static final double HEADER_RADIUS = 200;
	
	private static final double SHADOW_BLUR = 15;
	private static final double SHADOW_OFFSET = 8;
	
	private static final double INVISIBLE = 0.0;
	private static final double OPAQUE = 1.0;
	
	private static final Color TEXT_COLOR = Color.WHITE;
	
	private static final int SMALL_TEXT_SIZE = 30;
	private static final int LARGE_TEXT_SIZE = 45;
	private static final int TITLE_TEXT_SIZE = 80;
	
	private static final Duration DELAY = Duration.millis(500);
	private static final Duration IMMEDIATE = Duration.ZERO;

	private static final Duration TRANSITION_TIME = Duration.millis(2000);
	
	private static final EventHandler<ActionEvent> DO_NOTHING = (finished) -> {};
	
	private static final String RETURN_PROMPT = "Return to Menu";
	
	private Group root;

	private double width, height;
	
	private Polygon playSection, designSection, socialSection;
	private List<Polygon> sections = new ArrayList<>();
	
	
	private Group header;
	private VBox title;
	private HBox subTitle;
	
	public VoogaMenu(double w, double h) {
		width = w;
		height = h;
		
		root = new Group();
		
		header = initializeHeader();
		
		double[] playPoints = new double[] {0, 0, width/2, height/2, width/2, 3*height/2, -width/2, 3*height/2, -width/2, height/2};
		playSection = initializeSection(playPoints, width/2, height, "launcher/game.png");
		
		double[] designPoints = new double[] {0, 0, width/2, height/2, width, height/2, width, -height, 0, -height};
		designSection = initializeSection(designPoints, width, 0, "launcher/authoring.png");
		
		double[] socialPoints = new double[] {0, 0, -width/2, height/2, -width, height/2, -width, -height, 0, -height};
		socialSection = initializeSection(socialPoints, 0, 0, "launcher/socialcenter.png");
		
		enableMouseListeners();
		
		sections.add(playSection);
		sections.add(designSection);
		sections.add(socialSection);
		
		root.getChildren().addAll(playSection, designSection, socialSection, header);
	}
	
	private void setText(String message) {
		Text text = new Text(message);
		text.setFill(TEXT_COLOR);
		text.setFont(new Font(LARGE_TEXT_SIZE));
		text.setOpacity(INVISIBLE);
		VBox textHolder = new VBox();
		textHolder.setPrefWidth(width);
		textHolder.getChildren().add(text);
		textHolder.setAlignment(Pos.CENTER);
		textHolder.setTranslateY(HEADER_RADIUS/2);
		header.getChildren().add(textHolder);
		
		doFade(subTitle, INVISIBLE, TRANSITION_TIME.divide(5), IMMEDIATE, DO_NOTHING);
		doFade(text, OPAQUE, TRANSITION_TIME.divide(5), IMMEDIATE, DO_NOTHING);
	}
	
	private void revertText() {
		doFade(header.getChildren().get(2), INVISIBLE, TRANSITION_TIME.divide(5), IMMEDIATE, (finished) -> header.getChildren().remove(2));
		doFade(subTitle, OPAQUE, TRANSITION_TIME.divide(5), IMMEDIATE, DO_NOTHING);
	}
	
	public Scene initialize() {
		Scene scene = new Scene(root, width, height);
		scene.setFill(Color.BLACK);
		return scene;
	}
	
	private void focusPlay() {
		disableMouseListeners();
		sections.forEach((section) -> doTranslate(section, width/2, -height/2));
		revertText();
		setText(RETURN_PROMPT);
		VoogaFileChooser chooser = new VoogaFileChooser(width, height);
		VBox choiceMenu = chooser.getContent(width, height);
		header.setOnMouseClicked((clicked) -> {
			header.setOnMouseClicked(null);
			doTranslate(choiceMenu, 0, height, DELAY);
			doFade(choiceMenu, INVISIBLE, DELAY.multiply(2), (finished) -> {
				revertMenu();
				revertText();
				enableMouseListeners();
			});
		});
		playSection.setOnMouseClicked((clicked) -> {
			root.getChildren().add(choiceMenu);
			doTranslate(choiceMenu, 0, HEADER_RADIUS, DELAY);
			doFade(choiceMenu, OPAQUE, DELAY.multiply(2));
			choiceMenu.setOnMouseClicked((chosen) -> launchGame(chooser.getChosenFile()));
		});
	}
	
	private void launchGame(XMLParser file) {
		if (file != null) {
			VoogaGameBuilder gameBuilder = new VoogaGameBuilder(file);
			VoogaGame game = gameBuilder.buildGame();
			game.start();
		}
	}
	
	private void focusDesign() {
		disableMouseListeners();
		sections.forEach((section) -> doTranslate(section, 0, height));
		revertText();
		setText(RETURN_PROMPT);
		header.setOnMouseClicked((clicked) -> {
			revertMenu();
			revertText();
			enableMouseListeners();
		});
		designSection.setOnMouseClicked((clicked) -> {
			Stage stage = new Stage();
			stage.setTitle("Authoring Environment");
			stage.setHeight(1000);
			stage.setWidth(1200);
			stage.setScene(new AuthoringWindow().GameCreateUI());
			stage.show();
		});
	}
	
	private void focusSocial() {
		disableMouseListeners();
		sections.forEach((section) -> doTranslate(section, width, height));
		revertText();
		setText(RETURN_PROMPT);
		header.setOnMouseClicked((clicked) -> {
			revertMenu();
			revertText();
			enableMouseListeners();
		});
		socialSection.setOnMouseClicked((clicked) -> {
			Stage stage = new Stage();
			stage.setTitle("Social Center");
			stage.setWidth(1200);
			stage.setHeight(900);
			LoginScreen login = new LoginScreen(stage, stage.getWidth(), stage.getHeight());
			login.getLoginScreen(stage);
			stage.show();
		});
	}
	
	private void enableMouseListeners() {
		setMouseListeners(socialSection, (clicked) -> focusSocial(), (entered) -> setText("Connect"), (exited) -> revertText());
		setMouseListeners(designSection, (clicked) -> focusDesign(), (entered) -> setText("Design a game"), (exited) -> revertText());
		setMouseListeners(playSection, (clicked) -> focusPlay(), (entered) -> setText("Play a game"), (exited) -> revertText());
	}
	
	private void disableMouseListeners() {
		sections.forEach((section) -> section.setOnMouseClicked(null));
		sections.forEach((section) -> section.setOnMouseEntered(null));
		sections.forEach((section) -> section.setOnMouseExited(null));
	}
	
	private void revertMenu() {
		doTranslate(playSection, width/2, height/2, DELAY);
		doTranslate(designSection, width/2 + PADDING, height/2 - PADDING * Math.sqrt(3), DELAY);
		doTranslate(socialSection, width/2 - PADDING, height/2 - PADDING * Math.sqrt(3), DELAY);
	}
	
	private Polygon initializeSection(double[] points, double startX, double startY, String image) {
		Polygon background = new Polygon(points);
		
		background.setFill(new ImagePattern(new Image(image)));
		
		background.setOpacity(INVISIBLE);
		
		background.setTranslateX(startX);
		background.setTranslateY(startY);
		
		return background;
	}
	
	private void setMouseListeners(Polygon section, EventHandler<MouseEvent> onClicked, EventHandler<MouseEvent> onEntered, EventHandler<MouseEvent> onExited) {
		section.setOnMouseClicked(onClicked);
		section.setOnMouseEntered(onEntered);
		section.setOnMouseExited(onExited);
	}
	
	private Group initializeHeader() {
		Group header = new Group();
		
		Ellipse background = new Ellipse();
		background.setRadiusX(width/2);
		background.setRadiusY(HEADER_RADIUS);
		background.setFill(Color.BLACK);
		background.setTranslateX(width/2);
		background.setTranslateY(-PADDING);
		
		initializeTitle();
		
		header.getChildren().addAll(background, title);
		
		return header;
	}
	
	private void initializeTitle() {
		title = new VBox(PADDING/2);
		
		Text titleText = new Text("Welcome to VoogaSalad");
		titleText.setEffect(createShadow(SHADOW_BLUR, SHADOW_OFFSET));
		titleText.setStroke(TEXT_COLOR);
		titleText.setFont(new Font(TITLE_TEXT_SIZE));
		titleText.setOpacity(INVISIBLE);

		subTitle = new HBox();
		subTitle.getChildren().addAll(
				makeText(SMALL_TEXT_SIZE, "presented by the ", TEXT_COLOR),
				makeText(LARGE_TEXT_SIZE, "High ", TEXT_COLOR),
				makeText(LARGE_TEXT_SIZE, "$", Color.DARKGOLDENROD),
				makeText(LARGE_TEXT_SIZE, "croller", TEXT_COLOR),
				makeText(LARGE_TEXT_SIZE, "$", Color.DARKGOLDENROD));
		subTitle.setAlignment(Pos.CENTER);
		subTitle.setOpacity(INVISIBLE);

		title.getChildren().addAll(titleText, subTitle);
		title.setAlignment(Pos.CENTER);
		
		Rectangle background = new Rectangle(width, height, Color.TRANSPARENT);
		root.getChildren().add(background);
		
		doFade(titleText, OPAQUE, DELAY, (mainFinished) -> {
			doFade(subTitle, OPAQUE, DELAY, (subFinished) -> {
				root.setOnMouseClicked((clicked) -> {
					root.setOnMouseClicked(null);
					root.getChildren().remove(background);
					displayMenu();
				});
			});
		});
		
		title.setPrefWidth(width);
		title.setTranslateY(height/3);
	}
	
	private Text makeText(double textSize, String content, Color textColor) {
		Text text = new Text(content);
		text.setEffect(createShadow(SHADOW_BLUR, SHADOW_OFFSET));
		text.setFill(textColor);
		text.setFont(new Font(textSize));
		text.setStroke(Color.BLACK);
		return text;
	}
	
	private DropShadow createShadow(double blurAmount, double blurOffset) {
		DropShadow shadow = new DropShadow();
		shadow.setRadius(blurAmount);
		shadow.setOffsetX(blurOffset);
		shadow.setOffsetY(blurOffset);
		shadow.setBlurType(BlurType.GAUSSIAN);
		return shadow;
	}
	
	private void displayMenu() {
		doFade(playSection, OPAQUE, DELAY.multiply(2));
		doFade(designSection, OPAQUE, DELAY.multiply(2));
		doFade(socialSection, OPAQUE, DELAY.multiply(2));
		
		doTranslate(title, 0, 0);
		
		revertMenu();
	}
	
	private void doFade(Node source, double toValue, Duration delay) {
		doFade(source, toValue, delay, DO_NOTHING);
	}
	
	private void doFade(Node source, double toValue, Duration delay, EventHandler<ActionEvent> onFinished) {
		doFade(source, toValue, TRANSITION_TIME, delay, onFinished);
	}
	
	private void doFade(Node source, double toValue, Duration time, Duration delay, EventHandler<ActionEvent> onFinished) {
		FadeTransition fade = new FadeTransition(time, source);
		fade.setDelay(DELAY);
		fade.setToValue(toValue);
		fade.setOnFinished(onFinished);
		fade.play();
	}
	
	private void doTranslate(Node source, double toX, double toY) {
		doTranslate(source, toX, toY, IMMEDIATE, DO_NOTHING);
	}
	
	private void doTranslate(Node source, double toX, double toY, Duration delay) {
		doTranslate(source, toX, toY, delay, DO_NOTHING);
	}
	
	private void doTranslate(Node source, double toX, double toY, Duration delay, EventHandler<ActionEvent> onFinished) {
		TranslateTransition translate = new TranslateTransition(TRANSITION_TIME, source);
		translate.setDelay(delay);
		translate.setToX(toX);
		translate.setToY(toY);
		translate.setOnFinished(onFinished);
		translate.play();
	}
}