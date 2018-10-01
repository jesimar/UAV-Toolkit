package uav.generic.struct;

/**
 * The class models the autopilot parameter values.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Parameter {
    
    private final String key;
    private final double value;

    /**
     * Class constructor.
     * @param key attribute (name of parameter)
     * @param value value of key attribute
     * @since version 2.0.0
     */
    public Parameter(String key, double value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key
     * @return the key
     * @since version 2.0.0
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the value
     * @return the value
     * @since version 2.0.0
     */
    public double getValue() {
        return value;
    }
    
    /**
     * Gets the string with: key + ", " + value
     * @return the string with: key + ", " + value
     * @since version 2.0.0
     */
    public String string(){
        return key + ", " + value;
    }
    
    /**
     * Gets the string with: "Parameter{" + "key=" + key + ", value=" + value + '}'
     * @return the string with: "Parameter{" + "key=" + key + ", value=" + value + '}'
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return "Parameter{" + "key=" + key + ", value=" + value + '}';
    }        
    
}
