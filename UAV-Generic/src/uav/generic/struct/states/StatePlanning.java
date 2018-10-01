package uav.generic.struct.states;

/**
 * The class models the possible states of the planner system.
 * @author Jesimar S. Arantes
 * @since  version 2.0.0
 */
public enum StatePlanning {
    
    WAITING, PLANNING, READY, DISABLED;
    
    /**
     * Returns the operating state of the path planner.
     * @param state state of planner {WAITING, PLANNING, READY, DISABLED}.
     * @return {0, 1, 2, 3, -1}.
     * @since version 4.0.0
     */
    public static int getState(StatePlanning state){
        switch (state) {
            case WAITING:
                return 0;
            case PLANNING:
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
