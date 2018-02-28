package uav.ifa.struct;

/**
 *
 * @author jesimar
 */
public enum TypesOfFailures {
    
    FAIL_GENERIC, FAIL_ENGINE, FAIL_BATTERY, FAIL_GPS, FAIL_SYSTEM_MOSA, 
    FAIL_SYSTEM_IFA, FAIL_BASED_TIME, FAIL_AP_EMERGENCY, 
    FAIL_AP_POWEROFF, FAIL_BASED_INSERT_FAILURE;
    
    public static int getStatus(TypesOfFailures failure){
        switch (failure) {
            case FAIL_GENERIC:
                return 0;
            case FAIL_ENGINE:
                return 1;
            case FAIL_BATTERY:
                return 2;
            case FAIL_GPS:
                return 3;  
            case FAIL_SYSTEM_MOSA:
                return 4;
            case FAIL_SYSTEM_IFA:
                return 5;   
            case FAIL_BASED_TIME:
                return 6; 
            case FAIL_AP_EMERGENCY:
                return 7;
            case FAIL_AP_POWEROFF:
                return 8;
            case FAIL_BASED_INSERT_FAILURE:
                return 9;
            default:
                return -1;
        }
    }
    
    public static TypesOfFailures getTypeOfFailure(int value){        
        switch (value){
            case 0: 
                return TypesOfFailures.FAIL_GENERIC;
            case 1: 
                return TypesOfFailures.FAIL_ENGINE;
            case 2: 
                return TypesOfFailures.FAIL_BATTERY;
            case 3: 
                return TypesOfFailures.FAIL_GPS;  
            case 4: 
                return TypesOfFailures.FAIL_SYSTEM_MOSA; 
            case 5: 
                return TypesOfFailures.FAIL_SYSTEM_IFA; 
            case 6: 
                return TypesOfFailures.FAIL_BASED_TIME;
            case 7: 
                return TypesOfFailures.FAIL_AP_EMERGENCY;
            case 8: 
                return TypesOfFailures.FAIL_AP_POWEROFF;
            case 9: 
                return TypesOfFailures.FAIL_BASED_INSERT_FAILURE;
            default:
                return TypesOfFailures.FAIL_GENERIC;
        }        
    }
    
    public static String getTypeOfFailure(TypesOfFailures failure){
        switch (failure) {
            case FAIL_GENERIC:
                return "FAIL_GENERIC";
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
            case FAIL_BASED_TIME:
                return "FAIL_BASED_TIME";  
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
