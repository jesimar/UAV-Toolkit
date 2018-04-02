package uav.generic.struct;

import lib.color.StandardPrints;

/**
 * Classe que modela o orientação da aeronave para transmissão via JSON.
 * @author Jesimar S. Arantes
 */
public class HeadingJSON {
    
    private final Heading heading;
    
    /**
     * Class constructor.
     * @param heading angle of heading of the aircraft
     */
    public HeadingJSON(Heading heading){
        this.heading = heading;
    }
    
    /**
     * Class constructor.
     * @param value angle between 0 and 360
     * @param typeDirection CCW or CW
     * @param typeAngle ABSOLUTE or RELATIVE
     */
    public HeadingJSON(int value, String typeDirection, String typeAngle){
        this.heading = new Heading(value, typeDirection, typeAngle);        
    }
    
    public Heading getHeading(){
        return this.heading;
    }
    
    public void printHeading(){
        StandardPrints.printMsgEmph("Heading");        
        StandardPrints.printMsgEmph(heading.toString());
    }
}
