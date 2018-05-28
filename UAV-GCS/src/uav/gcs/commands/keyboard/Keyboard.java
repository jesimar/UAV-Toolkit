package uav.gcs.commands.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import uav.gcs.communication.CommunicationIFA;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Keyboard implements KeyListener {
    
    private final CommunicationIFA communicationIFA;

    public Keyboard(CommunicationIFA communicationIFA) {
        this.communicationIFA = communicationIFA;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
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
    public void keyReleased(KeyEvent e) {
        // to do
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // to do
    }
}
