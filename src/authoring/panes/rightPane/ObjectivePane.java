package authoring.panes.rightPane;

import javax.swing.GroupLayout.Alignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ObjectivePane extends EditingPane {

	private final String[] categories = { "On complete", "On failed", "blabla" };
	private final String[] categories_name = { "onComplete", "onFailed" };
	private final String[] behaviours = { "targetType", "targetIndex", "name",
			"parameters" };

	private int numObjectives;
	private ObservableList<VBox> mObjectives = FXCollections.observableArrayList();
 	
	public ObjectivePane(Scene scene, RightPane parent) {
		super(scene, parent);
		initButtons();
	}

	private void initButtons() {
		ListView<VBox> list = new ListView<VBox>(mObjectives);
		this.getChildren().add(list);
		
		Button b = new Button("Add objective");
		b.setOnMouseClicked(e -> {
			createFields(numObjectives++);
		});
		this.getChildren().add(b);
	}

	public void createFields(int index) {
		VBox v = new VBox(5);
		v.setAlignment(Pos.CENTER);
		v.getChildren().add(new Text("objective_" + index));
		for (String s : behaviours) 
			v.getChildren().add(createLabelField(s));
		mObjectives.add(v);
		mObjectives.add(new VBox(5));
	}

	private HBox createLabelField(String label) {
		HBox h = new HBox(5);
		h.getChildren().add(new Text(label));
		h.getChildren().add(new TextField());
		h.setAlignment(Pos.CENTER);
		return h;
	}

}
