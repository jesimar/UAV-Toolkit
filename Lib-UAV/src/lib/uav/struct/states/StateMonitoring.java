package lib.uav.struct.states;

/**
 * The class models the possible states of UAV monitoring.
 * @author Jesimar S. Arantes
 * @since  version 2.0.0
 */
public enum StateMonitoring {
    
    WAITING, MONITORING, DISABLED;
    
    /**
     * Returns the operating state of the monitoring
     * @param state state of monitoring {WAITING, MONITORING, DISABLED}.
     * @return {0, 1, 2, -1}.
     * @since version 4.0.0
     */
    public static int getState(StateMonitoring state){
        switch (state) {
            case WAITING:
                return 0;
            case MONITORING:
                return 1;
            case DISABLED:
                return 2;            
            default:
                return -1;
        }
    }
}
