package uav.generic.struct;

/**
 * The class models the orientation of the aircraft.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Heading {
    
    private final int value;
    private final String typeDirection;
    private final String typeAngle;    
    
    /**
     * Class constructor.
     * @param value angle between 0 and 360
     * @param typeDirection CCW or CW
     * @param typeAngle ABSOLUTE or RELATIVE
     * @since version 2.0.0
     */
    public Heading(int value, String typeDirection, String typeAngle){
        this.value = value;
        this.typeDirection = typeDirection;
        this.typeAngle = typeAngle;
    }
    
    public double getValue() {
        return value;
    }

    public String getTypeDirection() {
        return typeDirection;
    }

    public String getTypeAngle() {
        return typeAngle;
    }

    public String string(){
        return value  + ", " + typeDirection + ", " + typeAngle;
    }

    @Override
    public String toString() {
        return "Heading: [" + value + ", " + typeDirection + ", " + typeAngle + "]";
    } 
}
