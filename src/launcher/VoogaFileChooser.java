package launcher;

import game_player.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * VoogaFileChooser finds all of the files in the game data directory and allows
 * the user to pick from a graphical user interface. The VoogaFileChooser
 * displays different files depending on which mode the users select, .game for
 * the play mode and .dev for the design mode.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class VoogaFileChooser {

	private static final String GAME_DATA_DIRECTORY = "resources/game_files/";

	private static final double INVISIBLE = 0.0;
	private static final double TRANSPARENT = 0.6;

	private static final double OPTION_RATIO = .65;
	private static final int OPTIONS_PER_ROW = 4;

	private VBox layout;

	private double optionSize, horizontalPadding, verticalPadding;

	private XMLParser chosenFile;

	/**
	 * General constructor, needs height and width of the stage in order to
	 * dynamically space and size options. Creates the layout for either game
	 * files and dev files during preprocess.
	 * 
	 * @param width
	 *            is the horizontal size of the stage.
	 * 
	 * @param height
	 *            is the vertical size of the stage.
	 * 
	 * @param fileType
	 *            is a String holding the file extension of the files to be put
	 *            in the layout
	 */
	public VoogaFileChooser(double width, double height) {
		verticalPadding = height * (1 - OPTION_RATIO) / (1 + OPTIONS_PER_ROW);
		horizontalPadding = width * (1 - OPTION_RATIO) / (3 + OPTIONS_PER_ROW);
		optionSize = width * OPTION_RATIO / OPTIONS_PER_ROW;

		layout = createLayout(getFiles(), height);
	}

	/**
	 * Creates the actual menu that will be displayed by placing the layout on
	 * top of a GaussianBlur to blur the background image.
	 * 
	 * @return a StackPane holding the layout of choices.
	 */
	public VBox getContent(double width, double height) {
		layout.setPrefWidth(width);
		layout.setTranslateY(height);
		layout.setOpacity(INVISIBLE);
		return layout;
	}

	/**
	 * Returns the selected file so that a program can be executed.
	 * 
	 * @return An XMLParser containing the information of the selected file.
	 */
	public XMLParser getChosenFile() {
		return chosenFile;
	}

	/*
	 * Creates the layout via a VBox, also defines the scrolling limits of the
	 * VBox.
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

		double size = options.getChildren().size()
				* (optionSize + verticalPadding);

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
	 * Creates a single row using an HBox, a group of these are added to the
	 * VBox to create the full layout.
	 */
	private HBox createRow(List<File> group) {
		HBox row = new HBox(10);
		
		row.setSpacing(horizontalPadding);

		for (File f : group) {
			Rectangle content = createButtonContent(Color.BLACK, Color.BLACK,
					3, TRANSPARENT);

			XMLParser parser = new XMLParser(f);

			parser.moveDown("game");
			String fileName = parser.getValue("title");
			parser.moveUp();
			Text name = new Text(fileName);
			name.setFill(Color.WHITE);
			name.setFont(new Font(20));
			name.setEffect(createShadow(15, 5));

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
	 * Creates the buttons contained in the layout. Both the fill and stroke
	 * colors can be specified as well as the stroke width.
	 */
	private Rectangle createButtonContent(Color fill, Color stroke, int width,
			double opacity) {
		Rectangle content = new Rectangle(optionSize, optionSize);
		content.setArcHeight(verticalPadding);
		content.setArcWidth(horizontalPadding);
		content.setFill(fill);
		content.setStroke(stroke);
		content.setStrokeWidth(width);
		content.setEffect(createShadow(15, 10));
		content.setOpacity(opacity);
		return content;
	}

	/*
	 * Creates a shadow to enhance the appearance of a Text object or Button.
	 */
	private DropShadow createShadow(int blurAmount, int offset) {
		DropShadow shadow = new DropShadow();
		shadow.setRadius(blurAmount);
		shadow.setOffsetX(offset);
		shadow.setOffsetY(offset);
		shadow.setBlurType(BlurType.GAUSSIAN);
		return shadow;
	}

	/*
	 * Finds every file in the directory where game files are stored and adds
	 * them to a list.
	 */
	private List<File> getFiles() {
		File[] files = new File(GAME_DATA_DIRECTORY).listFiles();
		List<File> matchingFiles = new ArrayList<>();
		for (File file : files) {
			matchingFiles.add(file);
		}
		return matchingFiles;
	}
}