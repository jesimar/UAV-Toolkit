/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import marte.swing.graphics.pkg2d.navigation.sFrame;
import marte.swing.graphics.pkg2d.navigation.sPanel;
import static marte.swing.graphics.pkg2d.navigation.sPanel.HGAP;
import static marte.swing.graphics.pkg2d.navigation.sPanel.WGAP;
import uav.manager.check.Check;
import uav.manager.check.CheckOutputPattern;

/**
 *
 * @author Marcio
 */
public class UAVManager {
    private static final Color BACKGROUNDS[] = new Color[]{
        new Color(228, 215, 197),
        new Color(218, 191, 221),
        new Color(194, 211, 241),
        new Color(194, 241, 211)
    };
    
    //private final Color BACKGROUND = new Color(194, 211, 241);
    private final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 20);
    private final Font FONT_TITLE = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);

    private final JFileChooser fileChooser;
    private final sFrame windows;
    
    private final sPanel panels[];
    private final sPanel panelBtms;
    
    
    //private final JLabel labelPython;
    //private final JLabel labelOk;
    private final ImageIcon iconLogo;
    private final ImageIcon iconOk;
    private final ImageIcon iconFail;
    private final ImageIcon iconInstall;
    
    private final ImageIcon iconNext;
    private final ImageIcon iconBack;
    
    private final Properties properties;
    private int selected = 0;
        
    public UAVManager(String properties_file) throws FileNotFoundException, IOException {
        properties = new Properties();
        properties.load(new FileInputStream(properties_file)); 
    
        iconLogo = new ImageIcon("./resources/logo-uav-toolkit.png");
        iconOk = new ImageIcon(new ImageIcon("./resources/ok.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconFail = new ImageIcon(new ImageIcon("./resources/fail.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconInstall = new ImageIcon(new ImageIcon("./resources/install.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconNext = new ImageIcon(new ImageIcon("./resources/rigth.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconBack = new ImageIcon(new ImageIcon("./resources/left.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        
        panels = new sPanel[]{
            new InstallPanel(iconLogo, properties_file, BACKGROUNDS[0]),
            new SITLPanel(iconLogo, properties_file, BACKGROUNDS[1]),
        };
        for(sPanel panel : panels){
            panel.setVisible(false);
        }
        panels[0].setVisible(true);
        
        this.windows = new sFrame("UAV Manager"){
            @Override
            public void Config(int w, int h) {
                setSize(w, h);
                for(sPanel panel : panels){
                    panel.Config(w, h-120);
                }
                panelBtms.Config(w-50, 60);
            }
        };
        
        
        this.windows.tryLookAndFeel("Nimbus");
        SwingUtilities.updateComponentTreeUI(this.windows);
        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.fileChooser.setMultiSelectionEnabled(false);
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("File extencion '.exe' or '.AppImage'", "exe", "AppImage"));
        this.fileChooser.updateUI();
        
        this.windows.getContentPane().setBackground(BACKGROUNDS[0]);
        
        panelBtms = new sPanel(new FlowLayout(FlowLayout.RIGHT, WGAP, HGAP), new Color(0, 0, 0, 0));
        JButton btmBack = new JButton(iconBack);
        btmBack.addActionListener((e)->{
            if(selected>0){
                panels[selected].setVisible(false);
                selected--;
                panels[selected].setVisible(true);
                this.windows.getContentPane().setBackground(BACKGROUNDS[selected]);
            }
        });
        JButton btmNext = new JButton(iconNext);
        btmNext.addActionListener((e)->{
            if(selected+1<panels.length){
                panels[selected].setVisible(false);
                selected++;
                panels[selected].setVisible(true);
                this.windows.getContentPane().setBackground(BACKGROUNDS[selected]);
            }
        });
        panelBtms.add(btmBack);
        panelBtms.add(btmNext);
        
        
        for(sPanel panel : panels){
            this.windows.add(panel);
        }
        this.windows.add(panelBtms);
        
        this.windows.Config(1300, 720);
        this.windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windows.setVisible(true);
        
        ((InstallPanel)panels[0]).check();
    }
    
    private class SITLPanel extends sPanel{
        private final LogoPanel panelLogo;
        private final JLabel labelTitle;
        private final sPanel panelChecks;
        
        
        public SITLPanel(final ImageIcon logo, String properties_file, Color background) {
            super(background);
            this.panelLogo = new LogoPanel(logo, background);
            this.labelTitle = new JLabel("Software-In-The-Loop");
            this.labelTitle.setFont(FONT_TITLE);
            

            this.panelChecks = new sPanel(background){
                @Override
                public void Config(int w, int h) {
                    setPreferredSize(new Dimension(w, h));
                    
                }
            };

            this.add(panelLogo);
            this.add(labelTitle);
            this.add(panelChecks);
        }
        
        @Override
        public void Config(int w, int h) {
            setPreferredSize(new Dimension(w, h));
            panelLogo.Config(w-16, h/2-40);
            panelChecks.Config(w-16, h/2);
        }
    }
    
    private class CheckPanel extends sPanel{
        private final Check<Boolean> checker;
        private final Check<Boolean> installer;
        
        private final JLabel validate;
        private final JLabel description;    
        private final String toolTipText;
        public CheckPanel(String description, Check<Boolean> checker) {
            this(description, checker, null, "Please install "+description+" first and add to path system");
        }
        public CheckPanel(String description, Check<Boolean> checker, CheckOutputPattern installer) {
            this(description, checker, installer, "Click to install: "+installer.comand);
        }
        public CheckPanel(String description, Check<Boolean> checker, Check<Boolean> installer, String toolTipText) {
            super(BACKGROUNDS[0]);
            this.checker = checker;
            this.installer = installer;
            this.toolTipText = toolTipText;
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
                        this.validate.setToolTipText(toolTipText);
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

    
    private class LogoPanel extends sPanel{
        private final ImageIcon logo;
        public LogoPanel(ImageIcon logo, Color background) {
            super(background); 
            this.logo = logo;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
            double factorW = getWidth()*1.0/logo.getIconWidth();
            double factorH = getHeight()*1.0/logo.getIconHeight();
            double factor = Math.min(factorW, factorH);
            double diffW = getWidth()-logo.getIconWidth()*factor;
            double diffH = getHeight()-logo.getIconHeight()*factor;

            g.drawImage(logo.getImage(), (int)(diffW/2), (int)(diffH/2), (int)(logo.getIconWidth()*factor), (int)(logo.getIconHeight()*factor), null);
        }
    };
    
    private class InstallPanel extends sPanel{
        
        private final LogoPanel panelLogo;
        private final JLabel labelTitle;
        private final sPanel panelChecks;
        private final CheckPanel checkers[];
        
        public InstallPanel(final ImageIcon logo, String properties_file, Color background) {
            super(background);
            this.panelLogo = new LogoPanel(logo, background);
            this.labelTitle = new JLabel("Installations");
            this.labelTitle.setFont(FONT_TITLE);
            this.checkers = new CheckPanel[]{
                new CheckPanel("Python 2.7.*", 
                    new CheckOutputPattern("python --version", "(.)* 2\\.7\\.(.)*")
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
                ),
                new CheckPanel("Ground Station", 
                    //new CheckOutputPattern("python C:/Python27/Scripts/mavproxy.py --version", "(.)*", 10000, 1), 
                   (r)-> r.accept(new File(properties.getProperty("GROUND_STATION_DIR"), properties.getProperty("GROUND_STATION_APP")).exists()), 
                   (r)-> {
                        int flag = fileChooser.showOpenDialog(windows);
                        if(flag == JFileChooser.APPROVE_OPTION){
                            File file= fileChooser.getSelectedFile();
                            if(file.exists() && file.isFile()){
                                properties.setProperty("GROUND_STATION_DIR", file.getParent());
                                properties.setProperty("GROUND_STATION_APP", file.getName());
                                try {
                                    properties.store(new FileOutputStream(properties_file), "comments");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                r.accept(true);
                            }else{
                                r.accept(false);
                            }
                        }else{
                            r.accept(false);
                        }
                    },
                    "Please install manually and click here to select the the Ground Station aplication"
                )

            };

            this.panelChecks = new sPanel(background){
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

            this.add(panelLogo);
            this.add(labelTitle);
            this.add(panelChecks);
        }
        
        public void check(){
            for (CheckPanel checker : checkers) {
                checker.check();
            }
        }
        
        @Override
        public void Config(int w, int h) {
            setPreferredSize(new Dimension(w, h));
            panelLogo.Config(w-16, h/2-40);
            panelChecks.Config(w-16, h/2);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        
        
        
        UAVManager manager = new UAVManager("./manager.properties"); 

        /*CheckOutputPattern python = new CheckOutputPattern("python --version", "(.)* 2\\.7\\.(.)*");
        //CheckOutputPattern python = new CheckOutputPattern("gcc --version", "(.)* 5\\.2\\.(.)*");
        python.check((result)->{
            manager.python(result);
        });*/
    }

    
    
}
