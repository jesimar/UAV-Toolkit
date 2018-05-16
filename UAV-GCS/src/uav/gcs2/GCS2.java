package uav.gcs2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Locale;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import uav.gcs2.struct.Drone;
import uav.gcs2.struct.ReaderFileConfig;
import uav.gcs2.communication.CommunicationIFA;
import uav.gcs2.conection.SaveDB;
import uav.gcs2.google_maps.GoogleMaps;

/**
 * @author Jesimar S. Arantes
 */
public final class GCS2 extends JFrame {

    private final JPanel panelTop;
    private final JPanel panelMain;
    private final JPanel panelLeft;
    private final JPanel panelRight;   
    
    private final JButton btnBadWeather;
    private final JButton btnPathReplanningEmergency;
    private final JButton btnLand;
    private final JButton btnRTL;
    private final JButton btnBuzzer;
    private final JButton btnAlarm;
    private final JButton btnPicture;
    private final JButton btnVideo;
    private final JButton btnLED;    
    private final JButton btnBlink;
    private final JButton btnSpraying;
    private final JButton btnParachute;
    
    private final JLabel labelIsConnectedIFA;
    
    private final int width = 800;
    private final int height = 580;
    
    private final Drone drone;
    private final ReaderFileConfig config;
    private final CommunicationIFA communicationIFA;
    private SaveDB saveDB;
    private GoogleMaps googleMaps;

    public static void main(String[] args) {
        GCS2 gcs = new GCS2();
    }

