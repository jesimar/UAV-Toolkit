package uav.generic.struct;

import lib.color.StandardPrints;

/**
 *
 * @author Jesimar S. Arantes
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
    
    public ParameterJSON(Parameter parameter){
        this.parameter = parameter;
    }
    
    public ParameterJSON(String key, double value){
        this.parameter = new Parameter(key, value);        
    }
    
    public Parameter getParameter(){
        return this.parameter;
    }
    
    public void printParameter(){
        StandardPrints.printMsgEmph("Parameter");        
        StandardPrints.printMsgEmph(parameter.toString());
    }
}
