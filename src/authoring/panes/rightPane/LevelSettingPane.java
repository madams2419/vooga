package authoring.panes.rightPane;

import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import authoring.panes.centerPane.CenterCanvas;

/**
 * Updates levels settings
 * @author Someone, Andrew
 *
 */
public class LevelSettingPane extends EditingPane {
    public Map<String, String> fields;
    public LevelSettingPane(Scene myScene, RightPane parent) {
        super(myScene, parent);
        updateLevels(parent);
    }

    public void updateLevels(RightPane parent){
        this.getChildren().clear();
        /* Default Map */
        List<List<CenterCanvas>> it = myParent.getParent().getCenterPane().getMaps();
        int levelIndex = 0, mapIndex = 0;
        HBox h = new HBox(5);
        for(int i = 0; i < it.size(); i++){
            List<CenterCanvas> maps = it.get(i);
            Label levelLabel = new Label("Level "+ levelIndex++);
            ComboBox<String> mapBox = new ComboBox<String>();
            mapBox.setPromptText("Select Map");
            for(int k = 0; k < maps.size(); k++){
                mapBox.getItems().add("Map "+mapIndex++);
                mapBox.setEditable(true);
            }
            h.getChildren().addAll(levelLabel,mapBox);
            h.setMaxWidth(parent.getContainer().getWidth());
            this.getChildren().add(h);
        }
    }
    
}
