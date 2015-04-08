package menu;

import game_player.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
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
 * @since 8 April 2015
 */
public class VoogaFileChooser {
    
    private static final String GAME_DATA_DIRECTORY = "src/gamedata/";
    
    private static final double INVISIBLE = 0.0;
    
    private static final double OPTION_RATIO = .7;
    
    private static final int OPTIONS_PER_ROW = 5;
    
    private static final String GAME_FILE_TYPE = "game";
    private static final String DEV_FILE_TYPE = "dev";
    
    private Map<String, VBox> layouts;
    
    private double optionSize, horizontalPadding, verticalPadding;
    
    private XMLParser chosenFile;
    
    /**
     * General constructor, needs height and width of the stage in order to dynamically space
     * and size options. Creates the layout for both game files and dev files during preprocess.
     * 
     * @param width
     *              is the horizontal size of the stage.
     *              
     * @param height
     *               is the vertical size of the stage.
     */
    public VoogaFileChooser(double width, double height) {
	verticalPadding = height * (1 - OPTION_RATIO) / (1 + OPTIONS_PER_ROW);
	horizontalPadding = width * (1 - OPTION_RATIO) / (3 + OPTIONS_PER_ROW);
	optionSize = width * OPTION_RATIO / OPTIONS_PER_ROW;
	
	layouts = new HashMap<String, VBox>();
	
	layouts.put(GAME_FILE_TYPE, createLayout(getFiles(GAME_FILE_TYPE), height));
	layouts.put(DEV_FILE_TYPE, createLayout(getFiles(DEV_FILE_TYPE), height));
    }
    
    /**
     * Retrieves the correct layout depending on whether or not the user wants to play
     * a game or design a new one.
     * 
     * @param fileType
     *                 is a String holding the type of file the user wants to load, either .game or .dev.
     *                 
     * @return
     *         a StackPane holding the layout of choices.
     */
    public StackPane getContent(String fileType) {
	StackPane content = new StackPane();
	content.getChildren().add(layouts.get(fileType));
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
	
	while (!matchingFiles.isEmpty()) {
	    List<File> temp = new ArrayList<File>();
	    
	    int size = matchingFiles.size();
	    for (int i = 0; i < OPTIONS_PER_ROW && i < size; i++) {
		temp.add(matchingFiles.remove(0));
	    }
	    
	    options.getChildren().add(createRow(temp));
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
	    Rectangle content = new Rectangle(optionSize, optionSize);
	    content.setArcHeight(verticalPadding);
	    content.setArcWidth(horizontalPadding);
	    
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