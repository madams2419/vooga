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

import java.util.*;
import java.io.*;
import java.net.URL;

/// Handles all speech recognition (i.e. speech-to-text) functions.  Uses 
/// a separate thread for recognition.  Maintains an internal queue of 
/// recognized strings.
public class SpeechRecognizer implements Runnable{
	private Recognizer mRecognizer;
	private Microphone mMicrophone;
	private volatile Thread mRecognitionThread;
	private boolean mRecognitionThreadEnabled = false;
	private LinkedList<String> mRecognizedStringQueue;
	
	
	public SpeechRecognizer(String configFilename, String grammarPath, String grammarName){
		try{
			URL configURL = new File(configFilename).toURI().toURL();
			ConfigurationManager cm = new ConfigurationManager(configURL);

			mRecognizer = (Recognizer) cm.lookup("recognizer");
			mMicrophone = (Microphone) cm.lookup("microphone");
			cm.lookup("jsgfGrammar");
			cm.setProperty("jsgfGrammar", "grammarLocation", grammarPath);
			cm.setProperty("jsgfGrammar", "grammarName", grammarName);
			mRecognizer.allocate();
			mRecognizedStringQueue = new LinkedList<String>();
		} catch (IOException e) {
			System.out.println("Cannot load speech recognizer: ");
		} catch (PropertyException e) {
			System.out.println("Cannot configure speech recognizer: ");
		} catch (InstantiationException e) {
			System.out.println("Cannot create speech recognizer: ");
		}
	}

	/// Contains the main processing to be done by the recognition 
	/// thread.  Called indirectly after 'start' is called.
	public void run(){
		while (mRecognitionThreadEnabled == true){
			if (!mMicrophone.isRecording()){
				System.out.println("Microphone is disable");
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

	/// Returns the number of recognized strings currently in the 
	/// recognized string queue.
	public int getQueueSize(){
		return mRecognizedStringQueue.size();
	}

	/// Returns and removes the oldest recognized string from the 
	/// recognized string queue.  Returns an empty string if the 
	/// queue is empty.
	public String popString(){
		if (getQueueSize() > 0){
			return mRecognizedStringQueue.removeFirst();
		} else {
			return "";
		}
	}

	/// Enables and disables the speech recognizer.  Starts and stops the 
	/// speech recognition thread.
	public void setEnabled(boolean enable){
		if (enable){
			boolean success = mMicrophone.startRecording();
			if (!success){
				System.out.println("Cannot initialize microphone");
				return;
			} else {
				if (mRecognitionThread != null){
					System.out.println("New recognition thread is created before finishing the previous one");
				}

				mRecognitionThread = new Thread(this, "Recognition thread");

				// Start running the recognition thread.
				mRecognitionThreadEnabled = true;
				mRecognitionThread.start();
			}
		} else {
			mMicrophone.stopRecording();

			// The following line indirectly stops the recognition thread 
			// from running.  The next time the recognition thread checks 
			// this variable, it will stop running.
			mRecognitionThreadEnabled = false;

			// Wait for the thread to die before proceeding.
			while (mRecognitionThread.isAlive())
			{
				try{
					// Have the main thread sleep for a bit...
					Thread.sleep(100);
				} catch (InterruptedException exception) {
					System.out.println("Thread Interruption");
				}
			}

			mRecognitionThread = null;
			mMicrophone.clear();
			mRecognizedStringQueue.clear();
		}
	}

	/// Returns true if the recognizer is currently enabled.
	public boolean isEnabled(){
		return mMicrophone.isRecording();
	}

	/// Deallocates speech recognizer.
	public void destroy(){
		// This function call will shut down everything, including the 
		// recognition thread.
		setEnabled(false);

		// It should now be safe to deallocate the recognizer.
		mRecognizer.deallocate();
	}
}
