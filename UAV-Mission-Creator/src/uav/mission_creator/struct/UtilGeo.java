package uav.mission_creator.struct;

import uav.mission_creator.struct.geom.Line3D;
import uav.mission_creator.struct.geom.Point3D;
import uav.mission_creator.struct.geom.PointGeo;
import java.io.PrintStream;
import java.util.Locale;

/**
 * Class with useful methods on transformations between Cartesian and geographical coordinates.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class UtilGeo {
        
    public final static double CIRC_EQUATORIAL = 40075017.0;//40075000.0;
    public final static double CIRC_MERIDIONAL = 40007860.0;//40000000.0;            
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param x the x value to convert
     * @param y the y value to convert
     * @param h the height to convert
     * @param separator the type of separator
     * @return the string with value: lat + separator + lon + separator + alt
     * @since version 2.0.0
     */
    public static String parseToGeo(PointGeo base, double x, double y, double h, String separator){
        double lat = converteYtoLatitude(base.getLat(), y);
        double lon = converteXtoLongitude(base.getLng(), base.getLat(), x);        
        double alt = base.getAlt() + h;
        return lat + separator + lon + separator + alt;
    }
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param point the point to convert
     * @return the point in geographic coordinates
     * @since version 2.0.0
     */
    public static PointGeo parseToGeo(PointGeo base, Point3D point){
        return new PointGeo(
            converteXtoLongitude(base.getLng(), base.getLat(), point.getX()), 
            converteYtoLatitude(base.getLat(), point.getY()), 
            base.getAlt() + point.getH()
        );
    }
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param x the x value to convert
     * @param y the y value to convert
     * @param h the height to convert
     * @return the point in geographic coordinates
     * @since version 2.0.0
     */
    public static PointGeo parseToGeo1(PointGeo base, double x, double y, double h){
        return new PointGeo(
            converteXtoLongitude(base.getLng(), base.getLat(), x), 
            converteYtoLatitude(base.getLat(), y), 
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
     * @since version 2.0.0
     */
    public static String parseToGeo2(PointGeo base, double x, double y, double h){
        double lat = converteYtoLatitude(base.getLat(), y);
        double lon = converteXtoLongitude(base.getLng(), base.getLat(), x);        
        double alt = base.getAlt() + h;
        return lat + "\t" + lon + "\t" + alt;
    }
    
    /**
     * Converts Cartesian coordinates to geographical coordinates
     * @param base the point base to conversion
     * @param x the x value to convert
     * @param y the y value to convert
     * @return the string with value: lon + "," + lat + ",0 "
     * @since version 2.0.0
     */
    public static String parseToGeo3(PointGeo base, double x, double y){
        double lat = converteYtoLatitude(base.getLat(), y);
        double lon = converteXtoLongitude(base.getLng(), base.getLat(), x);
        return lon + "," + lat + ",0 ";
    }
    
    /**
     * Converts Y to latitude
     * @param latBase latitude of point base
     * @param y value of y point
     * @return the value converted to latitude
     * @since version 2.0.0
     */
    public static double converteYtoLatitude(double latBase, double y){
        return latBase + y*360/CIRC_MERIDIONAL;
    }
    
    /**
     * Converts X to longitude
     * @param lonBase longitude of point base
     * @param latBase latitude of point base
     * @param x value of x point
     * @return the value converted to longitude
     * @since version 2.0.0
     */
    public static double converteXtoLongitude(double lonBase, double latBase, double x){
        return lonBase+ x*360/(CIRC_EQUATORIAL*Math.cos(latBase*Math.PI/180));
    }
    
    /**
     * Converts Geo to X
     * @param base point base
     * @param point point to convert
     * @return the converted value
     * @since version 2.0.0
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
     * @since version 2.0.0
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
     * @since version 2.0.0
     */
    public static double convertGeoToY(PointGeo base, PointGeo point){
        return (point.getLat() - base.getLat())*CIRC_MERIDIONAL/360.0;
    }  
    
    /**
     * Converts Geo to Y
     * @param base point base
     * @param latitude latitude to convert
     * @return the converted value
     * @since version 2.0.0
     */
    public static double convertGeoToY(PointGeo base, double latitude){
        return (latitude - base.getLat())*CIRC_MERIDIONAL/360.0;
    } 
    
    /**
     * Converts Geo to Z
     * @param base point base
     * @param point point to convert
     * @return the converted value
     * @since version 2.0.0
     */
    public static double convertGeoToZ(PointGeo base, PointGeo point){
        return point.getAlt() - base.getAlt();
    }
    
    /**
     * Converts Geo to Z
     * @param base point base
     * @param altitudeAbs absolute altitude to convert
     * @return the converted value
     * @since version 2.0.0
     */
    public static double convertGeoToZ(PointGeo base, double altitudeAbs){
        return altitudeAbs - base.getAlt();
    }
    
    /**
     * Converts Geo to Point3D.
     * @param base the point base
     * @param point the point to convert
     * @return the point converted
     * @since version 2.0.0
     */
    public static Point3D convertGeoTo3D(PointGeo base, PointGeo point){
        double x = convertGeoToX(base, point);
        double y = convertGeoToY(base, point);        
        double z = convertGeoToZ(base, point);
        return new Point3D(x, y, z);
    }
    
    public static void savePointsDirections(PrintStream output, Line3D line3d) {
        double vx = 24;
        double vy = 0;
        Point3D p1 = line3d.getPoint3D(0);
        Point3D p2 = line3d.getPoint3D(1);
        double px = p1.getX();
        double py = p1.getY();
        double pz = 100.0;
        double vz = 0.0;         
        double px2 = p2.getX();
        double py2 = p2.getY();
        double angle = Math.atan2(py2 - py, px2 - px);//Math.atan2(py - py2, px - px2);
        vx = Math.cos(angle) * 24;
        vy = Math.sin(angle) * 24;
        System.out.println("angle:" + angle);
        output.println(String.format(
                Locale.ENGLISH,"%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", 
                px, py, pz, vx, vy, vz));
    }
}
