package menu;

import java.io.File;

import game_player.VoogaGame;
import game_player.VoogaGameBuilder;
import game_player.XMLParser;
import authoring.userInterface.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * MainMenu provides VoogaSaladMain with an intro and the choice to either play
 * an existing game or design a new one.
 * 
 * @author Brian Lavallee
 * @since 12 April 2015
 */
public class VoogaMenu {
    private static final double PADDING = 10;

    private static final double INVISIBLE = 0.0;
    private static final double TRANSPARENT = 0.6;
    private static final double OPAQUE = 1.0;

    private static final int BUTTON_RADIUS = 250;

    private static final Duration TRANSITION_TIME = Duration.millis(2500);
    private static final Duration DELAY = Duration.millis(500);
    private static final Duration IMMEDIATE = Duration.ZERO;

    private static final EventHandler<ActionEvent> DO_NOTHING = (event) -> {
    };

    private static final int SMALL_TEXT = 30;
    private static final int LARGE_TEXT = 40;
    private static final int TITLE_TEXT = 80;

    private static final String GAME = "game";
    private static final String DESIGN = "dev";

    private static final String SIMPLE_GAME = "src/gamedata/Simple.game";

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
     * Primary constructor for a MainMenu. Sets up all of the components
     * (background, overlay, title, and buttons) in the root (which is a
     * StackPane).
     * 
     * @param w
     *            the desired width of the Scene, based on the width of the
     *            stage it will be applied to.
     * 
     * @param h
     *            is the desired height of the Scene, also based on the size of
     *            the stage.
     */
    public VoogaMenu(double w, double h) {
	width = w;
	height = h;

	root = new StackPane();
	mainMenu = new StackPane();

	// TODO: Replace rectangles with ImageViews which hold screenshots from
	// our working game player / authoring environment

	background = new HBox(2 * PADDING);
	Rectangle left = new Rectangle(width, height);
	left.setFill(Color.DARKRED.darker());
	Rectangle right = new Rectangle(width, height);
	right.setFill(Color.DARKBLUE.darker());
	background.getChildren().addAll(left, right);
	background.setOpacity(INVISIBLE);

	overlay = new Rectangle(width, height);
	overlay.setOpacity(TRANSPARENT);

	textHolder = setUpText();

	playButton = makeButton("Play", -width / 2);
	designButton = makeButton("Design", width / 2);

	root.setOnMouseClicked((clicked) -> displayMenu());

	mainMenu.getChildren().addAll(overlay, textHolder, playButton,
		designButton);

	root.getChildren().addAll(background, mainMenu);
    }

    /**
     * Creates a Scene object from the root.
     * 
     * @return A Scene object containing all of the elements in the root.
     */
    public Scene initialize() {
	scene = new Scene(root, width, height);
	scene.setFill(Color.BLACK);

	scene.setOnKeyTyped((key) -> {
	    launchGame(new XMLParser(new File(SIMPLE_GAME)));
	});

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
	subHolder.getChildren().addAll(
		makeText(SMALL_TEXT, "presented by the ", Color.WHITE),
		makeText(LARGE_TEXT, "High ", Color.WHITE),
		makeText(LARGE_TEXT, "$", Color.DARKGOLDENROD),
		makeText(LARGE_TEXT, "croller", Color.WHITE),
		makeText(LARGE_TEXT, "$", Color.DARKGOLDENROD));
	subHolder.setAlignment(Pos.CENTER);
	subHolder.setOpacity(INVISIBLE);

	textHolder.getChildren().addAll(title, subHolder);
	textHolder.setAlignment(Pos.CENTER);

	doFade(title, DELAY, OPAQUE, (finished) -> {
	    displayMenu();
	    doFade(subHolder, DELAY, OPAQUE, DO_NOTHING);
	});

	return textHolder;
    }

    /*
     * Fades the full background into view, moves the title into place, and
     * moves the buttons onto the screen. Only does this if the method hasn't
     * been called before, indicated by the root onMouseClicked action.
     */
    private void displayMenu() {
	if (root.getOnMouseClicked() != null) {
	    doTranslate(textHolder, IMMEDIATE, textHolder.getTranslateX(),
		    -height / 3, DO_NOTHING);

	    doFade(background, IMMEDIATE, OPAQUE, DO_NOTHING);

	    doTranslate(playButton, IMMEDIATE, -width / 4,
		    playButton.getTranslateY(), (finished) -> enableButtons());
	    doTranslate(designButton, IMMEDIATE, width / 4,
		    designButton.getTranslateY(), DO_NOTHING);

	    doFade(playButton, DELAY, OPAQUE, DO_NOTHING);
	    doFade(designButton, DELAY, OPAQUE, DO_NOTHING);

	    root.setOnMouseClicked(null);
	}
    }

