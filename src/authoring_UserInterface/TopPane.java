package authoring_UserInterface;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TopPane extends HBox {

	TopPane() {
//		this.getStylesheets().add("src/styles/top_pane.css");
	}

	void addButtons(Map<String, EventHandler<Event>> labels_listeners) {
//		GridPane grid = new GridPane();
		List<Button> mButtons = (List<Button>) labels_listeners.entrySet().stream()
				.map(FrontEndUtils::makeButton).collect(Collectors.toList());
		this.getChildren().addAll(mButtons);
	}

}
