package uav.generic.struct;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe que agrupa todos os parâmetros do piloto automático.
 * @author Jesimar S. Arantes
 */
public class ListParameters {
    
    private final List<Parameter> listParameter;
    private final double ERROR = -111111;//represent an error

    /**
     * Class constructor.
     */
    public ListParameters() {
        this.listParameter = new LinkedList<>();        
    }

    /**
     * Converts parameters in JSON format to ListParameter values.
     * @param parameters FORMAT: {"parameters": "Key:RC7_REV Value:1.0; ... 
     * Key:WPNAV_LOIT_SPEED Value:500.0; Key:WPNAV_RADIUS Value:200.0; 
     * Value:0.699999988079; ... Key:BATT_CURR_PIN Value:12.0; "}
     */
    public void parseInfoParameters(String parameters) {
        parameters = parameters.substring(16, parameters.length() - 4);       
        parameters = parameters.replace("Key:", "");
        parameters = parameters.replace("Value:", "");
        String param[] = parameters.split("; ");
        for (String p : param) {
            String[] v = p.split(" ");
            String key = v[0];
            double value = Double.parseDouble(v[1]);
            listParameter.add(new Parameter(key, value));
        }
    }   
    
    public double getValue(String nome){
        double value = ERROR;
        for (Parameter param : listParameter){
            if (param.getKey().equals(nome)){
                value = param.getValue();
            }
        }
        return value;
    }
    
}
