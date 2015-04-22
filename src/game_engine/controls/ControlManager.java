package game_engine.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.InputEvent;

public class ControlManager {
	
	private List<ControlScheme> availableControlSchemes;
	private ControlScheme activeControlScheme;
	
	public ControlManager() {
		availableControlSchemes = new ArrayList<>();
	}
	
	public void addControlScheme(ControlScheme scheme) {
		availableControlSchemes.add(scheme);
	}
	
	public void setActiveControlScheme(int index) {
		activeControlScheme = availableControlSchemes.get(index);
	}
	
	public void handleInput(InputEvent input) {
		activeControlScheme.handleInput(input);
	}
	
	public void update() {
		activeControlScheme.update();
	}
}