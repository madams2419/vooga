package menu;

import game_player.VoogaGame;
import game_player.VoogaGameBuilder;
import authoring.userInterface.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
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
 * @since 10 April 2015
 */
public class VoogaMenu {
    private static final double PADDING = 10;
    
    private static final double INVISIBLE = 0.0;
    private static final double TRANSPARENT = 0.6;
    private static final double OPAQUE = 1.0;
    
    private static final int BUTTON_RADIUS = 250;
    
    private static final int TRANSITION_TIME = 2000;
    private static final int TRANSITION_DELAY = 500;
    
    private static final int SMALL_TEXT = 30;
    private static final int LARGE_TEXT = 40;
    private static final int TITLE_TEXT = 80;
    
    private static final String GAME = "game";
    private static final String DESIGN = "dev";
    
    private VoogaFileChooser chooser;
    
    private StackPane root;
    private Scene scene;
    
    private double width, height;
    
    private HBox background;
    
    private StackPane mainMenu;
    private Rectangle overlay;
    private StackPane playButton, designButton;
    private VBox textHolder;
    
    private StackPane choiceMenu;
    
    /**
     * Primary constructor for a MainMenu.  Sets up all of the components (background,
     * overlay, title, and buttons) in the root (which is a StackPane).
     * 
     * @param w
     *          the desired width of the Scene, based on the width of the stage it will be applied to.
     *          
     * @param h
     *          is the desired height of the Scene, also based on the size of the stage.
     */
    public VoogaMenu(double w, double h) {
	width = w;
	height = h;
	
	root = new StackPane();
	mainMenu = new StackPane();
	
	// TODO: Replace rectangles with ImageViews which hold screenshots from our working game player / authoring environment
	
	background = new HBox(2 * PADDING);
	Rectangle left = new Rectangle(width, height);
	left.setFill(Color.DARKRED);
	Rectangle right = new Rectangle(width, height);
	right.setFill(Color.DARKBLUE);
	background.getChildren().addAll(left, right);
	background.setOpacity(INVISIBLE);
	
	overlay = new Rectangle(width, height);
	overlay.setOpacity(TRANSPARENT);
	
	textHolder = setUpText();
	
	playButton = makeButton("Play", -width/2 - 2 * BUTTON_RADIUS);
	designButton = makeButton("Design", width/2 + 2 * BUTTON_RADIUS);
	
	root.setOnMouseClicked((clicked) -> displayMenu());
	
	mainMenu.getChildren().addAll(overlay, textHolder, playButton, designButton);
	
	root.getChildren().addAll(background, mainMenu);
    }
    
    /**
     * Creates a Scene object from the root.
     * 
     * @return
     *         A Scene object containing all of the elements in the root.
     */
    public Scene initialize() {
	scene = new Scene(root, width, height);
	scene.setFill(Color.BLACK);
	return scene;
    }
    
    /*
     * Creates two text objects and applies a FadeTransition to both.
     */
    private VBox setUpText() {
	VBox textHolder = new VBox(PADDING);
	
	Text title = new Text("Welcome to VoogaSalad");
	title.setEffect(createShadow(15, 10, 10));
	title.setStroke(Color.WHITE);
	title.setFont(new Font(TITLE_TEXT));
	title.setOpacity(INVISIBLE);
	
	HBox subHolder = new HBox();
	subHolder.getChildren().addAll(makeText(SMALL_TEXT, "presented by the ", Color.WHITE),
				       makeText(LARGE_TEXT, "High ", Color.WHITE),
				       makeText(LARGE_TEXT, "$", Color.DARKGOLDENROD),
				       makeText(LARGE_TEXT, "croller", Color.WHITE),
				       makeText(LARGE_TEXT, "$", Color.DARKGOLDENROD));
	subHolder.setAlignment(Pos.CENTER);
	subHolder.setOpacity(INVISIBLE);
	
	textHolder.getChildren().addAll(title, subHolder);
	textHolder.setAlignment(Pos.CENTER);
	
	FadeTransition fadeTitleIn = new FadeTransition(Duration.millis(TRANSITION_TIME), title);
	fadeTitleIn.setToValue(OPAQUE);
	fadeTitleIn.setDelay(Duration.millis(TRANSITION_DELAY));
	
	FadeTransition fadeNameIn = new FadeTransition(Duration.millis(TRANSITION_TIME), subHolder);
	fadeNameIn.setToValue(OPAQUE);
	fadeNameIn.setDelay(Duration.millis(TRANSITION_DELAY));
	
	fadeTitleIn.setOnFinished((finished) -> {
	    displayMenu();
	    fadeNameIn.play();
	});
	
	fadeTitleIn.play();
	
	return textHolder;
    }
    
