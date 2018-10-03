package lib.uav.struct.states;

/**
 * The class models the possible states of the system.
 * @author Jesimar S. Arantes
 * @since  version 2.0.0
 */
public enum StateSystem {
    
    INITIALIZING, INITIALIZED, DISABLED;
    
    /**
     * Returns the operating state of the system.
     * @param state state of system {INITIALIZING, INITIALIZED, DISABLED}.
     * @return {0, 1, 2, -1}.
     * @since version 4.0.0
     */
    public static int getState(StateSystem state){
        switch (state) {
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
