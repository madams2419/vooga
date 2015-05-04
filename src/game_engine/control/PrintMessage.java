package game_engine.control;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is the enum class that declares all the printout message constants
 * @author Yancheng Zeng
 */
public enum PrintMessage {
	RECOGNIZER("recognizer"),
	INVALID_INDEX("invalid index"),
	SPEAK_CUE("speak_now"),
	THREAD_ERROR("thread_error"),
	SWITCH_CONTROL("switch_control"),
	CLOSE_SPEECH("close_speech"),
	INITIALIZE_RECOGNIZER("initialize_recognizer"),
	INITIALIZE_COMPLELE("initialize_complete"),
	SHUTDOWN_RECOGNIZER("shutdown_recognizer"),
	RECOGNIZER_ERROR("recognizer_error"),
	RECOGNIZER_LOAD_ERROR("recognizer_load_error"),
	RECOGNIZER_CREATE_ERROR("recognizer_create_error"),
	RECOGNIZER_CONFIGURE_ERROR("recognizer_configure_error"),
	MICROPHONE_DISABLE("microphone_disable"),
	MISCROPHONE_ERROR("microphone_error"),
	RECOGNITION_THREAD_ERROR("recognition_thread_error"),
	INVALID_CONTROL_TYPE("invalid_control_type"),
	KEYBOARD("keyboard");
	private String myString;
	
	/**
	 * Constructor for PrintMessage.
	 * @param printText String
	 */
	private PrintMessage(String printText){
		myString = printText;
	}
	
	/**
	 * Method getVal.
	 * @return String
	 */
	public String getVal(){
		ResourceBundle myStringResources = ResourceBundle.getBundle("Resources.ControlMessage", new Locale("en", "US"));
		return myStringResources.getString(this.myString);
	}
}
