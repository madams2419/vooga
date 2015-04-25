package authoring.panes.rightPane;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import authoring.dataEditors.Interaction;
import authoring.dataEditors.InteractionManager;
import authoring.dataEditors.Sprite;
import authoring.util.FrontEndUtils;
import authoring.dialogs.InteractionsDialog;
import javafx.scene.control.Label;

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
    private ObjectProperty<String> myActionProperty1, myActionProperty2;
    private Interaction myInteraction;

    InteractionEditingPane (Scene scene, RightPane parent, Sprite sprite1,
                            Sprite sprite2, List<String> actionPossibilities) throws IOException {
        super(scene, parent);
        this.getChildren().add(
                               new TextArea(String
                                       .format("This will contain the interactions")));

        mySprite1 = sprite1;
        mySprite2 = sprite2;

//        myInteraction = getInteraction();
//        // TODO: somehow get the number of parameters that each interaction requires
//        myActionProperty1 = addSpriteAndModifiersToPane(sprite1, actionPossibilities, 
//                                                        myInteraction.getAction1());
//        myActionProperty2 = addSpriteAndModifiersToPane(sprite2, actionPossibilities, 
//                                                        myInteraction.getAction2());
        addInteractionParams(sprite1, "Update Interaction Params");
        addInteractionParams(sprite2, "Update Interaction Params");
        addButtonToUpdate(updateButtonString);
        addButtonToReturnToCreationPane(doneButtonString);
    }

    private void addButtonToUpdate (String label) {
        Button b = new Button(label);
//        b.setOnMouseClicked(i -> setInteraction(myActionProperty1.getValue(),
//                                                myActionProperty2.getValue()));
        getChildren().add(b);
    }

//    private Interaction getInteraction () {
//        return InteractionManager.getInstance().getOrCreateInteraction(mySprite1, mySprite2);
//    }
//
//    private void setInteraction (String action1, String action2) {
////        System.out.println("action 1 is : " + action1 + ", action 2 is : " + action2);
//        myInteraction = new Interaction(mySprite1, mySprite2, action1, action2);
//        InteractionManager.getInstance().setInteraction(mySprite1, mySprite2,
//                                                        myInteraction);
//    }

    private ObjectProperty<String> addSpriteAndModifiersToPane (Sprite sprite,
                                                                List<String> actionPossibilities,
                                                                String action) throws IOException {
        addSpriteToPane(sprite);
//        addToggleGroupToPane();
        return addComboBoxToPane(actionPossibilities, action);
    }
    
    // TODO: refactor this method and "add button to update method" into superclass?
    private void addInteractionParams(Sprite sprite, String s) throws IOException{
    	Button b = new Button(s);
			b.setOnMouseClicked(e -> {
				try{
					new InteractionsDialog(mySprite1, mySprite2);
				}
				catch (Exception a){}
	    	});
    	getChildren().add(b);
    }

    private void addSpriteToPane (Sprite sprite) {
        ImageView spriteIcon = sprite.getIcon();
        getChildren().add(spriteIcon);
    }

    private void addToggleGroupToPane () {
        getChildren().add(FrontEndUtils.makeToggleGroup());
    }

}
