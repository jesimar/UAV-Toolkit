package uav.generic.struct;

import lib.color.StandardPrints;

/**
 * The class models the orientation of the aircraft for transmission via JSON.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class HeadingJSON {
    
    private final Heading heading;
    
    /**
     * Class constructor.
     * @param heading angle of heading of the aircraft
     * @since version 2.0.0
     */
    public HeadingJSON(Heading heading){
        this.heading = heading;
    }
    
    /**
     * Class constructor.
     * @param value angle between 0 and 360
     * @param typeDirection CCW or CW
     * @param typeAngle ABSOLUTE or RELATIVE
     * @since version 2.0.0
     */
    public HeadingJSON(int value, String typeDirection, String typeAngle){
        this.heading = new Heading(value, typeDirection, typeAngle);        
    }
    
    /**
     * Gets the heading
     * @return the heading
     * @since version 2.0.0
     */
    public Heading getHeading(){
        return this.heading;
    }
    
    /**
     * Print the heading values
     * @since version 2.0.0
     */
    public void printHeading(){
        StandardPrints.printMsgEmph("Heading");        
        StandardPrints.printMsgEmph(heading.toString());
    }
}
