package game_engine.control;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;
import game_engine.behaviors.MultipleBehaviors;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlTester extends Application{
	static int track1 = 10;
	static String action = "do something";
	static String printout = "Tracking Number is: ";
	static Text text1 = new Text(100, 200, printout + track1);
	static Text text2 = new Text(100, 280, "Let's "+action);
	static ControlManagerFactory cFactory = new ControlManagerFactory();
	
	public static void addTrack(){
		track1++;
		System.out.println("add " + printout + track1);
		action = "add";
		updateText();
	}

	public static void subTrack(){
		track1--;
		System.out.println("sub " + printout + track1);
		action = "subtract";
		updateText();
	}

	public static void mulTrack(){
		track1*=2;
		System.out.println("mul " + printout + track1);
		action = "multiply";
		updateText();
	}
	
	public static void divTrack(){
		track1/=2;
		System.out.println("div " + printout + track1);
		action = "divide";
		updateText();
	}


	@Override
	public void start(Stage s) throws Exception {
		s.setTitle("Control Demo");
		Group myGroup = new Group();
		Scene scene = new Scene(myGroup, 400, 400, Color.WHITE);
		myGroup.getChildren().add(text1);
		myGroup.getChildren().add(text2);
		text1.setFont(new Font(20));
		text2.setFont(new Font(20));
		s.setScene(scene);
		keyManipulation();
		scene.setOnKeyPressed(e -> cFactory.getControlManager("keyboard").handleEvent(e));
		scene.setOnKeyReleased(e -> cFactory.getControlManager("keyboard").handleEvent(e));
		cFactory.getControlManager("voicecontrol").handleEvent(null);
		s.show();
	}
	

	private void keyManipulation(){
		IAction add = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.addTrack();
			}
		};
		IAction sub = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.subTrack();
			}
		};
		IAction mul = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.mulTrack();
			}
		};
		IAction div = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.divTrack();
			}
		};
		
		MultipleBehaviors map1 = new MultipleBehaviors();
		map1.addBehavior(() -> add.execute(new String[3]));
		MultipleBehaviors map2 = new MultipleBehaviors();
		map2.addBehavior(() -> sub.execute(new String[3]));
		
		Map<KeyCode, IBehavior> pressMap = new HashMap<>();
		pressMap.put(KeyCode.UP, map1);
		pressMap.put(KeyCode.DOWN, map2);
		Map<KeyCode, IBehavior> releaseMap = new HashMap<>();
        releaseMap.put(KeyCode.UP, null);
        releaseMap.put(KeyCode.DOWN, null);
		Control keyC1 = new KeyControl(pressMap, releaseMap, null);
		
		Map<String, IBehavior> voiceMap = new HashMap<>();
		voiceMap.put("add", new IBehavior(){
			@Override
			public void perform() {
				addTrack();
			}});
		
		voiceMap.put("multiply", new IBehavior(){
			@Override
			public void perform() {
				mulTrack();
			}});
		
		voiceMap.put("subtract", new IBehavior(){
			@Override
			public void perform() {
				subTrack();
			}});
		
		voiceMap.put("divide", new IBehavior(){
			@Override
			public void perform() {
				divTrack();
			}});
		
		Control voiceC1 = new VoiceControl(voiceMap);
		
		cFactory.getControlManager("voicecontrol").addControl(voiceC1);
		cFactory.getControlManager("keyboard").addControl(keyC1);    
 	}

	public static void updateText(){
		text1.setText(printout + track1);
		text2.setText("Let's " + action);
	}
}
