package authoring.panes.rightPane;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import authoring.dataEditors.Sprite;
import authoring.dialogs.ObjectiveDialog;

public class ObjectivePane extends EditingPane {

	private int numObjectives;
	private ObservableList<Text> mObjectives = FXCollections
			.observableArrayList();
	private Map<Integer, ObjectiveDialog> mDialogs = new HashMap<>();
	private ObjectiveDialog currentDialog;
	private List<Node> components = new LinkedList<>();

	public ObjectivePane(Scene scene, RightPane parent) {
		super(scene, parent);
		initComponents();
	}

	private void initComponents() {
		ListView<Text> list = new ListView<>(mObjectives);
		list.setOnMouseClicked(e -> {
			if (!mDialogs.containsKey(list.getSelectionModel()
					.getSelectedIndex()))
				mDialogs.put(list.getSelectionModel().getSelectedIndex(),
						currentDialog = new ObjectiveDialog(this, list
								.getSelectionModel().getSelectedIndex()));
			else{
				currentDialog = mDialogs.get(list.getSelectionModel()
					.getSelectedIndex());
				currentDialog.showBox();
			}
		});
		this.getChildren().add(list);

		Button b = new Button("Add objective");
		b.setOnMouseClicked(e -> {
			createObjective(numObjectives++);
		});
		this.getChildren().add(b);
		components.add(b);
		components.add(list);
	}

	public void createObjective(int index) {
		mObjectives.add(new Text("objective_" + index));
	}

	public List<Text> getObjectives() {
		return mObjectives;
	}
	
	public void returnFromSpriteSelection(Sprite s){
		currentDialog.setSprite(s);
		myParent.getParent().setSpriteWaiting(false);
		currentDialog.showBox();
	}
	
	public List<Node> getComponents() {
		return components;
	}

}
