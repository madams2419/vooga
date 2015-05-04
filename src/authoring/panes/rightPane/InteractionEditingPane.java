package authoring.panes.rightPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import authoring.dataEditors.Action;
import authoring.dataEditors.Sprite;
import authoring.dialogs.ChooseInteractionDialog;
import authoring.util.GUIElementCreator;

/**
 * This will allow the user to edit the interaction between two characters.
 * 
 * @author Natalie Chanfreau and Andrew Sun
 *
 */

public class InteractionEditingPane extends EditingPane {
    private static final String HIT_BOX = "HitBox";
    private static final String PIXEL_PERFECT = "Pixel Perfect";
    private static final String SIMPLE = "Simple";
    private static final String UPDATE_INTERACTION_PARAMS = "Update Interaction Params";
    private static final String DONE_BUTTON_STRING = "Done";
    private static final String UPDATE_BUTTON_STRING = "Update";
    
    private Sprite mySprite1, mySprite2;
    private ObservableList<String> mySprite1Interactions = 
            FXCollections.observableArrayList();
    private ObservableList<String> mySprite2Interactions = 
            FXCollections.observableArrayList();

    InteractionEditingPane (Scene scene, RightPane parent, Sprite sprite1,
                            Sprite sprite2) throws IOException {
        super(scene, parent);
        
        mySprite1 = sprite1;
        mySprite2 = sprite2;
        
        setupSprite(mySprite1Interactions, mySprite1, mySprite2);
        setupSprite(mySprite2Interactions, mySprite2, mySprite1);
        
        addInteractionParams(UPDATE_INTERACTION_PARAMS);
        
        List<String> collisionChoices = Arrays.asList(SIMPLE, PIXEL_PERFECT, HIT_BOX);
        GUIElementCreator.addComboBoxToPane(collisionChoices, SIMPLE, this);
        
        getInteractions();
        addButtonToUpdate(UPDATE_BUTTON_STRING);
        addButtonToReturnToCreationPane(DONE_BUTTON_STRING);
        
    }
    
    private void setupSprite (ObservableList<String> mySpriteInteractions, 
                              Sprite mySprite, Sprite other) {
        
        ListView<String> list = new ListView<>(mySpriteInteractions);
        getInteractions(mySprite, mySpriteInteractions, other);
        addSpriteToPane(mySprite);
        getChildren().add(list);
    }

    private void getInteractions(Sprite s, ObservableList<String> list, Sprite other){
    	list.clear();
    	Map<Action, String> m = s.getInteractionWithSprite(other);
    	for (Action a : m.keySet()){
    	    list.add(a.getAction());
    	}
    }
    
    private void addButtonToUpdate (String label) {
        getChildren().add(new Button(label));
    }

    private void addInteractionParams (String s) throws IOException{
    	Button b = new Button(s);
    	b.setOnMouseClicked(e -> newInteractionDialog());
    	getChildren().add(b);
    }

    private void newInteractionDialog (){
        new ChooseInteractionDialog(myParent, mySprite1, mySprite2);
        getInteractions();
    }
    
    private void addSpriteToPane (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        getChildren().add(spriteIcon);
    }
    
    private void getInteractions () {
        getInteractions(mySprite1, mySprite1Interactions, mySprite2);
        getInteractions(mySprite2, mySprite2Interactions, mySprite1);
    }

}