    /*
     * Fades the full background into view, moves the title into place,
     * and moves the buttons onto the screen.  Only does this if the method
     * hasn't been called before, indicated by the root onMouseClicked action.
     */
    private void displayMenu() {
	// TODO: get rid of this if statement if possible
	if (root.getOnMouseClicked() != null) {
	    TranslateTransition moveText = new TranslateTransition(Duration.millis(TRANSITION_TIME), textHolder);
	    moveText.setToY(-height / 3);
	    moveText.play();

	    FadeTransition fadeBackgroundIn = new FadeTransition(Duration.millis(TRANSITION_TIME), background);
	    fadeBackgroundIn.setToValue(OPAQUE);
	    fadeBackgroundIn.play();

	    TranslateTransition movePlay = moveButton(playButton, -width / 4);
	    moveButton(designButton, width / 4);

	    movePlay.setOnFinished((finished) -> {
		enableButtons();
	    });

	    root.setOnMouseClicked(null);
	}
    }
    
    /*
     * Creates a single Text object.  Useful in above method for setting up the
     * "presented by ..." message.
     */
    private Text makeText(int size, String message, Color color) {
	Text text = new Text(message);
	text.setEffect(createShadow(15, 5, 5));
	text.setFill(color);
	text.setFont(new Font(size));
	text.setStroke(Color.BLACK);
	return text;
    }
    
    /*
     * Creates the shadow for a Text object or a Circle.
     */
    private DropShadow createShadow(int blurAmount, int offsetX, int offsetY) {
	DropShadow shadow = new DropShadow();
	shadow.setRadius(blurAmount);
	shadow.setOffsetX(offsetX);
	shadow.setOffsetY(offsetY);
	shadow.setBlurType(BlurType.GAUSSIAN);
	return shadow;
    }
    
    /*
     * Creates a button (which is Text description over a Circle).  Circle is fetched
     * later to use for MouseEvents.
     */
    private StackPane makeButton(String description, double location) {
	StackPane button = new StackPane();
	
	DropShadow shadow = createShadow(15, 10, 10);
	
	Circle buttonContent = new Circle(BUTTON_RADIUS);
	buttonContent.setFill(Color.TRANSPARENT);
	buttonContent.setStroke(Color.TRANSPARENT);
	
	Text buttonDescription = new Text(description);
	buttonDescription.setFill(Color.WHITE);
	buttonDescription.setFont(new Font(LARGE_TEXT));
	buttonDescription.setEffect(shadow);
	
	button.getChildren().addAll(buttonDescription, buttonContent);
	button.setTranslateX(location);
	
	button.setMaxSize(2 * BUTTON_RADIUS, 2 * BUTTON_RADIUS);
	
	return button;
    }
    
    /*
     * Moves the buttons onto the Screen on the first mouse click.
     */
    private TranslateTransition moveButton(StackPane button, double location) {
	TranslateTransition moveButton = new TranslateTransition(Duration.millis(TRANSITION_TIME), button);
	moveButton.setToX(location);
	moveButton.play();
	return moveButton;
    }
    
