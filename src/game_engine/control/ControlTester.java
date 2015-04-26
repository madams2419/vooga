package game_engine.control;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;
import game_engine.behaviors.MultipleBehaviors;
import game_engine.control.speech_recognition.SpeechRecognition;

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
	static ControlManager cManager = new SceneControlManager();
	static Text text1 = new Text(100, 200, printout + track1);
	static Text text2 = new Text(100, 280, "Let's "+action);
	
	public static void addTrack(){
		track1++;
		System.out.println("add " + printout + track1);
		action = "add";
	}

	public static void subTrack(){
		track1--;
		System.out.println("sub " + printout + track1);
		action = "subtract";
	}

	public static void mulTrack(){
		track1*=2;
		System.out.println("mul " + printout + track1);
		action = "multiply";
	}
	
	public static void divTrack(){
		track1/=2;
		System.out.println("div " + printout + track1);
		action = "divide";
	}

//	public static IAction selectBehavior(String key){
//		if(behaviorPool.containsKey(key)){
//			return behaviorPool.get(key);
//		} else {
//			System.out.println("Key does not exist");
//			return null;
//		}
//	}

//	public static void main(String[] args){
//		launch(args);
//	}

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
		scene.setOnKeyPressed(e -> ((SceneControlManager) cManager).handleEvent(e));
		scene.setOnKeyReleased(e -> ((SceneControlManager) cManager).handleEvent(e));
		s.show();
	}
	
	private void launchVoiceControl(){
		SpeechRecognition speech = new SpeechRecognition();
		Thread myThread = new Thread(new Runnable(){
			@Override
			public void run() {
				speech.activateSpeech();
			}});
		myThread.start();
	}
	
	private void keyManipulation(){
		IAction add = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.addTrack();
				ControlTester.updateText();
			}
		};
		IAction sub = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.subTrack();
				ControlTester.updateText();
			}
		};
		IAction mul = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.mulTrack();
				ControlTester.updateText();
			}
		};
		IAction div = new IAction(){
			@Override
			public void execute(String... params) {
				ControlTester.divTrack();
				ControlTester.updateText();
			}
		};
		
		MultipleBehaviors map1 = new MultipleBehaviors();
		map1.addBehavior(() -> add.execute(new String[3]));
		//map1.addBehavior(() -> mul.execute(new String[3]));
		MultipleBehaviors map2 = new MultipleBehaviors();
		map2.addBehavior(() -> sub.execute(new String[3]));
		//map2.addBehavior(() -> div.execute(new String[3]));
		Map<KeyCode, IBehavior> pressMap = new HashMap<>();
		pressMap.put(KeyCode.UP, map1);
		pressMap.put(KeyCode.DOWN, map2);
		Map<KeyCode, IBehavior> releaseMap = new HashMap<>();
        releaseMap.put(KeyCode.UP, null);
        releaseMap.put(KeyCode.DOWN, null);
		SceneControlFactory sceneCF = new SceneControlFactory((SceneControlManager) cManager);
		Control c1 = new KeyControl(pressMap, releaseMap, null, sceneCF);
		cManager.addControl(c1);    
 	}

	public static void updateText(){
		text1.setText(printout + track1);
		text2.setText("Let's " + action);
	}
}
