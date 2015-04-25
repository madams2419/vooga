package authoring.dialogs;

import game_engine.annotation.ReadProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import authoring.dataEditors.Action;
import authoring.dataEditors.Sprite;
import authoring.userInterface.DialogGridOrganizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * 
 * @author Andrew Sun
 *
 */
public class InteractionsDialog extends Dialog<ButtonType>{
	
	private List<Action> myActions;
	private Map<String, String> mySprite1Interactions;
	private Map<String, String> mySprite2Interactions;
	private List<ComboBox<Action>> myComboBoxes1;
	private List<ComboBox<Action>> myComboBoxes2;
	private List<Label> myDescriptions1, myDescriptions2, myChecks1, myChecks2;
	private List<TextField> myParams1;
	private List<TextField> myParams2;
	private HBox myHBox;
	
	private static final int BOTTOM_SPACING = 31;
	
	public InteractionsDialog(Sprite a, Sprite b) throws IOException{
		
		mySprite1Interactions = new HashMap<>();
		mySprite2Interactions = new HashMap<>();
		myDescriptions1 = new ArrayList<>();
		myChecks1 = new ArrayList<>();
		myParams1 = new ArrayList<>();
		myComboBoxes1 = new ArrayList<>();
		myDescriptions2 = new ArrayList<>();
		myChecks2 = new ArrayList<>();
		myParams2 = new ArrayList<>();
		myComboBoxes2 = new ArrayList<>();
		
		DialogGridOrganizer sprite1Grid = createGrid();
		DialogGridOrganizer sprite2Grid = createGrid();
		myHBox = new HBox(50);
		myHBox.getChildren().addAll(sprite1Grid, sprite2Grid);
		
		myActions = grabActions();

		this.getDialogPane().setContent(myHBox);
		ButtonType spr1 = new ButtonType("Add Sprite 1 Action");
		ButtonType spr2 = new ButtonType("Add Sprite 2 Action");
		this.getDialogPane().getButtonTypes().addAll(spr1, spr2, ButtonType.OK, ButtonType.CANCEL);
		
		addButton(spr1, e->{
			System.out.println(sprite1Grid.getHeight());
			System.out.println(this.getHeight());
			if (sprite1Grid.getHeight() + 116 >= this.getHeight()){
				 this.setHeight(this.getHeight() + BOTTOM_SPACING);
			}
			sprite1Grid.addRowEnd(addComboBox(myComboBoxes1, myDescriptions1), 
					addParamTextField(myParams1),
					addLabel(myDescriptions1), addLabel(myChecks1));
			this.setWidth(1200);
			e.consume();
		});
		
		addButton(spr2, e->{
			System.out.println(sprite1Grid.getHeight());
			System.out.println(this.getHeight());
			if (sprite2Grid.getHeight() + 116 >= this.getHeight()){
				 this.setHeight(this.getHeight() + BOTTOM_SPACING);
			}
			sprite1Grid.addRowEnd(addComboBox(myComboBoxes2, myDescriptions2), 
					addParamTextField(myParams2),
					addLabel(myDescriptions2), addLabel(myChecks2));
			this.setWidth(1200);
			e.consume();
		});
		
		showBox(a, b);
	}
	
	private DialogGridOrganizer createGrid(){
		DialogGridOrganizer result = new DialogGridOrganizer(4);
		result.addRowEnd(new Label("Action"), new Label("Params"), 
				new Label("Description"), new Label("Check"));
		return result;
	}
	
	public void showBox(Sprite a, Sprite b){
		
		this.showAndWait().filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			mySprite1Interactions = new HashMap<>();
			mySprite2Interactions = new HashMap<>();
			for (int i = 0; i < myDescriptions1.size(); i++){
				mySprite1Interactions.put(myComboBoxes1.get(i).getValue().getAction(),
						myParams1.get(i).getText());
			}
			for (int i = 0; i < myDescriptions2.size(); i++){
				mySprite2Interactions.put(myComboBoxes2.get(i).getValue().getAction(),
						myParams2.get(i).getText());
			}
			a.addInteraction(b, mySprite1Interactions);
			b.addInteraction(a, mySprite2Interactions);		
		});
	}
	
	private void addButton(ButtonType b, EventHandler<ActionEvent> e){

		 final Button addButton = (Button) this.getDialogPane().lookupButton(b);
		 addButton.addEventFilter(ActionEvent.ACTION, e);
	}
	
	private TextField addParamTextField(List<TextField> list){
		TextField result = new TextField();
		list.add(result);
		return result;
	}
	
	private Label addLabel(List<Label> labelList){
		Label result = new Label();
		labelList.add(result);
		return result;
	}
	
	private ComboBox<Action> addComboBox(List<ComboBox<Action>> comboList, List<Label> descriptionList){
		ComboBox<Action> result = new ComboBox<>();
		for (Action a : myActions){
			result.getItems().add(a);
		}
		result.setCellFactory(new Callback<ListView<Action>,ListCell<Action>>(){
			 
            @Override
            public ListCell<Action> call(ListView<Action> p) {                
                final ListCell<Action> cell = new ListCell<Action>(){
                    @Override
                    protected void updateItem(Action t, boolean bln) {
                        super.updateItem(t, bln);                         
                        if(t != null){
                            setText(t.getAction());
                        }else{
                            setText(null);
                        }
                    } 
                };
                 
                return cell;
            }
		});
		result.valueProperty().addListener(new ChangeListener<Action>() {
            @Override 
            public void changed(ObservableValue<? extends Action> ov, Action t, Action t1) { 
            	int index = comboList.indexOf(result);
            	descriptionList.get(index).setText(t1.getDescription());
            }    
        });
		comboList.add(result);
		return result;
	}
	

//	private Label[] createLabels(){
//		Label[] labels = new Label[8];
//		for (int i = 1; i < 2; i++){
//			labels[1*i-1] = new Label("Action"+Integer.toString(i));
//			labels[2*i-1] = new Label("Params"+Integer.toString(i));
//			labels[3*i-1] = new Label("Description"+Integer.toString(i));
//			labels[4*i-1] = new Label("Check"+Integer.toString(i));
//		}
//		return labels;
//	}
	
	private List<Action> grabActions() throws IOException{
		ReadProperties reader = new ReadProperties();
		Map<Integer, Map<String, String>> actionsMap = reader.getPropertiesMap("Actions.properties");
		List<Action> actionsList = new ArrayList<>();
		for (Map<String, String> action : actionsMap.values()){
			actionsList.add(new Action(action.get("name"),
					action.get("description"),
					Integer.parseInt(action.get("numParams"))));
		}
		return actionsList;
	}
}
