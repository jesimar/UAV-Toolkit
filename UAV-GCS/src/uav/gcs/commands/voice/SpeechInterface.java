/** ***********************************************************************
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
 ************************************************************************ */

package uav.gcs.commands.voice;

public class SpeechInterface {

    private static SpeechSynthesizer mSynthesizer = null;
    private static SpeechRecognizer mRecognizer = null;

    // Initializes Voce.  The 'vocePath' String specifies the path where 
    // Voce classes and config file can be found.  'initSynthesis' 
    // and 'initRecognition' enable these capabilities; if you don't 
    // need one or the other, not initializing it will save load time 
    // and memory (though the feature will be disabled, of course).  
    // 'grammarPath' is a relative or absolute path to one or more 
    // grammar files (all .gram files in 'grammarPath' will automatically 
    // be searched).  'grammarName' is the name of a specific grammar 
    // within a .gram file in the 'grammarPath'.  If the 'grammarName' 
    // is empty, a simple default grammar will be used.
    public static void init(String vocePath, boolean initSynthesis,
            boolean initRecognition, String grammarPath, String grammarName) {
        if (initSynthesis) {
            mSynthesizer = new SpeechSynthesizer("Speech");
        }
        if (initRecognition) {
            if (grammarPath.equals("")) {
                grammarPath = "./";
            } 
            mRecognizer = new SpeechRecognizer(vocePath + "/config.xml", grammarPath, grammarName);
            // Enable the recognizer; this will start the recognition thread.
            setRecognizerEnabled(true);
        }
    }

    public static void destroy() {
        if (mSynthesizer != null) {
            mSynthesizer.destroy();
        }
        if (mRecognizer != null) {
            mRecognizer.destroy();
        }
    }

    // Requests that the given string be synthesized as soon as possible.
    public static void synthesize(String message) {
        if (mSynthesizer != null) {
            mSynthesizer.synthesize(message);
        }
    }

    // Tells the speech synthesizer to stop synthesizing. This cancels all pending messages.
    public static void stopSynthesizing() {
        if (mSynthesizer != null) {
            mSynthesizer.stopSynthesizing();
        }
    }

    // Returns the number of recognized strings currently in the recognizer's queue.
    public static int getRecognizerQueueSize() {
        if (mRecognizer == null) {
            return 0;
        }
        return mRecognizer.getQueueSize();
    }

    // Returns and removes the oldest recognized string from the recognizer's queue.
    public static String popRecognizedString() {
        if (mRecognizer == null) {
            return "";
        }
        return mRecognizer.popString();
    }

    // Enables and disables the speech recognizer.
    public static void setRecognizerEnabled(boolean e) {
        if (mRecognizer != null) {
            mRecognizer.setEnabled(e);
        }
    }

    // Returns true if the recognizer is currently enabled.
    public static boolean isRecognizerEnabled() {
        if (mRecognizer == null) {
            return false;
        }
        return mRecognizer.isEnabled();
    }
}
