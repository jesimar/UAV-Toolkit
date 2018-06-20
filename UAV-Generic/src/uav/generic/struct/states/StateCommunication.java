package uav.generic.struct.states;

/**
 * Classe que modela os possíveis estados do sistema de comunicação.
 * @author Jesimar S. Arantes
 */
public enum StateCommunication {
    
    WAITING, LISTENING, DISABLED;
    
    /**
     * Método que obtem o modo de operação do sistema de comunicação.
     * @param mode - state of communication {WAITING, LISTENING, DISABLED}.
     * @return {0, 1, 2, -1}.
     */
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
