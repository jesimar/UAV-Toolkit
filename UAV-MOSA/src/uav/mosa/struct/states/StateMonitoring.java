package uav.mosa.struct.states;

/**
 *
 * @author jesimar
 */
public enum StateMonitoring {
    
    WAITING, MONITORING, DISABLED;
    
    public static int getMode(StateMonitoring mode){
        switch (mode) {
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
