package uav.generic.struct.states;

/**
 * Classe que modela os possíveis estados do monitoramento do drone.
 * @author Jesimar S. Arantes
 */
public enum StateMonitoring {
    
    WAITING, MONITORING, DISABLED;
    
    public static int getMode(StateMonitoring mode){
        switch (mode) {
            case WAITING:
                return 0;
            case MONITORING:
                return 1;
            case DISABLED:
                return 2;            
            default:
                return -1;
        }
    }
}
