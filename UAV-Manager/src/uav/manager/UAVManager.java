/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import marte.swing.graphics.pkg2d.navigation.iDimensions;
import marte.swing.graphics.pkg2d.navigation.sFrame;
import marte.swing.graphics.pkg2d.navigation.sPanel;
import sun.security.pkcs11.wrapper.PKCS11Constants;
import uav.manager.check.Check;
import uav.manager.check.CheckOutputPattern;

/**
 *
 * @author Marcio
 */
public class UAVManager {
    private final Color BACKGROUND = new Color(194, 211, 241);
    private final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 20);

    private final sFrame windows;
    private final sPanel panelLogo;
    private final sPanel panelChecks;
    private final CheckPanel checkers[];
    
    //private final JLabel labelPython;
    //private final JLabel labelOk;
    
    private final ImageIcon iconOk;
    private final ImageIcon iconFail;
    private final ImageIcon iconInstall;
    
    public UAVManager() {
        this.windows = new sFrame("UAV Manager"){
            @Override
            public void Config(int w, int h) {
                setSize(w, h);
                panelLogo.Config(w-16, h/2-40);
                panelChecks.Config(w-16, h/2);
            }
        };
        this.windows.tryLookAndFeel("ninbus");
        this.windows.getContentPane().setBackground(BACKGROUND);

        final ImageIcon img = new ImageIcon("./resources/logo-uav-toolkit.png");
        iconOk = new ImageIcon(new ImageIcon("./resources/ok.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconFail = new ImageIcon(new ImageIcon("./resources/fail.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconInstall = new ImageIcon(new ImageIcon("./resources/install.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        
        this.panelLogo = new sPanel(BACKGROUND){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
                double factorW = getWidth()*1.0/img.getIconWidth();
                double factorH = getHeight()*1.0/img.getIconHeight();
                double factor = Math.min(factorW, factorH);
                double diffW = getWidth()-img.getIconWidth()*factor;
                double diffH = getHeight()-img.getIconHeight()*factor;
                
                g.drawImage(img.getImage(), (int)(diffW/2), (int)(diffH/2), (int)(img.getIconWidth()*factor), (int)(img.getIconHeight()*factor), null);
            }
        };
        
        this.checkers = new CheckPanel[]{
            new CheckPanel("Python 2.7.*", 
                new CheckOutputPattern("python --version", "(.)* 2\\.7\\.(.)*"), 
                null
            ),
            new CheckPanel("Pip 9.*.*", 
                new CheckOutputPattern("python -m pip --version", "pip (.)*\\.(.)*\\.(.)*"),
                new CheckOutputPattern("python -m pip install pip==9.0.1", "(.)*", 300000)
            ),
            new CheckPanel("DroneKit 2.9.*", 
                new CheckOutputPattern("python dronekit-test.py", "(.)*dronekit-ok(.)*"), 
                new CheckOutputPattern("python -m pip install dronekit", "(.)*", 180000)
            ),
            new CheckPanel("DroneKit-SITL 3.2.*", 
                new CheckOutputPattern("dronekit-sitl --version", "(.)*3\\.(.)*\\.(.)*"), 
                new CheckOutputPattern("python -m pip install dronekit-sitl", "(.)*", 300000)
            ),
            new CheckPanel("Mavproxy 1.6.*", 
                //new CheckOutputPattern("python C:/Python27/Scripts/mavproxy.py --version", "(.)*", 10000, 1), 
                new CheckOutputPattern("python mavproxy-test.py", "(.)*mavproxy-ok(.)*", 10000), 
                new CheckOutputPattern("python -m pip install MAVProxy==1.6.1", "(.)*", 300000)
            )
        };
        
        this.panelChecks = new sPanel(BACKGROUND){
            @Override
            public void Config(int w, int h) {
                setPreferredSize(new Dimension(w, h));
                for(int i=0; i<checkers.length; i++){
                    checkers[i].Config(w-12, 40);
                }
            }
        };
        
        for(int i=0; i<checkers.length; i++){
            this.panelChecks.add(checkers[i]);
        }
        
        this.windows.add(panelLogo);
        this.windows.add(panelChecks);
        
        this.windows.Config(1300, 720);
        this.windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windows.setVisible(true);
        
        for (CheckPanel checker : checkers) {
            checker.check();
        }
    }
    
    private class CheckPanel extends sPanel{
        private final Check<Boolean> checker;
        private final Check<Boolean> installer;
        
        private final JLabel validate;
        private final JLabel description;       
        public CheckPanel(String description, Check<Boolean> checker, Check<Boolean> installer) {
            super(BACKGROUND);
            this.checker = checker;
            this.installer = installer;
            this.validate = new JLabel("...");
            this.description = new JLabel(description);
            this.validate.setFont(FONT);
            this.description.setFont(FONT);
            this.add(this.validate);
            this.add(this.description);
        }
        public void check() {
            checker.check((result)->{
                SwingUtilities.invokeLater(()->{
                    this.validate.setText(null);
                    if(result){
                        this.validate.setIcon(iconOk);
                    }else{
                        this.validate.setIcon(iconFail);
                        this.validate.setToolTipText("Try install");
                        if(installer!=null){
                            enableInstall();
                        }
                    }
                });
            });
        }
        public void enableInstall(){
            this.validate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                    validate.removeMouseListener(this);
                    validate.setIcon(iconInstall);
                    validate.setToolTipText(null);
                    setCursor(Cursor.getDefaultCursor());
                    installer.check((instaled)->{
                        check();
                    });
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e); //To change body of generated methods, choose Tools | Templates.
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e); //To change body of generated methods, choose Tools | Templates.
                    setCursor(Cursor.getDefaultCursor());
                }
             });
        }
        @Override
        public void Config(int w, int h) {
            setPreferredSize(new Dimension(w, h));
            validate.setPreferredSize(new Dimension(48, 32));
            description.setPreferredSize(new Dimension(w-120, 32));
        }
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        UAVManager manager = new UAVManager();

        /*CheckOutputPattern python = new CheckOutputPattern("python --version", "(.)* 2\\.7\\.(.)*");
        //CheckOutputPattern python = new CheckOutputPattern("gcc --version", "(.)* 5\\.2\\.(.)*");
        python.check((result)->{
            manager.python(result);
        });*/
    }

    
    
}
