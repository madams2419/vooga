package authoring.rightPane;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import authoring.userInterface.CenterPane;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LevelSettingPane extends EditingPane {
//    private CenterPane cp;
//    public Map<String, String> fields;
//    private static Scene mScene;
//    
//    public LevelSettingPane (Scene myScene, RightPane parent) {
//        super(myScene, parent);
//        // TODO Auto-generated constructor stub
//        mScene = myScene;
//        /* Default Map */       

//    }
//
//    private List<HBox> setFields() {
//        List<HBox> myFields = new LinkedList<>();
        //levels.forEach((label, value) -> { edit this line to add levels
//            HBox h = new HBox(5);
//            h.getChildren().addAll(new Text(label),
//                    new javafx.scene.control.TextField(value));
//            parent.add(h);
//            myFields.add(h);
//        });

//        myFields.add(h);
//        return myFields;
//
//    }
    private List<HBox> myFields = new LinkedList<>();
    private CenterPane cp;
    public Map<String, String> fields;
    private static Scene mScene;

    public LevelSettingPane(Scene myScene, RightPane parent) {
        // TODO Auto-generated constructor stub
        super(myScene, parent);
        mScene = myScene;
        /* Default Map */
        createDefaultMap();
        HBox h = new HBox(5);
        ComboBox<String> levelBox = new ComboBox<String>();
        levelBox.setPromptText("Select level");
        levelBox.getItems().addAll("Level 1");
        levelBox.setEditable(true);
        ComboBox<String> mapBox = new ComboBox<String>();
        mapBox.setPromptText("Select Map");
        mapBox.getItems().addAll("Map 1");
        mapBox.setEditable(true);  
        h.getChildren().addAll(levelBox,mapBox);
        h.setMaxWidth(parent.myContainer.getWidth());
        this.getChildren().add(h);
    }

    private Map<String, String> createDefaultMap() {
        String[] key = { "Scrolling Speed", "Frame Rate", "Scrolling Size" };
        String[] value = { "Default", "Default", "Default" };
        fields = new HashMap<String, String>();
        for (int i = 0; i < key.length; i++) {
            fields.put(key[i], value[i]);
        }
        return fields;
    }

    private void setFields(ObservableList<Node> parent,
            Map<String, String> fields) {
        fields.forEach((label, value) -> {
            HBox h = new HBox(5);
            h.getChildren().addAll(new Text(label),
                    new javafx.scene.control.TextField(value));
            parent.add(h);
            myFields.add(h);
        });

    }

    private Map<String, String> updateMap() {
        // System.out.println();

        myFields.forEach(hbox -> {
            String s, t;
            fields.put((s = ((Text) hbox.getChildren().get(0)).getText()),
                    (t = ((TextField) hbox.getChildren().get(1)).getText()));
        });
        return fields;
        // System.out.println(sprite.getCharacteristics().toString());
    }

}
