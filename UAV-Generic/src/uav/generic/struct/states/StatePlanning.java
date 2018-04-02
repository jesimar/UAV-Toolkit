package uav.generic.struct.states;

/**
 * Classe que modela os possíveis estados do sistema de planejador.
 * @author Jesimar S. Arantes
 */
public enum StatePlanning {
    
    WAITING, PLANNING, READY, DISABLED;
    
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
