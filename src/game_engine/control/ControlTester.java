package game_engine.control;

import java.util.HashMap;
import java.util.Map;

import game_engine.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControlTester extends Application{
	static Map<String, Behavior> behaviorPool = new HashMap<>();
	static int track = 10;
	
	public static void addTrack(){
		track++;
		System.out.println("Track Number is: " + track);
	}
	
	public static void subTrack(){
		track--;
		System.out.println("Track Number is: " + track);
	}
	
	
	public static Behavior selectBehavior(String key){
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
		s.setScene(scene);
		Behavior add = new AddBehavior();
		Behavior sub = new AddBehavior();
		behaviorPool.put("Add", add);
		behaviorPool.put("Sub", sub);
		s.show();
	}
}
