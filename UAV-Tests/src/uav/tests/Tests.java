package uav.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.comm.DataAcquisitionS2DK;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.Waypoint;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.hardware.aircraft.DroneRotaryWing;
import uav.generic.module.comm.DataAcquisition;
import uav.generic.struct.Heading;
import uav.generic.struct.Parameter;
import uav.generic.struct.constants.TypeAngle;
import uav.generic.struct.constants.TypeDirection;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Tests {
        
    private final long timeInit;
    private long timeActual;
    private final ReaderFileConfig config;
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    
    private PrintStream printLogAircraft;        
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        StandardPrints.printMsgEmph2("UAV-Tests");
        Tests testes = new Tests();        
        testes.init();
    }
    
    public Tests() {
        timeInit = System.currentTimeMillis();
        config = ReaderFileConfig.getInstance();  
        if (!config.read()){
            System.exit(0);
        }
        drone = new DroneRotaryWing("iDroneAlpha");
        dataAcquisition = new DataAcquisitionS2DK(drone, "IFA");
    }
    
    public void init() {
        createFileLogAircraft();
        waitingForTheServer();        
        dataAcquisition.getParameters();
//        dataAcquisition.getHomeLocation();        
        monitoringAircraft();                
        sendCommandsToTest();        
    }
    
    private void sendCommandsToTest() {
        StandardPrints.printMsgEmph("send commands to test");
        Waypoint wpt;
        Mission mission;
        System.out.println("Number test: " +config.getNumberTest() );
        try {
            switch (config.getNumberTest()){
                case 1: //SET_WAYPOINT        => OK
                    wpt = new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0);
                    dataAcquisition.setWaypoint(wpt);
                    break;
                case 2: //SET_WAYPOINT        => OK
                    wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
                    dataAcquisition.setWaypoint(wpt);
                    break;
                case 3: //SET_WAYPOINT        => OK
                    wpt = new Waypoint(TypeWaypoint.LAND, 0.0, 0.0, 0.0);
                    dataAcquisition.setWaypoint(wpt);
                    break;
                case 4: //SET_WAYPOINT        => OK
                    wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
                    dataAcquisition.setWaypoint(wpt);
                    break;
                case 5: //APPEND_WAYPOINT     => OK
                    wpt = new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 6: //APPEND_WAYPOINT     => OK
                    wpt= new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 7: //APPEND_WAYPOINT     => OK
                    wpt= new Waypoint(TypeWaypoint.LAND, 0.0, 0.0, 0.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 8: //APPEND_WAYPOINT     => OK
                    wpt= new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 9: //SET_MISSION         => OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0));
                    dataAcquisition.setMission(mission);
                    break;
                case 10: //SET_MISSION -> APPEND_WAYPOINT     => OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));        
                    dataAcquisition.setMission(mission);
                    Thread.sleep(9000);
                    wpt = new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89853005, 2.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 11: //SET_MISSION -> APPEND_WAYPOINT     => NAO OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));        
                    dataAcquisition.setMission(mission);                    
                    Thread.sleep(15000);                    
                    wpt = new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89853005, 2.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 12: //SET_MISSION -> APPEND_WAYPOINT     => OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));        
                    dataAcquisition.setMission(mission);
                    wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 13: //SET_MISSION -> APPEND_WAYPOINT     => OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));        
                    dataAcquisition.setMission(mission);
                    wpt = new Waypoint(TypeWaypoint.LAND, 0.0, 0.0, 0.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 14: //SET_MISSION -> APPEND_WAYPOINT     => OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));        
                    dataAcquisition.setMission(mission);
                    wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
                    dataAcquisition.appendWaypoint(wpt);
                    break;
                case 15: //SET MISSION -> SET_WAYPOINT     => OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89853005, 2.0));
                    dataAcquisition.setMission(mission);
                    Thread.sleep(10000);
                    wpt = new Waypoint(TypeWaypoint.GOTO,  -22.00554778, -47.89853005, 4.0);
                    dataAcquisition.setWaypoint(wpt);
                    break;
                case 16: //SET MISSION -> SET_WAYPOINT     => OK
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89853005, 2.0));
                    dataAcquisition.setMission(mission);
                    Thread.sleep(10000);
                    wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
                    dataAcquisition.setWaypoint(wpt);
                    break;
                case 17: //SET MISSION -> APPEND_MISSION
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 2.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89853005, 2.0));
                    dataAcquisition.setMission(mission);
                    Thread.sleep(20000);
                    Mission mission2 = new Mission();        
                    mission2.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00582424, -47.89874454, 3.0));
                    mission2.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89855124, 5.0));
                    mission2.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89854005, 2.0));
                    mission2.addWaypoint(new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0));
                    dataAcquisition.appendMission(mission2);
                    break;
                case 18: //SET PARAMETER -> SET PARAMETER
                    Parameter param1 = new Parameter("RTL_ALT", 1200.0);       
                    dataAcquisition.setParameter(param1);
                    Parameter param2 = new Parameter("WPNAV_RADIUS", 50.0);
                    dataAcquisition.setParameter(param2);
                    break;
                case 19: //SET MISSION -> Change Modes Autopilot:
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.01587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89853005, 2.0));
                    dataAcquisition.setMission(mission);
                    Thread.sleep(10000);
                    dataAcquisition.setMode("GUIDED");
                    Thread.sleep(4000);
                    dataAcquisition.setMode("AUTO");
                    Thread.sleep(4000);
                    dataAcquisition.setMode("CIRCLE");
                    Thread.sleep(4000);
                    dataAcquisition.setMode("LOITER");
                    break;
                case 20: //SET MISSION -> CHANGE VELOCITY
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.01587424, -47.89874454, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 5.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00604778, -47.89853005, 2.0));
                    dataAcquisition.setMission(mission);
                    Thread.sleep(5000);
                    dataAcquisition.setVelocity(20);
                    Thread.sleep(8000);
                    dataAcquisition.setVelocity(1);
                    Thread.sleep(8000);
                    dataAcquisition.setVelocity(10);
                    Thread.sleep(8000);
                    dataAcquisition.setVelocity(3);
                    Thread.sleep(8000);
                    break;
                case 21: //Teste change mode in Flight //Este teste nao esta legal nao.     
                    mission = new Mission();
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 3.0));
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 3.0));
                    dataAcquisition.setMission(mission);
                    Thread.sleep(15000);
                    dataAcquisition.setMode("CIRCLE");
                    Thread.sleep(35000);
                    dataAcquisition.setMode("RTL");
                case 22: //Angulos (Heading)
                    Waypoint w5 = new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 3.0);
                    dataAcquisition.setWaypoint(w5);
                    Thread.sleep(5000);
                    wpt = new Waypoint(TypeWaypoint.GOTO, -22.00587325, -47.89854124, 2.0);
                    dataAcquisition.appendWaypoint(wpt);  
                    Heading heading = new Heading(80, TypeDirection.CCW, TypeAngle.RELATIVE);
                    dataAcquisition.setHeading(heading); 
                    Thread.sleep(1000);
                    wpt = new Waypoint(TypeWaypoint.GOTO, -22.00587424, -47.89874454, 2.0);
                    dataAcquisition.appendWaypoint(wpt);
                    Thread.sleep(2000);
                    Heading heading1 = new Heading(140, TypeDirection.CCW, TypeAngle.RELATIVE);
                    dataAcquisition.setHeading(heading1);
                    Thread.sleep(3000);        
                    Heading heading2 = new Heading(193, TypeDirection.CCW, TypeAngle.RELATIVE);
                    dataAcquisition.setHeading(heading2);
                default:
                    break;
            }                      
        } catch (InterruptedException ex) { 
        
        }
    }
    
    private void createFileLogAircraft(){
        StandardPrints.printMsgEmph("create file log aircraft ...");
        try {
            int i = 0;
            File file;
            do{
                i++;
                file = new File("log-aircraft" + i + ".csv");  
            }while(file.exists());
            printLogAircraft = new PrintStream(file);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(0);
        } catch(Exception ex){
            StandardPrints.printMsgError2("Error [Exception]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void waitingForTheServer(){
        StandardPrints.printMsgEmph("waiting for the server ...");
        try{
            boolean serverIsRunning = dataAcquisition.serverIsRunning();
            while(!serverIsRunning){          
                StandardPrints.printMsgWarning("waiting for the server uav-services-dronekit...");
                Thread.sleep(1000);
                serverIsRunning = dataAcquisition.serverIsRunning();
            }
            Thread.sleep(3000);
        } catch(InterruptedException ex){
            StandardPrints.printMsgError2("Error [InterruptedException]: waitingForTheServer()");
            ex.printStackTrace();
            System.exit(0);
        } catch(Exception ex){
            StandardPrints.printMsgError2("Error [Exception]: waitingForTheServer()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void monitoringAircraft() {
        int freqUpdateData = 2;
        StandardPrints.printMsgEmph("monitoring aircraft");        
        int time = (int)(1000.0/freqUpdateData);
        printLogAircraft.println(drone.title()); 
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        timeActual = System.currentTimeMillis();
                        double timeDiff = (timeActual - timeInit)/1000.0;
                        drone.getInfo().setTime(timeDiff);
//                        dataAcquisition.getAllInfoSensors();
                        dataAcquisition.getGPS();
                        dataAcquisition.getBarometer();
                        dataAcquisition.getBattery();
                        dataAcquisition.getVelocity();
                        dataAcquisition.getAttitude();
                        dataAcquisition.getHeading();
                        dataAcquisition.getGroundSpeed();
                        dataAcquisition.getAirSpeed();
                        dataAcquisition.getGPSInfo();
                        dataAcquisition.getMode();
                        dataAcquisition.getSystemStatus();
                        dataAcquisition.getArmed();
                        dataAcquisition.getIsArmable();
                        dataAcquisition.getEkfOk();
                        dataAcquisition.getNextWaypoint();
                        dataAcquisition.getCountWaypoint();
                        dataAcquisition.getDistanceToHome();
                        dataAcquisition.getDistanceToWptCurrent();
                        printLogAircraft.println(drone.toString());
                        printLogAircraft.flush();
                        Thread.sleep(time);
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgError2("Error [InterruptedException] monitoringAircraft()");
                    ex.printStackTrace();
                    printLogAircraft.close();                   
                } catch (Exception ex){
                    StandardPrints.printMsgError2("Error [Exception] monitoringAircraft()");
                    ex.printStackTrace();
                }
            }
        });
    }
            
        //Teste 8:         
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_LAND, 0.0, 0.0, 0.0));
//        dataAcquisition.setMission(mission);
        
