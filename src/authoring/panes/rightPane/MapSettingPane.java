package authoring.panes.rightPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import authoring.fileBuilders.PhysicsEngine_XML;

/**
 * @author hojeanniechung, Daniel Luker, Natalie
 *
 */
public class MapSettingPane extends EditingPane {

	private static final String UPDATE = "Update";
        private static final String ACCEL_0 = "Gravitational     ";
        private static final String GLOBAL_ACCELERATIONS = "Global Accelerations";
        private static final String DRAG_COEFFICIENT = "Drag coefficient";
        private static final String TYPES = "Types";
        private static final String COMPLEX_PHYSICS_ENGINE = "ComplexPhysicsEngine";
        private static final String SIMPLE_PHYSICS_ENGINE = "SimplePhysicsEngine";
        private static final String PHYSICS_ENGINE = "Physics Engine";
        private ComboBox<String> myType;
	private TextField myDragCoefficient;
	private List<TextField> myGlobalAccelerations = new ArrayList<>();

	public MapSettingPane(Scene myScene, RightPane parent) {
		super(myScene, parent);

		this.getChildren().add(new Label(PHYSICS_ENGINE));

		ObservableList<String> types = FXCollections.observableArrayList();
		types.addAll(SIMPLE_PHYSICS_ENGINE, COMPLEX_PHYSICS_ENGINE);
		addComboBox(TYPES, types);

		myDragCoefficient = addFields(DRAG_COEFFICIENT);

		this.getChildren().add(new Label(GLOBAL_ACCELERATIONS));

		myGlobalAccelerations.add(addFields(ACCEL_0));

		Button c = new Button(UPDATE);
		c.setOnAction(e -> addPhysicsEngine());
		this.getChildren().add(c);
	}

	private void addComboBox(String label, ObservableList<String> types) {
		HBox h = new HBox(5);
		h.getChildren().addAll(new Text(label),
				myType = new ComboBox<String>(types));
		myType.setValue(COMPLEX_PHYSICS_ENGINE);
		this.getChildren().add(h);
	}

	private TextField addFields(String label) {
	        HBox h = new HBox(5);
		TextField ret = new TextField();
		h.getChildren().addAll(new Label(label), ret);
		this.getChildren().addAll(h);
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