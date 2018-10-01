package uav.generic.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import uav.generic.struct.geom.PointGeo;
import uav.generic.struct.geom.Point3D;

/**
 * Class with useful methods on transformations between Cartesian and geographical coordinates.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public class UtilGeo {
        
    public final static double CIRC_EQUATORIAL = 40075017.0;
    public final static double CIRC_MERIDIONAL = 40007860.0;         
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param x the x value to convert
     * @param y the y value to convert
     * @param h the height to convert
     * @param separator the type of separator
     * @return the string with value: lat + separator + lon + separator + alt
     * @since version 1.0.0
     */
    public static String parseToGeo(PointGeo base, double x, double y, double h, String separator){
        double lat = convertYtoLatitude(base.getLat(), y);
        double lon = convertXtoLongitude(base.getLng(), base.getLat(), x);        
        double alt = base.getAlt() + h;
        return lat + separator + lon + separator + alt;
    }
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param point the point to convert
     * @return the point in geographic coordinates
     * @since version 1.0.0
     */
    public static PointGeo parseToGeo(PointGeo base, Point3D point){
        return new PointGeo(
            convertXtoLongitude(base.getLng(), base.getLat(), point.getX()), 
            convertYtoLatitude(base.getLat(), point.getY()), 
            base.getAlt() + point.getZ()
        );
    }
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param x the x value to convert
     * @param y the y value to convert
     * @param h the height to convert
     * @return the point in geographic coordinates
     * @since version 1.0.0
     */
    public static PointGeo parseToGeo1(PointGeo base, double x, double y, double h){
        return new PointGeo(
            convertXtoLongitude(base.getLng(), base.getLat(), x), 
            convertYtoLatitude(base.getLat(), y), 
            base.getAlt() + h
        );
    }
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param x the x value to convert
     * @param y the y value to convert
     * @param h the height to convert
     * @return the string with value: lat + "\t" + lon + "\t" + alt
     * @since version 1.0.0
     */
    public static String parseToGeo2(PointGeo base, double x, double y, double h){
        double lat = convertYtoLatitude(base.getLat(), y);
        double lon = convertXtoLongitude(base.getLng(), base.getLat(), x);        
        double alt = base.getAlt() + h;
        return lat + "\t" + lon + "\t" + alt;
    }
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param x the x value to convert
     * @param y the y value to convert
     * @return the string with value: lon + "," + lat + ",0 "
     * @since version 1.0.0
     */
    public static String parseToGeo3(PointGeo base, double x, double y){
        double lat = convertYtoLatitude(base.getLat(), y);
        double lon = convertXtoLongitude(base.getLng(), base.getLat(), x);
        return lon + "," + lat + ",0 ";
    }
    
    /**
     * Converts Y to latitude
     * @param latBase latitude of point base
     * @param y value of y point
     * @return the value converted to latitude
     * @since version 1.0.0
     */
    public static double convertYtoLatitude(double latBase, double y){
        return latBase + y*360/CIRC_MERIDIONAL;
    }
    
    /**
     * Converts X to longitude
     * @param lonBase longitude of point base
     * @param latBase latitude of point base
     * @param x value of x point
     * @return the value converted to longitude
     * @since version 1.0.0
     */
    public static double convertXtoLongitude(double lonBase, double latBase, double x){
        return lonBase + x*360/(CIRC_EQUATORIAL*Math.cos(latBase*Math.PI/180));
    }
    
    /**
     * Converts Geo to X
     * @param base point base
     * @param point point to convert
     * @return the converted value
     * @since version 1.0.0
     */
    public static double convertGeoToX(PointGeo base, PointGeo point){
        double eixo = CIRC_EQUATORIAL*Math.cos(base.getLat()*Math.PI/180.0);
        return (point.getLng() - base.getLng())*eixo/360.0;
    }
    
    /**
     * Converts Geo to X
     * @param base point base
     * @param longitude longitude to convert
     * @return the converted value
     * @since version 1.0.0
     */
    public static double convertGeoToX(PointGeo base, double longitude){
        double eixo = CIRC_EQUATORIAL*Math.cos(base.getLat()*Math.PI/180.0);
        return (longitude - base.getLng())*eixo/360.0;
    }
    
    /**
     * Converts Geo to Y
     * @param base point base
     * @param point point to convert
     * @return the converted value
     * @since version 1.0.0
     */
    public static double convertGeoToY(PointGeo base, PointGeo point){
        return (point.getLat() - base.getLat())*CIRC_MERIDIONAL/360.0;
    }  
    
    /**
     * Converts Geo to Y
     * @param base point base
     * @param latitude latitude to convert
     * @return the converted value
     * @since version 1.0.0
     */
    public static double convertGeoToY(PointGeo base, double latitude){
        return (latitude - base.getLat())*CIRC_MERIDIONAL/360.0;
    } 
    
    /**
     * Converts Geo to Z
     * @param base point base
     * @param point point to convert
     * @return the converted value
     * @since version 1.0.0
     */
    public static double convertGeoToZ(PointGeo base, PointGeo point){
        return point.getAlt() - base.getAlt();
    }
    
    /**
     * Converts Geo to Z
     * @param base point base
     * @param altitudeAbs absolute altitude to convert
     * @return the converted value
     * @since version 1.0.0
     */
    public static double convertGeoToZ(PointGeo base, double altitudeAbs){
        return altitudeAbs - base.getAlt();
    }
    
    /**
     * Converts Geo to Point3D.
     * @param base the point base
     * @param point the point to convert
     * @return the point converted
     * @since version 1.0.0
     */
    public static Point3D convertGeoTo3D(PointGeo base, PointGeo point){
        double x = convertGeoToX(base, point);
        double y = convertGeoToY(base, point);        
        double z = convertGeoToZ(base, point);
        return new Point3D(x, y, z);
    }
    
    /**
     * Get the point geo base.
     * @param pathFileGeoBase the path of the file geoBase.txt
     * @return a point geobase in geographical coordinates
     * @throws FileNotFoundException 
     * @since version 4.0.0
     */
    public static PointGeo getPointGeoBase(String pathFileGeoBase) throws FileNotFoundException {        
        Scanner readGeoBase = new Scanner(new File(pathFileGeoBase));
        String strGeoBase = readGeoBase.nextLine();
        strGeoBase = readGeoBase.nextLine();
        String strGeo[] = strGeoBase.split(" ");
        readGeoBase.close();
        double lon = Double.parseDouble(strGeo[0]);
        double lat = Double.parseDouble(strGeo[1]);
        double alt = Double.parseDouble(strGeo[2]);
        return new PointGeo(lon, lat, alt);        
    }
    
    /**
     * Convert the angle aviation to angle math.
     * @param heading the heading value [0 to 360]
     * @return the angle math
     * @since version 2.0.0
     */
    public static int convertAngleAviationToAngleMath(int heading){
        return (360 - heading + 90) % 360;
    }
    
}
