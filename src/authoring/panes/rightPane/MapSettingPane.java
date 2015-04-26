package authoring.panes.rightPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import authoring.fileBuilders.PhysicsEngine_XML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author hojeanniechung & Daniel Luker
 *
 */
public class MapSettingPane extends EditingPane {

	private ComboBox<String> myType;
	private TextField myDragCoefficient;
	private List<TextField> myGlobalAccelerations = new ArrayList<>();

	public MapSettingPane(Scene myScene, RightPane parent) {
		// TODO Auto-generated constructor stub
		super(myScene, parent);

		this.getChildren().add(new Label("Physics Engine"));

		ObservableList<String> types = FXCollections.observableArrayList();
		types.addAll("SimplePhysicsEngine", "ComplexPhysicsEngine");
		addComboBox("Types", types);

		myDragCoefficient = addFields("drag_coefficient");

		this.getChildren().add(new Label("Global Accelerations"));

		myGlobalAccelerations.add(addFields("accel_0"));

		Button c = new Button("Update");
		c.setOnAction(e -> addPhysicsEngine());
		this.getChildren().add(c);
	}

	private void addComboBox(String label, ObservableList<String> types) {
		HBox h = new HBox(5);
		h.getChildren().addAll(new Text(label),
				myType = new ComboBox<String>(types));
		this.getChildren().add(h);
	}

	private TextField addFields(String label) {
		TextField ret;
		HBox toAdd = new HBox(5);
		toAdd.getChildren().addAll(new Label(label), ret = new TextField());
		this.getChildren().add(toAdd);
		return ret;
	}

	private String[] getAccels() {
		return myGlobalAccelerations.stream().map(textField -> {
			return textField.getText();
		}).collect(Collectors.toList()).toArray(new String[0]);
	}

	private void addPhysicsEngine() {
		myParent.getParent()
				.getCenterPane()
				.getActiveTab()
				.addPhysics(
						new PhysicsEngine_XML(myType.getSelectionModel()
								.getSelectedItem(),
								myDragCoefficient.getText(), getAccels()));
	}
}