    /*
     * Gets the internal Circles from the StackPane buttons and uses those Circles
     * to set up mouse listeners.  Specifies where to move each element of the root
     * for each case.
     */
    private void enableButtons() {
	Circle play = (Circle) playButton.getChildren().get(1);
	Circle design = (Circle) designButton.getChildren().get(1);
	
	play.setOnMouseEntered((event) -> 
	    buttonSelectedAction(width/2 + PADDING, -2*height/3, OPAQUE, INVISIBLE, INVISIBLE));
	
	design.setOnMouseEntered((event) -> 
	    buttonSelectedAction(-width/2 - PADDING, -2*height/3, INVISIBLE, OPAQUE, INVISIBLE));
	
	play.setOnMouseExited((event) -> 
	    buttonSelectedAction(0, -height/3, OPAQUE, OPAQUE, TRANSPARENT));
	
	design.setOnMouseExited((event) -> 
	    buttonSelectedAction(0, -height/3, OPAQUE, OPAQUE, TRANSPARENT));
	
	play.setOnMouseClicked((event) -> {
	    addChoiceMenu(GAME);
	    choiceMenu.setOnMouseClicked((clicked) -> {
		VoogaGameBuilder builder = new VoogaGameBuilder(chooser.getChosenFile());
		VoogaGame gameLoop = builder.build();
		scene.setRoot(gameLoop.getRoot());
		gameLoop.getRoot().requestFocus();
		gameLoop.start();
	    });
	});
	
	design.setOnMouseClicked((event) -> {
	    addChoiceMenu(DESIGN);
	    choiceMenu.setOnMouseClicked((clicked) -> {
		root.getChildren().removeAll(choiceMenu, background);
		scene = new AuthoringWindow().GameCreateUI();
	    });
	});
    }
    
    /*
     * Removes the functionality set up by enableButtons so that when the choice menu is loaded
     * the events aren't triggered
     */
    private void disableButtons() {
	Circle play = (Circle) playButton.getChildren().get(1);
	Circle design = (Circle) designButton.getChildren().get(1);
	
	play.setOnMouseEntered(null);
	design.setOnMouseEntered(null);
	play.setOnMouseExited(null);
	design.setOnMouseExited(null);
	play.setOnMouseClicked(null);
	design.setOnMouseClicked(null);
    }
    
    /*
     * Adds the choice menu to the root and removes the main menu, also
     * blurs the background for effect.
     */
    private void addChoiceMenu(String fileType) {
	chooser = new VoogaFileChooser(width, height, fileType);
	choiceMenu = chooser.getContent();
	
	disableButtons();
	
	FadeTransition fadeMainOut = new FadeTransition(Duration.millis(TRANSITION_TIME), mainMenu);
	fadeMainOut.setToValue(INVISIBLE);
	fadeMainOut.play();
	fadeMainOut.setOnFinished((finished) -> root.getChildren().remove(mainMenu));
	
	root.getChildren().add(choiceMenu);
	FadeTransition fadeChoiceIn = new FadeTransition(Duration.millis(TRANSITION_TIME), choiceMenu);
	fadeChoiceIn.setDelay(Duration.millis(TRANSITION_DELAY));
	fadeChoiceIn.setToValue(OPAQUE);
	fadeChoiceIn.play();
	
	GaussianBlur blur = new GaussianBlur();
	background.setEffect(blur);
    }
    
    /*
     * Moves all of the elements in the root to the desired location with a pleasant transition.
     */
    private void buttonSelectedAction(double backgroundTo, double textTo, double playTo, double designTo, double overlayTo) {
	TranslateTransition moveBackground = new TranslateTransition(Duration.millis(TRANSITION_TIME), background);
	TranslateTransition moveText = new TranslateTransition(Duration.millis(TRANSITION_TIME), textHolder);
	FadeTransition fadePlay = new FadeTransition(Duration.millis(TRANSITION_TIME), playButton);
	FadeTransition fadeDesign = new FadeTransition(Duration.millis(TRANSITION_TIME), designButton);
	FadeTransition fadeOverlay = new FadeTransition(Duration.millis(TRANSITION_TIME), overlay);
	
	moveBackground.setToX(backgroundTo);
	moveBackground.play();
	
	moveText.setToY(textTo);
	moveText.play();
	
	fadePlay.setToValue(playTo);
	fadePlay.play();
	
	fadeDesign.setToValue(designTo);
	fadeDesign.play();
	
	fadeOverlay.setToValue(overlayTo);
	fadeOverlay.play();
    }
}