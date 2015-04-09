package authoring.userInterface;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import authoring.XMLBuilder;
import authoring.util.FrontEndUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/***
 * 
 * @author daniel
 *
 */
public class TopPane extends HBox {

	TopPane() {
		this.getStylesheets().add("styles/top_pane.css");
	}

	void addButtons(Map<String, EventHandler<Event>> labels_listeners) {
		GridPane grid = new GridPane();

		List<Button> mButtons = (List<Button>) labels_listeners.entrySet()
				.stream().map(FrontEndUtils::makeButton)
				.collect(Collectors.toList());

		for (int i = 0, j = 0; i < mButtons.size(); i++) {
			grid.add(mButtons.get(i), (i % 4) + 1, j + 1);
			j = i / 4;
			System.out.printf("Added %s at %d,%d%n", mButtons.get(i).getText(),
					i % 4, j);
		}
		this.getChildren().add(grid);

		Button c = new Button("Output xml");
		c.setOnAction(e -> {
			XMLBuilder.getInstance("game").streamFile("lib/test.xml",
					XMLBuilder.getInstance("game").getRoot());
		});
		this.getChildren().add(c);
	}

}
