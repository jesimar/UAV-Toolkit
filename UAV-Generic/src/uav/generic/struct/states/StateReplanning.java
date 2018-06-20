package uav.generic.struct.states;

/**
 * Classe que modela os possíveis estados do sistema replanejador.
 * @author Jesimar S. Arantes
 */
public enum StateReplanning {
    
    WAITING, REPLANNING, READY, DISABLED;
    
    /**
     * Método que obtem o modo de operação do replanejador.
     * @param mode - state of replanner {WAITING, REPLANNING, READY, DISABLED}.
     * @return {0, 1, 2, 3, -1}.
     */
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
