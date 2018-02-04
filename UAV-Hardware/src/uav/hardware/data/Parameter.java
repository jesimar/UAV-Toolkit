package uav.hardware.data;

/**
 *
 * @author jesimar
 */
public class Parameter {
    
    private String key;
    private double value;

    public Parameter() {
        
    }

    public Parameter(String key, double value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Parameter{" + "key=" + key + ", value=" + value + '}';
    }        
    
}
