package game_engine.controls;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.InputEvent;

public class ControlsManager implements IActor{
	
	private List<ControlScheme> availableControlSchemes;
	private ControlScheme activeControlScheme;
	private Map<String, IAction> actions;
	
	public ControlsManager() {
		availableControlSchemes = new ArrayList<>();
		actions = buildActionMap();
	}
	
	public void addControlScheme(ControlScheme scheme) {
		availableControlSchemes.add(scheme);
	}
	
	public void setActiveControlScheme(int index) {
		activeControlScheme = availableControlSchemes.get(index);
	}
	
	@IActionAnnotation(description = "Changings the active control scheme", numParams = 1, paramDetails = "Control scheme id")
	private IAction setActiveControl = (params) -> {
	    setActiveControlScheme (Integer.parseInt(params[0]));
	};
	
	public void handleInput(InputEvent input) {
		activeControlScheme.handleInput(input);
	}
	
	public void update() {
		activeControlScheme.update();
	}

    @Override
    public IAction getAction (String name) {
        return actions.get(name);
    }
}