    public GCS2() {
        Locale.setDefault(Locale.US);
        config = ReaderFileConfig.getInstance();
        config.read();
        drone = new Drone();
        communicationIFA = new CommunicationIFA(drone, config.getHostIFA(), config.getPortIFA());
        if (config.hasDB()){
            saveDB = new SaveDB(config.getHostOD_DB(), config.getPortOD_DB());
        }
        
        this.setTitle("UAV-GCS");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setLocationRelativeTo(null);
        
        panelTop = new JPanel();
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(new Color(166, 207, 255));
        panelTop.setVisible(true);
        
//        JPanel panelTop2 = new JPanel();
//        panelTop2.setLayout(new FlowLayout(FlowLayout.CENTER));
//        panelTop2.setPreferredSize(new Dimension(400, 80));
//        panelTop2.setBackground(new Color(100, 200, 255));
//        panelTop2.setVisible(true);
//        
//        JLabel labelHOST = new JLabel("IP:   ");
//        labelHOST.setPreferredSize(new Dimension(60, 20));
//        labelHOST.setForeground(Color.BLACK);
//        panelTop2.add(labelHOST);
//        
//        JTextField fieldHOST = new JTextField();
//        fieldHOST.setPreferredSize(new Dimension(160, 20));
//        fieldHOST.setForeground(Color.BLACK);
//        panelTop2.add(fieldHOST);
//        
//        JButton btnConnect = new JButton("Connect");
//        btnConnect.setPreferredSize(new Dimension(120, 25));
//        btnConnect.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                
//            }
//        });
//        panelTop2.add(btnConnect);
//        
//        JLabel labelPORT = new JLabel("PORT: ");
//        labelPORT.setPreferredSize(new Dimension(60, 20));
//        labelPORT.setForeground(Color.BLACK);
//        panelTop2.add(labelPORT);
//        
//        JTextField fieldPORT = new JTextField();
//        fieldPORT.setPreferredSize(new Dimension(160, 20));
//        fieldPORT.setForeground(Color.BLACK);
//        panelTop2.add(fieldPORT);      
//        
//        JButton btnDisconnect = new JButton("Disconnect");
//        btnDisconnect.setPreferredSize(new Dimension(120, 25));
//        btnDisconnect.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                
//            }
//        });
//        panelTop2.add(btnDisconnect);
//        
//        panelTop.add(panelTop2);
        
        JButton btnAbout = new JButton("About");
        btnAbout.setPreferredSize(new Dimension(120, 25));
        btnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
        panelTop.add(btnAbout);
        
        JButton btnExit = new JButton("Exit");
        btnExit.setPreferredSize(new Dimension(120, 25));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelTop.add(btnExit);

        panelMain = new JPanel();
        panelMain.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelMain.setBackground(Color.LIGHT_GRAY);
        panelMain.setVisible(true);

        panelLeft = new JPanel();
        panelLeft.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelLeft.setBackground(new Color(95, 161, 255));
        panelLeft.setVisible(true);
        panelMain.add(panelLeft);
        
        JLabel labelActions = new JLabel("Actions");
        labelActions.setPreferredSize(new Dimension(160, 20));
        labelActions.setForeground(Color.BLACK);
        panelLeft.add(labelActions);
        
        btnBadWeather = new JButton("BAD-WEATHER");
        btnBadWeather.setPreferredSize(new Dimension(185, 25));
        btnBadWeather.setEnabled(false);
        btnBadWeather.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("BAD-WEATHER");
            }
        });
        panelLeft.add(btnBadWeather);
        
        btnPathReplanningEmergency = new JButton("EMERGENCY-LANDING");
        btnPathReplanningEmergency.setPreferredSize(new Dimension(185, 25));
        btnPathReplanningEmergency.setEnabled(false);
        btnPathReplanningEmergency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("EMERGENCYLANDING");
            }
        });
        panelLeft.add(btnPathReplanningEmergency);
        
        btnLand = new JButton("LAND");
        btnLand.setPreferredSize(new Dimension(185, 25));
        btnLand.setEnabled(false);
        btnLand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("LAND");
            }
        });
        panelLeft.add(btnLand);
        
        btnRTL= new JButton("RTL");
        btnRTL.setPreferredSize(new Dimension(185, 25));
        btnRTL.setEnabled(false);
        btnRTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("RTL");
            }
        });
        panelLeft.add(btnRTL);

        btnBuzzer = new JButton("BUZZER-TURN-ON");
        btnBuzzer.setPreferredSize(new Dimension(185, 25));
        btnBuzzer.setEnabled(false);
        btnBuzzer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("BUZZER");
            }
        });
        panelLeft.add(btnBuzzer);
        
        btnAlarm = new JButton("BUZZER-ALARM");
        btnAlarm.setPreferredSize(new Dimension(185, 25));
        btnAlarm.setEnabled(false);
        btnAlarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("ALARM");
            }
        });
        panelLeft.add(btnAlarm);

        btnPicture = new JButton("CAMERA-PICTURE");
        btnPicture.setPreferredSize(new Dimension(185, 25));
        btnPicture.setEnabled(false);
        btnPicture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("PICTURE");
            }
        });
        panelLeft.add(btnPicture);
        
        btnVideo = new JButton("CAMERA-VIDEO");
        btnVideo.setPreferredSize(new Dimension(185, 25));
        btnVideo.setEnabled(false);
        btnVideo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("VIDEO");
            }
        });
        panelLeft.add(btnVideo);

        btnLED = new JButton("LED-TURN-ON");
        btnLED.setPreferredSize(new Dimension(185, 25));
        btnLED.setEnabled(false);
        btnLED.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("LED");
            }
        });
        panelLeft.add(btnLED);
        
        btnBlink = new JButton("LED-BLINK");
        btnBlink.setPreferredSize(new Dimension(185, 25));
        btnBlink.setEnabled(false);
        btnBlink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("BLINK");
            }
        });
        panelLeft.add(btnBlink);
        
        btnSpraying = new JButton("SPRAYING-START");
        btnSpraying.setPreferredSize(new Dimension(185, 25));
        btnSpraying.setEnabled(false);
        btnSpraying.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("SPRAYING");
            }
        });
        panelLeft.add(btnSpraying);
        
        btnParachute = new JButton("PARACHUTE-OPEN");
        btnParachute.setPreferredSize(new Dimension(185, 25));
        btnParachute.setEnabled(false);
        btnParachute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData("PARACHUTE");
            }
        });
        panelLeft.add(btnParachute);

        communicationIFA.connectServerIFA();
        communicationIFA.receiveData();

        if (config.hasDB()){
            saveDB.saveDB(drone, communicationIFA);
        }
        
        enableComponentsInterface();
        
        if (config.hasGoogleMaps()){
            //Painel com google maps
            panelRight = new JPanel(new BorderLayout());
            panelRight.setVisible(true);
            googleMaps = new GoogleMaps(this, panelRight, communicationIFA);
            googleMaps.plot();
        }else{
            //Painel sem google maps
            panelRight = new JPanel();
            panelRight.setLayout(new FlowLayout(FlowLayout.CENTER));
            panelRight.setBackground(new Color(95, 161, 255));
            panelRight.setVisible(true);
        }

        labelIsConnectedIFA = new JLabel("Is Connected IFA");
        labelIsConnectedIFA.setPreferredSize(new Dimension(160, 20));
        labelIsConnectedIFA.setForeground(Color.RED);
        panelRight.add(labelIsConnectedIFA);

        this.add(panelTop);
        this.add(panelMain);
        panelMain.add(panelRight);

        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                setSize(getWidth(), getHeight());
            }
        });
        this.setVisible(true);
    }
    
    private void enableComponentsInterface(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(500);
                        if (communicationIFA.isConnected()){
                            btnBadWeather.setEnabled(true);
                            btnPathReplanningEmergency.setEnabled(true);
                            btnLand.setEnabled(true);
                            btnRTL.setEnabled(true);
                            btnBuzzer.setEnabled(true);
                            btnAlarm.setEnabled(true);
                            btnPicture.setEnabled(true);
                            btnVideo.setEnabled(true);
                            btnLED.setEnabled(true);
                            btnBlink.setEnabled(true);
                            btnSpraying.setEnabled(true);
                            btnParachute.setEnabled(true);
                            labelIsConnectedIFA.setForeground(Color.GREEN);
                        }else{
                            labelIsConnectedIFA.setForeground(Color.RED);
                        }
                    }catch (InterruptedException ex) {

                    }
                }
            }
        });
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        panelTop.setPreferredSize(new Dimension(width - 30, 90));
        panelMain.setPreferredSize(new Dimension(width - 30, height - 130));
        panelLeft.setPreferredSize(new Dimension(200, height - 140));
        panelRight.setPreferredSize(new Dimension(width - 30 - 200 - 20, height - 140));
    }

    public void showAbout(){
        String msgAbout   = "Program: UAV-GCS\n" + 
                            "Author: Jesimar da Silva Arantes\n" + 
                            "Version: 1.0.0\n" + 
                            "Date: 08/05/2018";
        JOptionPane.showMessageDialog(null, msgAbout, "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}