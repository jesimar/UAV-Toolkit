package uav.ifa.struct;

import java.text.SimpleDateFormat;
import java.util.Date;
import uav.generic.struct.constants.TypeFailure;
import uav.generic.hardware.aircraft.Drone;

/**
 * The class models a critical failure by storing important information about the failure.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public class Failure {
    
    private final double time;   
    private final double lat;
    private final double lng;
    private final double alt_rel;
    private final double levelBattery;
    private final int satellitesVisible;
    private final TypeFailure typeFailure;
    
    /**
     * Class constructor.
     * @param drone drone instance
     * @param typeFailure type of failure occurred
     * @since version 1.0.0
     */
    public Failure(Drone drone, TypeFailure typeFailure) {
        this.time = drone.getInfo().getTime();
        this.lat = drone.getSensors().getGPS().lat;
        this.lng = drone.getSensors().getGPS().lng;
        this.alt_rel = drone.getSensors().getBarometer().alt_rel;
        this.levelBattery = drone.getSensors().getBattery().level;
        this.satellitesVisible = drone.getSensors().getGPSInfo().satellitesVisible;
        this.typeFailure = typeFailure;
    }
    
    /**
     * Get a string with the title
     * @return a string with the title
     * @since version 1.0.0
     */
    public String title(){
        return "date;hour;time;lat;lng;alt_rel;level_bat;satellitesvisible;typeOfFailure";
    } 

    /**
     * Get a string to print the information about the failure.
     * @return a string to print the information about the failure.
     * @since version 1.0.0
     */
    @Override
    public String toString() {
        String dateHour = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss").format(new Date());
        return String.format("%s;%.1f;%.7f;%.7f;%.2f;%.2f;%d;%s", dateHour, 
                time, lat, lng, alt_rel, levelBattery, satellitesVisible, 
                TypeFailure.getTypeFailure(typeFailure));
    }

    /**
     * Gets the type of failure
     * @return the type of failure
     * @since version 2.0.0
     */
    public TypeFailure getTypeFailure() {
        return typeFailure;
    }
    
}
