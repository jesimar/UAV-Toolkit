package uav.generic.struct.constants;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Constants {
    
    public static final double MAX_ALT_CONTROLLER = 50.0;//in meters
    public static final double MIN_ALT_CONTROLLER = 1.0;//in meters
    
    public static final double FACTOR_DESLC_CONTROLLER = 3.0;//in meters
    
    public static final double ONE_METER = 0.000009;
    
    public static final double HORIZONTAL_ERROR_GPS = 0.5;//in meters
    public static final double VERTICAL_ERROR_BAROMETER = 0.5;//in meters
    
    public static final int PORT_COMMUNICATION_BETWEEN_IFA_IF = 5556;
    
    public static final String HOST_IFA = "localhost";
    public static final int PORT_COMMUNICATION_BETWEEN_IFA_MOSA = 5555;
    public static final int TIME_TO_SLEEP_BETWEEN_MSG = 100;//in milliseconds
    
    public static final int TIME_TO_SLEEP_WAITING_SERVER = 1000;//in milliseconds
    public static final int TIME_TO_SLEEP_WAITING_FOR_AN_ACTION = 100;//in milliseconds
    public static final int TIME_TO_SLEEP_MONITORING_STATE_MACHINE = 100;//in milliseconds
    
}
