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

package game_engine.control.speech_recognition.voce;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import game_engine.control.PrintMessage;

import java.util.*;
import java.io.*;
import java.net.URL;


/**The interface is originally based on the Sphinx4 project at CMU.
 * It servers to handle the microphone input from the system
 * and converts voice input to strings 
 * @author Yancheng Zeng 
 * Note: This code is slightly refactored version of the SpeechInterface class
 * in the Voce Library by Taylor Street 
 */
public class SpeechRecognizer implements Runnable{
	
	private final String RECOGNIZER = "recognizer";
	private final String MICROPHONE = "microphone";
	private final String GRAM_VERSION = "jsgfGrammar";
	private final String GRAM_NAME = "grammarName";
	private final String GRAM_LOC = "grammarLocation";
	private final String RECOGNITION_THREAD = "Recognition thread";
	
	private Recognizer mRecognizer;
	private Microphone mMicrophone;
	private volatile Thread mRecognitionThread;
	private boolean mRecognitionThreadEnabled = false;
	private LinkedList<String> mRecognizedStringQueue;
	
	
	/**
	 * Constructor for SpeechRecognizer.
	 * @param configFilename String
	 * @param grammarPath String
	 * @param grammarName String
	 */
	public SpeechRecognizer(String configFilename, String grammarPath, String grammarName){
		try{
			URL configURL = new File(configFilename).toURI().toURL();
			ConfigurationManager cm = new ConfigurationManager(configURL);

			mRecognizer = (Recognizer) cm.lookup(RECOGNIZER);
			mMicrophone = (Microphone) cm.lookup(MICROPHONE);
			cm.lookup(GRAM_VERSION);
			cm.setProperty(GRAM_VERSION, GRAM_LOC, grammarPath);
			cm.setProperty(GRAM_VERSION, GRAM_NAME, grammarName);
			mRecognizer.allocate();
			mRecognizedStringQueue = new LinkedList<String>();
		} catch (IOException e) {
			System.out.println(PrintMessage.RECOGNIZER_LOAD_ERROR.getVal());
		} catch (PropertyException e) {
			System.out.println(PrintMessage.RECOGNIZER_CONFIGURE_ERROR.getVal());
		} catch (InstantiationException e) {
			System.out.println(PrintMessage.RECOGNIZER_CREATE_ERROR.getVal());
		}
	}


	/**
	 * Method run.
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		while (mRecognitionThreadEnabled){
			if (!mMicrophone.isRecording()){
				System.out.println(PrintMessage.MICROPHONE_DISABLE.getVal());
			} else {
				Result result = mRecognizer.recognize();
				if (result != null){
					String s = result.getBestFinalResultNoFiller();
					if (!s.equals("")){
						mRecognizedStringQueue.addLast(s);
					}
				}
			}
		}
	}


	/**
	 * Method getQueueSize.
	 * @return int
	 */
	public int getQueueSize(){
		return mRecognizedStringQueue.size();
	}


	/**
	 * Method popString.
	 * @return String
	 */
	public String popString(){
		if (getQueueSize() > 0){
			return mRecognizedStringQueue.removeFirst();
		} else {
			return "";
		}
	}


	/**
	 * Method setEnabled.
	 * @param enable boolean
	 */
	public void setEnabled(boolean enable){
		if (enable){
			boolean success = mMicrophone.startRecording();
			if (!success){
				System.out.println(PrintMessage.MISCROPHONE_ERROR.getVal());
				return;
			} else {
				if (mRecognitionThread != null){
					System.out.println(PrintMessage.RECOGNITION_THREAD_ERROR.getVal());
				}

				mRecognitionThread = new Thread(this, RECOGNITION_THREAD);

				// Start running the recognition thread.
				mRecognitionThreadEnabled = true;
				mRecognitionThread.start();
			}
		} else {
			mMicrophone.stopRecording();

			//Indirectly stop the recognition thread
			mRecognitionThreadEnabled = false;

			// Wait for the thread to die before proceeding.
			while (mRecognitionThread.isAlive())
			{
				try{
					Thread.sleep(100);
				} catch (InterruptedException exception) {
					System.out.println(PrintMessage.THREAD_ERROR.getVal());
				}
			}
			mRecognitionThread = null;
			mMicrophone.clear();
			mRecognizedStringQueue.clear();
		}
	}

	/**
	 * Method isEnabled.
	 * @return boolean
	 */
	public boolean isEnabled(){
		return mMicrophone.isRecording();
	}

	public void destroy(){
		setEnabled(false);
		mRecognizer.deallocate();
	}
}
