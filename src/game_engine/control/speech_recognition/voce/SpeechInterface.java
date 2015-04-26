/*************************************************************************
 *                                                                       *
 * Voce                                                                  *
 * Copyright (C) 2005                                                    *
 * Tyler Streeter  tylerstreeter@gmail.com                               *
 * All rights reserved.                                                  *
 * Web: voce.sourceforge.net                                             *
 *                                                                       *
 * This library is free software; you can redistribute it and/or         *
 * modify it under the terms of EITHER:                                  *
 *   (1) The GNU Lesser General Public License as published by the Free  *
 *       Software Foundation; either version 2.1 of the License, or (at  *
 *       your option) any later version. The text of the GNU Lesser      *
 *       General Public License is included with this library in the     *
 *       file license-LGPL.txt.                                          *
 *   (2) The BSD-style license that is included with this library in     *
 *       the file license-BSD.txt.                                       *
 *                                                                       *
 * This library is distributed in the hope that it will be useful,       *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the files    *
 * license-LGPL.txt and license-BSD.txt for more details.                *
 *                                                                       *
 *************************************************************************/

/// The main package that contains everything in the Voce Java API.
package game_engine.control.speech_recognition.voce;

public class SpeechInterface {
	
	private static final String CONFIG_PATH = "lib/voce.config.xml";
	private static final String LIB_PATH = "lib/gram";
	
	private static SpeechRecognizer mRecognizer;
	/// Initializes Voce.  The 'vocePath' String specifies the path where 
	/// Voce classes and config file can be found.  'initSynthesis' 
	/// and 'initRecognition' enable these capabilities; if you don't 
	/// need one or the other, not initializing it will save load time 
	/// and memory (though the feature will be disabled, of course).  
	/// 'grammarPath' is a relative or absolute path to one or more 
	/// grammar files (all .gram files in 'grammarPath' will automatically 
	/// be searched).  'grammarName' is the name of a specific grammar 
	/// within a .gram file in the 'grammarPath'.  If the 'grammarName' 
	/// is empty, a simple default grammar will be used.
	
	public void init(String grammarName){

			System.out.println("Initialize Recognizer...");
			mRecognizer = new SpeechRecognizer(CONFIG_PATH, LIB_PATH, grammarName);
			setRecognizerEnabled(true);
			System.out.println("Initialization Complete");
	}

	public void destroy(){
		if (mRecognizer != null){
			mRecognizer.destroy();
		}
		System.out.println("Shutdown Recognizer");
	}


	public int getRecognizerQueueSize(){
		if (mRecognizer == null){
			printWarning();
			return 0;
		}
		return mRecognizer.getQueueSize();
	}

	public String popRecognizedString(){
		if (mRecognizer == null)
		{
			printWarning();
			return "";
		}
		return mRecognizer.popString();
	}

	public void setRecognizerEnabled(boolean enable){
		if (null == mRecognizer)
		{
			printWarning();
			return;
		}
		mRecognizer.setEnabled(enable);
	}

	public boolean isRecognizerEnabled(){
		if (mRecognizer == null)
		{
			printWarning();
			return false;
		}	
		return mRecognizer.isEnabled();
	}
	
	private void printWarning(){
		System.out.println("Recognizer Uninitialized");
	}
}
