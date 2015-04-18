package game_engine.control.speech_recognition;

import game_engine.control.ControlTester;

public class SpeechRecognition {
	public void activateSpeech(){
		game_engine.control.speech_recognition.voce.SpeechInterface.init("../../../lib", false, true, 
				"./grammar", "digits");

			System.out.println("This is a speech recognition test. " 
				+ "Speak digits from 0-9 into the microphone. " 
				+ "Speak 'quit' to quit.");

			boolean quit = false;
			while (!quit)
			{
				// Normally, applications would do application-specific things 
				// here.  For this sample, we'll just sleep for a little bit.
				try
				{
					Thread.sleep(200);
				}
				catch (InterruptedException e)
				{
				}
				
				
				//System.out.println("Please BS");
				while (game_engine.control.speech_recognition.voce.SpeechInterface.getRecognizerQueueSize() > 0)
				{
					String s = game_engine.control.speech_recognition.voce.SpeechInterface.popRecognizedString();

					// Check if the string contains 'quit'.
					if (-1 != s.indexOf("quit"))
					{
						quit = true;
					}
					
					if(s.equals("walk") || s.equals("add")){
						ControlTester.addTrack();
						ControlTester.updateText();
					}
					
					if(s.equals("multiply")){
						ControlTester.mulTrack();
						ControlTester.updateText();
					}
					
					if(s.equals("divide")){
						ControlTester.divTrack();
						ControlTester.updateText();
					}
					
					if(s.equals("jump") || s.equals("subtract")){
						ControlTester.subTrack();
						ControlTester.updateText();
					}
					System.out.println("You said something: " + s);
					//voce.SpeechInterface.synthesize(s);
				}
			}
			System.out.println("About to close speech recognition");
			game_engine.control.speech_recognition.voce.SpeechInterface.destroy();
			System.exit(0);
	}
}
