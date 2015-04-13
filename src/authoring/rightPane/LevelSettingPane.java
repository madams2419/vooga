package authoring.rightPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import authoring.InteractionManager;
import authoring.LevelManager;
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
    private List<HBox> myFields = new LinkedList<>();
    private CenterPane cp;
    public Map<String, String> fields;
    private static Scene mScene;

    public LevelSettingPane(Scene myScene, RightPane parent) {
        // TODO Auto-generated constructor stub
        super(myScene, parent);
        mScene = myScene;
        Map<Integer, ArrayList<Integer>> levels = LevelManager.getInstance().getLevels();
        /* Default Map */
        Iterator<Entry<Integer, ArrayList<Integer>>> it = levels.entrySet().iterator();
        HBox h = new HBox(5);
        while(it.hasNext()){
            Entry<Integer, ArrayList<Integer>> thisLevel = it.next();
            Label levelLabel = new Label("Level "+thisLevel.getKey());
            ComboBox<String> mapBox = new ComboBox<String>();
            mapBox.setPromptText("Select Map");
            ArrayList<Integer> maps = thisLevel.getValue();
            for(Integer map:maps){
                mapBox.getItems().add("Map "+map);
                mapBox.setEditable(true);
            }
            h.getChildren().addAll(levelLabel,mapBox);
            h.setMaxWidth(parent.myContainer.getWidth());
            this.getChildren().add(h);
        }
    }

}
