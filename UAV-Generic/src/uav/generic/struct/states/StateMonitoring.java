package uav.generic.struct.states;

/**
 * Classe que modela os possíveis estados do monitoramento do drone.
 * @author Jesimar S. Arantes
 */
public enum StateMonitoring {
    
    WAITING, MONITORING, DISABLED;
    
    /**
     * Método que obtem o modo de operação do monitoramento.
     * @param mode - state of monitoring {WAITING, MONITORING, DISABLED}.
     * @return {0, 1, 2, -1}.
     */
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
