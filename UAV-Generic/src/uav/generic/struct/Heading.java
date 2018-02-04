package uav.generic.struct;

/**
 *
 * @author jesimar
 */
public class Heading {
    
    private final int value;
    private final String direction;
    private final String angle;    
    
    /**
     * Contrutor da classe.
     * @param value angle between 0 and 360
     * @param direction "ccw" or "cw"
     * @param angle "absolute" or "relative"
     */
    public Heading(int value, String direction, String angle){
        this.value = value;
        this.direction = direction;
        this.angle = angle;
    }
    
    public double getValue() {
        return value;
    }

    public String getDirection() {
        return direction;
    }

    public String getAngle() {
        return angle;
    }

    public String getString(){
        return value  + ", " + direction + ", " + angle;
    }

    @Override
    public String toString() {
        return "Heading: [" + value + ", " + direction + ", " + angle + "]";
    } 
}
