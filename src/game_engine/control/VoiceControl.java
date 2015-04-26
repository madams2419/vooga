package game_engine.control;

import game_engine.behaviors.IBehavior;
import game_engine.control.speech_recognition.voce.SpeechInterface;
import game_engine.control.speech_recognition.voce.SpeechRecognizer;

import java.util.*;

public class VoiceControl implements Control{
	
	private final boolean ENABLE_RECOGNIZER = true;
	private final boolean ENABLE_SYNTHESIZER = false;
	private final String QUIT = "quit";
	
	private Map<String, IBehavior> mySpeechMap;
	private SpeechInterface mySpeechInterface;
	
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
		mySpeechInterface.init("digits");
	}
	
	private void processInput(){
		System.out.println("You may speak");
		boolean quit = false;
		String input = "";
		while(!quit){
			try{
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Thread Interruption");
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
		
		System.out.println("About to close speech recognition");
		mySpeechInterface.destroy();
	}
	
	private void printInput(String message){
		System.out.println("You said: " + message);
	}
	
	
}
