package chatroom;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class View {
	private Scene scene;
	private BorderPane root;
	private CommandHistory history;
	private CommandLine commandLine;

	// Command Line Dimensions
	private static final int COMMAND_HEIGHT = 50;
	
	public View() {
		root = new BorderPane();
		history = new CommandHistory(400);
		commandLine = new CommandLine(COMMAND_HEIGHT);
		root.setRight(history.getCommandHistory());
		root.setBottom(commandLine.getCommandLine());
		scene = new Scene(root);
		setCommandLine(parse);
	}

	public CommandLine getCommandLine() {
		return commandLine;
	}

	public CommandHistory getCommandHistory() {
		return history;
	}

	public Scene getScene() {
		return scene;
	}
	
	public CommandHistory getHistory(){
		return history;
	}

	// sets command line
	public void setCommandLine(EventHandler<KeyEvent> handler) {
		commandLine.setCommandHandler(handler);
	}

	public void setCommandHistory(EventHandler<MouseEvent> handler) {
		history.setCommandHandler(handler);
	}
	
	private EventHandler<KeyEvent> parse = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			String parse;
			KeyCode keyCode = event.getCode();
			if (keyCode == KeyCode.ENTER) {
				parse = getCommandLine().getText();
				if (parse.toLowerCase().equals("clear")) {
					getCommandHistory().resetHistory();
				}
				getCommandHistory().addHistoryText(parse);
				System.out.println(parse);
			}

		}
	};
}
