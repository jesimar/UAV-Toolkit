package uav.hardware.data;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jesimar
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
    
}
