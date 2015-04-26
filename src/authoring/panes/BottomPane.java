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

    private List<Button> mButtonList = new ArrayList<>();
    public static final String XML_FILE_OUTPUT = "res/game.xml";

    // BottomPane() {
    // this(myScene,myContainer);
    // }

    public BottomPane(Scene s, AuthoringWindow parent) {
        super(s, new HBox(), parent);
//        System.out.printf("Instantiated %s%n", this.getClass().getName());
        myScene = s;
        getContainer().getStylesheets().add("styles/top_pane.css");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Group generateComponents(
            ArrayList<Map<String, Map<String, String>>> values) {
        for (int i = 0; i < values.size(); i++) {
            Map<String, Map<String, String>> m = values.get(i);
            for (String key : m.keySet()) {
                if (key.equals("Button")) {
                    mButtonList.add(ButtonFactory.generateButton(
                            myParent.getRightPane(), m.get(key)));
                }
                if (key.equals("Dropdown")) {
                    DropdownFactory dFactory = new DropdownFactory();
                    dFactory.generateDropdown(m.get(key));
                }
            }
        }
        MenuBar menuBar = new MenuBar();
        Menu b = new Menu("+");
        MenuItem nLevel = new MenuItem("New Level");
        MenuItem nMap = new MenuItem("New Map");
        b.getItems().addAll(nLevel,nMap);
        menuBar.getMenus().add(b);
        try {
            nLevel.setOnAction(new ClickHandler(
                    CenterPane.class.getMethod("addLevel"), myParent
                    .getCenterPane()));
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            nMap.setOnAction(new ClickHandler(
                    CenterPane.class.getMethod("addMap"), myParent
                .getCenterPane()));
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
                Button c = new Button("Output xml");
        c.setOnAction(e -> {
            FrontEndUtils.buildXMLFile(myParent, XML_FILE_OUTPUT);
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