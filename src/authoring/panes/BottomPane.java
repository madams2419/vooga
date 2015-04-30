package authoring.panes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import authoring.dialogs.SimpleDialog;
import authoring.panes.centerPane.CenterPane;
import authoring.userInterface.AuthoringWindow;
import authoring.userInterface.ButtonFactory;
import authoring.userInterface.ClickHandler;
import authoring.userInterface.DropdownFactory;
import authoring.util.FrontEndUtils;

/***
 * 
 * @author Daniel Luker and Jeannie
 *
 */
public class BottomPane extends WindowPane {

    private static final String OUTPUT_TITLE = "";
    private static final String OUTPUT_MESSAGE = "Success!";
    private static final String OUTPUT_XML = "Create game!";
    private static final String ADD_MAP = "addMap";
    private static final String ADD_LEVEL = "addLevel";
    private static final String NEW_MAP = "New Map";
    private static final String NEW_LEVEL = "New Level";
    private static final String PLUS = "+";
    private static final String DROPDOWN = "Dropdown";
    private static final String BUTTON = "Button";
    private static final String STYLES_TOP_PANE = "styles/top_pane.css";
    private List<Button> mButtonList = new ArrayList<>();
    public static final String XML_FILE_OUTPUT = "resources/game_files/test.game.xml";

    public BottomPane(Scene s, AuthoringWindow parent) {
        super(s, new HBox(), parent);
        myScene = s;
        getContainer().getStylesheets().add(STYLES_TOP_PANE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Group generateComponents(
            ArrayList<Map<String, Map<String, String>>> values) {
        for (int i = 0; i < values.size(); i++) {
            Map<String, Map<String, String>> m = values.get(i);
            for (String key : m.keySet()) {
                if (key.equals(BUTTON)) {
                    mButtonList.add(ButtonFactory.generateButton(
                            myParent.getRightPane(), m.get(key)));
                }
                if (key.equals(DROPDOWN)) {
                    DropdownFactory dFactory = new DropdownFactory();
                    dFactory.generateDropdown(m.get(key));
                }
            }
        }
        MenuBar menuBar = new MenuBar();
        Menu b = new Menu(PLUS);
        MenuItem nLevel = new MenuItem(NEW_LEVEL);
        MenuItem nMap = new MenuItem(NEW_MAP);
        b.getItems().addAll(nLevel,nMap);
        menuBar.getMenus().add(b);
        try {
            nLevel.setOnAction(new ClickHandler(
                    CenterPane.class.getMethod(ADD_LEVEL), myParent
                    .getCenterPane()));
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            nMap.setOnAction(new ClickHandler(
                    CenterPane.class.getMethod(ADD_MAP), myParent
                .getCenterPane()));
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
                Button c = new Button(OUTPUT_XML);
        c.setOnAction(e -> {
            FrontEndUtils.buildXMLFile(myParent, XML_FILE_OUTPUT);
            new SimpleDialog(OUTPUT_MESSAGE, OUTPUT_TITLE);
        });
        mButtonList.add(c);
        ((HBox) getContainer()).getChildren().addAll(mButtonList);
        ((HBox) getContainer()).getChildren().add(menuBar);
        return null;
    }

    public Iterator<Button> getButtons() {
        return mButtonList.iterator();
    }

    public Button[] getButtonArray() {
        return (Button[]) mButtonList.toArray();
    }

}