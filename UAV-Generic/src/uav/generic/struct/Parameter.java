package uav.generic.struct;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Parameter {
    
    private final String key;
    private final double value;

    public Parameter(String key, double value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }
    
    public String string(){
        return key + ", " + value;
    }
    
    @Override
    public String toString() {
        return "Parameter{" + "key=" + key + ", value=" + value + '}';
    }        
    
}
