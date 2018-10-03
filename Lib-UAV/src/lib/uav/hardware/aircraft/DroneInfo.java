package lib.uav.hardware.aircraft;

import lib.uav.struct.HomeLocation;
import lib.uav.struct.ListParameters;

/**
 * The class synthesizes a set of drone information.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class DroneInfo {
    
    double time;                                //in seconds
    int nextWaypoint;
    int countWaypoint;
    double distanceToHome;                      //in meters
    double distanceToCurrentWaypoint;           //in meters
    double estimatedTimeToDoRTL;                //in seconds
    double estimatedConsumptionBatForRTL;       //in percentage   
//    double estimatedTimeToDoEmergency;          //in seconds
//    double estimatedConsumptionBatForEmergency; //in percentage
    double estimatedMaxDistReached;             //in meters
    double estimatedMaxTimeFlight;              //in seconds
    String typeFailure;    
    HomeLocation homeLocation;
    ListParameters listParameters;

    /**
     * Class constructor.
     * @since 4.0.0
     */
    public DroneInfo() {
        time = 0.0;
        nextWaypoint = 0;
        countWaypoint = 0;
        distanceToHome = 0.0;
        distanceToCurrentWaypoint = 0.0;
        estimatedTimeToDoRTL = 0.0;
        estimatedConsumptionBatForRTL = 0.0;
        estimatedMaxDistReached = 0.0;
        estimatedMaxTimeFlight = 0.0;
        typeFailure = "NONE";
        homeLocation = new HomeLocation();
        listParameters = new ListParameters();
    }
    
    public double getTime() {
        return time;
    }
    
    public int getNextWaypoint() {
        return nextWaypoint;
    }
    
    public int getCountWaypoint() {
        return countWaypoint;
    }

    public double getDistanceToHome() {
        return distanceToHome;
    }
    
    public double getDistanceToCurrentWaypoint() {
        return distanceToCurrentWaypoint;
    }
    
    public double getEstimatedTimeForRLT(){
        return estimatedTimeToDoRTL;
    }
    
    public double getEstimatedConsumptionBatForRTL(){
        return estimatedConsumptionBatForRTL;
    }

    public double getEstimatedTimeToDoRTL() {
        return estimatedTimeToDoRTL;
    }

    public double getEstimatedMaxDistReached() {
        return estimatedMaxDistReached;
    }

    public double getEstimatedMaxTimeFlight() {
        return estimatedMaxTimeFlight;
    }
    
    public HomeLocation getHomeLocation() {
        return homeLocation;
    }  
    
    public ListParameters getListParameters() {
        return listParameters;
    }
    
    public void setTime(double time){
        this.time = time;
    }
    
    public void setNextWaypoint(String next) {
        this.nextWaypoint = Integer.parseInt(next);
    } 
    
    public void setCountWaypoint(String count) {
        this.countWaypoint = Integer.parseInt(count);
    }
    
    public void setEstimatedTimeToDoRTL(double time) {
        this.estimatedTimeToDoRTL = time;
    }
    
    public void setEstimatedConsumptionBatForRTL(double consumptionBattery){
        this.estimatedConsumptionBatForRTL = consumptionBattery;
    }

    public void setTypeFailure(String typeFailure) {
        this.typeFailure = typeFailure;
    }        

    public void setEstimatedMaxTimeFlight(double estimatedMaxTimeFlight) {
        this.estimatedMaxTimeFlight = estimatedMaxTimeFlight;
    }

    public void setEstimatedMaxDistReached(double estimatedMaxDistReached) {
        this.estimatedMaxDistReached = estimatedMaxDistReached;
    }
    
    public void setDistanceToHome(String dist) {
        try{
            this.distanceToHome = Double.parseDouble(dist);
        }catch (NumberFormatException ex){
            this.distanceToHome = -1;
        }
    }
    
    public void setDistanceToCurrentWaypoint(String dist) {
        try{
            this.distanceToCurrentWaypoint = Double.parseDouble(dist);
        }catch (NumberFormatException ex){
            this.distanceToCurrentWaypoint = -1;
        }
    }
    
    /**
     * Parser the info of home location of the drone
     * @param home the home containing the coordinate latitude, longitude and altitude.
     * @since version 2.0.0
     */
    public void parserInfoHomeLocation(String home) {
        homeLocation.parserInfoHomeLocation(home);
    }
    
    /**
     * Converts parameters in JSON format to ListParameter values.
     * @param parameters FORMAT: {"parameters": "Key:RC7_REV Value:1.0; ... 
     * Key:WPNAV_LOIT_SPEED Value:500.0; Key:WPNAV_RADIUS Value:200.0; 
     * Value:0.699999988079; ... Key:BATT_CURR_PIN Value:12.0; "}
     * @since version 2.0.0
     */
    public void parserInfoListParameters(String parameters) {
        listParameters.parserInfoListParameters(parameters);
    }
    
    /**
     * Converts line in JSON format to NextWaypoint values.
     * @param line FORMAT: {"next-waypoint": 5}
     * @since version 4.0.0
     */
    public void parserInfoNextWaypoint(String line) {
        line = line.substring(18, line.length() - 1);
        this.nextWaypoint = Integer.parseInt(line);
    }
    
    /**
     * Converts line in JSON format to CountWaypoint values.
     * @param line FORMAT: {"count-waypoint": 28}
     * @since version 4.0.0
     */
    public void parserInfoCountWaypoint(String line) {
        line = line.substring(19, line.length() - 1);
        this.countWaypoint = Integer.parseInt(line);
    }
    
    /**
     * Converts line in JSON format to DistanceToCurrentWaypoint values.
     * @param line FORMAT: {"distance-to-wpt-current": 4.32}
     * @since version 4.0.0
     */
    public void parserInfoDistanceToCurrentWaypoint(String line) {
        line = line.substring(28, line.length() - 1);
        this.distanceToCurrentWaypoint = Double.parseDouble(line);
    }

    /**
     * Converts line in JSON format to DistanceToHome values.
     * @param line FORMAT: {"distance-to-home": 20.50}
     * @since version 4.0.0
     */
    public void parserInfoDistanceToHome(String line) {
        line = line.substring(21, line.length() - 1);
        this.distanceToHome = Double.parseDouble(line);
    }

}
