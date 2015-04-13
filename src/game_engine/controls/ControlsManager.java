package game_engine.controls;

import game_engine.behaviors.IAction;

import java.util.*;

import javafx.scene.input.*;

public class ControlsManager {
    private Controls myActiveControl;
    private List<Controls> myInputControls;

    public ControlsManager() {
	myInputControls = new ArrayList<>();
    }

    public void addControl(Controls newControl) {
	myInputControls.add(newControl);
    }

    public void setActiveControl(int index) {
	myActiveControl = myInputControls.get(index);
    }

    public IAction getSetActiveControlBehavior() {
	IAction setActiveControl = (params) -> {
	    setActiveControl(Integer.parseInt(params[0]));
	};
	return setActiveControl;
    }

    public void handleKeyEvent(KeyEvent input) {
	myActiveControl.handleKeyEvent(input);
    }
    
    public void update(double time) {
	myActiveControl.update(time);
    }
}