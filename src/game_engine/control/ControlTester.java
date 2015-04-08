package game_engine.control;

import java.util.HashMap;
import java.util.Map;

import game_engine.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlTester extends Application{
	static Map<String, IBehavior> behaviorPool = new HashMap<>();
	static int track = 10;
	static String printout = "Tracking Number is: ";
	static ControlsManager cManager = new ControlsManager();
	static Text text = new Text(100, 200, printout + track);
	
	public static void addTrack(){
		track++;
		System.out.println("add " + printout + track);
	}

	public static void subTrack(){
		track--;
		System.out.println("sub " + printout + track);
	}


	public static IBehavior selectBehavior(String key){
		if(behaviorPool.containsKey(key)){
			return behaviorPool.get(key);
		} else {
			System.out.println("Key does not exist");
			return null;
		}
	}

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		s.setTitle("Control Demo");
		Group myGroup = new Group();
		Scene scene = new Scene(myGroup, 400, 400, Color.WHITE);
		myGroup.getChildren().add(text);
		text.setFont(new Font(20));
		s.setScene(scene);
		keyManipulation();
		scene.setOnKeyPressed(e -> handleKeyInput(e));
		s.show();
	}
	
	private void keyManipulation(){
		IBehavior add = new AddBehavior();
		IBehavior sub = new SubtractBehavior();
		behaviorPool.put("Add", add);
		behaviorPool.put("Sub", sub);
		cManager.addBehavior("Up", "Add");
		cManager.addBehavior("Down", "Sub");
	}


	private void handleKeyInput (KeyEvent e) {
		KeyCode keyCode = e.getCode();
		cManager.executeBehavior(keyCode);
	}
	
	public static void updateText(){
		text.setText(printout + track);
	}
}
