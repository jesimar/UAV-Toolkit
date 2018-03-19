package uav.generic.struct;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ListParameters {
    
    private final List<Parameter> listParameter;

    public ListParameters() {
        this.listParameter = new LinkedList();        
    }

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
    
    private final double ERROR = -111111;//represent an error
    
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
