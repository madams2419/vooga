package menu;

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
	
	background = new HBox(20);
	Rectangle left = new Rectangle(width, height);
	left.setFill(Color.DARKRED);
	Rectangle right = new Rectangle(width, height);
	right.setFill(Color.DARKBLUE);
	background.getChildren().addAll(left, right);
	
	overlay = new Rectangle(width, height);
	overlay.setOpacity(.5);
	
	textHolder = setUpText();
	
	playButton = makeButton("Play", -width/2 - 200);
	designButton = makeButton("Design", width/2 + 200);
	
	root.setOnMouseClicked((clicked) -> 
	{
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	    moveText.setToY(-300);
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
	VBox textHolder = new VBox(10);
	
	Text title = new Text("Welcome to VoogaSalad");
	title.setStroke(Color.WHITE);
	title.setFont(new Font(80));
	title.setOpacity(0.0);
	
	HBox subHolder = new HBox();
	subHolder.getChildren().addAll(makeText(30, "presented by the ", Color.GRAY),
				       makeText(40, "High ", Color.GRAY),
				       makeText(40, "$", Color.DARKGOLDENROD),
				       makeText(40, "croller", Color.GRAY),
				       makeText(40, "$", Color.DARKGOLDENROD));
	subHolder.setAlignment(Pos.CENTER);
	subHolder.setOpacity(0.0);
	
	textHolder.getChildren().addAll(title, subHolder);
	textHolder.setAlignment(Pos.CENTER);
	
	FadeTransition fadeTitleIn = new FadeTransition(Duration.millis(2000), title);
	fadeTitleIn.setToValue(1.0);
	fadeTitleIn.setDelay(Duration.millis(500));
	fadeTitleIn.play();
	
	FadeTransition fadeNameIn = new FadeTransition(Duration.millis(2000), subHolder);
	fadeNameIn.setToValue(1.0);
	fadeNameIn.setDelay(Duration.millis(3000));
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
	
	Circle buttonContent = new Circle(150);
	buttonContent.setFill(Color.TRANSPARENT);
	buttonContent.setStroke(Color.WHITE);
	
	Text buttonDescription = new Text(description);
	buttonDescription.setFill(Color.WHITE);
	buttonDescription.setFont(new Font(20));
	
	button.getChildren().addAll(buttonContent, buttonDescription);
	button.setAlignment(Pos.CENTER);
	button.setTranslateX(location);
	
	return button;
    }
    
    private void moveButton(StackPane button, int location)
    {
	TranslateTransition moveButton = new TranslateTransition(Duration.millis(2000), button);
	    moveButton.setToX(location);
	    moveButton.play();
    }
    
    private void setUpButtons()
    {
	Circle play = (Circle) playButton.getChildren().get(0);
	Circle design = (Circle) designButton.getChildren().get(0);
	
	play.setOnMouseEntered((event) -> 
	{
	    buttonSelectedAction(width/2 + 10, -600, 1.0, 0.0, 0.0);
	});
	
	design.setOnMouseEntered((event) -> 
	{
	    buttonSelectedAction(-width/2 - 10, -600, 0.0, 1.0, 0.0);
	});
	
	play.setOnMouseExited((event) -> 
	{
	    buttonSelectedAction(0, -300, 1.0, 1.0, 0.5);
	});
	
	design.setOnMouseExited((event) -> 
	{
	    buttonSelectedAction(0, -300, 1.0, 1.0, 0.5);
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
	TranslateTransition moveBackground = new TranslateTransition(Duration.millis(2000), background);
	TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	FadeTransition fadePlay = new FadeTransition(Duration.millis(2000), playButton);
	FadeTransition fadeDesign = new FadeTransition(Duration.millis(2000), designButton);
	FadeTransition fadeOutOverlay = new FadeTransition(Duration.millis(2000), overlay);
	
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