package uav.gcs.commands.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import uav.gcs.communication.CommunicationIFA;

/**
 * The class that listens to the key pressed.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class Keyboard implements KeyListener {
    
    private final CommunicationIFA communicationIFA;

    /**
     * Class constructor
     * @param communicationIFA the communication with IFA
     * @since version 3.0.0
     */
    public Keyboard(CommunicationIFA communicationIFA) {
        this.communicationIFA = communicationIFA;
    }

    /**
     * Performs an action based on the key pressed.
     * @param keyEvent key-based event
     * @since version 3.0.0
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                System.out.println("CMD: takeoff");
                communicationIFA.sendData("CMD: takeoff");
                break;
            case KeyEvent.VK_BACK_SPACE:
                System.out.println("CMD: land");
                communicationIFA.sendData("CMD: land");
                break;
            case KeyEvent.VK_PAGE_UP:
                System.out.println("CMD: up");
                communicationIFA.sendData("CMD: up");
                break;
            case KeyEvent.VK_PAGE_DOWN:
                System.out.println("CMD: down");
                communicationIFA.sendData("CMD: down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("CMD: left");
                communicationIFA.sendData("CMD: left");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("CMD: right");
                communicationIFA.sendData("CMD: right");
                break;
            case KeyEvent.VK_UP:
                System.out.println("CMD: forward");    
                communicationIFA.sendData("CMD: forward");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("CMD: back");
                communicationIFA.sendData("CMD: back");
                break;            
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // to do
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // to do
    }
}
