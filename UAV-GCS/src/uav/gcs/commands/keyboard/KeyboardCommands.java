/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.keyboard.commands;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author jesimar
 */
public class UAVKeyboardCommands {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("===Keyboard Commands===");
        System.out.println("Commands: ");
        System.out.println("    takeoff -> enter\n"
                         + "    land -> backspace\n"
                         + "    up -> page up\n"
                         + "    down -> page down\n"
                         + "    left -> left arrow\n"
                         + "    right -> right arrow\n"
                         + "    forward -> up arrow\n"
                         + "    back -> down arrow\n"
                         + "    rotate -> space\n"
                         + "    quit -> ESC\n");
        
        JFrame frame = new JFrame("Read Commands Keyboard");
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        Keyboard teclado = new Keyboard();
        frame.addKeyListener(teclado);
        frame.setSize(300, 80);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
}

class Keyboard implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                System.out.println("CMD: takeoff");
                break;
            case KeyEvent.VK_BACK_SPACE:
                System.out.println("CMD: land");
                break;
            case KeyEvent.VK_PAGE_UP:
                System.out.println("CMD: up");
                break;
            case KeyEvent.VK_PAGE_DOWN:
                System.out.println("CMD: down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("CMD: left");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("CMD: right");
                break;
            case KeyEvent.VK_UP:
                System.out.println("CMD: forward");                         
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("CMD: back");
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("CMD: rotate");
                break;
            case KeyEvent.VK_ESCAPE:
                System.out.println("CMD: quit");
                System.exit(0);
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

