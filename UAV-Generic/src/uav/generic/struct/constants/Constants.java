package uav.generic.struct.constants;

/**
 * Classe que modela algumas constantes utilizadas no sistema MOSA e IFA.
 * @author Jesimar S. Arantes
 */
public class Constants {
    
    //EFFICIENCY DRONE SIMULATED
    public static final double EFFICIENCY_VERTICAL_UP_SIMULATED    = 0.500;//in % of battery/meter
    public static final double EFFICIENCY_VERTICAL_DOWN_SIMULATED  = 0.450;//in % of battery/meter
    public static final double EFFICIENCY_HORIZONTAL_NAV_SIMULATED = 0.110;//in % of battery/meter
    
    //EFFICIENCY DRONE REAL (iDroneAlpha)
    public static final double EFFICIENCY_VERTICAL_UP_REAL    = 0.400;//in % of battery/meter
    public static final double EFFICIENCY_VERTICAL_DOWN_REAL  = 0.400;//in % of battery/meter
    public static final double EFFICIENCY_HORIZONTAL_NAV_REAL = 0.068;//in % of battery/meter
    
    public static final double ONE_METER = 0.000009;//one meter in degrees (lat, lng)
    
    public static final int TIME_TO_SLEEP_BETWEEN_MSG = 100;//in milliseconds    
    public static final int TIME_TO_SLEEP_WAITING_SERVER = 1000;//in milliseconds
    public static final int TIME_TO_SLEEP_WAITING_FOR_AN_ACTION = 100;//in milliseconds
    public static final int TIME_TO_SLEEP_MONITORING_STATE_MACHINE = 100;//in milliseconds
    
    public static final int TIME_TO_SLEEP_NEXT_FIXED_ROUTE = 20000;//in milliseconds
    
}