    /*
     * Creates a single Text object. Useful in above method for setting up the
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
     * Creates a button (which is Text description over a Circle). Circle is
     * fetched later to use for MouseEvents.
     */
    private StackPane makeButton(String description, double location) {
	StackPane button = new StackPane();

	button.setOpacity(INVISIBLE);

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
     * Gets the internal Circles from the StackPane buttons and uses those
     * Circles to set up mouse listeners. Specifies where to move each element
     * of the root for each case.
     */
    private void enableButtons() {
	Circle play = (Circle) playButton.getChildren().get(1);
	Circle design = (Circle) designButton.getChildren().get(1);

	play.setOnMouseEntered((event) -> buttonSelectedAction(width / 2
		+ PADDING, 31 * width / 40, INVISIBLE,
		playButton.getTranslateX(), OPAQUE, width / 2, INVISIBLE,
		INVISIBLE));

	design.setOnMouseEntered((event) -> buttonSelectedAction(-width / 2
		- PADDING, -31 * width / 40, INVISIBLE, -width / 2, INVISIBLE,
		designButton.getTranslateX(), OPAQUE, INVISIBLE));

	play.setOnMouseExited((event) -> buttonSelectedAction(0, 0, OPAQUE,
		playButton.getTranslateX(), OPAQUE, width / 4, OPAQUE,
		TRANSPARENT));

	design.setOnMouseExited((event) -> buttonSelectedAction(0, 0, OPAQUE,
		-width / 4, OPAQUE, designButton.getTranslateX(), OPAQUE,
		TRANSPARENT));
    }

    /*
     * Allows the user to click on one of the buttons to display the relevant
     * options.
     */
    private void enableClicking() {
	Circle play = (Circle) playButton.getChildren().get(1);
	Circle design = (Circle) designButton.getChildren().get(1);

	play.setOnMouseClicked((event) -> {
	    addChoiceMenu(GAME, playButton, -1);
	    setUpBackButtons();
	    choiceMenu.setOnMouseClicked((clicked) -> {
		launchGame(chooser.getChosenFile());
	    });
	});

	design.setOnMouseClicked((event) -> {
	    addChoiceMenu(DESIGN, designButton, 1);
	    setUpBackButtons();
	    choiceMenu.setOnMouseClicked((clicked) -> {
		root.getChildren().removeAll(choiceMenu, background);
<<<<<<< HEAD
		scene = new AuthoringWindow().GameCreateUI(scene);
=======
		scene = new AuthoringWindow().GameCreateUI(new Scene(choiceMenu));
>>>>>>> 22c1f5e5f0d9d58f364352d3e8a2a3fb75b02d75
	    });
	});
    }

    private void setUpBackButtons() {
	root.getChildren().add(createBackButton(Pos.CENTER_RIGHT, 180, 1));
	root.getChildren().add(createBackButton(Pos.CENTER_LEFT, 0, -1));
    }

    private StackPane createBackButton(Pos position, double rotate, double sign) {
	ImageView backButton = new ImageView(new Image(
		"menu/images/backButton.png"));
	backButton.setFitWidth(100);
	backButton.setFitHeight(100);
	backButton.setTranslateX(50 * sign);
	backButton.setOpacity(INVISIBLE);
	backButton.setRotate(rotate);

	Rectangle buttonOutline = new Rectangle(200, height);
	buttonOutline.setFill(Color.TRANSPARENT);
	buttonOutline.setTranslateX(100 * sign);
	buttonOutline.setOnMouseEntered((entered) -> {
	    doTranslate(backButton, TRANSITION_TIME.divide(5), IMMEDIATE, 0,
		    backButton.getTranslateY(), DO_NOTHING);
	    doFade(backButton, TRANSITION_TIME.divide(5), IMMEDIATE, OPAQUE,
		    DO_NOTHING);
	});
	buttonOutline.setOnMouseExited((exited) -> {
	    doTranslate(backButton, TRANSITION_TIME.divide(5), IMMEDIATE,
		    50 * sign, backButton.getTranslateY(), DO_NOTHING);
	    doFade(backButton, TRANSITION_TIME.divide(5), IMMEDIATE, INVISIBLE,
		    DO_NOTHING);
	});
	buttonOutline
		.setOnMouseClicked((clicked) -> {
		    doTranslate(backButton, TRANSITION_TIME.divide(5),
			    IMMEDIATE, 50 * sign, backButton.getTranslateY(),
			    DO_NOTHING);
		    doFade(backButton, TRANSITION_TIME.divide(5), IMMEDIATE,
			    INVISIBLE, DO_NOTHING);
		    buttonOutline.setOnMouseClicked(null);
		    buttonOutline.setOnMouseEntered(null);
		    buttonOutline.setOnMouseExited(null);
		    doTranslate(choiceMenu, DELAY, choiceMenu.getTranslateX(),
			    height, DO_NOTHING);
		    doFade(choiceMenu, IMMEDIATE, INVISIBLE,
			    (finished) -> resetMenu());
		});

	StackPane button = new StackPane();
	button.setMaxWidth(200);
	button.setTranslateX(width * sign / 2 - 100 * sign);
	button.getChildren().addAll(backButton, buttonOutline);
	button.setAlignment(position);

	return button;
    }

    private void resetMenu() {
	root.getChildren().clear();
	root.getChildren().addAll(background, mainMenu);

	background.setEffect(null);

	doTranslate(background, IMMEDIATE, 0, background.getTranslateY(),
		DO_NOTHING);
	doTranslate(textHolder, IMMEDIATE, 0, textHolder.getTranslateY(),
		DO_NOTHING);
	doTranslate(playButton, IMMEDIATE, -width / 4,
		playButton.getTranslateY(), DO_NOTHING);
	doTranslate(designButton, IMMEDIATE, width / 4,
		designButton.getTranslateY(), DO_NOTHING);

	doFade(textHolder, IMMEDIATE, OPAQUE, DO_NOTHING);
	doFade(playButton, IMMEDIATE, OPAQUE, DO_NOTHING);
	doFade(designButton, IMMEDIATE, OPAQUE, DO_NOTHING);
	doFade(overlay, IMMEDIATE, TRANSPARENT, (finished) -> enableButtons());
    }

    /*
     * Launches the game specified by the XMLParser input.
     */
    private void launchGame(XMLParser file) {
	if (file != null) {
	    VoogaGameBuilder gameBuilder = new VoogaGameBuilder(file);
	    VoogaGame game = gameBuilder.build();
	    scene.setRoot(game.getRoot());
	    game.start();
	}
    }

    /*
     * Removes the functionality set up by enableButtons so that when the choice
     * menu is loaded the events aren't triggered.
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
     * Adds the choice menu to the root and removes the main menu, also blurs
     * the background for effect.
     */
    private void addChoiceMenu(String fileType, StackPane button, int sign) {
	chooser = new VoogaFileChooser(width, height, fileType);
	choiceMenu = chooser.getContent(height);

	disableButtons();

	doTranslate(button, IMMEDIATE, (width / 2 + 50) * sign,
		playButton.getTranslateY(), (finished) -> root.getChildren()
			.remove(mainMenu));
	doFade(button, IMMEDIATE, INVISIBLE, DO_NOTHING);

	root.getChildren().add(choiceMenu);

	doTranslate(choiceMenu, DELAY.multiply(2), choiceMenu.getTranslateX(),
		0, DO_NOTHING);
	doFade(choiceMenu, DELAY.multiply(3), OPAQUE, DO_NOTHING);

	GaussianBlur blur = new GaussianBlur();
	background.setEffect(blur);
    }

    /*
     * Moves all of the elements in the root to the desired location with a
     * pleasant transition.
     */
    private void buttonSelectedAction(double backgroundLoc, double textLoc,
	    double textVis, double playLoc, double playVis, double designLoc,
	    double designVis, double overlayVis) {
	doTranslate(background, IMMEDIATE, backgroundLoc,
		background.getTranslateY(), DO_NOTHING);

	doTranslate(textHolder, IMMEDIATE, textLoc, textHolder.getTranslateY(),
		DO_NOTHING);
	doFade(textHolder, IMMEDIATE, textVis, DO_NOTHING);

	doTranslate(playButton, IMMEDIATE, playLoc, playButton.getTranslateY(),
		DO_NOTHING);
	doFade(playButton, IMMEDIATE, playVis, DO_NOTHING);

	doTranslate(designButton, IMMEDIATE, designLoc,
		designButton.getTranslateY(), DO_NOTHING);
	doFade(designButton, IMMEDIATE, designVis, DO_NOTHING);

	doFade(overlay, IMMEDIATE, overlayVis, DO_NOTHING);

	enableClicking();
    }

    /*
     * Performs a TranslateTransition.
     */
    private void doTranslate(Node source, Duration delay, double toX,
	    double toY, EventHandler<ActionEvent> onFinished) {
	doTranslate(source, TRANSITION_TIME, delay, toX, toY, onFinished);
    }

    /*
     * Performs a TranslateTransition.
     */
    private void doTranslate(Node source, Duration time, Duration delay,
	    double toX, double toY, EventHandler<ActionEvent> onFinished) {
	TranslateTransition translate = new TranslateTransition(time, source);
	translate.setToX(toX);
	translate.setToY(toY);
	translate.setDelay(delay);
	translate.setOnFinished(onFinished);
	translate.play();
    }

    /*
     * Performs a FadeTransition.
     */
    private void doFade(Node source, Duration delay, double toValue,
	    EventHandler<ActionEvent> onFinished) {
	doFade(source, TRANSITION_TIME, delay, toValue, onFinished);
    }

    /*
     * Performs a FadeTransition.
     */
    private void doFade(Node source, Duration time, Duration delay,
	    double toValue, EventHandler<ActionEvent> onFinished) {
	FadeTransition fade = new FadeTransition(time, source);
	fade.setDelay(delay);
	fade.setToValue(toValue);
	fade.setOnFinished(onFinished);
	fade.play();
    }
}