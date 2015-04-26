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

/**
 * This class is used to test the different components of the control package
 * @author Yancheng Zeng
 */
public class ControlTester extends Application{
	private final String TITLE = "CONTROL_DEMO";
	private String action = "do something";
	private final String printout = "Tracking Number is: ";
	private final String LET = "Let's ";
	private final String ADD = "add";
	private final String SUB = "subtract";
	private final String MUL = "multiply";
	private final String DIV = "divide";
	private final int text_1_x = 100;
	private final int text_1_y = 200;
	private final int text_2_x = 100;
	private final int text_2_y = 280;
	private final int SCENE_WIDTH = 400;
	private final int SCENE_HEIGHT = 400;
	private final int FONT_SIZE = 20;
	private int trackNum = 10;
	private Text text1 = new Text(text_1_x, text_1_y, printout + trackNum);
	private Text text2 = new Text(text_2_x, text_2_y, LET + action);
	private ControlManagerFactory cFactory = new ControlManagerFactory();
	
	private void addTrack(){
		trackNum++;
		action = ADD;
		updateText();
	}

	private void subTrack(){
		trackNum--;
		action = SUB;
		updateText();
	}

	private void mulTrack(){
		trackNum*=2;
		action = MUL;
		updateText();
	}
	
	private void divTrack(){
		trackNum/=2;
		action = DIV;
		updateText();
	}


	/**
	 * Method start.
	 * @param s Stage
	 * @throws Exception
	 */
	@Override
	public void start(Stage s) throws Exception {
		s.setTitle(TITLE);
		Group myGroup = new Group();
		Scene scene = new Scene(myGroup, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
		myGroup.getChildren().add(text1);
		myGroup.getChildren().add(text2);
		text1.setFont(new Font(FONT_SIZE));
		text2.setFont(new Font(FONT_SIZE));
		s.setScene(scene);
		keyInitialization();
		voiceInitialization();
		scene.setOnKeyPressed(e -> cFactory.getControlManager(ControlConstants.KEYBOARD).handleEvent(e));
		scene.setOnKeyReleased(e -> cFactory.getControlManager(ControlConstants.KEYBOARD).handleEvent(e));
		cFactory.getControlManager(ControlConstants.VOICE_CONTROL).handleEvent(null);
		s.show();
	}

	private void keyInitialization(){
		IAction add = new IAction(){
			@Override
			public void execute(String... params) {
				addTrack();
			}
		};
		IAction sub = new IAction(){
			@Override
			public void execute(String... params) {
				subTrack();
			}
		};
		IAction mul = new IAction(){
			@Override
			public void execute(String... params) {
				mulTrack();
			}
		};
		IAction div = new IAction(){
			@Override
			public void execute(String... params) {
				divTrack();
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
		cFactory.getControlManager(ControlConstants.KEYBOARD).addControl(keyC1);    
 	}
	
	private void voiceInitialization(){
		Map<String, IBehavior> voiceMap = new HashMap<>();
		voiceMap.put(ADD, new IBehavior(){
			@Override
			public void perform() {
				addTrack();
			}});
		
		voiceMap.put(MUL, new IBehavior(){
			@Override
			public void perform() {
				mulTrack();
			}});
		
		voiceMap.put(SUB, new IBehavior(){
			@Override
			public void perform() {
				subTrack();
			}});
		
		voiceMap.put(DIV, new IBehavior(){
			@Override
			public void perform() {
				divTrack();
			}});
		
		Control voiceC1 = new VoiceControl(voiceMap);
		cFactory.getControlManager(ControlConstants.VOICE_CONTROL).addControl(voiceC1);
	}

	private void updateText(){
		text1.setText(printout + trackNum);
		text2.setText(LET + action);
	}
}
