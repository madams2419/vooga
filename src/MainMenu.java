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
    private Circle play, design;
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
	overlay.setOpacity(.4);
	
	textHolder = new VBox(10);
	
	Text title = new Text("Welcome to VoogaSalad");
	title.setStroke(Color.WHITE);
	title.setFont(new Font(80));
	title.setOpacity(0.0);
	
	HBox subHolder = new HBox();
	subHolder.getChildren().add(makeText(30, "presented by the ", Color.GRAY));
	subHolder.getChildren().add(makeText(40, "High ", Color.GRAY));
	subHolder.getChildren().add(makeText(40, "$", Color.DARKGOLDENROD));
	subHolder.getChildren().add(makeText(40, "croller", Color.GRAY));
	subHolder.getChildren().add(makeText(40, "$", Color.DARKGOLDENROD));
	subHolder.setAlignment(Pos.CENTER);
	subHolder.setOpacity(0.0);
	
	textHolder.getChildren().addAll(title, subHolder);
	textHolder.setAlignment(Pos.CENTER);
	
	playButton = new StackPane();
	play = new Circle(150);
	play.setFill(Color.TRANSPARENT);
	play.setStroke(Color.WHITE);
	Text playDescription = new Text("Play");
	playDescription.setFill(Color.WHITE);
	playDescription.setFont(new Font(20));
	playButton.getChildren().addAll(play, playDescription);
	playButton.setAlignment(Pos.CENTER);
	playButton.setTranslateX(-width/2 - 200);
	
	designButton = new StackPane();
	design = new Circle(150);
	design.setFill(Color.TRANSPARENT);
	design.setStroke(Color.WHITE);
	Text designDescription = new Text("Design");
	designDescription.setFill(Color.WHITE);
	designDescription.setFont(new Font(20));
	designButton.getChildren().addAll(design, designDescription);
	designButton.setAlignment(Pos.CENTER);
	designButton.setTranslateX(width/2 + 200);
	
	FadeTransition fadeTitleIn = new FadeTransition(Duration.millis(2000), title);
	fadeTitleIn.setFromValue(0.0);
	fadeTitleIn.setToValue(1.0);
	fadeTitleIn.setDelay(Duration.millis(500));
	fadeTitleIn.play();
	
	FadeTransition fadeNameIn = new FadeTransition(Duration.millis(2000), subHolder);
	fadeNameIn.setFromValue(0.0);
	fadeNameIn.setToValue(1.0);
	fadeNameIn.setDelay(Duration.millis(3000));
	fadeNameIn.play();
	
	root.setOnMouseClicked((clicked) -> 
	{
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	    moveText.setToY(-300);
	    moveText.play();
	    
	    TranslateTransition movePlay = new TranslateTransition(Duration.millis(2000), playButton);
	    movePlay.setToX(-width/4);
	    movePlay.play();
	    
	    TranslateTransition moveDesign = new TranslateTransition(Duration.millis(2000), designButton);
	    moveDesign.setToX(width/4);
	    moveDesign.play();
	    
	    setUpButtons();
	    root.setOnMouseClicked(null);
	});
	
	root.getChildren().addAll(background, overlay, textHolder, playButton, designButton);
    }
    
    private Text makeText(int size, String message, Color color)
    {
	Text text = new Text(message);
	text.setFill(color);
	text.setFont(new Font(size));
	return text;
    }
    
    private void setUpButtons()
    {
	play.setOnMouseEntered((event) -> 
	{
	    TranslateTransition moveBackground = new TranslateTransition(Duration.millis(2000), background);
	    moveBackground.setToX(width/2 + 10);
	    moveBackground.play();
	    
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	    moveText.setToY(-600);
	    moveText.play();
	    
	    FadeTransition movePlay = new FadeTransition(Duration.millis(2000), playButton);
	    movePlay.setToValue(0.0);
	    movePlay.play();
	    
	    FadeTransition moveDesign = new FadeTransition(Duration.millis(2000), designButton);
	    moveDesign.setToValue(0.0);
	    moveDesign.play();
	    
	    FadeTransition fadeOutOverlay = new FadeTransition(Duration.millis(2000), overlay);
	    fadeOutOverlay.setToValue(0.0);
	    fadeOutOverlay.play();
	});
	
	design.setOnMouseEntered((event) -> 
	{
	    TranslateTransition moveBackground = new TranslateTransition(Duration.millis(2000), background);
	    moveBackground.setToX(-width/2 - 10);
	    moveBackground.play();
	    
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	    moveText.setToY(-600);
	    moveText.play();
	    
	    FadeTransition movePlay = new FadeTransition(Duration.millis(2000), playButton);
	    movePlay.setToValue(0.0);
	    movePlay.play();
	    
	    FadeTransition moveDesign = new FadeTransition(Duration.millis(2000), designButton);
	    moveDesign.setToValue(0.0);
	    moveDesign.play();
	    
	    FadeTransition fadeOutOverlay = new FadeTransition(Duration.millis(2000), overlay);
	    fadeOutOverlay.setToValue(0.0);
	    fadeOutOverlay.play();
	});
	
	play.setOnMouseExited((event) -> 
	{
	    TranslateTransition moveBackground = new TranslateTransition(Duration.millis(2000), background);
	    moveBackground.setToX(0);
	    moveBackground.play();
	    
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	    moveText.setToY(-300);
	    moveText.play();
	    
	    FadeTransition movePlay = new FadeTransition(Duration.millis(2000), playButton);
	    movePlay.setToValue(1.0);
	    movePlay.play();
	    
	    FadeTransition moveDesign = new FadeTransition(Duration.millis(2000), designButton);
	    moveDesign.setToValue(1.0);
	    moveDesign.play();
	    
	    FadeTransition fadeOutOverlay = new FadeTransition(Duration.millis(2000), overlay);
	    fadeOutOverlay.setToValue(0.4);
	    fadeOutOverlay.play();
	});
	
	design.setOnMouseExited((event) -> 
	{
	    TranslateTransition moveBackground = new TranslateTransition(Duration.millis(2000), background);
	    moveBackground.setToX(0);
	    moveBackground.play();
	    
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(2000), textHolder);
	    moveText.setToY(-300);
	    moveText.play();
	    
	    FadeTransition movePlay = new FadeTransition(Duration.millis(2000), playButton);
	    movePlay.setToValue(1.0);
	    movePlay.play();
	    
	    FadeTransition moveDesign = new FadeTransition(Duration.millis(2000), designButton);
	    moveDesign.setToValue(1.0);
	    moveDesign.play();
	    
	    FadeTransition fadeOutOverlay = new FadeTransition(Duration.millis(2000), overlay);
	    fadeOutOverlay.setToValue(0.4);
	    fadeOutOverlay.play();
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
    
    public Scene initialize()
    {
	Scene scene = new Scene(root, width, height);
	scene.setFill(Color.BLACK);
	return scene;
    }
}