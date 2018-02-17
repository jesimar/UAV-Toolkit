package uav.ifa.struct;

import uav.hardware.aircraft.Drone;

/**
 *
 * @author jesimar
 */
public class Failure {
    
    public double time;   
    public double lat;
    public double lng;
    public double alt_rel;
    public double level_bat;
    public double satellitesvisible;
    public TypesOfFailures typeOfFailure;
    
    public Failure(Drone drone, TypesOfFailures typeOfFailure) {
        this.time = drone.getTime();
        this.lat = drone.getGPS().lat;
        this.lng = drone.getGPS().lng;
        this.alt_rel = drone.getGPS().alt_rel;
        this.level_bat = drone.getBattery().level;
        this.satellitesvisible = drone.getGPSInfo().satellitesVisible;
        this.typeOfFailure = typeOfFailure;
    }
    
    public String title(){
        return "time;lat;lng;alt_rel;level_bat;satellitesvisible;typeOfFailure";
    } 

    @Override
    public String toString() {
        return time + ";"  + lat + ";" + lng + ";" + alt_rel + ";" + level_bat + 
                ";" + satellitesvisible + ";" + TypesOfFailures.getTypeOfFailure(typeOfFailure);
    }
    
}
