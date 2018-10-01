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
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import marte.swing.graphics.pkg2d.navigation.sFrame;
import marte.swing.graphics.pkg2d.navigation.sPanel;
import static marte.swing.graphics.pkg2d.navigation.sPanel.HGAP;
import static marte.swing.graphics.pkg2d.navigation.sPanel.WGAP;
import uav.manager.check.Check;
import uav.manager.check.CheckFindApp;
import uav.manager.check.CheckOutputPattern;
import uav.manager.check.ExecCommand;
import uav.manager.check.Find;
import uav.manager.check.TriConsumer;
import uav.manager.os.DetectOS;
import uav.manager.os.ServicesOS;

/**
 * @author Marcio S. Arantes
 * @see version 3.0.0
 */
public class UAVManager {
    
    private static final Color BACKGROUNDS[] = new Color[]{
        new Color(228, 215, 197),
        new Color(218, 191, 221),
        new Color(194, 211, 241),
        new Color(194, 241, 211)
    };
    
    //private final Color BACKGROUND = new Color(194, 211, 241);
    private final Font FONT_AREA = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    private final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 20);
    private final Font FONT_TITLE = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);
    private final Font FONT_SUB = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);

    private final JFileChooser fileChooser;
    private final sFrame windows;
    
    private final sPanel panels[];
    private final sPanel panelBtms;
    
    
    //private final JLabel labelPython;
    //private final JLabel labelOk;
    private final ImageIcon iconLogo;
    private final ImageIcon iconSITL_PC;
    private final ImageIcon iconSITL_CC;
    private final ImageIcon iconReal_Fligth;
    
    
    private final ImageIcon iconOk;
    private final ImageIcon iconFail;
    private final ImageIcon iconInstall;
    
    private final ImageIcon iconNext;
    private final ImageIcon iconBack;
    
    private final ImageIcon iconCmd;
    private final ImageIcon iconStop;
    private final ImageIcon iconSave;
    
    private final Properties properties;
    private int selected = 0;
        
    private final Find<File> finder;
    private final TriConsumer<File,String,String> saver;
    private final ServicesOS os;
        
    public UAVManager(String properties_file, ServicesOS os) throws FileNotFoundException, IOException {
        this.os = os;
        properties = new Properties();
        properties.load(new FileInputStream(properties_file)); 
        
        this.saver = (file, propert_dir, propert_app) -> {
            properties.setProperty(propert_dir, file.getParent());
            properties.setProperty(propert_app, file.getName());
            try {
                properties.store(new FileOutputStream(properties_file), "comments");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    
        iconLogo = new ImageIcon("./resources/logo-uav-toolkit.png");
        iconSITL_PC = new ImageIcon("./resources/SITL_PC.png");
        iconSITL_CC = new ImageIcon("./resources/SITL_CC.png");
        iconReal_Fligth = new ImageIcon("./resources/RealFlight_CC.png");
        iconOk = new ImageIcon(new ImageIcon("./resources/ok.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconFail = new ImageIcon(new ImageIcon("./resources/fail.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconInstall = new ImageIcon(new ImageIcon("./resources/install.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconNext = new ImageIcon(new ImageIcon("./resources/rigth.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconBack = new ImageIcon(new ImageIcon("./resources/left.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconCmd = new ImageIcon(new ImageIcon("./resources/run.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconStop = new ImageIcon(new ImageIcon("./resources/stop.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconSave = new ImageIcon(new ImageIcon("./resources/save_icon.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                
        this.windows = new sFrame("UAV Manager"){
            @Override
            public void Config(int w, int h) {
                setSize(w, h);
                for(sPanel panel : panels){
                    panel.Config(w, h-80);
                }
                panelBtms.Config(w-50, 48);
            }
        };
                
        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.fileChooser.setMultiSelectionEnabled(false);
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("File extencion '.exe' or '.AppImage' or '.py' or '.jar'", "exe", "AppImage", "py",  "jar"));
        this.fileChooser.updateUI();
        this.windows.getContentPane().setBackground(BACKGROUNDS[0]);
        
        this.finder = () -> {
            int flag = fileChooser.showOpenDialog(windows);
            if(flag == JFileChooser.APPROVE_OPTION){
                return fileChooser.getSelectedFile();
            }else{
                return null;
            }
        };
        
        panels = new sPanel[]{
            new InstallPanel(iconLogo, properties_file, BACKGROUNDS[0]),
            new SITLPanel(iconLogo, properties_file, BACKGROUNDS[1]),
        };
        for(sPanel panel : panels){
            panel.setVisible(false);
        }
        panels[0].setVisible(true);
       
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
        
        this.windows.Config(1300, 700);
        this.windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windows.setVisible(true);
        
        
        ((InstallPanel)panels[0]).check();
        
        SwingUtilities.invokeLater(()->{
            this.windows.tryLookAndFeel("Nimbus");
            SwingUtilities.updateComponentTreeUI(this.windows);
        });
    }
    
    private class PanelCommand extends sPanel{
        private final JLabel label;
        private final JTextField command;
        private final JButton btmCmd;
        private final JButton btmSave;
        
        private PrintStream terminal;
        
        private void execute(String name_bat, String propert_dir){
            try {
                String cmd = this.command.getText().replaceAll("\n", " ");
                File file = new File("./resources/scripts/"+name_bat+os.getBatchExtension());
                PrintStream out = new PrintStream(file);
                if(cmd.endsWith(".jar")){
                    out.println("java -version");
                    if(propert_dir!=null){
                        String dir = properties.getProperty(propert_dir);
                        out.println("cd "+dir.substring(0, dir.length()-5));
                    }
                    out.println("java -jar dist/"+cmd);
                } else if (cmd.endsWith(".py")){
                    if(propert_dir!=null){
                        String dir = properties.getProperty(propert_dir);
                        out.println("cd " + dir);
                    }
                    out.println("python " + cmd);
                } else {
                    if(propert_dir!=null){
                        out.println("cd "+properties.getProperty(propert_dir));
                        out.println(os.cmdToExecOnDir(cmd));
                    }else{
                        out.println(cmd);
                    }
                }
                out.println("echo Please, close the terminal");
                out.println("sleep 60");
                out.close();
                file.setExecutable(true);
                //System.out.println(cmd);
                //Thread.sleep(1000);
                //terminal.println(cmd);
                terminal.println(os.startExternalTerminalScript(name_bat+os.getBatchExtension()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        private void save(String properties_file, String propert){
            try {
                String cmd = this.command.getText().replaceAll("\n", " ");
                properties.setProperty(propert, cmd);
                properties.store(new FileOutputStream(properties_file), "comments");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } 
        public PanelCommand(String label, String properties_file,  Color background, String propert_command, String name_bat) {
            this(label, properties_file, background, propert_command, name_bat, null);
        }
        public PanelCommand(String label, String properties_file,  Color background, String propert_command, String name_bat, String propert_dir) {
            super(background);
            
            this.label = new JLabel(label);
            this.label.setFont(FONT_SUB);
            this.command = new JTextField(properties.getProperty(propert_command));
            this.command.setFont(FONT_AREA);
            this.command.setForeground(Color.GREEN);
            this.command.setBackground(Color.BLACK);
            
            this.btmCmd = new JButton("run",iconCmd);
            this.btmSave = new JButton("save",iconSave);
            
            btmCmd.addActionListener((e)->{
                execute(name_bat, propert_dir);
            });
            btmSave.addActionListener((e)->{
                save(properties_file, propert_command);
            });
            
            add(this.label);
            add(this.command);
            add(this.btmCmd);
            add(this.btmSave);
        }

        private void addTerminal(PrintStream terminal) {
            this.terminal = terminal;
        }

        @Override
        public void Config(int w, int h) {
            this.setPreferredSize(new Dimension(w, h));
            label.setPreferredSize(new Dimension(150, 30));
            command.setPreferredSize(new Dimension(w-165-30-btmCmd.getPreferredSize().width-btmSave.getPreferredSize().width, 40));
        }

    }
    private class SITLPanel extends sPanel{
        private final LogoPanel panelLogo;
        private final JLabel labelTitle;
        private final JScrollPane scrollOutput;
        
        //private final sPanel panelBtms;
        private final ExecCommand exec;
        private PanelCommand commands[];
        
        public SITLPanel(final ImageIcon logo, String properties_file, Color background) {
            super(background);
            this.exec = new ExecCommand();
            
            JTextArea taOutput = new JTextArea("");
            taOutput.setForeground(Color.GREEN);
            taOutput.setBackground(Color.BLACK);
            taOutput.setFont(FONT_AREA);
            taOutput.setEditable(false);
            
            this.scrollOutput = new JScrollPane();
            this.scrollOutput.setViewportView(taOutput);
            
            //this.panelBtms = new sPanel(background);
            //this.add(panelBtms);
            
            commands = new PanelCommand[]{
                new PanelCommand("SITL >>",     properties_file, background, "SITL_COMMAND", "exec-dronekit-sitl"),
                new PanelCommand("MAVProxy >>", properties_file, background, "MAVPROXY_COMMAND", "exec-mavproxy"),
                new PanelCommand("UAV-S2DK >>",  properties_file, background, "S2DK_APP", "exec-s2dk", "S2DK_DIR"),
                new PanelCommand("GCS >>",      properties_file, background, "GROUND_STATION_APP", "exec-gcs", "GROUND_STATION_DIR"),
                new PanelCommand("UAV-GCS >>",  properties_file, background, "UAV_GCS_APP", "exec-uav-gcs", "UAV_GCS_DIR"),
                new PanelCommand("IFA >>",      properties_file, background, "UAV_IFA_APP", "exec-uav-ifa", "UAV_IFA_DIR"),
                new PanelCommand("MOSA >>",     properties_file, background, "UAV_MOSA_APP", "exec-uav-mosa", "UAV_MOSA_DIR")
            };
            
            this.panelLogo = new LogoPanel(logo, background);
            this.labelTitle = new JLabel("Software-In-The-Loop", SwingConstants.CENTER);
            this.labelTitle.setFont(FONT_TITLE);

            this.add(labelTitle);
            for(PanelCommand cmd : commands){
                this.add(cmd);
            }
            this.add(panelLogo);
            this.add(scrollOutput);
            
            exec.execute(
                os.startInternalTerminalCmd(),
                (line)->{   //standard oupput
                    SwingUtilities.invokeLater(()->{
                        taOutput.append(line+"\n");
                    });
                }, 
                (line)->{   //standard erro
                    SwingUtilities.invokeLater(()->{
                        taOutput.append("err: "+line+"\n");
                    });
                },
                (sucess)->{  //finish status
                    SwingUtilities.invokeLater(()->{
                        taOutput.append("---------------------------[ run="+sucess+" ]---------------------------\n");
                        if(sucess){
                            //btmCmd.setEnabled(true);
                            //btmStop.setEnabled(false);
                        }
                    });
                },
                (terminal)->{
                    terminal.println("echo Please, check the your environment variables PATH");
                    terminal.println(os.cmdPrintPath());
                    for(PanelCommand cmd : commands){
                        cmd.addTerminal(terminal);
                    }
                }
            );
        }

        @Override
        public void Config(int w, int h) {
            setPreferredSize(new Dimension(w, h));
            labelTitle.setPreferredSize(new Dimension(w-24, 40));
            for(PanelCommand cmd : commands){
                cmd.Config(w-16, 48);
            }
            panelLogo.Config(w/3-24, h-46-commands.length*56);
            scrollOutput.setPreferredSize(new Dimension(w*2/3-24, h-46-commands.length*56));
            panelBtms.Config(w-16, 30);
        }
    }
    
    private class ModulePanel extends sPanel{
        private final Check<Boolean> checker;
        private final LogoPanel icon;
        //private final JLabel validate;
        private final JLabel description;
        private final Color backgound;
        private final Color fail = new Color(255, 128, 128);
        private final Color ok = new Color(181, 230, 29);
        
        public ModulePanel(ImageIcon icon, String description, Color background, Check<Boolean> checker) {
            super(background);
            this.backgound = background;
            this.checker = checker;
            this.icon = new LogoPanel(icon, background);
            //this.validate = new JLabel("...");
            this.description = new JLabel(description);
            //this.validate.setFont(FONT);
            this.description.setFont(FONT_SUB);
            this.add(this.icon);
            //this.add(this.validate);
            this.add(this.description);
        }
        public void changeBackground(Color background){
            this.setBackground(background);
            this.icon.setBackground(background);
            this.repaint();
            //this.icon.repaint();
        }
        public void check() {
            checker.check((result)->{
                SwingUtilities.invokeLater(()->{
                    //this.validate.setText(null);
                    if(result){
                        changeBackground(ok);
                    }else{
                        changeBackground(fail);
                    }
                });
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
            
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(0, 0, getPreferredSize().width-1, getPreferredSize().height-1);
            g.setColor(Color.GRAY);
            g.drawRect(1, 1, getPreferredSize().width-3, getPreferredSize().height-3);
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(2, 2, getPreferredSize().width-5, getPreferredSize().height-5);
        }
        
        @Override
        public void Config(int w, int h) {
            setPreferredSize(new Dimension(w, h));
            icon.setPreferredSize(new Dimension(w-48, w-48)); 
            //description.setPreferredSize(new Dimension(w-12, 32));
        }
    }
    
    private class CheckPanel extends sPanel{
        private final boolean module_sitl_pc;
        private final boolean module_sitl_cc;
        private final boolean module_real_flight_cc;
        
        private final Check<Boolean> checker;
        private final Check<Boolean> installer;
        
        private final JLabel validate;
        private final JLabel description;    
        private final String toolTipText;
        public CheckPanel(boolean sitl_pc, boolean sitl_cc, boolean real_flight_cc, String description, Check<Boolean> checker) {
            this(sitl_pc, sitl_cc, real_flight_cc, description, checker, null, "Please install "+description+" first and add to path system");
        }
        public CheckPanel(boolean sitl_pc, boolean sitl_cc, boolean real_flight_cc, String description, Check<Boolean> checker, CheckOutputPattern installer) {
            this(sitl_pc, sitl_cc, real_flight_cc, description, checker, installer, "Click to install: "+installer.command);
        }
        public CheckPanel(boolean sitl_pc, boolean sitl_cc, boolean real_flight_cc, String description, Check<Boolean> checker, Check<Boolean> installer, String toolTipText) {
            super(BACKGROUNDS[0]);
            this.module_sitl_pc = sitl_pc;
            this.module_sitl_cc = sitl_cc;
            this.module_real_flight_cc = real_flight_cc;
            
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
        public boolean needSITL_PC(){
            return module_sitl_pc;
        }
        public boolean needSITL_CC(){
            return module_sitl_cc;
        }
        public boolean needRealFlight_CC(){
            return module_real_flight_cc;
        }
        public void check(Consumer<Boolean> valid) {
            checker.check((result)->{
                SwingUtilities.invokeLater(()->{
                    this.validate.setText(null);
                    if(result){
                        this.validate.setIcon(iconOk);
                        valid.accept(true);
                    }else{
                        this.validate.setIcon(iconFail);
                        this.validate.setToolTipText(toolTipText);
                        if(installer!=null){
                            enableInstall(valid);
                        }
                        valid.accept(false);
                    }
                });
            });
        }
        public void enableInstall(Consumer<Boolean> valid){
            this.validate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                    validate.removeMouseListener(this);
                    validate.setIcon(iconInstall);
                    validate.setToolTipText(null);
                    setCursor(Cursor.getDefaultCursor());
                    installer.check((instaled)->{
                        check(valid);
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

        public boolean isOk() {
            return validate.getIcon()==iconOk;
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
        private final JLabel labelIntalations;
        private final JLabel labelModules;
        private final sPanel panelChecks;
        private final sPanel panelModules;
        
        private final sPanel panelRigth;
        private final sPanel panelLeft;
        
        private final ModulePanel modules[];
        private final CheckPanel checkers[];
        
        public InstallPanel(final ImageIcon logo, String properties_file, Color background) {
            super(background);
            this.panelLogo = new LogoPanel(logo, background);
            this.labelIntalations = new JLabel("Installations", SwingConstants.CENTER);
            this.labelIntalations.setFont(FONT_TITLE);
            this.checkers = new CheckPanel[]{
                new CheckPanel(true, true, true, "Python 2.7.*", 
                    new CheckOutputPattern(os.cmdTestInstallationPython(), 
                            os.patternTestInstallationPython())
                ),
                new CheckPanel(false, false, false, "Pip 9.*.*", 
                    new CheckOutputPattern(os.cmdTestInstallationPip(), 
                            os.patternTestInstallationPip(), 20000),
                    new CheckOutputPattern("python -m pip install pip==9.0.1", "(.)*", 300000)
                ),
                new CheckPanel(true, false, false, "DroneKit 2.9.*", 
                    new CheckOutputPattern(os.cmdTestInstallationDronekit(),
                            os.patternTestInstallationDronekit()), 
                    new CheckOutputPattern("python -m pip install dronekit", "(.)*", 180000)
                ),
                new CheckPanel(true, true, false, "DroneKit-SITL 3.2.*", 
                    new CheckOutputPattern(os.cmdTestInstallationDronekitSITL(), 
                            os.patternTestInstallationDronekitSITL()), 
                    new CheckOutputPattern("python -m pip install dronekit-sitl", "(.)*", 300000)
                ),
                new CheckPanel(true, false, false, "Mavproxy 1.6.*", 
                    new CheckOutputPattern(os.cmdTestInstallationMAVProxy(), 
                            os.patternTestInstallationMAVProxy(), 10000), 
                    new CheckOutputPattern("python -m pip install MAVProxy==1.6.1", "(.)*", 300000)
                ),
                new CheckPanel(true, true, true, "Ground Station", 
                    (r) -> r.accept(new File(properties.getProperty("GROUND_STATION_DIR"), properties.getProperty("GROUND_STATION_APP")).exists()),
                    new CheckFindApp(finder, saver, "GROUND_STATION_DIR", "GROUND_STATION_APP"),
                    "Please install manually and click here to select one Ground Control Station aplication"
                ),
                new CheckPanel(true, false, false, "S2DK", 
                    (r) -> r.accept(new File(properties.getProperty("S2DK_DIR"), properties.getProperty("S2DK_APP")).exists()),
                    new CheckFindApp(finder, saver, "S2DK_DIR", "S2DK_APP"),
                    "Please select the file init.py from UAV-Toolkit/UAV-S2DK/ directory"
                ),
                new CheckPanel(true, true, true, "UAV-GCS", 
                    (r) -> r.accept(new File(properties.getProperty("UAV_GCS_DIR"), properties.getProperty("UAV_GCS_APP")).exists()),
                    new CheckFindApp(finder, saver, "UAV_GCS_DIR", "UAV_GCS_APP"),
                    "Please select the UAV-GCS.jar from UAV-Toolkit/UAV-GCS/dist/ directory"
                ),
                new CheckPanel(true, true, true, "UAV-IFA", 
                    (r) -> r.accept(new File(properties.getProperty("UAV_IFA_DIR"), properties.getProperty("UAV_IFA_APP")).exists()),
                    new CheckFindApp(finder, saver, "UAV_IFA_DIR", "UAV_IFA_APP"),
                    "Please select the UAV-IFA.jar from UAV-Toolkit/UAV-IFA/dist/ directory"
                ),
                new CheckPanel(true, true, true, "UAV-MOSA", 
                    (r) -> r.accept(new File(properties.getProperty("UAV_MOSA_DIR"), properties.getProperty("UAV_MOSA_APP")).exists()),
                    new CheckFindApp(finder, saver, "UAV_MOSA_DIR", "UAV_MOSA_APP"),
                    "Please select the UAV-MOSA.jar from UAV-Toolkit/UAV-MOSA/dist/ directory"
                )
            };
            
            
            this.labelModules = new JLabel("Modules", SwingConstants.CENTER);
            this.labelModules.setFont(FONT_TITLE);
            this.modules = new ModulePanel[]{
                new ModulePanel(iconSITL_PC, "SITL - PC", background, (r)-> r.accept(verifiy(CheckPanel::needSITL_PC))),
                new ModulePanel(iconSITL_CC, "SITL - CC", background, (r)-> r.accept(verifiy(CheckPanel::needSITL_CC))),
                new ModulePanel(iconReal_Fligth, "Real Flight - CC", background,      (r)-> r.accept(verifiy(CheckPanel::needRealFlight_CC)))
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
            
            this.panelModules = new sPanel(background){
                @Override
                public void Config(int w, int h) {
                    setPreferredSize(new Dimension(w, h));
                    for(int i=0; i<modules.length; i++){
                        int min = Math.min(w/3-24, h-12);
                        modules[i].Config(min, min);
                    }
                }
            };

            for(int i=0; i<checkers.length; i++){
                this.panelChecks.add(checkers[i]);
            }
            for(int i=0; i<modules.length; i++){
                this.panelModules.add(modules[i]);
            }
            
            this.panelRigth = new sPanel(background){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
                    g.drawRect(0, 0, getPreferredSize().width-1, getPreferredSize().height-1);
                }
                
                
                @Override
                public void Config(int w, int h) {
                    setPreferredSize(new Dimension(w, h));
                    panelLogo.Config(w-12, h/2-58);
                    labelModules.setPreferredSize(new Dimension(w-12, 40));
                    panelModules.Config(w-12, h/2);
                }
            };
            
            this.panelLeft = new sPanel(background){
                @Override
                public void Config(int w, int h) {
                    setPreferredSize(new Dimension(w, h));
                    labelIntalations.setPreferredSize(new Dimension(w-12, 40));
                    panelChecks.Config(w-12, h-58);
                }
            };

            this.panelRigth.add(panelLogo);
            this.panelRigth.add(labelModules);
            this.panelRigth.add(panelModules);
            
            this.panelLeft.add(labelIntalations);
            this.panelLeft.add(panelChecks);
            
            this.add(panelLeft);
            this.add(panelRigth);
            
            
        }
        public boolean verifiy(Predicate<CheckPanel> predicate){
            return Arrays.stream(checkers).filter(predicate).allMatch((p)->p.isOk());
        }
        
        public void check(){
            for (CheckPanel checker : checkers) {
                checker.check((r)->check_modules());
            }
        }
        public void check_modules(){
            for (ModulePanel checker : modules) {
                checker.check();
            }
        }
        
        @Override
        public void Config(int w, int h) {
            setPreferredSize(new Dimension(w, h));
            panelLeft.Config(w/3-18, h-58);
            panelRigth.Config((w*2)/3-18, h-58);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ServicesOS os = DetectOS.detect();
        
        UAVManager manager = new UAVManager("./manager.properties", os); 

        /*CheckOutputPattern python = new CheckOutputPattern("python --version", "(.)* 2\\.7\\.(.)*");
        //CheckOutputPattern python = new CheckOutputPattern("gcc --version", "(.)* 5\\.2\\.(.)*");
        python.check((result)->{
            manager.python(result);
        });*/
    }
    
}
