package uav.generic.module.data_communication;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import lib.color.StandardPrints;
import uav.generic.struct.Heading;
import uav.generic.struct.HeadingJSON;
import uav.generic.struct.Parameter;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.ParameterJSON;
import uav.generic.struct.Waypoint;
import uav.generic.struct.WaypointJSON;
import uav.generic.hardware.aircraft.Drone;

/**
 * Classe que modela toda a comunicação do sistema MOSA e IFA com o UAV-SOA-Interface.
 * @author Jesimar S. Arantes
 */
public class DataCommunication {
           
    private final Drone drone;
    private final String UAV_SOURCE;
    private final String HOST;  
    private final String PROTOCOL;
    private final int PORT;
    private final PrintStream printLogOverhead;
    private boolean debug = false; 

    /**
     * Class constructor.
     * @param drone object drone
     * @param uavSource IFA or MOSA
     */
    public DataCommunication(Drone drone, String uavSource) {
        this.drone = drone;
        this.UAV_SOURCE = uavSource;
        this.HOST = "localhost";
        this.PROTOCOL = "http://";
        this.PORT = 50000;
        this.printLogOverhead = null;
    }
    
    /**
     * Class constructor.
     * @param drone object drone
     * @param uavSource IFA or MOSA
     * @param host ip of UAV-SOA-Interface
     * @param port network port used in UAV-SOA-Interface
     * @param overhead file to print informations overhead
     */
    public DataCommunication(Drone drone, String uavSource, String host, int port, 
            PrintStream overhead) {
        this.drone = drone;
        this.UAV_SOURCE = uavSource;           
        this.HOST = host;        
        this.PORT = port;
        this.printLogOverhead = overhead;
        this.PROTOCOL = "http://";
    } 
    
    public void setDebug(boolean debug){
        this.debug = debug;
    }
    
    public boolean serverIsRunning() {        
        try {
            URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/get-gps/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("UAV-Source", UAV_SOURCE);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in.close();
            return true;
        } catch (MalformedURLException ex) {
            StandardPrints.printMsgWarning("Warning [MalformedURLException]: serverIsRunning()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException]: serverIsRunning()");
            return false;
        }        
    }
    
    public boolean POST(String urlPost, String jsonMsg){
        try {
            long timeInit = System.currentTimeMillis();
            URL url = new URL(PROTOCOL + HOST + ":" + PORT + urlPost);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("UAV-Source", UAV_SOURCE);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            StandardPrints.printMsgEmph3(jsonMsg);
            writer.write(jsonMsg);
            writer.flush();
            writer.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = in.readLine();
            StandardPrints.printMsgEmph4(inputLine);               
            in.close();
            long timeFinal = System.currentTimeMillis();
            long time = timeFinal - timeInit;
            if (printLogOverhead != null){
                printLogOverhead.println("Time-in-POST(ms);" + urlPost + ";" + time);
                printLogOverhead.flush();
            }
            return true;
        } catch (MalformedURLException ex) {
            StandardPrints.printMsgWarning("Warning [MalformedURLException]: POST()");
            ex.printStackTrace();
            return false;
        } catch (ProtocolException ex) { 
            StandardPrints.printMsgWarning("Warning [ProtocolException]: POST()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException]: POST()");
            ex.printStackTrace();
            return false;
        }
    }
        
