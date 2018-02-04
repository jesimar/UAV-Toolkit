package uav.mosa.struct.states;

/**
 *
 * @author jesimar
 */
public enum StateMOSA {
    
    INITIALIZING, INITIALIZED, DISABLED;
    
    public static int getMode(StateMOSA mode){
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
