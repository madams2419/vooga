package game_engine.control;

import game_engine.behaviors.IBehavior;
import game_engine.control.speech_recognition.voce.SpeechInterface;
import game_engine.control.speech_recognition.voce.SpeechRecognizer;

import java.util.*;

/**
 * This class runs the voice control application
 * @author Yancheng Zeng
 */
public class VoiceControl implements Control{
	
	private final String QUIT = "quit";
	private final String GRAM_FILE = "words";
	private final String WORD_START = "You said: ";
	
	private Map<String, IBehavior> mySpeechMap;
	private SpeechInterface mySpeechInterface;
	
	/**
	 * Constructor for VoiceControl.
	 * @param speechMap Map<String,IBehavior>
	 */
	public VoiceControl(Map<String, IBehavior> speechMap){
		mySpeechMap = speechMap;
		mySpeechInterface = new SpeechInterface();
	}
	
	public void executeEvent(){
		initializeRecognizer();
		Thread voiceThread = new Thread(new Runnable(){
			@Override
			public void run() {
				processInput();
			}});
		voiceThread.start();
	}
	
	public void initializeRecognizer(){
		mySpeechInterface.init(GRAM_FILE);
	}
	
	private void processInput(){
		System.out.println(PrintMessage.SPEAK_CUE.getVal());
		boolean quit = false;
		String input = "";
		while(!quit){
			try{
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(PrintMessage.THREAD_ERROR.getVal());
			}
			
			while(mySpeechInterface.getRecognizerQueueSize() > 0){
				input = mySpeechInterface.popRecognizedString();
				
				if(input.contains(QUIT)){
					quit = true;
				}
				
				printInput(input);

				if(mySpeechMap.containsKey(input)){
					mySpeechMap.get(input).perform();
				}
			}
		}
		
		System.out.println(PrintMessage.CLOSE_SPEECH.getVal());
		mySpeechInterface.destroy();
	}
	
	/**
	 * Method printInput.
	 * @param message String
	 */
	private void printInput(String message){
		System.out.println(WORD_START + message);
	}
	
	
}