    public boolean setWaypoint(Waypoint wp) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(new WaypointJSON(wp));
        return POST("/set-waypoint/", jsonWaypoint);
    }
    
    public boolean appendWaypoint(Waypoint wp) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(new WaypointJSON(wp));
        return POST("/append-waypoint/", jsonWaypoint); 
    }
    
    public boolean setMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return POST("/set-mission/", jsonMission);
    }
    
    public boolean setMission(String missionJson) {
        return POST("/set-mission/", missionJson);
    }
    
    public boolean appendMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return POST("/append-mission/", jsonMission);
    }
    
    public boolean setVelocity(double velocity) {
        Gson gson = new Gson();
        String jsonVelocity = gson.toJson(velocity);
        return POST("/set-velocity/", jsonVelocity);
    }
    
    public boolean setParameter(Parameter parameter) {
        Gson gson = new Gson();
        String jsonParameter = gson.toJson(new ParameterJSON(parameter));
        return POST("/set-parameter/", jsonParameter);
    }
    
    public boolean setHeading(Heading heading) {
        Gson gson = new Gson();
        String jsonHeading = gson.toJson(new HeadingJSON(heading));
        return POST("/set-heading/", jsonHeading);
    }
    
    public boolean setMode(String mode) {
        Gson gson = new Gson();
        String jsonMode = gson.toJson(mode);
        return POST("/set-mode/", jsonMode);
    }
       
    public String GET(String urlGet) {
        String inputLine = "";
        try{
            long timeInit = System.currentTimeMillis();
            URL url = new URL(PROTOCOL + HOST + ":" + PORT + urlGet);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("UAV-Source", UAV_SOURCE);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
            inputLine = in.readLine();
            in.close();
            long timeFinal = System.currentTimeMillis();
            long time = timeFinal - timeInit;
            if (printLogOverhead != null){
                printLogOverhead.println("Time-in-GET(ms);" + urlGet + ";" + time);
                printLogOverhead.flush();
            }
            return inputLine;
        } catch (MalformedURLException ex) {
            StandardPrints.printMsgWarning("Warning [MalformedURLException]: GET()");
            ex.printStackTrace();
            return inputLine;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException]: GET()");
            ex.printStackTrace();
            return inputLine;
        } 
    }
    
    public void getLocation() {
        String gps = GET("/get-gps/");
        drone.getGPS().parserInfoGPS(gps);
        String barometer = GET("/get-barometer/");
        drone.getBarometer().parserInfoBarometer(barometer);
        String msg = String.format(
                "gps (lat, lng, alt_rel, alt_abs): [%3.7f, %3.7f, %3.2f, %3.2f]", 
                drone.getGPS().lat, drone.getGPS().lng, drone.getBarometer().alt_rel, 
                drone.getBarometer().alt_abs);
        StandardPrints.printMsgEmph3(msg);
    }
    
    public void getGPS() {
        String gps = GET("/get-gps/");        
        drone.getGPS().parserInfoGPS(gps);  
        if (debug){
            StandardPrints.printMsgEmph3(gps);
        }
    }
    
    public void getBarometer() {
        String barometer = GET("/get-barometer/");        
        drone.getBarometer().parserInfoBarometer(barometer);  
        if (debug){
            StandardPrints.printMsgEmph3(barometer);
        }
    }
    
    public void getBattery() {
        String battery = GET("/get-battery/");          
        drone.getBattery().parserInfoBattery(battery);
        if (debug){
            StandardPrints.printMsgEmph3(battery);
        }
    }
    
    public void getAttitude() {
        String attitude = GET("/get-attitude/");
        drone.getAttitude().parserInfoAttitude(attitude); 
        if (debug){
            StandardPrints.printMsgEmph3(attitude);
        }
    }
    
    public void getVelocity() {
        String velocity = GET("/get-velocity/");        
        drone.getVelocity().parserInfoVelocity(velocity);
        if (debug){
            StandardPrints.printMsgEmph3(velocity);
        }
    }        
            
    public void getHeading() {
        String heading = GET("/get-heading/");          
        drone.getSensorUAV().parserInfoHeading(heading);  
        if (debug){
            StandardPrints.printMsgEmph3(heading);
        }
    }
    
    public void getGroundSpeed() {
        String groundspeed = GET("/get-groundspeed/");            
        drone.getSensorUAV().parserInfoGroundSpeed(groundspeed);   
        if (debug){
            StandardPrints.printMsgEmph3(groundspeed);
        }
    }
    
    public void getAirSpeed() {
        String airspeed = GET("/get-airspeed/");
        drone.getSensorUAV().parserInfoAirSpeed(airspeed);
        if (debug){
            StandardPrints.printMsgEmph3(airspeed);
        }
    }
    
    public void getGPSInfo() {
        String gpsinfo = GET("/get-gpsinfo/");        
        drone.getGPSInfo().parserInfoGPSInfo(gpsinfo);  
        if (debug){
            StandardPrints.printMsgEmph3(gpsinfo);
        }
    }
    
    public void getMode() {
        String mode = GET("/get-mode/");
        drone.getStatusUAV().parserInfoMode(mode);   
        if (debug){
            StandardPrints.printMsgEmph3(mode);
        }
    }
    
    public void getSystemStatus() {
        String systemStatus = GET("/get-system-status/");        
        drone.getStatusUAV().parserInfoSystemStatus(systemStatus); 
        if (debug){
            StandardPrints.printMsgEmph3(systemStatus);
        }
    }
    
    public void getArmed() {
        String armed = GET("/get-armed/");
        drone.getStatusUAV().parserInfoArmed(armed);  
        if (debug){
            StandardPrints.printMsgEmph3(armed);
        }
    }    
    
    public void getIsArmable() {
        String isArmable = GET("/get-is-armable/");
        drone.getStatusUAV().parserInfoIsArmable(isArmable);       
        if (debug){
            StandardPrints.printMsgEmph3(isArmable);
        }
    }        

    public void getEkfOk() {
        String ekfOk = GET("/get-ekf-ok/");
        drone.getStatusUAV().parserInfoEkfOk(ekfOk); 
        if (debug){
            StandardPrints.printMsgEmph3(ekfOk);
        }
    }
    
    public void getNextWaypoint() {
        String nextWaypoint = GET("/get-next-waypoint/");
        drone.parserNextWaypoint(nextWaypoint); 
        if (debug){
            StandardPrints.printMsgEmph3(nextWaypoint);
        }
    }
    
    public void getCountWaypoint() {
        String countWaypoint = GET("/get-count-waypoint/");
        drone.parserCountWaypoint(countWaypoint); 
        if (debug){
            StandardPrints.printMsgEmph3(countWaypoint);
        }
    }
    
    public void getDistanceToWptCurrent() {
        String distToWptCur = GET("/get-distance-to-waypoint-current/");
        drone.parserDistanceToCurrentWaypoint(distToWptCur); 
        if (debug){
            StandardPrints.printMsgEmph3(distToWptCur);
        }
    }
    
    public void getDistanceToHome() {
        String distToHome = GET("/get-distance-to-home/");
        drone.parserDistanceToHome(distToHome);
        if (debug){
            StandardPrints.printMsgEmph3(distToHome);
        }
    }
    
    public void getHomeLocation() {
        String homeLocation = GET("/get-home-location/");
        drone.defineHomeLocation(homeLocation);       
        if (debug){
            StandardPrints.printMsgEmph3(homeLocation);
        }
    }
    
    public void getParameters() {
        String parameters = GET("/get-parameters/");
        drone.defineListParameters(parameters);
        if (debug){
            StandardPrints.printMsgEmph3(parameters);
        }
    }
   
    /**
     * Request for UAV-SOA-Interface of all sensor data.
     * FORMAT:
     * {"all-sensors": [-22.0059333, -47.8987082, 0.07, 870.0, 0.009657, 2.025, 
     * 0.004823, 116, 0.0, 0.0, 3, 10, 121, 65535, [0.0, -0.31, 0.01], 0, 0, 
     * 0.16173128321728691, null, "STABILIZE", "STANDBY", false, true, true]} 
     */
    public void getAllInfoSensors() {
        String allinfo = GET("/get-all-sensors/");
        allinfo = allinfo.replace("[", "");
        allinfo = allinfo.replace("]", "");
        allinfo = allinfo.replace("\"", "");
        allinfo = allinfo.substring(14, allinfo.length() - 1);
        String v[] = allinfo.split(", ");
        drone.getGPS().updateGPS(v[0], v[1]);
        drone.getBarometer().updateBarometer(v[2], v[3]);
        drone.getAttitude().updateAttitude(v[4], v[5], v[6]);
        drone.getSensorUAV().setHeading(v[7]);
        drone.getSensorUAV().setGroundspeed(v[8]);
        drone.getSensorUAV().setAirspeed(v[9]);
        drone.getGPSInfo().setFixType(v[10]);
        drone.getGPSInfo().setSatellitesVisible(v[11]);
        drone.getGPSInfo().setEPH(v[12]);
        drone.getGPSInfo().setEPV(v[13]);
        drone.getVelocity().updateVelocity(v[14], v[15], v[16]);
        drone.setNextWaypoint(v[17]);
        drone.setCountWaypoint(v[18]);
        drone.setDistanceToHome(v[19]);
        drone.setDistanceToCurrentWaypoint(v[20]);
        drone.getStatusUAV().setMode(v[21]);
        drone.getStatusUAV().setSystemStatus(v[22]);
        drone.getStatusUAV().setArmed(v[23]);
        drone.getStatusUAV().setIsArmable(v[24]);
        drone.getStatusUAV().setEkfOk(v[25]);
    } 
    
    /**
     * Este comando troca a velocidade de navegação da aeronave.
     * @param value novo valor de velocidade de navegação em cm/s.
     */
    public void changeNavigationSpeed(double value){
        Parameter param = new Parameter("WPNAV_SPEED", value);
        setParameter(param);
    }
}
