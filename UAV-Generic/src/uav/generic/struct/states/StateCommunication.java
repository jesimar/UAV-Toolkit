package uav.generic.struct.states;

/**
 *
 * @author Jesimar S. Arantes
 */
public enum StateCommunication {
    
    WAITING, LISTENING, DISABLED;
    
    public static int getMode(StateCommunication mode){
        switch (mode) {
            case WAITING:
                return 0;
            case LISTENING:
                return 1;
            case DISABLED:
                return 2;            
            default:
                return -1;
        }
    }
}