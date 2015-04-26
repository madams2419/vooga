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

import game_engine.control.PrintMessage;


/**
 * The interface is originally based on the Sphinx4 project at CMU.
 * It servers to manage the speechrecognizer and processes user voice input
 * @author Yancheng Zeng 
 * Note: This code is the refactored and modified version of the SpeechInterface class
 * in the Voce Library by Taylor Street 
 */
public class SpeechInterface {
	
	private static final String CONFIG_PATH = "lib/voce.config.xml";
	private static final String LIB_PATH = "lib/gram";
	
	private static SpeechRecognizer mRecognizer;

	/**
	 * Method init.
	 * @param grammarName String
	 */
	public void init(String grammarName){

			System.out.println(PrintMessage.INITIALIZE_RECOGNIZER.getVal());
			mRecognizer = new SpeechRecognizer(CONFIG_PATH, LIB_PATH, grammarName);
			setRecognizerEnabled(true);
			System.out.println(PrintMessage.INITIALIZE_COMPLELE.getVal());
	}

	public void destroy(){
		if (mRecognizer != null){
			mRecognizer.destroy();
		}
		System.out.println(PrintMessage.SHUTDOWN_RECOGNIZER.getVal());
	}


	/**
	 * Method getRecognizerQueueSize.
	 * @return int
	 */
	public int getRecognizerQueueSize(){
		if (mRecognizer == null){
			printWarning();
			return 0;
		}
		return mRecognizer.getQueueSize();
	}

	/**
	 * Method popRecognizedString.
	 * @return String
	 */
	public String popRecognizedString(){
		if (mRecognizer == null)
		{
			printWarning();
			return "";
		}
		return mRecognizer.popString();
	}

	/**
	 * Method setRecognizerEnabled.
	 * @param enable boolean
	 */
	public void setRecognizerEnabled(boolean enable){
		if (null == mRecognizer)
		{
			printWarning();
			return;
		}
		mRecognizer.setEnabled(enable);
	}

	/**
	 * Method isRecognizerEnabled.
	 * @return boolean
	 */
	public boolean isRecognizerEnabled(){
		if (mRecognizer == null)
		{
			printWarning();
			return false;
		}	
		return mRecognizer.isEnabled();
	}
	
	private void printWarning(){
		System.out.println(PrintMessage.RECOGNIZER_ERROR.getVal());
	}
}
