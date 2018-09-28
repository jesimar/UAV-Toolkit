package uav.generic.hardware.aircraft;

import uav.generic.struct.HomeLocation;
import uav.generic.struct.ListParameters;

/**
 *
 * @author Jesimar S. Arantes
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

    public DroneInfo() {
        time = 0.0;
        nextWaypoint = 0;
        countWaypoint = 0;
        distanceToHome = 0.0;
        distanceToCurrentWaypoint = 0.0;
        estimatedTimeToDoRTL = 0.0;
        estimatedConsumptionBatForRTL = 0.0;
//        estimatedTimeToDoEmergency = 0.0;
//        estimatedConsumptionBatForEmergency = 0.0;
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

//    public double getEstimatedTimeToDoEmergency() {
//        return estimatedTimeToDoEmergency;
//    }
//
//    public double getEstimatedConsumptionBatForEmergency() {
//        return estimatedConsumptionBatForEmergency;
//    }

    public double getEstimatedMaxDistReached() {
        return estimatedMaxDistReached;
    }

    public double getEstimatedMaxTimeFlight() {
        return estimatedMaxTimeFlight;
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
     * Converts line in JSON format to NextWaypoint values.
     * @param line FORMAT: {"next-waypoint": 5}
     */
    public void parserNextWaypoint(String line) {
        line = line.substring(18, line.length() - 1);
        this.nextWaypoint = Integer.parseInt(line);
    }
    
    /**
     * Converts line in JSON format to CountWaypoint values.
     * @param line FORMAT: {"count-waypoint": 28}
     */
    public void parserCountWaypoint(String line) {
        line = line.substring(19, line.length() - 1);
        this.countWaypoint = Integer.parseInt(line);
    }
    
    /**
     * Converts line in JSON format to DistanceToCurrentWaypoint values.
     * @param line FORMAT: {"distance-to-wpt-current": 4.32}
     */
    public void parserDistanceToCurrentWaypoint(String line) {
        line = line.substring(28, line.length() - 1);
        this.distanceToCurrentWaypoint = Double.parseDouble(line);
    }

    /**
     * Converts line in JSON format to DistanceToHome values.
     * @param line FORMAT: {"distance-to-home": 20.50}
     */
    public void parserDistanceToHome(String line) {
        line = line.substring(21, line.length() - 1);
        this.distanceToHome = Double.parseDouble(line);
    }

    public void setTypeFailure(String typeFailure) {
        this.typeFailure = typeFailure;
    }        

    public HomeLocation getHomeLocation() {
        return homeLocation;
    }  
    
    public ListParameters getListParameters() {
        return listParameters;
    }
    
    public void defineHomeLocation(double lat, double lng, double alt) {
        homeLocation.setHomeLocation(lat, lng, alt);
    }
    
    public void defineHomeLocation(String home) {
        homeLocation.parserInfoHomeLocation(home);
    }
    
    public void defineListParameters(String parameters) {
        listParameters.parseInfoParameters(parameters);
    }

//    public void setEstimatedTimeToDoEmergency(double estimatedTimeToDoEmergency) {
//        this.estimatedTimeToDoEmergency = estimatedTimeToDoEmergency;
//    }
//
//    public void setEstimatedConsumptionBatForEmergency(double estimatedConsumptionBat) {
//        this.estimatedConsumptionBatForEmergency = estimatedConsumptionBat;
//    }

    public void setEstimatedMaxTimeFlight(double estimatedMaxTimeFlight) {
        this.estimatedMaxTimeFlight = estimatedMaxTimeFlight;
    }

    public void setEstimatedMaxDistReached(double estimatedMaxDistReached) {
        this.estimatedMaxDistReached = estimatedMaxDistReached;
    }

}
