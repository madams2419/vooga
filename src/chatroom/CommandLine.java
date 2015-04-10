package chatroom;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CommandLine {
	TextField commandLine;

	public CommandLine(double height) {
		commandLine = new TextField();
		commandLine.setPrefHeight(height);
	}

	public TextField getCommandLine() {
		return commandLine;
	}
	
	public void setCommandHandler(EventHandler<KeyEvent> handler){
		commandLine.setOnKeyPressed(handler);
	}
	
	//returns string and clears command line
	public String getText() {
		String parse = commandLine.getText();
		commandLine.clear();
		return parse;
	}
}
