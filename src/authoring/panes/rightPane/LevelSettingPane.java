package authoring.panes.rightPane;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import authoring.panes.centerPane.CenterCanvas;
import authoring.panes.centerPane.CenterPane;

public class LevelSettingPane extends EditingPane {
    private List<HBox> myFields = new LinkedList<>();
    private CenterPane cp;
    public Map<String, String> fields;
    private static Scene mScene;

    public LevelSettingPane(Scene myScene, RightPane parent) {
        // TODO Auto-generated constructor stub
        super(myScene, parent);
        mScene = myScene;
        updateLevels(parent);
    }

    public void updateLevels(RightPane parent){
        this.getChildren().clear();
        /* Default Map */
        Iterator<List<CenterCanvas>> it = myParent.getParent().getCenterPane().getMaps();
        int levelIndex = 0, mapIndex = 0;
        HBox h = new HBox(5);
        while(it.hasNext()){
            List<CenterCanvas> maps = it.next();
            Label levelLabel = new Label("Level "+ levelIndex++);
            ComboBox<String> mapBox = new ComboBox<String>();
            mapBox.setPromptText("Select Map");
            for(CenterCanvas c : maps){
                mapBox.getItems().add("Map "+mapIndex++);
                mapBox.setEditable(true);
            }
            h.getChildren().addAll(levelLabel,mapBox);
            h.setMaxWidth(parent.getContainer().getWidth());
            this.getChildren().add(h);
        }
    }
}
