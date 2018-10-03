package lib.uav.struct.states;

/**
 * The class models the possible states of the replanner system.
 * @author Jesimar S. Arantes
 * @since  version 2.0.0
 */
public enum StateReplanning {
    
    WAITING, REPLANNING, READY, DISABLED;
    
    /**
     * Returns the operating state of the path replanner.
     * @param state state of replanner {WAITING, REPLANNING, READY, DISABLED}.
     * @return {0, 1, 2, 3, -1}.
     * @since version 4.0.0
     */
    public static int getState(StateReplanning state){
        switch (state) {
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
