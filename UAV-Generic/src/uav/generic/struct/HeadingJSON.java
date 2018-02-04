package uav.generic.struct;

import lib.color.StandardPrints;

/**
 *
 * @author Jesimar S. Arantes
 */
public class HeadingJSON {
    
    private final Heading heading;
    
    public HeadingJSON(Heading heading){
        this.heading = heading;
    }
    
    public HeadingJSON(int value, String direction, String type){
        this.heading = new Heading(value, direction, type);        
    }
    
    public Heading getHeading(){
        return this.heading;
    }
    
    public void printHeading(){
        StandardPrints.printMsgEmph("Heading");        
        StandardPrints.printMsgEmph(heading.toString());
    }
}
