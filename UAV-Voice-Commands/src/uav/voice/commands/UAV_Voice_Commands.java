package uav.voice.commands;

import uav.voice.commands.speech.SpeechInterface;

public class UAV_Voice_Commands {

    public static void main(String[] argv) {
        SpeechInterface.init("lib", false, true, "./lib", "commands");

        System.out.println("===Voice Commands===");
        System.out.println("Commands: 'takeoff' | 'land' | 'up' | 'down' | 'left' | "
                + "'right' | 'forward' | 'back' | 'rotate'");
        System.out.println("Quit: 'quit'");

        boolean quit = false;
        while (!quit) {
            try {
                Thread.sleep(200);                
            } catch (InterruptedException e) {
                
            }
            while (SpeechInterface.getRecognizerQueueSize() > 0) {
                String s = SpeechInterface.popRecognizedString();
                if (s.indexOf("quit") != -1) {
                    quit = true;
                }
                System.out.println("CMD: " + s);
            }
        }
        SpeechInterface.destroy();
    }
}
