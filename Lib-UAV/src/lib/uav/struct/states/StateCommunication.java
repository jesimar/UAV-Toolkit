package lib.uav.struct.states;

/**
 * The class models the possible states of the communication system.
 * @author Jesimar S. Arantes
 * @since  version 2.0.0
 */
public enum StateCommunication {
    
    WAITING, LISTENING, DISABLED;
    
    /**
     * Returns the operating state of the communication system.
     * @param state state of communication {WAITING, LISTENING, DISABLED}.
     * @return {0, 1, 2, -1}.
     * @since version 4.0.0
     */
    public static int getState(StateCommunication state){
        switch (state) {
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
