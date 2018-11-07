package lib.uav.module.comm;

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
import lib.uav.struct.Heading;
import lib.uav.struct.HeadingJSON;
import lib.uav.struct.Parameter;
import lib.uav.struct.mission.Mission;
import lib.uav.struct.ParameterJSON;
import lib.uav.struct.Waypoint;
import lib.uav.struct.WaypointJSON;
import lib.uav.hardware.aircraft.Drone;

/**
 * The class models all MOSA and IFA system communication with Autopilot.
 * Using the UAV-S2DK that use the DroneKit.
 * @author Jesimar S. Arantes
 * @see    lib.uav.module.comm.DataAcquisition
 * @see    lib.uav.module.comm.HTTPRequest
 * @since version 4.0.0
 * @see DataAcquisition
 * @see HTTPRequest
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
     * @since version 4.0.0
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
     * @since version 4.0.0
     */
    public DataAcquisitionS2DK(Drone drone, String uavSource, String host, int port, 
            PrintStream overhead) {
        this.drone = drone;
        this.UAV_SOURCE = uavSource;           
        this.HOST = host;        
        this.PORT = port;
        this.printLogOverhead = overhead;
        printLogOverhead.println("Type-of-Method;Requisition-URL;Time-In-MilliSeconds");
        printLogOverhead.flush();
        this.PROTOCOL = "http://";
    } 
    
    /**
     * Verifies if the S2DK server is running
     * @return {@code true} if server if running
     *         {@code false} otherwise
     * @since version 4.0.0
     */
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
        
    /**
     * Send a waypoint to the autopilot.
     * Note: first clear the mission and then add the new waypoint
     * @param waypoint the waypoint to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setWaypoint(Waypoint waypoint) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(new WaypointJSON(waypoint));
        return POST("/set-waypoint/", jsonWaypoint);
    }
    
    /**
     * Send a waypoint to the autopilot.
     * Note: add the new waypoint in the end of the mission
     * @param waypoint the waypoint to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean appendWaypoint(Waypoint waypoint) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(new WaypointJSON(waypoint));
        return POST("/append-waypoint/", jsonWaypoint); 
    }
    
    /**
     * Send a mission to the autopilot.
     * Note: first clear the mission and then add the new mission
     * @param mission the mission to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return POST("/set-mission/", jsonMission);
    }
    
    /**
     * Send a mission to the autopilot.
     * Note: first clear the mission and then add the new mission
     * @param missionJson the mission in JSON to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setMission(String missionJson) {
        return POST("/set-mission/", missionJson);
    }
    
    /**
     * Send a mission to the autopilot.
     * Note: add the new mission in the end of the mission
     * @param mission the mission to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean appendMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return POST("/append-mission/", jsonMission);
    }
    
    /**
     * Send a mission to the autopilot.
     * Note: add the new mission in the end of the mission
     * @param missionJson the mission JSON to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean appendMission(String missionJson) {
        return POST("/append-mission/", missionJson);
    }
    
    /**
     * Send a new heading to the autopilot.
     * @param heading the heading to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setHeading(Heading heading) {
        Gson gson = new Gson();
        String jsonHeading = gson.toJson(new HeadingJSON(heading));
        return POST("/set-heading/", jsonHeading);
    }
    
    /**
     * Send a new flight mode to the autopilot.
     * @param mode the flight mode to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setMode(String mode) {
        Gson gson = new Gson();
        String jsonMode = gson.toJson(mode);
        return POST("/set-mode/", jsonMode);
    }
    
    /**
     * Send a new parameter to the autopilot.
     * @param parameter the parameter to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setParameter(Parameter parameter) {
        Gson gson = new Gson();
        String jsonParameter = gson.toJson(new ParameterJSON(parameter));
        return POST("/set-parameter/", jsonParameter);
    }
    
    /**
     * Send a new parameter to the autopilot.
     * @param key the key of parameter to be changed
     * @param value the value of parameter to changed
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setParameter(String key, double value){
        Parameter param = new Parameter(key, value);
        return setParameter(param);
    }
    
    /**
     * Send a new velocity to the autopilot.
     * Note: change the value airspeed and groundspeed.
     * @param velocity the velocity to be sent to the autopilot
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setVelocity(double velocity) {
        Gson gson = new Gson();
        String jsonVelocity = gson.toJson(velocity);
        return POST("/set-velocity/", jsonVelocity);
    }
    
    /**
     * This command changes the navigation speed of the aircraft.
     * Note: change the parameter value WPNAV_SPEED.
     * @param value the new value of navegation speed in cm/s
     * @return {@code true} if this operation was a success
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    @Override
    public boolean setNavigationSpeed(double value){
        return setParameter("WPNAV_SPEED", value);
    }
    
    /**
     * Get the location of autopilot
     * @since version 4.0.0
     */
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
    
    /**
     * Get the GPS coordinates of GPS Sensor
     * @since version 4.0.0
     */
    @Override
    public void getGPS() {
        String gps = GET("/get-gps/");        
        drone.getSensors().getGPS().parserInfoGPS(gps);  
        if (debug){
            StandardPrints.printMsgEmph3(gps);
        }
    }
    
    /**
     * Get the barometer of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getBarometer() {
        String barometer = GET("/get-barometer/");        
        drone.getSensors().getBarometer().parserInfoBarometer(barometer);  
        if (debug){
            StandardPrints.printMsgEmph3(barometer);
        }
    }
    
    /**
     * Get the battery of power module sensor
     * @since version 4.0.0
     */
    @Override
    public void getBattery() {
        String battery = GET("/get-battery/");          
        drone.getSensors().getBattery().parserInfoBattery(battery);
        if (debug){
            StandardPrints.printMsgEmph3(battery);
        }
    }
    
    /**
     * Get the attitude of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getAttitude() {
        String attitude = GET("/get-attitude/");
        drone.getSensors().getAttitude().parserInfoAttitude(attitude); 
        if (debug){
            StandardPrints.printMsgEmph3(attitude);
        }
    }
    
    /**
     * Get the velocity of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getVelocity() {
        String velocity = GET("/get-velocity/");        
        drone.getSensors().getVelocity().parserInfoVelocity(velocity);
        if (debug){
            StandardPrints.printMsgEmph3(velocity);
        }
    }        
          
    /**
     * Get the heading of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getHeading() {
        String heading = GET("/get-heading/");          
        drone.getSensors().getSensorUAV().parserInfoHeading(heading);  
        if (debug){
            StandardPrints.printMsgEmph3(heading);
        }
    }
    
    /**
     * Get the groundspeed of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getGroundSpeed() {
        String groundspeed = GET("/get-groundspeed/");            
        drone.getSensors().getSensorUAV().parserInfoGroundSpeed(groundspeed);   
        if (debug){
            StandardPrints.printMsgEmph3(groundspeed);
        }
    }
    
    /**
     * Get the airspeed of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getAirSpeed() {
        String airspeed = GET("/get-airspeed/");
        drone.getSensors().getSensorUAV().parserInfoAirSpeed(airspeed);
        if (debug){
            StandardPrints.printMsgEmph3(airspeed);
        }
    }
    
    /**
     * Get the GPS info of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getGPSInfo() {
        String gpsinfo = GET("/get-gpsinfo/");        
        drone.getSensors().getGPSInfo().parserInfoGPSInfo(gpsinfo);  
        if (debug){
            StandardPrints.printMsgEmph3(gpsinfo);
        }
    }
    
    /**
     * Get the flight mode of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getMode() {
        String mode = GET("/get-mode/");
        drone.getSensors().getStatusUAV().parserInfoMode(mode);   
        if (debug){
            StandardPrints.printMsgEmph3(mode);
        }
    }
    
    /**
     * Get the system status of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getSystemStatus() {
        String systemStatus = GET("/get-system-status/");        
        drone.getSensors().getStatusUAV().parserInfoSystemStatus(systemStatus); 
        if (debug){
            StandardPrints.printMsgEmph3(systemStatus);
        }
    }
    
    /**
     * Get the Armed status of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getArmed() {
        String armed = GET("/get-armed/");
        drone.getSensors().getStatusUAV().parserInfoArmed(armed);  
        if (debug){
            StandardPrints.printMsgEmph3(armed);
        }
    }    
    
    /**
     * Get the IsArmable status of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getIsArmable() {
        String isArmable = GET("/get-is-armable/");
        drone.getSensors().getStatusUAV().parserInfoIsArmable(isArmable);       
        if (debug){
            StandardPrints.printMsgEmph3(isArmable);
        }
    }        

    /**
     * Get the EKF-OK status of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getEkfOk() {
        String ekfOk = GET("/get-ekf-ok/");
        drone.getSensors().getStatusUAV().parserInfoEkfOk(ekfOk); 
        if (debug){
            StandardPrints.printMsgEmph3(ekfOk);
        }
    }
    
    /**
     * Get the next waypoint of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getNextWaypoint() {
        String nextWaypoint = GET("/get-next-waypoint/");
        drone.getInfo().parserInfoNextWaypoint(nextWaypoint); 
        if (debug){
            StandardPrints.printMsgEmph3(nextWaypoint);
        }
    }
    
    /**
     * Get the count of waypoint of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getCountWaypoint() {
        String countWaypoint = GET("/get-count-waypoint/");
        drone.getInfo().parserInfoCountWaypoint(countWaypoint); 
        if (debug){
            StandardPrints.printMsgEmph3(countWaypoint);
        }
    }
    
    /**
     * Get the distance to waypoint current of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getDistanceToWptCurrent() {
        String distToWptCur = GET("/get-distance-to-waypoint-current/");
        drone.getInfo().parserInfoDistanceToCurrentWaypoint(distToWptCur); 
        if (debug){
            StandardPrints.printMsgEmph3(distToWptCur);
        }
    }
    
    /**
     * Get the distance to home of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getDistanceToHome() {
        String distToHome = GET("/get-distance-to-home/");
        drone.getInfo().parserInfoDistanceToHome(distToHome);
        if (debug){
            StandardPrints.printMsgEmph3(distToHome);
        }
    }
    
    /**
     * Get the home location of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getHomeLocation() {
        String homeLocation = GET("/get-home-location/");
        drone.getInfo().parserInfoHomeLocation(homeLocation);       
        if (debug){
            StandardPrints.printMsgEmph3(homeLocation);
        }
    }
    
    /**
     * Get the parameters of autopilot
     * @since version 4.0.0
     */
    @Override
    public void getParameters() {
        String parameters = GET("/get-parameters/");
        drone.getInfo().parserInfoListParameters(parameters);
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
     * @since version 4.0.0
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
    
    /**
     * Method that makes a POST request
     * @param urlPost the URL to the POST
     * @param jsonMsg the message in JSON to the POST
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
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
                printLogOverhead.println("POST" + ";" + urlPost + ";" + time);
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
           
    /**
     * Method that makes a GET request
     * @param urlGet the URL to the GET
     * @return the content of GET
     * @since version 4.0.0
     */
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
                printLogOverhead.println("GET" + ";" + urlGet + ";" + time);
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
