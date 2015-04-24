package authoring.panes.rightPane;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import authoring.dialogs.ObjectiveDialog;

public class ObjectivePane extends EditingPane {

	private int numObjectives;
	private ObservableList<Text> mObjectives = FXCollections.observableArrayList();
 	
	public ObjectivePane(Scene scene, RightPane parent) {
		super(scene, parent);
		initButtons();
	}

	private void initButtons() {
		ListView<Text> list = new ListView<>(mObjectives);
		list.setOnMouseClicked(e -> {
			new ObjectiveDialog(this);
		});
		this.getChildren().add(list);
		
		Button b = new Button("Add objective");
		b.setOnMouseClicked(e -> {
			createObjective(numObjectives++);
		});
		this.getChildren().add(b);
	}

	public void createObjective(int index) {
		
		mObjectives.add(new Text("objective_" + index));
	}

	private HBox createLabelField(String label) {
		HBox h = new HBox(5);
		h.getChildren().add(new Text(label));
		h.getChildren().add(new TextField());
		h.setAlignment(Pos.CENTER);
		return h;
	}

	public List<Text> getObjectives() {
		return mObjectives;
	}
	
}
