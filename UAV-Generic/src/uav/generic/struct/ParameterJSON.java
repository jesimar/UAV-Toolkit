package uav.generic.struct;

import lib.color.StandardPrints;

/**
 * The class models the parameter values transmitted in JSON format.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class ParameterJSON {
    
    /**
     * List with some parameters that I usually change.
     * Format: KEY VALUE      
     *   RTL_ALT 1200.0           //in cm
     *   WPNAV_RADIUS 50.0        //in cm
     *   WPNAV_SPEED 200.0        //in cm/s
     *   WPNAV_SPEED_UP 50.0      //in cm/s
     *   WPNAV_SPEED_DN 50.0      //in cm/s         
     *   WPNAV_LOIT_SPEED 300.0   //in cm/s
     *   LAND_SPEED 50.0          //in cm/s
     */
    
    private final Parameter parameter;
    
    /**
     * Class constructor.
     * @param parameter objetct parameter
     * @since version 2.0.0
     */
    public ParameterJSON(Parameter parameter){
        this.parameter = parameter;
    }
    
    /**
     * Class constructor.
     * @param key attribute (name of parameter)
     * @param value value of key attribute
     * @since version 2.0.0
     */
    public ParameterJSON(String key, double value){
        this.parameter = new Parameter(key, value);        
    }
    
    /**
     * Gets a paramter.
     * @return the parameter
     * @since version 2.0.0
     */
    public Parameter getParameter(){
        return this.parameter;
    }
    
    /**
     * Print the parameter info.
     * @since version 2.0.0
     */
    public void printParameter(){
        StandardPrints.printMsgEmph("Parameter");        
        StandardPrints.printMsgEmph(parameter.toString());
    }
}
