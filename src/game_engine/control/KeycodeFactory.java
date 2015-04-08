package game_engine.control;

import javafx.scene.input.KeyCode;

public class KeycodeFactory {
	public KeycodeFactory(){
		
	}
	
	public static KeyCode generateKeyCode(String index){
		switch(index){
		case "Up":
			return KeyCode.UP;
		case "Down":
			return KeyCode.DOWN;
		case "Left":
			return KeyCode.LEFT;
		case "Right":
			return KeyCode.RIGHT;
		case "W":
			return KeyCode.W;
		default:
			return KeyCode.UP;
		}
	}
}
