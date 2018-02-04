package uav.generic.struct;

import lib.color.StandardPrints;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ParameterJSON {
    
    /**
     * Lista com alguns parametros que costumo alterar.
     * Name Value      
     *   RTL_ALT 1200.0           //1200cm de altura para voltar o RTL
     *   WPNAV_RADIUS 50.0        //  50cm de raio para antender um waypoint
     *   WPNAV_SPEED 100.0        // 300cm/s velocidade horizontal
     *   WPNAV_SPEED_UP 100.0     // 150cm/s velocidade para cima 
     *   WPNAV_SPEED_DN 100.0     // 120cm/s velocidade para baixo         
     *   WPNAV_LOIT_SPEED 300.0   // 300cm/s velocidade horizontal em modo loiter
     *   LAND_SPEED 50.0          //  50cm/s velocidade de pouso
     */
    
    private final Parameter parameter;
    
    public ParameterJSON(Parameter parameter){
        this.parameter = parameter;
    }
    
    public ParameterJSON(String parameter, double value){
        this.parameter = new Parameter(parameter, value);        
    }
    
    public Parameter getParameter(){
        return this.parameter;
    }
    
    public void printParameter(){
        StandardPrints.printMsgEmph("Parameter");        
        StandardPrints.printMsgEmph(parameter.toString());
    }
}
