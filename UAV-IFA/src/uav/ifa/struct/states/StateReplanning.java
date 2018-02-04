package uav.ifa.struct.states;

/**
 *
 * @author jesimar
 */
public enum StateReplanning {
    
    WAITING, REPLANNING, READY, DISABLED;
    
    public static int getMode(StateReplanning mode){
        switch (mode) {
            case WAITING:
                return 0;
            case REPLANNING:
                return 1;
            case READY:
                return 2;
            case DISABLED:
                return 3;
            default:
                return -1;
        }
    }
}
