package uav.ifa.struct;

import uav.generic.hardware.aircraft.Drone;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Failure {
    
    public double time;   
    public double lat;
    public double lng;
    public double alt_rel;
    public double levelBattery;
    public double satellitesVisible;
    public TypeFailure typeFailure;
    
    public Failure(Drone drone, TypeFailure typeFailure) {
        this.time = drone.getTime();
        this.lat = drone.getGPS().lat;
        this.lng = drone.getGPS().lng;
        this.alt_rel = drone.getBarometer().alt_rel;
        this.levelBattery = drone.getBattery().level;
        this.satellitesVisible = drone.getGPSInfo().satellitesVisible;
        this.typeFailure = typeFailure;
    }
    
    public String title(){
        return "time;lat;lng;alt_rel;level_bat;satellitesvisible;typeOfFailure";
    } 

    @Override
    public String toString() {
        return time + ";"  + lat + ";" + lng + ";" + alt_rel + ";" + levelBattery + ";" + 
                satellitesVisible + ";" + TypeFailure.getTypeFailure(typeFailure);
    }
    
}
