// This entire file is part of my masterpiece.
// Yancheng Zeng

package game_engine.control;

import game_engine.behaviors.IAction;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.scene.input.*;

/**
 * This class defines the functionalities of the keycontrol object and
 * extends the scenecontrol class
 * @author Yancheng Zeng
 */
public class MouseControl extends SceneControl{
	private List<IAction> myClickedList;
	private List<IAction> myMovedList;
	private List<IAction> myReleasedList;
	
	public MouseControl(List<IAction> clickList, List<IAction> dragList, List<IAction> releaseList){
		myClickedList = clickList;
		myMovedList = dragList;
		myReleasedList = releaseList;
	}
	
	@Override
	public void executeEvent(InputEvent e){
		executeMouseEvent((MouseEvent) e);
	}
	
	public void executeMouseEvent(MouseEvent e){
		if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED) || e.getEventType().equals(MouseEvent.MOUSE_MOVED) || e.getEventType().equals(MouseEvent.MOUSE_RELEASED)){
			SceneControlFactory.getMouseEventType(e).apply(e);
		}
	}
	
	private void executeList(Consumer<IAction> command, List<IAction> list){
		list.forEach(command);
	}
	
	private void performAction(IAction action, String... params){
		action.execute(params);
	}
	
	public Function<MouseEvent, Integer> executeClicked(){
		Function<MouseEvent, Integer> clickedFunction = t -> {
			String[] params = {String.valueOf(t.getX()), String.valueOf(t.getY())};
			executeList(e -> performAction(e, params), myClickedList);
			return ControlConstants.EXECUTION_RESULT;
		};
		return clickedFunction;
	}

	public Function<MouseEvent, Integer> executeReleased(){
		Function<MouseEvent, Integer> releasedFunction = t -> {
			String[] params = {String.valueOf(t.getX()), String.valueOf(t.getY())};
			executeList(e -> performAction(e, params), myReleasedList);
			return ControlConstants.EXECUTION_RESULT;
		};
		return releasedFunction;
	}

	public Function<MouseEvent, Integer> executeMoved(){
		Function<MouseEvent, Integer> movedFunction = t -> {
			String[] params = {String.valueOf(t.getX()), String.valueOf(t.getY())};
			executeList(e -> performAction(e, params), myMovedList);
			return ControlConstants.EXECUTION_RESULT;
		};
		return movedFunction;
	}

}
