/**
 * 
 */
package authoring.rightPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import authoring.util.PropertiesFileParser;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


/**
 * @author hojeanniechung, Natalie Chanfreau
 *
 */
public class GlobalCreationPane extends EditingPane {
    private static final String PROPERTIES_FILEPATH = "/Resources/GlobalSettingsPane.properties";
    private static final String DEFAULT_STRING = "Default";
    private static final String UPDATE_STRING = "Update";
    private List<HBox> myFields = new LinkedList<>();
    private Map<String, String> fields;

    public GlobalCreationPane (Scene myScene, RightPane parent) {
        // TODO Auto-generated constructor stub
        super(myScene, parent);
        /* Default Map */
        createDefaultMap(getFieldNames());
        setFields(this.getChildren(), updateMap());
        Button c = new Button(UPDATE_STRING);
        c.setOnAction(e -> updateMap());
        this.getChildren().add(c);
    }

    private enum GlobalCreationPaneFieldNames {
        GAME_NAME, SCROLLING_SPEED, FRAME_RATE, SCROLLING_SIZE
    }
    
    private String[] getFieldNames () {
        GlobalCreationPaneFieldNames[] enums = GlobalCreationPaneFieldNames.values();
        
        try {
            return PropertiesFileParser.loadProperties(enums, PROPERTIES_FILEPATH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> createDefaultMap (String[] key) {
        String[] value = { DEFAULT_STRING, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_STRING };
        fields = new HashMap<String, String>();
        for (int i = 0; i < key.length; i++) {
            fields.put(key[i], value[i]);
        }
        return fields;
    }

    private void setFields (ObservableList<Node> parent,
                            Map<String, String> fields) {
        fields.forEach( (label, value) -> {
            HBox h = new HBox(5);
            h.getChildren().addAll(new Text(label),
                                   new javafx.scene.control.TextField(value));
            parent.add(h);
            myFields.add(h);
        });

    }

    private Map<String, String> updateMap () {
        // System.out.println();

        myFields.forEach(hbox -> {
            fields.put((((Text) hbox.getChildren().get(0)).getText()),
                       (((TextField) hbox.getChildren().get(1)).getText()));
        });
        return fields;
        // System.out.println(sprite.getCharacteristics().toString());
    }

    public Map<String, String> getFields () {
        return fields;
    }

    // public static GlobalCreationPane getInstance(Scene scene) {
    // if (mInstance == null)
    // mInstance = new GlobalCreationPane(scene);
    // return mInstance;
    // }
}
