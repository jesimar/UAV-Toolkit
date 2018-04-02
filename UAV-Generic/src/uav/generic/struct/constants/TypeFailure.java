package uav.generic.struct.constants;

/**
 * Classe que modela as possíveis falhas que podem ocorrer no drone.
 * @author Jesimar S. Arantes
 */
public enum TypeFailure {
    
    FAIL_ENGINE, FAIL_BATTERY, FAIL_GPS, FAIL_SYSTEM_MOSA, FAIL_SYSTEM_IFA, 
    FAIL_AP_EMERGENCY, FAIL_AP_POWEROFF, FAIL_BASED_INSERT_FAILURE;
    
    public static String getTypeFailure(TypeFailure failure){
        switch (failure) {
            case FAIL_ENGINE:
                return "FAIL_ENGINE";
            case FAIL_BATTERY:
                return "FAIL_BATTERY";
            case FAIL_GPS:
                return "FAIL_GPS";  
            case FAIL_SYSTEM_MOSA:
                return "FAIL_SYSTEM_MOSA";
            case FAIL_SYSTEM_IFA:
                return "FAIL_SYSTEM_IFA";  
            case FAIL_AP_EMERGENCY:
                return "FAIL_AP_EMERGENCY";  
            case FAIL_AP_POWEROFF:
                return "FAIL_AP_POWEROFF";  
            case FAIL_BASED_INSERT_FAILURE:
                return "FAIL_BASED_INSERT_FAILURE";  
            default:
                return "null";
        }
    }
}
