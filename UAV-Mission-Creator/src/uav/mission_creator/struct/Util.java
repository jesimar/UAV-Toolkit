package uav.mission_creator.struct;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Util {
    
    public static String defineSeparator(String separator){
        if (separator.equals("space")){
            return " ";
        } else if (separator.equals("tab")){
            return "\t";
        } else if (separator.equals("semicolon")){
            return ";";
        } else if (separator.equals("comma")){
            return ",";
        } else if (separator.equals("barn")){
            return "\n";
        } else {
            return "-1";
        }
    }
    
}
