package chatroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class CommandHistory {
	private ListView<String> commandList;
	private ObservableList<String> commandItems;

	public CommandHistory(double width) {
		commandList = new ListView<String>();
		commandItems = FXCollections.observableArrayList("Chat History");
		commandList.setItems(commandItems);
		commandList.setMaxWidth(width);
	}

	public void addHistoryText(String text) {
		commandItems.add(">> " + text);
		commandList.setItems(commandItems);
	}

	public void resetHistory() {
		commandItems = FXCollections.observableArrayList("Chat History");
		commandList.setItems(commandItems);
	}

	public ListView<String> getCommandHistory() {
		return commandList;
	}

	public ListView<String> getCommandList() {
		return commandList;
	}

	public void setCommandHandler(EventHandler<MouseEvent> handler) {
		commandList.setOnMouseClicked(handler);
	}

}
