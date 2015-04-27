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
import authoring.dialogs.InteractionsDialog;

/**
 * This will allow the user to edit the interaction between two characters.
 * 
 * @author Natalie Chanfreau and Andrew Sun
 *
 */

public class InteractionEditingPane extends EditingPane {
    private static final String doneButtonString = "Done";
    private static final String updateButtonString = "Update";
    private Sprite mySprite1, mySprite2;
	private ObservableList<String> mySprite1Interactions = FXCollections
			.observableArrayList();
	private ObservableList<String> mySprite2Interactions = FXCollections
			.observableArrayList();

    InteractionEditingPane (Scene scene, RightPane parent, Sprite sprite1,
                            Sprite sprite2) throws IOException {
        super(scene, parent);
		ListView<String> list1 = new ListView<>(mySprite1Interactions);
		ListView<String> list2 = new ListView<>(mySprite2Interactions);
		
        mySprite1 = sprite1;
        mySprite2 = sprite2;

        getInteractions(sprite1, mySprite1Interactions);
        getInteractions(sprite2, mySprite2Interactions);
               
        addSpriteToPane(sprite1);
        getChildren().add(list1);
        addSpriteToPane(sprite2);
        getChildren().add(list2);
        addInteractionParams("Update Interaction Params");
        
        List<String> collisionChoices = Arrays.asList("Simple", "Pixel Perfect", "HitBox");
        addComboBoxToPane(collisionChoices, "Simple");
        
        addButtonToUpdate(updateButtonString);
        addButtonToReturnToCreationPane(doneButtonString);
        

    }

    private void getInteractions(Sprite s, ObservableList<String> list){
    	list.clear();
    	for (Map<Action, String> m : s.getInteractionMap().values()){
    		for (Action a : m.keySet()){
    			System.out.println("printing");
    			list.add(a.getAction());
    		}
    	}
    }
    
    private void addButtonToUpdate (String label) {
        Button b = new Button(label);
        getInteractions(mySprite1, mySprite1Interactions);
        getInteractions(mySprite2, mySprite2Interactions);
        getChildren().add(b);
    }


    private void addInteractionParams(String s) throws IOException{
    	Button b = new Button(s);
			b.setOnMouseClicked(e -> newInteractionDialog());
    	getChildren().add(b);
    }

    private void newInteractionDialog(){
      try {
        new InteractionsDialog(myParent, mySprite1, mySprite2);
      } catch (IOException e) {
        e.printStackTrace();
      }
      getInteractions(mySprite1, mySprite1Interactions);
      getInteractions(mySprite2, mySprite2Interactions);
    }
    
    private void addSpriteToPane (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        getChildren().add(spriteIcon);
    }

}
