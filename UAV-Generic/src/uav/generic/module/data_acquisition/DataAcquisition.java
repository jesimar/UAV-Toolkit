package uav.generic.module.data_acquisition;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.color.StandardPrints;
import uav.generic.struct.HeadingJSON;
import uav.generic.struct.Mission;
import uav.generic.struct.ParameterJSON;
import uav.generic.struct.WaypointJSON;
import uav.hardware.aircraft.Drone;

/**
 *
 * @author jesimar
 */
public class DataAcquisition {
           
    private final Drone drone;
    private final String UAV_SOURCE;
    private final String HOST;  
    private final String PROTOCOL;
    private final int PORT;
    private final int TIME_OUT = 5;//in secunds
    private final PrintStream printLogOverhead;
    private boolean debug = false; 

    public DataAcquisition(Drone drone, String uav_source) {
        this.drone = drone;
        this.UAV_SOURCE = uav_source;
        this.HOST = "localhost";
        this.PROTOCOL = "http://";
        this.PORT = 50000;
        this.printLogOverhead = null;
    }
    
    public DataAcquisition(Drone drone, String uav_source, PrintStream overhead) {
        this.drone = drone;
        this.UAV_SOURCE = uav_source;               
        this.printLogOverhead = overhead;
        this.HOST = "localhost";
        this.PROTOCOL = "http://";
        this.PORT = 50000;        
    }  
    
    public DataAcquisition(Drone drone, String uav_source, String host, 
            String protocol, int port, PrintStream overhead) {
        this.drone = drone;
        this.UAV_SOURCE = uav_source;           
        this.HOST = host;
        this.PROTOCOL = protocol;
        this.PORT = port;
        this.printLogOverhead = overhead;
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
    
    public boolean POST(String typePost, String jsonMsg){
        try {
            long timeInit = System.currentTimeMillis();
            URL url = new URL(PROTOCOL + HOST + ":" + PORT + typePost);
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
                printLogOverhead.println("Time-in-POST(ms);" + typePost + ";" + time);
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
    
    public boolean PersistentePOST(String typePost, String jsonMsg) {
        boolean ok = false;
        long timeInitial = System.currentTimeMillis();
        long timeActual;
        long diff;
        int t = 0;
        do{
            timeActual = System.currentTimeMillis();
            ok = POST(typePost, jsonMsg);
            diff = timeActual - timeInitial;            
            if ((diff/1000) == t){
                if (t > 0){
                    StandardPrints.printMsgError("time in seconds: " + diff/1000);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {                        
                    
                    }
                }
                t++;
            }
        }while(!ok && diff < TIME_OUT * 1000);
        return ok;
    }
        
    public boolean setWaypoint(WaypointJSON wp) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(wp);
        return PersistentePOST("/set-waypoint/", jsonWaypoint);
    }
    
    public boolean appendWaypoint(WaypointJSON wp) {
        Gson gson = new Gson();
        String jsonWaypoint = gson.toJson(wp);
        return PersistentePOST("/append-waypoint/", jsonWaypoint); 
    }
    
    public boolean setMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return PersistentePOST("/set-mission/", jsonMission);
    }
    
    public boolean appendMission(Mission mission) {
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        return PersistentePOST("/append-mission/", jsonMission);
    }
    
    public boolean setVelocity(double velocity) {
        Gson gson = new Gson();
        String jsonVelocity = gson.toJson(velocity);
        return PersistentePOST("/set-velocity/", jsonVelocity);
    }
    
    public boolean setParameter(ParameterJSON parameter) {
        Gson gson = new Gson();
        String jsonParameter = gson.toJson(parameter);
        return PersistentePOST("/set-parameter/", jsonParameter);
    }
    
    public boolean setHeading(HeadingJSON heading) {
        Gson gson = new Gson();
        String jsonHeading = gson.toJson(heading);
        return PersistentePOST("/set-heading/", jsonHeading);
    }
    
    public boolean setMode(String mode) {
        Gson gson = new Gson();
        String jsonMode = gson.toJson(mode);
        return PersistentePOST("/set-mode/", jsonMode);
    }
       
    public String GET(String typeGet) {
        String inputLine = "";
        try{
            long timeInit = System.currentTimeMillis();
            URL url = new URL(PROTOCOL + HOST + ":" + PORT + typeGet);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("UAV-Source", UAV_SOURCE);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
            inputLine = in.readLine();
            in.close();
            long timeFinal = System.currentTimeMillis();
            long time = timeFinal - timeInit;
            if (printLogOverhead != null){
                printLogOverhead.println("Time-in-GET(ms);" + typeGet + ";" + time);
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
    
    public void getGPS() {
        String gps = GET("/get-gps/");        
        drone.getGPS().parserInfoGPS(gps);  
        if (debug){
            StandardPrints.printMsgEmph3(gps);
        }
    }
    
    public void getGPS2() {
        String gps = GET("/get-gps/");
        drone.getGPS().parserInfoGPS(gps);
        String msg = String.format(
                "gps (lat, lng, alt_rel, alt_abs): [%3.7f, %3.7f, %3.2f, %3.2f]", 
                drone.getGPS().lat, drone.getGPS().lng, drone.getGPS().alt_rel, 
                drone.getGPS().alt_abs);
        StandardPrints.printMsgEmph3(msg);
    }
    
    //Necessita de Power Module equipado no drone.
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
    
    public void getDistanceToHome() {
        String distToHome = GET("/get-distance-to-home/");
        drone.setDistanceToHome(distToHome);
        if (debug){
            StandardPrints.printMsgEmph3(distToHome);
        }
    } 
   
    public void getAllInfoSensors() {
        String allinfo = GET("/get-all-sensors/");
        allinfo = allinfo.replace("[", "");
        allinfo = allinfo.replace("]", "");
        allinfo = allinfo.replace("\"", "");
        allinfo = allinfo.substring(14, allinfo.length() - 1);
        String v[] = allinfo.split(", ");
        drone.getGPS().updateGPS(v[0], v[1], v[2], v[3]);
        drone.getBattery().updateBattery(v[4], v[5], v[6]);
        drone.getAttitude().updateAttitude(v[7], v[8], v[9]);
        drone.getSensorUAV().setHeading(v[10]);
        drone.getSensorUAV().setGroundspeed(v[11]);
        drone.getSensorUAV().setAirspeed(v[12]);
        drone.getGPSInfo().setFixType(v[13]);
        drone.getGPSInfo().setSatellitesVisible(v[14]);
        drone.getGPSInfo().setEPH(v[15]);
        drone.getGPSInfo().setEPV(v[16]);
        drone.getVelocity().updateVelocity(v[17], v[18], v[19]);
        drone.getStatusUAV().setMode(v[20]);
        drone.getStatusUAV().setSystemStatus(v[21]);
        drone.getStatusUAV().setArmed(v[22]);
        drone.getStatusUAV().setIsArmable(v[23]);
        drone.getStatusUAV().setEkfOk(v[24]);
    }   
}
