package uav.generic.struct.states;

/**
 * Classe que modela os possíveis estados do sistema como um todo.
 * @author Jesimar S. Arantes
 */
public enum StateSystem {
    
    INITIALIZING, INITIALIZED, DISABLED;
    
    /**
     * Método que obtem o modo de operação do sistema.
     * @param mode - state of system {INITIALIZING, INITIALIZED, DISABLED}.
     * @return {0, 1, 2, -1}.
     */
    public static int getMode(StateSystem mode){
        switch (mode) {
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
