package uav.generic.util;

import uav.generic.struct.geom.Position2D;
import uav.generic.struct.geom.Position3D;

/**
 * Class with util methods on string.
 * @author Jesimar S. Arantes
 */
public class UtilString {
    
    /**
     * Method that change the type of separator.
     * @param line - Any line
     * @return the line with the separator ";" without "\t", "; ", ", ", ",", " ".
     */
    public static String changeValueSeparator(String line){
        line = line.replace("\t", ";");
        line = line.replace("; ", ";");
        line = line.replace(", ", ";");
        line = line.replace(",",  ";");
        line = line.replace(" ",  ";");
        return line;
    }
    
    /**
     * Method that return the type of separator.
     * @param separator - String {"space", "tab", "semicolon", "comma", "barn"}
     * @return Type of separator.
     */
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
    
    /**
     * Method that split the line using the separator and convert in the Position2D.
     * @param line content the line
     * @param separator - Any separator i.e: ";", ",", "\t", "; ", ", ", ...
     * @return the object Position2D.
     */
    public static Position2D split2D(String line, String separator){
        String v[] = line.split(separator);
        return new Position2D(Double.parseDouble(v[0]), Double.parseDouble(v[1]));
    }
    
    /**
     * Method that split the line using the separator and convert in the Position3D.
     * @param line content the line
     * @param separator - String {"space", "tab", "semicolon", "comma", "barn"}
     * @return the object Position3D.
     */
    public static Position3D split3D(String line, String separator){
        String v[] = line.split(separator);
        return new Position3D(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]));
    }
    
}
