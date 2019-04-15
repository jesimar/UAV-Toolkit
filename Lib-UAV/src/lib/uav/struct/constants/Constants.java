package lib.uav.struct.constants;

/**
 * The class models some constants used in the MOSA and IFA system.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Constants {
    
    //EFFICIENCY DRONE SIMULATED (arducopter simulated)
    public static final double EFFICIENCY_VERTICAL_UP_SIMULATED    = 0.500;//in % of battery/meter
    public static final double EFFICIENCY_VERTICAL_DOWN_SIMULATED  = 0.450;//in % of battery/meter
    public static final double EFFICIENCY_HORIZONTAL_NAV_SIMULATED = 0.110;//in % of battery/meter
    public static final double EFFICIENCY_FLIGHT_TIME_SIMULATED    = 0.216;//in % of battery/second
    
    //EFFICIENCY DRONE REAL (iDroneAlpha and iDroneBeta)
    public static final double EFFICIENCY_VERTICAL_UP_REAL    = 0.481;//in % of battery/meter -> foi atualizado era 0.400
    public static final double EFFICIENCY_VERTICAL_DOWN_REAL  = 0.337;//in % of battery/meter -> foi atualizado era 0.400
    public static final double EFFICIENCY_HORIZONTAL_NAV_REAL = 0.077;//in % of battery/meter -> foi atualizado era 0.111 e antes era 0.068
    public static final double EFFICIENCY_FLIGHT_TIME_REAL    = 0.151;//in % of battery/second -> foi atualizado era 0.162
    
    public static final double ONE_METER = 0.000009;//one meter in degrees (lat, lng)
    
    public static final int TIME_TO_SLEEP_BETWEEN_MSG = 200;//in milliseconds    
    public static final int TIME_TO_SLEEP_WAITING_SERVER = 1000;//in milliseconds
    public static final int TIME_TO_SLEEP_WAITING_FOR_AN_ACTION = 100;//in milliseconds
    public static final int TIME_TO_SLEEP_MONITORING_STATE_MACHINE = 100;//in milliseconds
    
    public static final int TIME_TO_SLEEP_NEXT_FIXED_ROUTE = 20000;//in milliseconds
    
}
