package uav.ifa.struct.states;

/**
 *
 * @author jesimar
 */
public enum StateIFA {
    
    INITIALIZING, INITIALIZED, DISABLED;
    
    public static int getMode(StateIFA mode){
        switch (mode) {
            case INITIALIZING:
                return 0;
            case INITIALIZED:
                return 1;
            case DISABLED:
                return 2;            
            default:
                return -1;
        }
    }
}
