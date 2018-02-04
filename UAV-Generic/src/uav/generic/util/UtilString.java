package uav.generic.util;

import uav.generic.struct.Position2D;
import uav.generic.struct.Position3D;

/**
 *
 * @author jesimar
 */
public class UtilString {
    
    public static String changeValueSeparator(String line){
        line = line.replace("\t", ";");
        line = line.replace("; ", ";");
        line = line.replace(", ", ";");
        line = line.replace(",",  ";");
        return line;
    }
    
    public static String defineSeparator(String separator){
        switch (separator) {
            case "space":
                return " ";
            case "tab":
                return "\t";
            case "semicolon":
                return ";";
            case "comma":
                return ",";
            case "barn":
                return "\n";
            default:
                return "-1";
        }
    }
    
    public static Position2D split2D(String str, String separator){
        String v[] = str.split(separator);
        return new Position2D(Double.parseDouble(v[0]), Double.parseDouble(v[1]));
    }
    
    public static Position3D split3D(String str, String separator){
        String v[] = str.split(separator);
        return new Position3D(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]));
    }
    
}
