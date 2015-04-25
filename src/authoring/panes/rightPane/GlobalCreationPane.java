/**
 * 
 */
package authoring.panes.rightPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import authoring.util.PropertiesFileParser;


/**
 * @author hojeanniechung, Natalie Chanfreau
 *
 */
public class GlobalCreationPane extends EditingPane {
    private static final String PROPERTIES_FILEPATH = "/Resources/GlobalSettingsPane.properties";
    private static final String UPDATE_STRING = "Update";
    private static final String _VALUE_STRING = "_VALUE";
    private List<HBox> myFieldBoxes = new LinkedList<>();
    private Map<String, String> myValueMap;
    private String[] myFieldNames;

    public GlobalCreationPane (Scene myScene, RightPane parent) {
        // TODO Auto-generated constructor stub
        super(myScene, parent);
        /* Default Map */
        createDefaultMap(getFieldNames(), getDefaultValues());
        setFields(this.getChildren(), updateMap());
        Button c = new Button(UPDATE_STRING);
        c.setOnAction(e -> updateMap());
        this.getChildren().add(c);
    }

    private enum GlobalCreationPaneFieldNames {
        GAME_NAME, SCENE_WIDTH, SCENE_HEIGHT, FRAME_RATE, FIRST_LEVEL
    }
    
    private String[] getFieldNames () {
        GlobalCreationPaneFieldNames[] enums = GlobalCreationPaneFieldNames.values();
        myFieldNames = PropertiesFileParser.convertEnumToStringArray(enums);
        
        try {
            return PropertiesFileParser.alphabeticallyLoadProperties(enums, PROPERTIES_FILEPATH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String[] getDefaultValues () {
        String[] values = new String[myFieldNames.length];
        for (int i = 0; i < myFieldNames.length; i++) {
            values[i] = myFieldNames[i] + _VALUE_STRING;
        }
        
        try {
            return PropertiesFileParser.alphabeticallyLoadProperties(values, PROPERTIES_FILEPATH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createDefaultMap (String[] keys, String[] values) {
        myValueMap = new HashMap<String, String>();
        for (int i = 0; i < keys.length; i++) {
            myValueMap.put(keys[i], values[i]);
        }
    }

    private void setFields (ObservableList<Node> parent,
                            Map<String, String> fields) {
        fields.forEach( (label, value) -> {
            HBox h = new HBox(5);
            h.getChildren().addAll(new Text(label),
                                   new javafx.scene.control.TextField(value));
            parent.add(h);
            myFieldBoxes.add(h);
        });

    }

    private Map<String, String> updateMap () {
        // System.out.println();

        myFieldBoxes.forEach(hbox -> {
            myValueMap.put((((Text) hbox.getChildren().get(0)).getText()),
                       (((TextField) hbox.getChildren().get(1)).getText()));
        });
        return myValueMap;
        // System.out.println(sprite.getCharacteristics().toString());
    }

    public Map<String, String> getFields () {
        return myValueMap;
    }

    // public static GlobalCreationPane getInstance(Scene scene) {
    // if (mInstance == null)
    // mInstance = new GlobalCreationPane(scene);
    // return mInstance;
    // }
}
