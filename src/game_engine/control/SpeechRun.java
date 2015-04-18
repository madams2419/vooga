package game_engine.control;

import game_engine.control.speech_recognition.SpeechRecognition;

public class SpeechRun implements Runnable{
	
	private SpeechRecognition mySpeech;
	
	public SpeechRun(SpeechRecognition speech){
		mySpeech = speech;
	}
	
	@Override
	public void run() {
		mySpeech.activateSpeech();
		System.out.println("Ready");
	}

}
