package game_engine.control;

public class ControlManagerFactory {
	private SceneControlManager mySceneControlManager;
	private VoiceControlManager myVoiceControlManager;
	
	public ControlManagerFactory(){
		mySceneControlManager = new SceneControlManager();
		myVoiceControlManager = new VoiceControlManager();
	}
	
	public ControlManager getControlManager(String select){
		switch(select){
			case "keyboard":
				return mySceneControlManager;
			case "mouse":
				return mySceneControlManager;
			case "voicecontrol":
				return myVoiceControlManager;
			default:
				return mySceneControlManager;
		}
	}
}
