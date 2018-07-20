package uav.gcs.commands.keyboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uav.gcs.communication.CommunicationIFA;

/**
 *
 * @author Jesimar S. Arantes
 */
public class KeyboardCommands {
    
    private JFrame frame;
    private final CommunicationIFA communicationIFA;

    public KeyboardCommands(CommunicationIFA communicationIFA) {
        this.communicationIFA = communicationIFA;
    }
    
    public void openTheWindow(){
        frame = new JFrame("CMDs KEYBOARD");
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setSize(210, 220);
        frame.setVisible(true); 
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(166, 207, 255));
        panel.setPreferredSize(new Dimension(200, 210));
        
        JLabel labelCommands = new JLabel("======COMMANDS=====");
        JLabel labelTakeoff  = new JLabel("takeoff  -> enter");
        JLabel labelLand     = new JLabel("land       -> backspace");
        JLabel labelUp       = new JLabel("up          -> page up");
        JLabel labelDown     = new JLabel("down     -> page down");
        JLabel labelLeft     = new JLabel("left        -> left arrow");
        JLabel labelRight    = new JLabel("right      -> right arrow");
        JLabel labelForward  = new JLabel("forward -> up arrow");
        JLabel labelBack     = new JLabel("back       -> down arrow");
        
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
    
    public void listenerTheKeyboard(){
        Keyboard keyboard = new Keyboard(communicationIFA);
        frame.addKeyListener(keyboard);
    }
}