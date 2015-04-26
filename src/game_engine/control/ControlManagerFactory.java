package game_engine.control;

/**
 * This class serves navigate through different types of 
 * control managers used in the game.
 * @author Yancheng Zeng
 */
public class ControlManagerFactory {
	private SceneControlManager mySceneControlManager;
	private VoiceControlManager myVoiceControlManager;
	
	public ControlManagerFactory(){
		mySceneControlManager = new SceneControlManager();
		myVoiceControlManager = new VoiceControlManager();
	}
	
	/**
	 * Method getControlManager.
	 * @param select String
	 * @return ControlManager
	 */
	public ControlManager getControlManager(String select){
		switch(select){
			case ControlConstants.KEYBOARD:
				return mySceneControlManager;
			case ControlConstants.MOUSE:
				return mySceneControlManager;
			case ControlConstants.VOICE_CONTROL:
				return myVoiceControlManager;
			default:
				return mySceneControlManager;
		}
	}
}
