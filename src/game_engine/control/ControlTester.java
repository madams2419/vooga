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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlTester extends Application{
	static Map<String, IAction> behaviorPool = new HashMap<>();
	static int track1 = 10;
	static String action = "do something";
	static String printout = "Tracking Number is: ";
	static SceneControlManager cManager = new SceneControlManager();
	static Text text1 = new Text(100, 200, printout + track1);
	static Text text2 = new Text(100, 280, "Let's "+action);
	static private int activeControl = 0;
	static private final boolean PRESSED_KEY = true;
	static private final boolean RELEASED_KEY = false;
	
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

	public static IAction selectBehavior(String key){
		if(behaviorPool.containsKey(key)){
			return behaviorPool.get(key);
		} else {
			System.out.println("Key does not exist");
			return null;
		}
	}

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
		scene.setOnKeyPressed(e -> handleKeyInput(e,PRESSED_KEY));
		scene.setOnKeyReleased(e -> handleKeyInput(e,RELEASED_KEY));
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
		IAction add = new AddBehavior();
		IAction sub = new SubtractBehavior();
		IAction mul = new MulBehavior();
		IAction div = new DivBehavior();
		behaviorPool.put("Add", add);
		behaviorPool.put("Sub", sub);
		MultipleBehaviors map1 = new MultipleBehaviors();
		map1.addBehavior(() -> add.execute(new String[3]));
		map1.addBehavior(() -> mul.execute(new String[3]));
		MultipleBehaviors map2 = new MultipleBehaviors();
		map2.addBehavior(() -> sub.execute(new String[3]));
		map2.addBehavior(() -> div.execute(new String[3]));
		Map<KeyCode, IBehavior> pressMap = new HashMap<>();
		pressMap.put(KeyCode.UP, map1);
		pressMap.put(KeyCode.DOWN, map2);
		Map<KeyCode, IBehavior> releaseMap = new HashMap<>();
        releaseMap.put(KeyCode.UP, null);
        releaseMap.put(KeyCode.DOWN, null);
		
		//Map<IAction, String[]> map2 = new LinkedHashMap<IAction, String[]>(){{put(sub, new String[3]); put(div, new String[3]);}};
		//Map<KeyCode, Map<IAction, String[]>> pressMap = new HashMap<KeyCode, Map<IAction, String[]>>(){{  put(KeyCode.UP, map1); put(KeyCode.DOWN, map2);}};
		//Map<KeyCode, Map<IAction, String[]>> releaseMap = new HashMap<KeyCode, Map<IAction, String[]>>(){{  put(KeyCode.DOWN, map2); put(KeyCode.UP, map2);}};
		cManager.addControl(pressMap, releaseMap, null);
		
//		myControl.addBehavior("UP", "Add");
//		myControl.addBehavior("DOWN", "Sub");
	}


	private void handleKeyInput (KeyEvent e, boolean pressed) {
		System.out.println("The event name is "+e.getClass());
		KeyCode keyCode = e.getCode();
		cManager.handleKeyEvent(keyCode, pressed);
	}
	
	public static void updateText(){
		text1.setText(printout + track1);
		text2.setText("Let's " + action);
	}
}
