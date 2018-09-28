package uav.ifa.module.decision_making;

import uav.generic.hardware.aircraft.Drone;
import uav.generic.module.comm.DataAcquisition;
import uav.generic.struct.Waypoint;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeInputCommand;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.reader.ReaderFileConfig;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Controller {
    
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final ReaderFileConfig config;
    
    public Controller(Drone drone, DataAcquisition dataAcquisition) {
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void interpretCommand(String cmd) {
        double factor = config.getDisplacFactorController();
        double oneMeter = Constants.ONE_METER;
        if (cmd.contains(TypeInputCommand.CMD_TAKEOFF)) {
            Waypoint wpt = new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 3.0);
            dataAcquisition.setWaypoint(wpt);
            return;
        }
        if (cmd.contains(TypeInputCommand.CMD_LAND)) {
            Waypoint wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
            dataAcquisition.setWaypoint(wpt);
            return;
        }
        if (cmd.contains(TypeInputCommand.CMD_UP)) {
            double lat = drone.getSensors().getGPS().lat;
            double lng = drone.getSensors().getGPS().lng;
            double newAltRel = drone.getSensors().getBarometer().alt_rel + 1;
            if (newAltRel > config.getMaxAltController()) {
                newAltRel = config.getMaxAltController();
            }
            Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, lng, newAltRel);
            dataAcquisition.setWaypoint(wpt);
            return;
        }
        if (cmd.contains(TypeInputCommand.CMD_DOWN)) {
            double lat = drone.getSensors().getGPS().lat;
            double lng = drone.getSensors().getGPS().lng;
            double newAltRel = drone.getSensors().getBarometer().alt_rel - 1;
            if (newAltRel < config.getMinAltController()) {
                newAltRel = config.getMinAltController();
            }
            Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, lng, newAltRel);
            dataAcquisition.setWaypoint(wpt);
            return;
        }
        if (cmd.contains(TypeInputCommand.CMD_LEFT)) {
            double lat = drone.getSensors().getGPS().lat;
            double newLon = drone.getSensors().getGPS().lng - factor * oneMeter;
            double alt_rel = drone.getSensors().getBarometer().alt_rel;
            Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, newLon, alt_rel);
            dataAcquisition.setWaypoint(wpt);
            return;
        }
        if (cmd.contains(TypeInputCommand.CMD_RIGHT)) {
            double lat = drone.getSensors().getGPS().lat;
            double newLon = drone.getSensors().getGPS().lng + factor * oneMeter;
            double alt_rel = drone.getSensors().getBarometer().alt_rel;
            Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, newLon, alt_rel);
            dataAcquisition.setWaypoint(wpt);
            return;
        }
        if (cmd.contains(TypeInputCommand.CMD_FORWARD)) {
            double newLat = drone.getSensors().getGPS().lat + factor * oneMeter;
            double lng = drone.getSensors().getGPS().lng;
            double alt_rel = drone.getSensors().getBarometer().alt_rel;
            Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, newLat, lng, alt_rel);
            dataAcquisition.setWaypoint(wpt);
            return;
        }
        if (cmd.contains(TypeInputCommand.CMD_BACK)) {
            double newLat = drone.getSensors().getGPS().lat - factor * oneMeter;
            double lng = drone.getSensors().getGPS().lng;
            double alt_rel = drone.getSensors().getBarometer().alt_rel;
            Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, newLat, lng, alt_rel);
            dataAcquisition.setWaypoint(wpt);
        }
    }
    
}
