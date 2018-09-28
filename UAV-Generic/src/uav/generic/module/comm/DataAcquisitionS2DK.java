package uav.generic.module.comm;

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
 * Classe que modela toda a comunicação do sistema MOSA e IFA com o UAV-S2DK usando DroneKit.
 * @author Jesimar S. Arantes
 */
public class DataAcquisitionS2DK extends DataAcquisition implements HTTPRequest{
    
    private final String UAV_SOURCE;
    private final String HOST;  
    private final String PROTOCOL;
    private final int PORT;

    /**
     * Class constructor.
     * @param drone object drone
     * @param uavSource IFA or MOSA
     */
    public DataAcquisitionS2DK(Drone drone, String uavSource) {
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
     * @param host ip of UAV-S2DK
     * @param port network port used in UAV-S2DK
     * @param overhead file to print informations overhead
     */
    public DataAcquisitionS2DK(Drone drone, String uavSource, String host, int port, 
            PrintStream overhead) {
        this.drone = drone;
        this.UAV_SOURCE = uavSource;           
        this.HOST = host;        
        this.PORT = port;
        this.printLogOverhead = overhead;
        this.PROTOCOL = "http://";
    } 
    
    @Override
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
        
    @Override
    public boolean setWaypoint(Waypoint wp) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(new WaypointJSON(wp));
        return POST("/set-waypoint/", jsonWaypoint);
    }
    
