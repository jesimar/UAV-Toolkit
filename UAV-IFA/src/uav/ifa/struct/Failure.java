package uav.ifa.struct;

import java.text.SimpleDateFormat;
import java.util.Date;
import uav.generic.struct.constants.TypeFailure;
import uav.generic.hardware.aircraft.Drone;

/**
 * Classe que modela uma falha crítica armazendando informações importantes sobre a falha.
 * @author Jesimar S. Arantes
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
     */
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
        return "date;hour;time;lat;lng;alt_rel;level_bat;satellitesvisible;typeOfFailure";
    } 

    @Override
    public String toString() {
        String dateHour = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss").format(new Date());
        return String.format("%s;%.1f;%.7f;%.7f;%.2f;%.2f;%d;%s", dateHour, 
                time, lat, lng, alt_rel, levelBattery, satellitesVisible, 
                TypeFailure.getTypeFailure(typeFailure));
    }

    public TypeFailure getTypeFailure() {
        return typeFailure;
    }
    
}
