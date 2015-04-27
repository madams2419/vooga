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
	private final String TITLE = "CONTROL_DEMO";
	private String myAction = "do something";
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
	private int myTrackNum = 10;
	private final int CIRCLE_X = 150;
	private final int CIRCLE_Y = 150;
	private final int CIRCLE_R = 30;
	private final Color CIRCLE_C = Color.RED;
	private boolean dragTag = true;
	private boolean enterTag = false;
	private Text text1 = new Text(text_1_x, text_1_y, printout + myTrackNum);
	private Text text2 = new Text(text_2_x, text_2_y, LET + myAction);
	private Circle myCircle = new Circle(CIRCLE_X, CIRCLE_Y, CIRCLE_R, CIRCLE_C);
	private ControlManagerFactory cFactory = new ControlManagerFactory();

	private void addTrack(){
		myTrackNum++;
		myAction = ADD;
		updateText();
	}

	private void subTrack(){
		myTrackNum--;
		myAction = SUB;
		updateText();
	}

	private void mulTrack(){
		myTrackNum*=2;
		myAction = MUL;
		updateText();
	}

	private void divTrack(){
		myTrackNum/=2;
		myAction = DIV;
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
		text1.setFont(new Font(FONT_SIZE));
		text2.setFont(new Font(FONT_SIZE));
		myGroup.getChildren().add(myCircle);
		s.setScene(scene);
		
		keyInitialization();
		mouseInitialization();
		
		myGroup.getChildren().add(text1);
		myGroup.getChildren().add(text2);
		
		voiceInitialization();
		cFactory.getControlManager(ControlConstants.VOICE_CONTROL).handleEvent(null);
		
		scene.setOnKeyPressed(e -> cFactory.getControlManager(ControlConstants.KEYBOARD).handleEvent(e));
		scene.setOnKeyReleased(e -> cFactory.getControlManager(ControlConstants.KEYBOARD).handleEvent(e));
		scene.setOnMouseClicked(e -> cFactory.getControlManager(ControlConstants.MOUSE).handleEvent(e));
		scene.setOnMouseMoved(e -> cFactory.getControlManager(ControlConstants.MOUSE).handleEvent(e));
		scene.setOnMouseReleased(e -> cFactory.getControlManager(ControlConstants.MOUSE).handleEvent(e));
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
		cFactory.getControlManager(ControlConstants.MOUSE).addControl(mouseC);
		
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
		text1.setText(printout + myTrackNum);
		text2.setText(LET + myAction);
	}
	
	private boolean boundCheck(double x, double y){
		return (x < myCircle.getCenterX() + myCircle.getRadius()) &&
				(x > myCircle.getCenterX() - myCircle.getRadius()) &&
				(y < myCircle.getCenterY() + myCircle.getRadius()) &&
				(y > myCircle.getCenterY() - myCircle.getRadius());
	}


	
	private void handleClick(double x, double y){
		enterTag = true;
		if(!boundCheck(x, y)){
			dragTag = true;
		} 
		System.out.println("MouseClick" + dragTag);
	}



	private void handleMove(double x, double y){
		if(dragTag && boundCheck(x, y)){
			myCircle.setCenterX(x);
			myCircle.setCenterY(y);
			System.out.println("X and Y is " + x + y);
			System.out.println("CX and CY is " + myCircle.getCenterX() + myCircle.getCenterY());
		}
	}

	
	private void handleRelease(){
		dragTag = false;
		enterTag = true;
		System.out.println("DragRelease" + dragTag + enterTag);
	}

	private boolean checkB(MouseEvent e){
		return (e.getX() < myCircle.getCenterX() + myCircle.getRadius()) &&
				(e.getX() > myCircle.getCenterX() - myCircle.getRadius()) &&
				(e.getY() < myCircle.getCenterY() + myCircle.getRadius()) &&
				(e.getY() > myCircle.getCenterY() - myCircle.getRadius());
	}
}
