package uav.gcs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.reader.ReaderFileMission;
import lib.uav.struct.constants.TypeAP;
import lib.uav.struct.constants.TypeAircraft;
import lib.uav.struct.constants.TypeCC;
import lib.uav.struct.constants.TypeInputCommand;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.struct.geom.PointGeo;
import lib.uav.struct.mission.Mission;
import lib.uav.util.UtilGeo;
import lib.uav.util.UtilRunScript;
import uav.gcs.struct.Drone;
import uav.gcs.communication.CommunicationIFA;
import uav.gcs.conection.db.SaveDB;
import uav.gcs.commands.keyboard.KeyboardCommands;
import uav.gcs.commands.voice.VoiceCommands;
import uav.gcs.communication.CommunicationMOSA;
import uav.gcs.plotmap.PanelPlotGoogleMaps;
import uav.gcs.plotmap.PanelPlotMission;
import uav.gcs.struct.EnabledResources;
import uav.gcs.window.LabelsInfo;

/**
 * The class used to instantiate/start GCS.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public final class GCS extends JFrame {

    public static PointGeo pointGeo;
    private final JPanel panelTop;
    private final JPanel panelTopMenu1;
    private final JPanel panelTopMenu2;
    private final JPanel panelMain;
    private final JPanel panelLeft;
    private final JPanel panelUAVStatus;
    private final JPanel panelUAVData;
    private final PanelPlotMission panelPlotMission;
    private PanelPlotGoogleMaps panelPlotGoogleMaps;
    private final JPanel panelCameraData;
    private final LabelsInfo labelsInfo;
    private final JTabbedPane tab;

    private final JButton btnGetPicture;
    private final JButton btnGetVideo;
    private final JButton btnBadWeather;
    private final JButton btnPathReplanningEmergency;
    private final JButton btnLand;
    private final JButton btnRTL;
    private final JButton btnChangeBehavior;
    private final JButton btnChangeBehaviorCircle;
    private final JButton btnChangeBehaviorTriangle;
    private final JButton btnChangeBehaviorRectangle;

    private JButton btnBuzzer;
    private JButton btnAlarm;
    private JButton btnPicture;
    private JButton btnVideo;
    private JButton btnPhotoInSequence;
    private JButton btnLED;
    private JButton btnBlink;
    private JButton btnSpraying;
    private JButton btnParachute;    
    
    private JButton btnShowRoutePlanner;
    private JButton btnShowRouteReplanner;
    private JButton btnShowRouteSimplifier;
    private JButton btnShowRouteDrone;
    private JButton btnShowPositionDrone;
    private JButton btnShowMarkers;
    private JButton btnShowMap;
    private JButton btnMaxDistReached;

    private final JButton btnKeyboardCommands;
    private final JButton btnVoiceCommands;
    
    private ImageIcon imgTextOperationMode;
    private ImageIcon imgTextDroneAP_CC;
    private ImageIcon imgTextSensors;
    private ImageIcon imgTextActuators;
    private ImageIcon imgOperationMode;
    private ImageIcon imgDrone;
    private ImageIcon imgAP;
    private ImageIcon imgCC;
    private ImageIcon imgAccessoriesTelemetry;
    private ImageIcon imgSensorGPS;
    private ImageIcon imgSensorPowerModule;
    private ImageIcon imgSensorCamera;
    private ImageIcon imgSensorTemperature;
    private ImageIcon imgSensorSonar;    
    private ImageIcon imgActuatorSpraying;
    private ImageIcon imgActuatorParachute;
    private ImageIcon imgActuatorBuzzer;
    private ImageIcon imgActuatorLED;    
    
    private final JLabel labelIsConnectedIFA;
    private final JLabel labelIsConnectedMOSA;
    private final JLabel labelIsConnectedDB;
    private final JLabel labelIsRunningPathPlanner;
    private final JLabel labelIsRunningPathReplanner;

    private final int width = 900;
    private final int height = 680;

    private final Drone drone;
    private final ReaderFileConfig config;
    private final EnabledResources enabledResources;
    private final CommunicationIFA communicationIFA;
    private final CommunicationMOSA communicationMOSA;

    private SaveDB saveDB;
    private final KeyboardCommands keyboard;
    private final VoiceCommands voice;
    
    private Mission wptsMission;
    private Mission wptsBuzzer;
    private Mission wptsCameraPicture;
    private Mission wptsCameraVideo;
    private Mission wptsCameraPhotoInSeq;
    private Mission wptsSpraying;
    
    private final int dimImg = 110;

    /**
     * Method main that start the GCS System.
     * @param args used only to see the version/help
     * @since version 2.0.0
     */
    public static void main(String[] args) {
        if (args.length == 0){
            Locale.setDefault(Locale.US);
            GCS gcs = new GCS();
        }else{
            if (args[0].equals("--version")){
                System.out.println("UAV-GCS version: 4.0.0");
                System.exit(0);
            }else if (args[0].equals("--help")){
                System.out.println("UAV-GCS:");
                System.out.println("    --version          prints the system version");
                System.out.println("    --help             prints the system help");
                System.exit(0);
            }{
                System.out.println("invalid arguments");
                System.exit(1);
            }
        }
    }

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public GCS() {
        config = ReaderFileConfig.getInstance();
        enabledResources = EnabledResources.getInstance();
        if (!config.read()) {
            System.exit(1);
        }
        if (!config.checkReadFields()) {
            System.exit(1);
        }
        if (!config.parseToVariables()) {
            System.exit(1);
        }

        UtilRunScript.execScript("../Scripts/exec-swap-mission.sh " + config.getDirMission());

        try {
            pointGeo = UtilGeo.getPointGeoBase(config.getDirFiles() + config.getFileGeoBase());
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException] pointGeo");
            ex.printStackTrace();
            System.exit(1);
        }
        
        panelCameraData = new JPanel();
        panelCameraData.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelCameraData.setBackground(new Color(255, 255, 255));
        panelCameraData.setVisible(true);

        drone = new Drone(config.getUserEmail());
        communicationIFA = new CommunicationIFA(drone);
        communicationMOSA = new CommunicationMOSA(drone, panelCameraData);
        keyboard = new KeyboardCommands(communicationIFA);
        voice = new VoiceCommands(communicationIFA);
        if (config.hasDB()) {
            saveDB = new SaveDB(config.getHostOD(), config.getPortOD());
        }

        this.setTitle("UAV-GCS");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setLocationRelativeTo(null);

        panelTop = new JPanel();
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(new Color(166, 207, 255));
        panelTop.setVisible(true);
        
        panelTopMenu1 = new JPanel();
        panelTopMenu1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTopMenu1.setBackground(new Color(95, 161, 255));
        panelTopMenu1.setVisible(true);
        
        panelTopMenu2 = new JPanel();
        panelTopMenu2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTopMenu2.setBackground(new Color(95, 161, 255));
        panelTopMenu2.setVisible(true);
        
        panelTop.add(panelTopMenu1);
        panelTop.add(panelTopMenu2);

        JButton btnAbout = new JButton("About");
        btnAbout.setPreferredSize(new Dimension(120, 25));
        btnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
        panelTopMenu1.add(btnAbout);

        JButton btnExit = new JButton("Exit");
        btnExit.setPreferredSize(new Dimension(120, 25));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelTopMenu1.add(btnExit);
        
        btnShowRoutePlanner = new JButton("Show Route Planner");
        btnShowRoutePlanner.setPreferredSize(new Dimension(170, 25));
        btnShowRoutePlanner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showRoutePlanner = !enabledResources.showRoutePlanner;
            }
        });
        panelTopMenu2.add(btnShowRoutePlanner);
        
        btnShowRouteReplanner = new JButton("Show Route Replanner");
        btnShowRouteReplanner.setPreferredSize(new Dimension(170, 25));
        btnShowRouteReplanner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showRouteReplanner = !enabledResources.showRouteReplanner;
            }
        });
        panelTopMenu2.add(btnShowRouteReplanner);
        
        btnShowRouteSimplifier = new JButton("Show Route Simplifier");
        btnShowRouteSimplifier.setPreferredSize(new Dimension(170, 25));
        btnShowRouteSimplifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showRouteSimplifier = !enabledResources.showRouteSimplifier;
            }
        });
        panelTopMenu2.add(btnShowRouteSimplifier);
        
        btnShowRouteDrone = new JButton("Show Route Drone");
        btnShowRouteDrone.setPreferredSize(new Dimension(170, 25));
        btnShowRouteDrone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showRouteDrone = !enabledResources.showRouteDrone;
            }
        });
        panelTopMenu2.add(btnShowRouteDrone);
        
        btnShowPositionDrone = new JButton("Show Position Drone");
        btnShowPositionDrone.setPreferredSize(new Dimension(170, 25));
        btnShowPositionDrone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showPositionDrone = !enabledResources.showPositionDrone;
            }
        });
        panelTopMenu2.add(btnShowPositionDrone);
        
        btnShowMarkers = new JButton("Show Markers");
        btnShowMarkers.setPreferredSize(new Dimension(170, 25));
        btnShowMarkers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showMarkers = !enabledResources.showMarkers;
            }
        });
        panelTopMenu2.add(btnShowMarkers);
        
        btnShowMap = new JButton("Show Map");
        btnShowMap.setPreferredSize(new Dimension(170, 25));
        btnShowMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showMap = !enabledResources.showMap;
            }
        });
        panelTopMenu2.add(btnShowMap);
        
        btnMaxDistReached = new JButton("Show Max Dist Reached");
        btnMaxDistReached.setPreferredSize(new Dimension(170, 25));
        btnMaxDistReached.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enabledResources.showMaxDistReached = !enabledResources.showMaxDistReached;
            }
        });
        panelTopMenu2.add(btnMaxDistReached);

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

        JButton btnClearSimulations = new JButton("Clear Simulations");
        btnClearSimulations.setPreferredSize(new Dimension(185, 25));
        btnClearSimulations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilRunScript.execClearSimulations();
            }
        });
        panelLeft.add(btnClearSimulations);

        JButton btnCopyFilesResults = new JButton("Copy File Results");
        btnCopyFilesResults.setPreferredSize(new Dimension(185, 25));
        btnCopyFilesResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilRunScript.execCopyFilesResults();
            }
        });
        panelLeft.add(btnCopyFilesResults);
        
        btnGetPicture = new JButton("Get Picture");
        btnGetPicture.setPreferredSize(new Dimension(185, 25));
        btnGetPicture.setEnabled(false);
        btnGetPicture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationMOSA.sendData(TypeInputCommand.CMD_GET_PICTURE);
            }
        });
        panelLeft.add(btnGetPicture);
        
        btnGetVideo = new JButton("Get Video");
        btnGetVideo.setPreferredSize(new Dimension(185, 25));
        btnGetVideo.setEnabled(false);
        btnGetVideo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationMOSA.sendData(TypeInputCommand.CMD_GET_VIDEO);
            }
        });
        panelLeft.add(btnGetVideo);

        btnBadWeather = new JButton("BAD-WEATHER");
        btnBadWeather.setPreferredSize(new Dimension(185, 25));
        btnBadWeather.setEnabled(false);
        btnBadWeather.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData(TypeInputCommand.CMD_BAD_WEATHER);
            }
        });
        panelLeft.add(btnBadWeather);

        btnPathReplanningEmergency = new JButton("EMERGENCY-LANDING");
        btnPathReplanningEmergency.setPreferredSize(new Dimension(185, 25));
        btnPathReplanningEmergency.setEnabled(false);
        btnPathReplanningEmergency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData(TypeInputCommand.CMD_EMERGENCY_LANDING);
            }
        });
        panelLeft.add(btnPathReplanningEmergency);

        btnLand = new JButton("LAND");
        btnLand.setPreferredSize(new Dimension(185, 25));
        btnLand.setEnabled(false);
        btnLand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData(TypeInputCommand.CMD_LAND);
            }
        });
        panelLeft.add(btnLand);

        btnRTL = new JButton("RTL");
        btnRTL.setPreferredSize(new Dimension(185, 25));
        btnRTL.setEnabled(false);
        btnRTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationIFA.sendData(TypeInputCommand.CMD_RTL);
            }
        });
        panelLeft.add(btnRTL);

        btnChangeBehavior = new JButton("CHANGE BEHAVIOR");
        btnChangeBehavior.setPreferredSize(new Dimension(185, 25));
        btnChangeBehavior.setEnabled(false);
        btnChangeBehavior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationMOSA.sendData(TypeInputCommand.CMD_CHANGE_BEHAVIOR);
            }
        });
        panelLeft.add(btnChangeBehavior);

        btnChangeBehaviorCircle = new JButton("CHANGE BEHAVIOR CIRCLE");
        btnChangeBehaviorCircle.setPreferredSize(new Dimension(185, 25));
        btnChangeBehaviorCircle.setEnabled(false);
        btnChangeBehaviorCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationMOSA.sendData(TypeInputCommand.CMD_CHANGE_BEHAVIOR_CIRCLE);
            }
        });
        panelLeft.add(btnChangeBehaviorCircle);

        btnChangeBehaviorTriangle = new JButton("CHANGE BEHAVIOR TRIANGLE");
        btnChangeBehaviorTriangle.setPreferredSize(new Dimension(185, 25));
        btnChangeBehaviorTriangle.setEnabled(false);
        btnChangeBehaviorTriangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationMOSA.sendData(TypeInputCommand.CMD_CHANGE_BEHAVIOR_TRIANGLE);
            }
        });
        panelLeft.add(btnChangeBehaviorTriangle);

        btnChangeBehaviorRectangle = new JButton("CHANGE BEHAVIOR RECTANGLE");
        btnChangeBehaviorRectangle.setPreferredSize(new Dimension(185, 25));
        btnChangeBehaviorRectangle.setEnabled(false);
        btnChangeBehaviorRectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                communicationMOSA.sendData(TypeInputCommand.CMD_CHANGE_BEHAVIOR_RECTANGLE);
            }
        });
        panelLeft.add(btnChangeBehaviorRectangle);

        if (config.hasBuzzer()) {
            btnBuzzer = new JButton("BUZZER-TURN-ON");
            btnBuzzer.setPreferredSize(new Dimension(185, 25));
            btnBuzzer.setEnabled(false);
            btnBuzzer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_BUZZER);
                }
            });
            panelLeft.add(btnBuzzer);

            btnAlarm = new JButton("BUZZER-ALARM");
            btnAlarm.setPreferredSize(new Dimension(185, 25));
            btnAlarm.setEnabled(false);
            btnAlarm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_ALARM);
                }
            });
            panelLeft.add(btnAlarm);
        }

        if (config.hasCamera()) {
            btnPicture = new JButton("CAMERA-PICTURE");
            btnPicture.setPreferredSize(new Dimension(185, 25));
            btnPicture.setEnabled(false);
            btnPicture.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_PICTURE);
                }
            });
            panelLeft.add(btnPicture);

            btnVideo = new JButton("CAMERA-VIDEO");
            btnVideo.setPreferredSize(new Dimension(185, 25));
            btnVideo.setEnabled(false);
            btnVideo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_VIDEO);
                }
            });
            panelLeft.add(btnVideo);

            btnPhotoInSequence = new JButton("CAMERA-PHOTO-IN-SEQ");
            btnPhotoInSequence.setPreferredSize(new Dimension(185, 25));
            btnPhotoInSequence.setEnabled(false);
            btnPhotoInSequence.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_PHOTO_IN_SEQUENCE);
                }
            });
            panelLeft.add(btnPhotoInSequence);
        }

        if (config.hasLED()) {
            btnLED = new JButton("LED-TURN-ON");
            btnLED.setPreferredSize(new Dimension(185, 25));
            btnLED.setEnabled(false);
            btnLED.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_LED);
                }
            });
            panelLeft.add(btnLED);

            btnBlink = new JButton("LED-BLINK");
            btnBlink.setPreferredSize(new Dimension(185, 25));
            btnBlink.setEnabled(false);
            btnBlink.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_BLINK);
                }
            });
            panelLeft.add(btnBlink);
        }

        if (config.hasSpraying()) {
            btnSpraying = new JButton("SPRAYING-START");
            btnSpraying.setPreferredSize(new Dimension(185, 25));
            btnSpraying.setEnabled(false);
            btnSpraying.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_SPRAYING);
                }
            });
            panelLeft.add(btnSpraying);
        }

        if (config.hasParachute()) {
            btnParachute = new JButton("PARACHUTE-OPEN");
            btnParachute.setPreferredSize(new Dimension(185, 25));
            btnParachute.setEnabled(false);
            btnParachute.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicationIFA.sendData(TypeInputCommand.CMD_OPEN_PARACHUTE);
                }
            });
            panelLeft.add(btnParachute);
        }

        btnKeyboardCommands = new JButton("Keyboard Commands");
        btnKeyboardCommands.setPreferredSize(new Dimension(185, 25));
        btnKeyboardCommands.setEnabled(false);
        btnKeyboardCommands.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyboard.openTheWindow();
                keyboard.listenerTheKeyboard();
            }
        });
        panelLeft.add(btnKeyboardCommands);

        btnVoiceCommands = new JButton("Voice Commands");
        btnVoiceCommands.setPreferredSize(new Dimension(185, 25));
        btnVoiceCommands.setEnabled(false);
        btnVoiceCommands.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voice.openTheWindow();
                voice.listenerTheSpeech();
            }
        });
        panelLeft.add(btnVoiceCommands);

        communicationIFA.connectServer();
        communicationIFA.receiveData();

        communicationMOSA.connectServer();
        communicationMOSA.receiveData();

        if (config.hasDB()) {
            saveDB.saveDB(drone, communicationIFA);
        }

        tab = new JTabbedPane();
        
        panelUAVStatus = new JPanel();
        panelUAVStatus.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelUAVStatus.setBackground(new Color(255, 255, 255));
        panelUAVStatus.setVisible(true);
                   
        imgTextOperationMode = new ImageIcon("resources/text-operation-mode.png");
        JLabel labelOperationMode = new JLabel(imgTextOperationMode, JLabel.CENTER);
        panelUAVStatus.add(labelOperationMode);
        
        //operation mode
        if (config.getOperationMode().equals(TypeOperationMode.SITL)){
            imgOperationMode = new ImageIcon("resources/operation-mode-sitl.png");
        }else if (config.getOperationMode().equals(TypeOperationMode.HITL)){
            imgOperationMode = new ImageIcon("resources/operation-mode-hitl.png");
        }else if (config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
            imgOperationMode = new ImageIcon("resources/operation-mode-real-flight.png");
        } 
        Image image = imgOperationMode.getImage();
        Image newimg = image.getScaledInstance(210, 90,  java.awt.Image.SCALE_SMOOTH);
        imgOperationMode = new ImageIcon(newimg); 
        JLabel labelOperationMod = new JLabel(imgOperationMode, JLabel.CENTER);
        panelUAVStatus.add(labelOperationMod);
        
        imgTextDroneAP_CC = new ImageIcon("resources/text-drone-ap-cc.png");
        JLabel labelDroneAP_CC = new JLabel(imgTextDroneAP_CC, JLabel.CENTER);
        panelUAVStatus.add(labelDroneAP_CC);
        
        //type aircraft
        if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
            imgDrone = new ImageIcon("resources/drone-quadcopter.png");
        } else if (config.getTypeAircraft().equals(TypeAircraft.FIXED_WING)){
            imgDrone = new ImageIcon("resources/drone-fixedwing.png");
        }
        image = imgDrone.getImage();
        newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
        imgDrone = new ImageIcon(newimg); 
        JLabel labelDrone = new JLabel(imgDrone, JLabel.CENTER);
        panelUAVStatus.add(labelDrone);
        
        ImageIcon imgBlank = new ImageIcon("resources/blank.png");
        image = imgBlank.getImage();
        newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imgBlank = new ImageIcon(newimg); 
        JLabel labelBlank = new JLabel(imgBlank, JLabel.CENTER);
        panelUAVStatus.add(labelBlank);
        
        //type AP
        if (config.getTypeAP().equals(TypeAP.APM)){
            imgAP = new ImageIcon("resources/ap-apm.png"); 
        }else if (config.getTypeAP().equals(TypeAP.PIXHAWK)){
            imgAP = new ImageIcon("resources/ap-pixhawk.png");
        }
        image = imgAP.getImage();
        newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
        imgAP = new ImageIcon(newimg);
        JLabel labelAP = new JLabel(imgAP, JLabel.CENTER);
        panelUAVStatus.add(labelAP);
        
        ImageIcon imgBlank2 = new ImageIcon("resources/blank.png");
        image = imgBlank2.getImage();
        newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imgBlank2 = new ImageIcon(newimg); 
        JLabel labelBlank2 = new JLabel(imgBlank2, JLabel.CENTER);
        panelUAVStatus.add(labelBlank2);      
        
        //type CC
        if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
            imgCC = new ImageIcon("resources/cc-rpi.png");
        }else if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
            imgCC = new ImageIcon("resources/cc-intel-edison.png");
        }else if (config.getTypeCC().equals(TypeCC.INTEL_GALILEO)){
            imgCC = new ImageIcon("resources/cc-intel-galileo.png");
        }else if (config.getTypeCC().equals(TypeCC.BEAGLE_BONE)){
            imgCC = new ImageIcon("resources/cc-bb-black.png");
        }else if (config.getTypeCC().equals(TypeCC.ODROID)){
            imgCC = new ImageIcon("resources/cc-odroid.png");
        }
        if (config.getOperationMode().equals(TypeOperationMode.SITL)){
            imgCC = new ImageIcon("resources/cc-notebook.png");
        }
        image = imgCC.getImage();
        newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
        imgCC = new ImageIcon(newimg);
        JLabel labelCC = new JLabel(imgCC, JLabel.CENTER);
        panelUAVStatus.add(labelCC);
        
        ImageIcon imgBlank3 = new ImageIcon("resources/blank.png");
        image = imgBlank3.getImage();
        newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imgBlank3 = new ImageIcon(newimg); 
        JLabel labelBlank3 = new JLabel(imgBlank3, JLabel.CENTER);
        panelUAVStatus.add(labelBlank3); 
            
        //type Accessories
        if (config.hasTelemetry()){
            imgAccessoriesTelemetry = new ImageIcon("resources/accessory-telemetry.png"); 
        }else {
            imgAccessoriesTelemetry = new ImageIcon("resources/blank.png");
        }
        image = imgAccessoriesTelemetry.getImage();
        newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
        imgAccessoriesTelemetry = new ImageIcon(newimg);
        JLabel labelAccessoriesTelemetry = new JLabel(imgAccessoriesTelemetry, JLabel.CENTER);
        panelUAVStatus.add(labelAccessoriesTelemetry);
        
        imgTextSensors = new ImageIcon("resources/text-sensors.png");
        JLabel labelSensors = new JLabel(imgTextSensors, JLabel.CENTER);
        panelUAVStatus.add(labelSensors);
        
        //Sensors
        if (config.hasGPS()){
            imgSensorGPS = new ImageIcon("resources/sensor-gps.png");
            image = imgSensorGPS.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgSensorGPS = new ImageIcon(newimg);
            JLabel label = new JLabel(imgSensorGPS, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        if (config.hasPowerModule()){
            imgSensorPowerModule = new ImageIcon("resources/sensor-power-module.png");
            image = imgSensorPowerModule.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgSensorPowerModule = new ImageIcon(newimg);
            JLabel label = new JLabel(imgSensorPowerModule, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        if (config.hasCamera()){
            imgSensorCamera = new ImageIcon("resources/sensor-camera.png");
            image = imgSensorCamera.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgSensorCamera = new ImageIcon(newimg);
            JLabel label = new JLabel(imgSensorCamera, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        if (config.hasSonar()){
            imgSensorSonar = new ImageIcon("resources/sensor-sonar.png");
            image = imgSensorSonar.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgSensorSonar = new ImageIcon(newimg);
            JLabel label = new JLabel(imgSensorSonar, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        if (config.hasTemperatureSensor()){
            imgSensorTemperature = new ImageIcon("resources/sensor-temperature.png");
            image = imgSensorTemperature.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgSensorTemperature = new ImageIcon(newimg);
            JLabel label = new JLabel(imgSensorTemperature, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        
        imgTextActuators = new ImageIcon("resources/text-actuators.png");
        JLabel labelActuators = new JLabel(imgTextActuators, JLabel.CENTER);
        panelUAVStatus.add(labelActuators);
        
        //Actuators
        if (config.hasSpraying()){
            imgActuatorSpraying = new ImageIcon("resources/actuator-spraying.png");
            image = imgActuatorSpraying.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgActuatorSpraying = new ImageIcon(newimg);
            JLabel label = new JLabel(imgActuatorSpraying, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        if (config.hasParachute()){
            imgActuatorParachute = new ImageIcon("resources/actuator-parachute.png");
            image = imgActuatorParachute.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgActuatorParachute = new ImageIcon(newimg);
            JLabel label = new JLabel(imgActuatorParachute, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        if (config.hasBuzzer()){
            imgActuatorBuzzer = new ImageIcon("resources/actuator-buzzer.png");
            image = imgActuatorBuzzer.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgActuatorBuzzer = new ImageIcon(newimg);
            JLabel label = new JLabel(imgActuatorBuzzer, JLabel.CENTER);
            panelUAVStatus.add(label);
        }
        if (config.hasLED()){
            imgActuatorLED = new ImageIcon("resources/actuator-led.png");
            image = imgActuatorLED.getImage();
            newimg = image.getScaledInstance(dimImg, dimImg,  java.awt.Image.SCALE_SMOOTH);
            imgActuatorLED = new ImageIcon(newimg);
            JLabel label = new JLabel(imgActuatorLED, JLabel.CENTER);
            panelUAVStatus.add(label);
        }

        panelUAVData = new JPanel();
        panelUAVData.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelUAVData.setBackground(new Color(255, 255, 255));
        panelUAVData.setVisible(true);

        panelPlotMission = new PanelPlotMission();
        panelPlotMission.waitingForRoutes();
        panelPlotMission.plotDroneInRealTime(drone);

        if (config.hasGoogleMaps()) {
            panelPlotGoogleMaps = new PanelPlotGoogleMaps();
            panelPlotGoogleMaps.init(width - 200, height - 100);
        }
        tab.add("UAV HW Status", panelUAVStatus);
        tab.add("UAV Flight Data", panelUAVData);
        tab.add("Plot Simple Map", panelPlotMission);
        if (config.hasGoogleMaps()) {
            tab.add("Plot Google Maps", panelPlotGoogleMaps);
        }
        tab.add("Camera Data", panelCameraData);

        labelIsConnectedIFA = new JLabel("Connected IFA: False");
        labelIsConnectedIFA.setPreferredSize(new Dimension(170, 20));
        labelIsConnectedIFA.setForeground(Color.RED);
        panelUAVData.add(labelIsConnectedIFA);

        labelIsConnectedMOSA = new JLabel("Connected MOSA: False");
        labelIsConnectedMOSA.setPreferredSize(new Dimension(170, 20));
        labelIsConnectedMOSA.setForeground(Color.RED);
        panelUAVData.add(labelIsConnectedMOSA);

        labelIsConnectedDB = new JLabel("Connected DB: False");
        labelIsConnectedDB.setPreferredSize(new Dimension(170, 20));
        labelIsConnectedDB.setForeground(Color.RED);
        panelUAVData.add(labelIsConnectedDB);

        labelIsRunningPathPlanner = new JLabel("Run Planner: False");
        labelIsRunningPathPlanner.setPreferredSize(new Dimension(160, 20));
        labelIsRunningPathPlanner.setForeground(Color.RED);
        panelUAVData.add(labelIsRunningPathPlanner);

        labelIsRunningPathReplanner = new JLabel("Run Replanner: False");
        labelIsRunningPathReplanner.setPreferredSize(new Dimension(160, 20));
        labelIsRunningPathReplanner.setForeground(Color.RED);
        panelUAVData.add(labelIsRunningPathReplanner);

        labelsInfo = new LabelsInfo(panelUAVData);
        this.add(panelTop);
        this.add(panelMain);

        panelMain.add(tab);

        enableComponentsInterface();
        
        this.wptsMission = new Mission();
        readerFileMission();
        panelPlotMission.setWptsMission(wptsMission);
        if (config.hasGoogleMaps()) {
            panelPlotGoogleMaps.setWptsMission(wptsMission);
        }
        if (config.hasBuzzer()){
            this.wptsBuzzer = new Mission();
            readerFileBuzzer();
            panelPlotMission.setWptsBuzzer(wptsBuzzer);
            if (config.hasGoogleMaps()) {
                panelPlotGoogleMaps.setWptsBuzzer(wptsBuzzer);
            }
        }
        if (config.hasCamera()){
            this.wptsCameraPicture = new Mission(); 
            this.wptsCameraVideo = new Mission();
            this.wptsCameraPhotoInSeq = new Mission();
            readerFileCameraPhoto();
            readerFileCameraVideo();
            readerFileCameraPhotoInSeq();
            panelPlotMission.setWptsCameraPicture(wptsCameraPicture);
            panelPlotMission.setWptsCameraPhotoInSeq(wptsCameraPhotoInSeq);
            panelPlotMission.setWptsCameraVideo(wptsCameraVideo);    
            if (config.hasGoogleMaps()) {
                panelPlotGoogleMaps.setWptsCameraPicture(wptsCameraPicture);
                panelPlotGoogleMaps.setWptsCameraPhotoInSeq(wptsCameraPhotoInSeq);
                panelPlotGoogleMaps.setWptsCameraVideo(wptsCameraVideo);
            }
        }
        if (config.hasSpraying()){
            this.wptsSpraying = new Mission(); 
            readerFileSpraying();
            panelPlotMission.setWptsSpraying(wptsSpraying);
            if (config.hasGoogleMaps()) {
                panelPlotGoogleMaps.setWptsSpraying(wptsSpraying);
            }
        }
        
        if (config.hasGoogleMaps()) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    //aguarda o google maps inicializar. se não aguardar não 
                    //pode mandar desenhar nada. alterar isso depois. 
                    //mandar aguardar até o google inicilizar como uma variavel 
                    //controlando isso.
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    panelPlotGoogleMaps.drawMap();
                    panelPlotGoogleMaps.waitingForRoutes();
                    panelPlotGoogleMaps.plotDroneInRealTime(drone);
                    panelPlotGoogleMaps.drawMarkers();
                }
            });
        }

        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                setSize(getWidth(), getHeight());
            }
        });
        this.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            tryLookAndFeel("Nimbus");
            SwingUtilities.updateComponentTreeUI(this);
        });
    }

    /**
     * Enable components on GUI
     * @since version 2.0.0
     */
    private void enableComponentsInterface() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        if (communicationIFA.isConnected()) {
                            btnBadWeather.setEnabled(true);
                            btnPathReplanningEmergency.setEnabled(true);
                            btnLand.setEnabled(true);
                            btnRTL.setEnabled(true);
                            btnChangeBehavior.setEnabled(true);
                            btnChangeBehaviorCircle.setEnabled(true);
                            btnChangeBehaviorTriangle.setEnabled(true);
                            btnChangeBehaviorRectangle.setEnabled(true);
                            if (config.hasBuzzer()) {
                                btnBuzzer.setEnabled(true);
                                btnAlarm.setEnabled(true);
                            }
                            if (config.hasCamera()) {
                                btnPicture.setEnabled(true);
                                btnVideo.setEnabled(true);
                                btnPhotoInSequence.setEnabled(true);
                            }
                            if (config.hasLED()) {
                                btnLED.setEnabled(true);
                                btnBlink.setEnabled(true);
                            }
                            if (config.hasSpraying()) {
                                btnSpraying.setEnabled(true);
                            }
                            if (config.hasParachute()) {
                                btnParachute.setEnabled(true);
                            }
                            btnKeyboardCommands.setEnabled(true);
                            btnVoiceCommands.setEnabled(true);
                            labelIsConnectedIFA.setText("Connected IFA: True");
                            labelIsConnectedIFA.setForeground(Color.GREEN);
                            if (communicationIFA.isRunningReplanner()) {
                                labelIsRunningPathReplanner.setText("Run Replanner: True");
                                labelIsRunningPathReplanner.setForeground(Color.GREEN);
                            } else {
                                labelIsRunningPathReplanner.setText("Run Replanner: False");
                                labelIsRunningPathReplanner.setForeground(Color.RED);
                            }
                            labelsInfo.updateInfoGUI(drone);
                        } else {
                            labelIsConnectedIFA.setText("Connected IFA: False");
                            labelIsConnectedIFA.setForeground(Color.RED);
                        }
                        if (communicationMOSA.isConnected()) {
                            labelIsConnectedMOSA.setText("Connected MOSA: True");
                            labelIsConnectedMOSA.setForeground(Color.GREEN);
                            if (communicationMOSA.isRunningPlanner()) {
                                labelIsRunningPathPlanner.setText("Run Planner: True");
                                labelIsRunningPathPlanner.setForeground(Color.GREEN);
                            } else {
                                labelIsRunningPathPlanner.setText("Run Planner: False");
                                labelIsRunningPathPlanner.setForeground(Color.RED);
                            }
                            if (config.hasCamera()) {
                                btnGetPicture.setEnabled(true);
                                btnGetVideo.setEnabled(true);
                            }
                        } else {
                            labelIsConnectedMOSA.setText("Connected MOSA: False");
                            labelIsConnectedMOSA.setForeground(Color.RED);
                        }
                        if (config.hasDB()) {
                            if (saveDB.isConnected()) {
                                labelIsConnectedDB.setText("Connected DB: True");
                                labelIsConnectedDB.setForeground(Color.GREEN);
                            } else {
                                labelIsConnectedDB.setText("Connected DB: False");
                                labelIsConnectedDB.setForeground(Color.RED);
                            }
                        } else {
                            labelIsConnectedDB.setText("Connected DB: False");
                            labelIsConnectedDB.setForeground(Color.RED);
                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }
    
    /**
     * Shows the message about the program
     * @since version 2.0.0
     */
    public void showAbout() {
        String msgAbout = "Program: UAV-GCS\n"
                + "Author: Jesimar da Silva Arantes\n"
                + "Version: 5.0.0\n"
                + "Date: 03/11/2018\n"
                + "Since: 17/05/2018";
        JOptionPane.showMessageDialog(null, msgAbout, "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        panelTop.setPreferredSize(new Dimension(width - 30, 70));
        panelTopMenu1.setPreferredSize(new Dimension(140, 62));
        panelTopMenu2.setPreferredSize(new Dimension(width - 140 - 20 - 30, 62));
        panelMain.setPreferredSize(new Dimension(width - 30, height - 110));
        panelLeft.setPreferredSize(new Dimension(200, height - 120));
        panelUAVStatus.setPreferredSize(new Dimension(width - 30 - 200 - 20, height - 120));
        panelUAVData.setPreferredSize(new Dimension(width - 30 - 200 - 20, height - 120));
        panelPlotMission.setNewDimensions(width - 30 - 200 - 20, height - 120);
    }
    
    public void tryLookAndFeel(String part_name) {
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        int index = -1;
        for (int i = 0; i < lookAndFeels.length; i++) {
            if (lookAndFeels[i].getName().contains(part_name)) {
                index = i;
            }
        }
        if (index != -1) {
            this.setLookAndFeel(lookAndFeels[index].getClassName());
        }
    }

    public void setLookAndFeel(String lookAndFeelds) {
        try {
            UIManager.setLookAndFeel(lookAndFeelds);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Reads the feature mission file with the mission waypoints.
     * @since version 4.0.0
     */
    private void readerFileMission(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionMission(new File(path), wptsMission);
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException]: readerFileMission()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the buzzer's trigger data.
     * @since version 4.0.0
     */
    private void readerFileBuzzer(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionBuzzer(new File(path), wptsBuzzer);
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException]: readerFileBuzzer()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the camera's trigger data (photo).
     * @since version 4.0.0
     */
    private void readerFileCameraPhoto(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionCameraPicture(new File(path), wptsCameraPicture);
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException]: readerFileCameraPhoto()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the camera's trigger data (video).
     * @since version 4.0.0
     */
    private void readerFileCameraVideo(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionCameraVideo(new File(path), wptsCameraVideo);
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException]: readerFileCameraVideo()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the camera's trigger data (photo in sequence).
     * @since version 4.0.0
     */
    private void readerFileCameraPhotoInSeq(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionCameraPhotoInSequence(new File(path), wptsCameraPhotoInSeq);
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException]: readerFileCameraVideo()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the spraying's trigger data.
     * @since version 3.0.0
     */
    private void readerFileSpraying(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionSpraying(new File(path), wptsSpraying);
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException]: readerFileSpraying()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
