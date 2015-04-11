package authoring.userInterface;

import java.util.ArrayList;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * @author hojeanniechung
 *
 */
public class DropdownFactory {

	public ComboBox<String> generateDropdown(Map<String, String> m) {
		ArrayList<String> labels = new ArrayList<String>();
		for (int i = 0; i < m.size(); i++) {
			String label = "label" + i;
			if (m.containsKey(label)) {
				labels.add(m.get(label));
			}
		}
		ObservableList<String> options = FXCollections
				.observableArrayList(labels);
		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(options);
		comboBox.setValue(m.get("default"));
		return comboBox;
	}

}