//        Waypoint w8 = new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0);
//        WaypointJSON wpt8 = new WaypointJSON(w8);
//        dataAcquisition.setWaypoint(wpt8);
//        Thread.sleep(8000);
//        Waypoint w9 = new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 6.0);
//        WaypointJSON wpt9 = new WaypointJSON(w9);
//        dataAcquisition.appendWaypoint(wpt9);
//        Thread.sleep(8000);
//        Waypoint w10 = new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0);
//        WaypointJSON wpt10 = new WaypointJSON(w10);
//        dataAcquisition.appendWaypoint(wpt10);

//        for (int i = 0; i < 1800; i++){
//            for (int j = 1; j <= 2; j++){  
//                Heading heading = new Heading(140, Direction.CCW, Angle.ABSOLUTE);
//                HeadingJSON hj = new HeadingJSON(heading);
//                dataAcquisition.setHeading(hj);
//                Thread.sleep(1000);
//            }
//        }
                
//        WaypointSimple wpt7= new WaypointSimple();
//        wpt7.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 2.0));
//        dataAcquisition.setWaypoint(wpt7);
        
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        dataAcquisition.setMission(mission);
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        WaypointSimple wpt5= new WaypointSimple();
//        wpt5.addWaypoint(new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 3.0));
//        dataAcquisition.setWaypoint(wpt5);
        
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        WaypointSimple wpt2= new WaypointSimple();
//        wpt2.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.01587424417797, -47.99874454308930, 3.0));
//        dataAcquisition.setWaypoint(wpt2);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        WaypointSimple wpt3= new WaypointSimple();
//        wpt3.addWaypoint(new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0));
//        dataAcquisition.setWaypoint(wpt3);
                 
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        Mission mission2 = new Mission();
//        mission2.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00594843,-47.89839733, 2.0));
//        mission2.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00595094,-47.89864182, 5.0));
//        mission2.addWaypoint(new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0));
//
//        dataAcquisition.setMission(mission2); 
//        WaypointSimple wpt = new WaypointSimple();
//        wpt.addWaypoint(new Waypoint(Command.CMD_LAND, -22.00587325146283, -47.89854124629112, 0.0));
//        dataAcquisition.appendWaypoint(wpt);
        
//        WaypointSimple wpt2 = new WaypointSimple();
//        wpt2.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
//        dataAcquisition.appendWaypoint(wpt2);

//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0, 0, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_LAND, -22.00587424417797, -47.89874454308930, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0, 0, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_LAND, -22.00587325146283, -47.89854124629112, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00605030421996, -47.89872355493285, 1.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));

          //Teste 20:
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        dataAcquisition.setMission(mission);
//        
//        Thread.sleep(13000);
//
//        WaypointJSON wpt3= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00704778, -47.89853005, 2.0));
//        dataAcquisition.setWaypoint(wpt3);
//        
//        Thread.sleep(7000);
//
//        WaypointJSON wpt4= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00504778, -47.89863005, 2.0));
//        dataAcquisition.setWaypoint(wpt4);
//        
//        Thread.sleep(8000);
//
//        WaypointJSON wpt5= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00404778, -47.89153005, 2.0));
//        dataAcquisition.setWaypoint(wpt5);
//        
//        Thread.sleep(10000);
//
//        WaypointJSON wpt6= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00304778, -47.89453005, 2.0));
//        dataAcquisition.setWaypoint(wpt6);
    
}
