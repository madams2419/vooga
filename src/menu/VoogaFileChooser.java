package menu;

import game_player.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * VoogaFileChooser finds all of the files in the game data directory and
 * allows the user to pick from a graphical user interface.  The VoogaFileChooser
 * displays different files depending on which mode the users select, .game for
 * the play mode and .dev for the design mode.
 * 
 * @author Brian Lavallee
 * @since 9 April 2015
 */
public class VoogaFileChooser {
    
    private static final String GAME_DATA_DIRECTORY = "src/gamedata/";
    
    private static final double INVISIBLE = 0.0;
    
    private static final double OPTION_RATIO = .7;
    private static final int OPTIONS_PER_ROW = 5;
    
    private VBox layout;
    private Rectangle blur;
    
    private double optionSize, horizontalPadding, verticalPadding;
    
    private XMLParser chosenFile;
    
    /**
     * General constructor, needs height and width of the stage in order to dynamically space
     * and size options. Creates the layout for either game files and dev files during preprocess.
     * 
     * @param width
     *              is the horizontal size of the stage.
     *              
     * @param height
     *               is the vertical size of the stage.
     *               
     * @param fileType
     *                 is a String holding the file extension of the files to be put in the layout
     */
    public VoogaFileChooser(double width, double height, String fileType) {
	verticalPadding = height * (1 - OPTION_RATIO) / (1 + OPTIONS_PER_ROW);
	horizontalPadding = width * (1 - OPTION_RATIO) / (3 + OPTIONS_PER_ROW);
	optionSize = width * OPTION_RATIO / OPTIONS_PER_ROW;
	
	blur = new Rectangle(width, height);
	blur.setEffect(new GaussianBlur());
	blur.setOpacity(.5);
	
	layout = createLayout(getFiles(fileType), height);
    }
    
    /**
     * Creates the actual menu that will be displayed by placing the layout
     * on top of a GaussianBlur to blur the background image.
     *                 
     * @return
     *         a StackPane holding the layout of choices.
     */
    public StackPane getContent() {
	StackPane content = new StackPane();
	content.getChildren().addAll(layout, blur);
	content.setOpacity(INVISIBLE);
	return content;
    }
    
    /**
     * Returns the selected file so that a program can be executed.
     * 
     * @return
     *         An XMLParser containing the information of the selected file.
     */
    public XMLParser getChosenFile() {
	return chosenFile;
    }
    
    /*
     * Creates the layout via a VBox, also defines the scrolling limits of the VBox.
     */
    private VBox createLayout(List<File> matchingFiles, double height) {
	VBox options = new VBox(verticalPadding);
	
	HBox lastRow = createRow(new ArrayList<File>());
	while (!matchingFiles.isEmpty()) {
	    List<File> temp = new ArrayList<File>();
	    
	    int size = matchingFiles.size();
	    for (int i = 0; i < OPTIONS_PER_ROW && i < size; i++) {
		temp.add(matchingFiles.remove(0));
	    }
	    
	    lastRow = createRow(temp);
	    options.getChildren().add(lastRow);
	}
	
	if (lastRow.getChildren().size() != 5) {
	    lastRow.getChildren().add(newContentOption());
	}
	else {
	    HBox newRow = createRow(new ArrayList<File>());
	    newRow.getChildren().add(newContentOption());
	    options.getChildren().add(newRow);
	}
	
	options.setAlignment(Pos.CENTER);
	
	double size = options.getChildren().size() * (optionSize + verticalPadding);
	
	double maxY = (size - height) / 2;
	double minY = (height - size) / 2 - verticalPadding;
	
	options.setTranslateY(maxY);
	
	options.setOnScroll((scroll) -> {
	    double location = options.getTranslateY() + scroll.getDeltaY();
	    if (location <= maxY && location >= minY) {
		options.setTranslateY(location);
	    }
	});
	
	return options;
    }
    
    /*
     * Creates a single row using an HBox, a group of these are added to the VBox to
     * create the full layout.
     */
    private HBox createRow(List<File> group) {
	HBox row = new HBox(horizontalPadding);
	
	for (File f : group) {
	    // TODO: Replace rectangles with imageviews
	    Rectangle content = createButtonContent(Color.BLACK, Color.BLACK, 0);
	    
	    XMLParser parser = new XMLParser(f);
	    
	    String fileName = parser.getValidSubDirectories().get(0);
	    fileName = fileName.substring(0, fileName.length() - 1);
	    fileName = fileName.replace('_', ' ');
	    Text name = new Text(fileName);
	    name.setFill(Color.WHITE);
	    name.setFont(new Font(20));
	    
	    StackPane option = new StackPane();
	    option.getChildren().addAll(content, name);
	    option.setMaxSize(optionSize, optionSize);
	    
	    option.setOnMouseClicked((clicked) -> chosenFile = parser);
	    
	    row.getChildren().add(option);
	}
	
	row.setAlignment(Pos.CENTER);
	return row;
    }
    
    /*
     * Creates the menu option to design a new game rather than playing
     * or designing an existing one.
     */
    private StackPane newContentOption() {
	Rectangle background = createButtonContent(Color.TRANSPARENT, Color.WHITE, 5);
	Text description = new Text("Create New Game");
	description.setFont(new Font(20));
	description.setFill(Color.WHITE);
	
	StackPane content = new StackPane();
	content.getChildren().addAll(background, description);
	return content;
    }
    
    /*
     * Creates the buttons contained in the layout.  Both the fill and stroke
     * colors can be specified as well as the stroke width.
     */
    private Rectangle createButtonContent(Color fill, Color stroke, int width) {
	Rectangle content = new Rectangle(optionSize, optionSize);
	content.setArcHeight(verticalPadding);
	content.setArcWidth(horizontalPadding);
	content.setFill(fill);
	content.setStroke(stroke);
	content.setStrokeWidth(width);
	return content;
    }
    
    /*
     * Finds every file in the directory where game files are stored and
     * adds them to a list.
     */
    private List<File> getFiles(String type) {
	File rootDirectory = new File(GAME_DATA_DIRECTORY);
	List<File> matchingFiles = new ArrayList<File>();
	
	for (File possible : rootDirectory.listFiles()) {
	    if (getGameType(possible).equals(type)) {
		matchingFiles.add(possible);
	    }
	}
	
	return matchingFiles;
    }
    
    /*
     * Finds the file extension of a file so it can be compared to .game and .dev.
     */
    private String getGameType(File possible) {
	String[] path = possible.getPath().split("/");
	String fileName = path[path.length - 1];
	int index = fileName.lastIndexOf(".") + 1;
	return fileName.substring(index);
    }
}