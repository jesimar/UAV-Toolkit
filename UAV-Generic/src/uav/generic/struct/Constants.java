package uav.generic.struct;

/**
 *
 * @author jesimar
 */
public class Constants {
    
    public static final String NAME_DRONE_ARARINHA     = "ARARINHA";
    public static final String NAME_DRONE_iDRONE_ALPHA = "iDRONE_ALPHA";
    
    public static final String SYS_EXEC_CONTROLLER       = "CONTROLLER";
    public static final String SYS_EXEC_FIXED_ROUTE      = "FIXED_ROUTE";
    public static final String SYS_EXEC_PLANNER          = "PLANNER";
    
    public static final String SYS_EXEC_REPLANNER      = "REPLANNER";
    
    public static final String METHOD_PLANNER_HGA4m    = "HGA4m";
    
    public static final String METHOD_REPLANNER_GH4s   = "GH4s";
    public static final String METHOD_REPLANNER_GA4s   = "GA4s";
    public static final String METHOD_REPLANNER_MPGA4s = "MPGA4s";
    public static final String METHOD_REPLANNER_DE4s   = "DE4s";
    
    public static final String CMD_NONE   = "NONE";
    public static final String CMD_LAND   = "LAND";
    public static final String CMD_RTL    = "RTL";
    
    public static final String TYPE_ALTITUDE_DECAY_CONSTANTE  = "CONSTANT";
    public static final String TYPE_ALTITUDE_DECAY_LINEAR     = "LINEAR";
    public static final String TYPE_ALTITUDE_DECAY_LOG        = "LOG";
    
    public static final String TYPE_CONTROLLER_VOICE     = "VOICE_COMMANDS";
    public static final String TYPE_CONTROLLER_TEXT      = "TEXT_COMMANDS";
    public static final String TYPE_CONTROLLER_KEYBOARD  = "KEYBOARD_COMMANDS";
    
    public static final double MAX_ALT = 40.0;
    public static final double MIN_ALT = 1.0;
    
    public static final int FACTOR_DESLC = 3;
    public static final double ONE_METER = 0.000009;
    
    
}
