package uav.ifa.module.decision_making;

import lib.uav.hardware.aircraft.Drone;
import lib.uav.module.comm.DataAcquisition;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.struct.Waypoint;
import lib.uav.struct.constants.Constants;
import lib.uav.struct.constants.TypeInputCommand;
import lib.uav.struct.constants.TypeWaypoint;
import uav.ifa.module.communication.CommunicationGCS;

/**
 * The class receives data from the keyboard or voice control and sends it to the drone.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class Controller {
    
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final ReaderFileConfig config;
    
    /**
     * Class constructor.
     * @param drone instance of the aircraft
     * @param dataAcquisition object to send commands to drone
     * @since version 4.0.0
     */
    public Controller(Drone drone, DataAcquisition dataAcquisition) {
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;
        this.config = ReaderFileConfig.getInstance();
    }
    
    /**
     * Method that interprets the received command and sends an action to the drone.
     * @param cmd the command to send an action to the drone
     * @since version 4.0.0
     * @see CommunicationGCS#receiveData()
     */
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
