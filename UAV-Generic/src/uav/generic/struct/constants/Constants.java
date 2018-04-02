package uav.generic.struct.constants;

/**
 * Classe que modela algumas constantes utilizadas no sistema MOSA e IFA.
 * @author Jesimar S. Arantes
 */
public class Constants {
    
    public static final double MAX_ALT_CONTROLLER = 50.0;//in meters
    public static final double MIN_ALT_CONTROLLER = 1.0;//in meters
    
    public static final double FACTOR_DESLC_CONTROLLER = 3.0;//in meters
    
    public static final double ONE_METER = 0.000009;
    
    public static final double HORIZONTAL_ERROR_GPS = 1.0;//in meters
    public static final double VERTICAL_ERROR_BAROMETER = 0.5;//in meters
    
    public static final int TIME_TO_SLEEP_BETWEEN_MSG = 100;//in milliseconds
    
    public static final int TIME_TO_SLEEP_WAITING_SERVER = 1000;//in milliseconds
    public static final int TIME_TO_SLEEP_WAITING_FOR_AN_ACTION = 100;//in milliseconds
    public static final int TIME_TO_SLEEP_MONITORING_STATE_MACHINE = 100;//in milliseconds
    
}
