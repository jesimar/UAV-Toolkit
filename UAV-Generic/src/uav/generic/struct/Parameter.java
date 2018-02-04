package uav.generic.struct;

/**
 *
 * @author jesimar
 */
public class Parameter {
    
    private final String name;
    private final double value;
                
    public Parameter(String name, double value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
    
    public String getString(){
        return name + ", " + value;
    }

    @Override
    public String toString() {
        return "Parameter: [" + name + ", " + value + "]";
    } 
}
