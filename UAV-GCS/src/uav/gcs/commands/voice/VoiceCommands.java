package uav.gcs.commands.voice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uav.gcs.communication.CommunicationIFA;

/**
 * The class that controls the drone using voice commands.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class VoiceCommands {
    
    private JFrame frame;
    private final CommunicationIFA communicationIFA;

    /**
     * Class constructor
     * @param communicationIFA the communication with IFA
     * @since version 3.0.0
     */
    public VoiceCommands(CommunicationIFA communicationIFA) {
        this.communicationIFA = communicationIFA;
    }
    
    /**
     * Opens a new graphical interface for input with voice commands
     * @since version 3.0.0
     */
    public void openTheWindow(){
        frame = new JFrame("CMDs VOICE");
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                SpeechInterface.destroy();
            }
        });
        frame.setSize(210, 220);
        frame.setVisible(true); 
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(166, 207, 255));
        panel.setPreferredSize(new Dimension(200, 210));
        
        JLabel labelCommands = new JLabel("======COMMANDS=====");
        JLabel labelTakeoff  = new JLabel("takeoff                   ");
        JLabel labelLand     = new JLabel("land                      ");
        JLabel labelUp       = new JLabel("up                        ");
        JLabel labelDown     = new JLabel("down                      ");
        JLabel labelLeft     = new JLabel("left                      ");
        JLabel labelRight    = new JLabel("right                     ");
        JLabel labelForward  = new JLabel("forward                   ");
        JLabel labelBack     = new JLabel("back                      ");
        
        panel.add(labelCommands);
        panel.add(labelTakeoff);
        panel.add(labelLand);
        panel.add(labelUp);
        panel.add(labelDown);
        panel.add(labelLeft);
        panel.add(labelRight);
        panel.add(labelForward);
        panel.add(labelBack);
        frame.add(panel);
    }
    
    /**
     * The method is listening to microphone.
     * @since version 3.0.0
     */
    public void listenerTheSpeech(){
        SpeechInterface.init("lib", false, true, "./lib", "commands");
        System.out.println("Speech the Commands: ");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                boolean quit = false;
                while (!quit) {
                    try {
                        Thread.sleep(200);                
                    } catch (InterruptedException ex) {
                        
                    }
                    while (SpeechInterface.getRecognizerQueueSize() > 0) {
                        String str = SpeechInterface.popRecognizedString();
                        if (str.indexOf("quit") != -1) {
                            quit = true;
                        }
                        communicationIFA.sendData("CMD: " + str);
                        System.out.println("CMD: " + str);
                    }
                }
                SpeechInterface.destroy();
            }
        });
    }
}
