package uav.generic.struct.constants;

/**
 * The class models the types of possible failures that can occur in the drone.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public enum TypeFailure {
    
    FAIL_ENGINE, FAIL_LOW_BATTERY, FAIL_BATTERY_OVERHEATING, FAIL_GPS, FAIL_SYSTEM_MOSA, 
    FAIL_SYSTEM_IFA, FAIL_AP_CRITICAL, FAIL_AP_EMERGENCY, FAIL_AP_POWEROFF, 
    FAIL_BASED_INSERT_FAILURE, FAIL_BAD_WEATHER, FAIL_PROXIMITY_TO_GROUND, 
    FAIL_INTRUDER_AIRCRAFT;
    
    /**
     * Returns a string with the name of the failure that occurred.
     * @param typeFailure type of failure occurred
     * @return name of the failure
     */
    public static String getTypeFailure(TypeFailure typeFailure){
        switch (typeFailure) {
            case FAIL_ENGINE:
                return "FAIL_ENGINE";
            case FAIL_LOW_BATTERY:
                return "FAIL_LOW_BATTERY";
            case FAIL_BATTERY_OVERHEATING:
                return "FAIL_BATTERY_OVERHEATING";
            case FAIL_GPS:
                return "FAIL_GPS";  
            case FAIL_SYSTEM_MOSA:
                return "FAIL_SYSTEM_MOSA";
            case FAIL_SYSTEM_IFA:
                return "FAIL_SYSTEM_IFA";
            case FAIL_AP_CRITICAL:
                return "FAIL_AP_CRITICAL";  
            case FAIL_AP_EMERGENCY:
                return "FAIL_AP_EMERGENCY";  
            case FAIL_AP_POWEROFF:
                return "FAIL_AP_POWEROFF";  
            case FAIL_BASED_INSERT_FAILURE:
                return "FAIL_BASED_INSERT_FAILURE";
            case FAIL_BAD_WEATHER:
                return "FAIL_BAD_WEATHER";
            case FAIL_PROXIMITY_TO_GROUND:
                return "FAIL_PROXIMITY_TO_GROUND";
            case FAIL_INTRUDER_AIRCRAFT:
                return "FAIL_INTRUDER_AIRCRAFT";
            default:
                return "null";
        }
    }
}
