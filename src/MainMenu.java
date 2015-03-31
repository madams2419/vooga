

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * MainMenu provides VoogaSaladMain with an intro and the choice
 * to either play an existing game or design a new one.
 * 
 * @author Brian Lavallee
 * @since 30 March 2015
 */
public class MainMenu
{
    private static final int PADDING = 10;
    
    private static final double INVISIBLE = 0.0;
    private static final double TRANSPARENT = 0.5;
    private static final double OPAQUE = 1.0;
    
    private static final int TITLE_LOCATION = -300;
    private static final int TITLE_REMOVED = -600;
    
    private static final int BUTTON_RADIUS = 150;
    
    private static final int TRANSITION_TIME = 2000;
    private static final int TRANSITION_DELAY = 500;
    
    private static final int SMALL_TEXT = 30;
    private static final int LARGE_TEXT = 40;
    private static final int TITLE_TEXT = 80;
    
    private StackPane root;
    private int width, height;
    private HBox background;
    private Rectangle overlay;
    private StackPane playButton, designButton;
    private VBox textHolder;
    
    public MainMenu(int w, int h)
    {
	width = w;
	height = h;
	
	root = new StackPane();
	
	background = new HBox(2*PADDING);
	Rectangle left = new Rectangle(width, height);
	left.setFill(Color.DARKRED);
	Rectangle right = new Rectangle(width, height);
	right.setFill(Color.DARKBLUE);
	background.getChildren().addAll(left, right);
	
	overlay = new Rectangle(width, height);
	overlay.setOpacity(TRANSPARENT);
	
	textHolder = setUpText();
	
	playButton = makeButton("Play", -width/2 - 2*BUTTON_RADIUS);
	designButton = makeButton("Design", width/2 + 2*BUTTON_RADIUS);
	
	root.setOnMouseClicked((clicked) -> 
	{
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	    moveText.setToY(TITLE_LOCATION);
	    moveText.play();
	    
	    moveButton(playButton, -width/4);
	    moveButton(designButton, width/4);
	    
	    setUpButtons();
	    
	    root.setOnMouseClicked(null);
	});
	
	root.getChildren().addAll(background, overlay, textHolder, playButton, designButton);
    }
    
    public Scene initialize()
    {
	Scene scene = new Scene(root, width, height);
	scene.setFill(Color.BLACK);
	return scene;
    }
    
    private VBox setUpText()
    {
	VBox textHolder = new VBox(PADDING);
	
	Text title = new Text("Welcome to VoogaSalad");
	title.setStroke(Color.WHITE);
	title.setFont(new Font(TITLE_TEXT));
	title.setOpacity(INVISIBLE);
	
	HBox subHolder = new HBox();
	subHolder.getChildren().addAll(makeText(SMALL_TEXT, "presented by the ", Color.GRAY),
				       makeText(LARGE_TEXT, "High ", Color.GRAY),
				       makeText(LARGE_TEXT, "$", Color.DARKGOLDENROD),
				       makeText(LARGE_TEXT, "croller", Color.GRAY),
				       makeText(LARGE_TEXT, "$", Color.DARKGOLDENROD));
	subHolder.setAlignment(Pos.CENTER);
	subHolder.setOpacity(INVISIBLE);
	
	textHolder.getChildren().addAll(title, subHolder);
	textHolder.setAlignment(Pos.CENTER);
	
	FadeTransition fadeTitleIn = new FadeTransition(Duration.millis(TRANSITION_TIME), title);
	fadeTitleIn.setToValue(OPAQUE);
	fadeTitleIn.setDelay(Duration.millis(TRANSITION_DELAY));
	fadeTitleIn.play();
	
	FadeTransition fadeNameIn = new FadeTransition(Duration.millis(TRANSITION_TIME), subHolder);
	fadeNameIn.setToValue(OPAQUE);
	fadeNameIn.setDelay(Duration.millis(TRANSITION_TIME + 2*TRANSITION_DELAY));
	fadeNameIn.play();
	
	return textHolder;
    }
    
    private Text makeText(int size, String message, Color color)
    {
	Text text = new Text(message);
	text.setFill(color);
	text.setFont(new Font(size));
	return text;
    }
    
    private StackPane makeButton(String description, int location)
    {
	StackPane button = new StackPane();
	
	Circle buttonContent = new Circle(BUTTON_RADIUS);
	buttonContent.setFill(Color.TRANSPARENT);
	buttonContent.setStroke(Color.WHITE);
	
	Text buttonDescription = new Text(description);
	buttonDescription.setFill(Color.WHITE);
	buttonDescription.setFont(new Font(SMALL_TEXT));
	
	button.getChildren().addAll(buttonContent, buttonDescription);
	button.setAlignment(Pos.CENTER);
	button.setTranslateX(location);
	
	return button;
    }
    
    private void moveButton(StackPane button, int location)
    {
	TranslateTransition moveButton = new TranslateTransition(Duration.millis(TRANSITION_TIME), button);
	    moveButton.setToX(location);
	    moveButton.play();
    }
    
    private void setUpButtons()
    {
	Circle play = (Circle) playButton.getChildren().get(0);
	Circle design = (Circle) designButton.getChildren().get(0);
	
	play.setOnMouseEntered((event) -> 
	{
	    buttonSelectedAction(width/2 + PADDING, TITLE_REMOVED, OPAQUE, INVISIBLE, INVISIBLE);
	});
	
	design.setOnMouseEntered((event) -> 
	{
	    buttonSelectedAction(-width/2 - PADDING, TITLE_REMOVED, INVISIBLE, OPAQUE, INVISIBLE);
	});
	
	play.setOnMouseExited((event) -> 
	{
	    buttonSelectedAction(0, TITLE_LOCATION, OPAQUE, OPAQUE, TRANSPARENT);
	});
	
	design.setOnMouseExited((event) -> 
	{
	    buttonSelectedAction(0, TITLE_LOCATION, OPAQUE, OPAQUE, TRANSPARENT);
	});
	
	play.setOnMouseClicked((event) -> 
	{
	    System.out.println("replace this with code to run a game");
	});
	
	design.setOnMouseClicked((event) -> 
	{
	    System.out.println("replace this with code to design a game");
	});
    }
    
    private void buttonSelectedAction(int backgroundTo, int textTo, double playTo, double designTo, double overlayTo)
    {
	TranslateTransition moveBackground = new TranslateTransition(Duration.millis(TRANSITION_TIME), background);
	TranslateTransition moveText = new TranslateTransition(Duration.millis(TRANSITION_TIME), textHolder);
	FadeTransition fadePlay = new FadeTransition(Duration.millis(TRANSITION_TIME), playButton);
	FadeTransition fadeDesign = new FadeTransition(Duration.millis(TRANSITION_TIME), designButton);
	FadeTransition fadeOutOverlay = new FadeTransition(Duration.millis(TRANSITION_TIME), overlay);
	
	moveBackground.setToX(backgroundTo);
	moveBackground.play();
	
	moveText.setToY(textTo);
	moveText.play();
	
	fadePlay.setToValue(playTo);
	fadePlay.play();
	
	fadeDesign.setToValue(designTo);
	fadeDesign.play();
	
	fadeOutOverlay.setToValue(overlayTo);
	fadeOutOverlay.play();
    }
}