    @Override
    public boolean appendWaypoint(Waypoint wp) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(new WaypointJSON(wp));
        return POST("/append-waypoint/", jsonWaypoint); 
    }
    
    @Override
    public boolean setMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return POST("/set-mission/", jsonMission);
    }
    
    @Override
    public boolean setMission(String missionJson) {
        return POST("/set-mission/", missionJson);
    }
    
    @Override
    public boolean appendMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return POST("/append-mission/", jsonMission);
    }
    
    @Override
    public boolean appendMission(String missionJson) {
        return POST("/append-mission/", missionJson);
    }
    
    @Override
    public boolean setVelocity(double velocity) {
        Gson gson = new Gson();
        String jsonVelocity = gson.toJson(velocity);
        return POST("/set-velocity/", jsonVelocity);
    }
    
    @Override
    public boolean setParameter(Parameter parameter) {
        Gson gson = new Gson();
        String jsonParameter = gson.toJson(new ParameterJSON(parameter));
        return POST("/set-parameter/", jsonParameter);
    }
    
    @Override
    public boolean setHeading(Heading heading) {
        Gson gson = new Gson();
        String jsonHeading = gson.toJson(new HeadingJSON(heading));
        return POST("/set-heading/", jsonHeading);
    }
    
    @Override
    public boolean setMode(String mode) {
        Gson gson = new Gson();
        String jsonMode = gson.toJson(mode);
        return POST("/set-mode/", jsonMode);
    }
    
    @Override
    public boolean setParameter(String key, double value){
        Parameter param = new Parameter(key, value);
        return setParameter(param);
    }
    
    /**
     * Este comando troca a velocidade de navegação da aeronave.
     * @param value novo valor de velocidade de navegação em cm/s.
     * @return true if correct change. false otherwise.
     */
    @Override
    public boolean setNavigationSpeed(double value){
        return setParameter("WPNAV_SPEED", value);
    }
    
    @Override
    public void getLocation() {
        String gps = GET("/get-gps/");
        drone.getSensors().getGPS().parserInfoGPS(gps);
        String barometer = GET("/get-barometer/");
        drone.getSensors().getBarometer().parserInfoBarometer(barometer);
        String msg = String.format(
                "gps (lat, lng, alt_rel, alt_abs): [%3.7f, %3.7f, %3.2f, %3.2f]", 
                drone.getSensors().getGPS().lat, 
                drone.getSensors().getGPS().lng, 
                drone.getSensors().getBarometer().alt_rel, 
                drone.getSensors().getBarometer().alt_abs);
        StandardPrints.printMsgEmph3(msg);
    }
    
    @Override
    public void getGPS() {
        String gps = GET("/get-gps/");        
        drone.getSensors().getGPS().parserInfoGPS(gps);  
        if (debug){
            StandardPrints.printMsgEmph3(gps);
        }
    }
    
    @Override
    public void getBarometer() {
        String barometer = GET("/get-barometer/");        
        drone.getSensors().getBarometer().parserInfoBarometer(barometer);  
        if (debug){
            StandardPrints.printMsgEmph3(barometer);
        }
    }
    
    @Override
    public void getBattery() {
        String battery = GET("/get-battery/");          
        drone.getSensors().getBattery().parserInfoBattery(battery);
        if (debug){
            StandardPrints.printMsgEmph3(battery);
        }
    }
    
    @Override
    public void getAttitude() {
        String attitude = GET("/get-attitude/");
        drone.getSensors().getAttitude().parserInfoAttitude(attitude); 
        if (debug){
            StandardPrints.printMsgEmph3(attitude);
        }
    }
    
    @Override
    public void getVelocity() {
        String velocity = GET("/get-velocity/");        
        drone.getSensors().getVelocity().parserInfoVelocity(velocity);
        if (debug){
            StandardPrints.printMsgEmph3(velocity);
        }
    }        
            
    @Override
    public void getHeading() {
        String heading = GET("/get-heading/");          
        drone.getSensors().getSensorUAV().parserInfoHeading(heading);  
        if (debug){
            StandardPrints.printMsgEmph3(heading);
        }
    }
    
    @Override
    public void getGroundSpeed() {
        String groundspeed = GET("/get-groundspeed/");            
        drone.getSensors().getSensorUAV().parserInfoGroundSpeed(groundspeed);   
        if (debug){
            StandardPrints.printMsgEmph3(groundspeed);
        }
    }
    
    @Override
    public void getAirSpeed() {
        String airspeed = GET("/get-airspeed/");
        drone.getSensors().getSensorUAV().parserInfoAirSpeed(airspeed);
        if (debug){
            StandardPrints.printMsgEmph3(airspeed);
        }
    }
    
    @Override
    public void getGPSInfo() {
        String gpsinfo = GET("/get-gpsinfo/");        
        drone.getSensors().getGPSInfo().parserInfoGPSInfo(gpsinfo);  
        if (debug){
            StandardPrints.printMsgEmph3(gpsinfo);
        }
    }
    
    @Override
    public void getMode() {
        String mode = GET("/get-mode/");
        drone.getSensors().getStatusUAV().parserInfoMode(mode);   
        if (debug){
            StandardPrints.printMsgEmph3(mode);
        }
    }
    
    @Override
    public void getSystemStatus() {
        String systemStatus = GET("/get-system-status/");        
        drone.getSensors().getStatusUAV().parserInfoSystemStatus(systemStatus); 
        if (debug){
            StandardPrints.printMsgEmph3(systemStatus);
        }
    }
    
    @Override
    public void getArmed() {
        String armed = GET("/get-armed/");
        drone.getSensors().getStatusUAV().parserInfoArmed(armed);  
        if (debug){
            StandardPrints.printMsgEmph3(armed);
        }
    }    
    
    @Override
    public void getIsArmable() {
        String isArmable = GET("/get-is-armable/");
        drone.getSensors().getStatusUAV().parserInfoIsArmable(isArmable);       
        if (debug){
            StandardPrints.printMsgEmph3(isArmable);
        }
    }        

    @Override
    public void getEkfOk() {
        String ekfOk = GET("/get-ekf-ok/");
        drone.getSensors().getStatusUAV().parserInfoEkfOk(ekfOk); 
        if (debug){
            StandardPrints.printMsgEmph3(ekfOk);
        }
    }
    
    @Override
    public void getNextWaypoint() {
        String nextWaypoint = GET("/get-next-waypoint/");
        drone.getInfo().parserNextWaypoint(nextWaypoint); 
        if (debug){
            StandardPrints.printMsgEmph3(nextWaypoint);
        }
    }
    
    @Override
    public void getCountWaypoint() {
        String countWaypoint = GET("/get-count-waypoint/");
        drone.getInfo().parserCountWaypoint(countWaypoint); 
        if (debug){
            StandardPrints.printMsgEmph3(countWaypoint);
        }
    }
    
    @Override
    public void getDistanceToWptCurrent() {
        String distToWptCur = GET("/get-distance-to-waypoint-current/");
        drone.getInfo().parserDistanceToCurrentWaypoint(distToWptCur); 
        if (debug){
            StandardPrints.printMsgEmph3(distToWptCur);
        }
    }
    
    @Override
    public void getDistanceToHome() {
        String distToHome = GET("/get-distance-to-home/");
        drone.getInfo().parserDistanceToHome(distToHome);
        if (debug){
            StandardPrints.printMsgEmph3(distToHome);
        }
    }
    
    @Override
    public void getHomeLocation() {
        String homeLocation = GET("/get-home-location/");
        drone.getInfo().defineHomeLocation(homeLocation);       
        if (debug){
            StandardPrints.printMsgEmph3(homeLocation);
        }
    }
    
    @Override
    public void getParameters() {
        String parameters = GET("/get-parameters/");
        drone.getInfo().defineListParameters(parameters);
        if (debug){
            StandardPrints.printMsgEmph3(parameters);
        }
    }
   
    /**
     * Request for UAV-S2DK of all sensor data.
     * FORMAT:
     * {"all-sensors": [-22.0059333, -47.8987082, 0.07, 870.0, 0.009657, 2.025, 
     * 0.004823, 116, 0.0, 0.0, 3, 10, 121, 65535, [0.0, -0.31, 0.01], 0, 0, 
     * 0.16173128321728691, null, "STABILIZE", "STANDBY", false, true, true]} 
     */
    @Override
    public void getAllInfoSensors() {
        String allinfo = GET("/get-all-sensors/");
        allinfo = allinfo.replace("[", "");
        allinfo = allinfo.replace("]", "");
        allinfo = allinfo.replace("\"", "");
        allinfo = allinfo.substring(14, allinfo.length() - 1);
        String v[] = allinfo.split(", ");
        drone.getSensors().getGPS().updateGPS(v[0], v[1]);
        drone.getSensors().getBarometer().updateBarometer(v[2], v[3]);
        drone.getSensors().getAttitude().updateAttitude(v[4], v[5], v[6]);
        drone.getSensors().getSensorUAV().setHeading(v[7]);
        drone.getSensors().getSensorUAV().setGroundspeed(v[8]);
        drone.getSensors().getSensorUAV().setAirspeed(v[9]);
        drone.getSensors().getGPSInfo().setFixType(v[10]);
        drone.getSensors().getGPSInfo().setSatellitesVisible(v[11]);
        drone.getSensors().getGPSInfo().setEPH(v[12]);
        drone.getSensors().getGPSInfo().setEPV(v[13]);
        drone.getSensors().getVelocity().updateVelocity(v[14], v[15], v[16]);
        drone.getInfo().setNextWaypoint(v[17]);
        drone.getInfo().setCountWaypoint(v[18]);
        drone.getInfo().setDistanceToHome(v[19]);
        drone.getInfo().setDistanceToCurrentWaypoint(v[20]);
        drone.getSensors().getStatusUAV().setMode(v[21]);
        drone.getSensors().getStatusUAV().setSystemStatus(v[22]);
        drone.getSensors().getStatusUAV().setArmed(v[23]);
        drone.getSensors().getStatusUAV().setIsArmable(v[24]);
        drone.getSensors().getStatusUAV().setEkfOk(v[25]);
    } 
    
    @Override
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
           
    @Override
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
}
