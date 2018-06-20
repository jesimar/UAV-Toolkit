package uav.generic.struct.states;

/**
 * Classe que modela os possíveis estados do sistema de planejador.
 * @author Jesimar S. Arantes
 */
public enum StatePlanning {
    
    WAITING, PLANNING, READY, DISABLED;
    
    /**
     * Método que obtem o modo de operação do planejador.
     * @param mode - state of planner {WAITING, PLANNING, READY, DISABLED}.
     * @return {0, 1, 2, 3, -1}.
     */
    public static int getMode(StatePlanning mode){
        switch (mode) {
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
