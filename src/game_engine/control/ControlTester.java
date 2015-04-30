package game_engine.control;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IBehavior;
import game_engine.behaviors.MultipleBehaviors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is used to test the different components of the control package
 * @author Yancheng Zeng
 */
public class ControlTester extends Application{
	private final String myTITLE = "CONTROL_DEMO";
	private String myAction = "do something";
	private final String myPrintout = "Tracking Number is: ";
	private final String myLET = "Let's ";
	private final String myADD = "add";
	private final String mySUB = "subtract";
	private final String myMUL = "multiply";
	private final String myDIV = "divide";
	private final int my_text_1_x = 400;
	private final int my_text_1_y = 200;
	private final int my_text_2_x = 400;
	private final int my_text_2_y = 480;
	private final int SCENE_WIDTH = 400;
	private final int SCENE_HEIGHT = 400;
	private final int FONT_SIZE = 80;
	private int myTrackNum = 10;
	private final int CIRCLE_X = 150;
	private final int CIRCLE_Y = 150;
	private final int CIRCLE_R = 30;
	private final Color CIRCLE_C = Color.RED;
	private boolean myDragTag = true;
	private Text my_text1 = new Text(my_text_1_x, my_text_1_y, myPrintout + myTrackNum);
	private Text my_text2 = new Text(my_text_2_x, my_text_2_y, myLET + myAction);
	private Circle myCircle = new Circle(CIRCLE_X, CIRCLE_Y, CIRCLE_R, CIRCLE_C);
	private ControlManagerFactory myControlFactory = new ControlManagerFactory();

	private void addTrack(){
		myTrackNum++;
		myAction = myADD;
		updateText();
	}

	private void subTrack(){
		myTrackNum--;
		myAction = mySUB;
		updateText();
	}

	private void mulTrack(){
		myTrackNum*=2;
		myAction = myMUL;
		updateText();
	}

	private void divTrack(){
		myTrackNum/=2;
		myAction = myDIV;
		updateText();
	}


	/**
	 * Method start.
	 * @param s Stage
	 * @throws Exception
	 */
	@Override
	public void start(Stage s) throws Exception {
		s.setTitle(myTITLE);
		Group myGroup = new Group();
		Scene scene = new Scene(myGroup, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
		my_text1.setFont(new Font(FONT_SIZE));
		my_text2.setFont(new Font(FONT_SIZE));
		myGroup.getChildren().add(myCircle);
		s.setScene(scene);
		
		keyInitialization();
		//mouseInitialization();
		
		myGroup.getChildren().add(my_text1);
		myGroup.getChildren().add(my_text2);
		
		voiceInitialization();
		myControlFactory.getControlManager(ControlConstants.VOICE_CONTROL).handleEvent(null);
		
		scene.setOnKeyPressed(e -> myControlFactory.getControlManager(ControlConstants.KEYBOARD).handleEvent(e));
		scene.setOnKeyReleased(e -> myControlFactory.getControlManager(ControlConstants.KEYBOARD).handleEvent(e));
		//scene.setOnMouseClicked(e -> myControlFactory.getControlManager(ControlConstants.MOUSE).handleEvent(e));
		//scene.setOnMouseMoved(e -> myControlFactory.getControlManager(ControlConstants.MOUSE).handleEvent(e));
		//scene.setOnMouseReleased(e -> myControlFactory.getControlManager(ControlConstants.MOUSE).handleEvent(e));
		s.show();
	}

	private void mouseInitialization(){
		IAction click = new IAction(){
			@Override
			public void execute(String... params) {
				double xVal = Double.parseDouble(params[0]);
				double yVal = Double.parseDouble(params[1]);
				handleClick(xVal, yVal);
			}
		};
		
		IAction move = new IAction(){
			@Override
			public void execute(String... params) {
				double xVal = Double.parseDouble(params[0]);
				double yVal = Double.parseDouble(params[1]);
				handleMove(xVal, yVal);
			}
		};
		
		IAction release = new IAction(){
			@Override
			public void execute(String... params) {
				handleRelease();
			}
		};
		
		List<IAction> clickM = new ArrayList<IAction>(){{add(click);}};
		List<IAction> moveM = new ArrayList<IAction>(){{add(move);}};
		List<IAction> releaseM = new ArrayList<IAction>(){{add(release);}};
		
		Control mouseC = new MouseControl(clickM, moveM, releaseM);
		myControlFactory.getControlManager(ControlConstants.MOUSE).addControl(mouseC);
		
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
		myControlFactory.getControlManager(ControlConstants.KEYBOARD).addControl(keyC1);    
	}

	private void voiceInitialization(){
		Map<String, IBehavior> voiceMap = new HashMap<>();
		voiceMap.put(myADD, new IBehavior(){
			@Override
			public void perform() {
				addTrack();
			}});

		voiceMap.put(myMUL, new IBehavior(){
			@Override
			public void perform() {
				mulTrack();
			}});

		voiceMap.put(mySUB, new IBehavior(){
			@Override
			public void perform() {
				subTrack();
			}});

		voiceMap.put(myDIV, new IBehavior(){
			@Override
			public void perform() {
				divTrack();
			}});

		Control voiceC1 = new VoiceControl(voiceMap);
		myControlFactory.getControlManager(ControlConstants.VOICE_CONTROL).addControl(voiceC1);
	}

	private void updateText(){
		my_text1.setText(myPrintout + myTrackNum);
		my_text2.setText(myLET + myAction);
	}
	
	private boolean boundCheck(double x, double y){
		return (x < myCircle.getCenterX() + myCircle.getRadius()) &&
				(x > myCircle.getCenterX() - myCircle.getRadius()) &&
				(y < myCircle.getCenterY() + myCircle.getRadius()) &&
				(y > myCircle.getCenterY() - myCircle.getRadius());
	}


	
	private void handleClick(double x, double y){
		if(!boundCheck(x, y)){
			myDragTag = true;
		} 
	}



	private void handleMove(double x, double y){
		if(myDragTag && boundCheck(x, y)){
			myCircle.setCenterX(x);
			myCircle.setCenterY(y);
		}
	}

	
	private void handleRelease(){
		myDragTag = false;
	}
}
