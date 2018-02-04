package uav.generic.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import lib.color.StandardPrints;
import uav.generic.struct.PointGeo;
import uav.generic.struct.Line3D;
import uav.generic.struct.Point3D;

/**
 *
 * @author jesimar
 */
public class UtilGeo {
        
    public final static double CIRC_EQUATORIAL = 40075017.0;//40075000.0;
    public final static double CIRC_MERIDIONAL = 40007860.0;//40000000.0;            
    
    public static String parseToGeo(PointGeo base, double x, double y, double h, String separator){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);        
        double alt = base.getAltitude() + h;
        return lat + separator + lon + separator + alt;
    }
    
    public static PointGeo parseToGeo(PointGeo base, Point3D point){
        return new PointGeo(
            converteXtoLongitude(base.getLongitude(), base.getLatitude(), point.getX()), 
            converteYtoLatitude(base.getLatitude(), point.getY()), 
            base.getAltitude() + point.getZ()
        );
    }
    
    public static PointGeo parseToGeo1(PointGeo base, double x, double y, double h){
        return new PointGeo(
            converteXtoLongitude(base.getLongitude(), base.getLatitude(), x), 
            converteYtoLatitude(base.getLatitude(), y), 
            base.getAltitude() + h
        );
    }
    
    public static String parseToGeo2(PointGeo base, double x, double y, double h){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);        
        double alt = base.getAltitude() + h;
        return lat + "\t" + lon + "\t" + alt;
    }
    
    public static String parseToGeo3(PointGeo base, double x, double y){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);
        return lon + "," + lat + ",0 ";
    }
    
    public static String parseToGeoRelativeGround2(PointGeo base, double x, double y, double h){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);
        return lat + "\t" + lon + "\t" + h;
    }
    
    public static double converteYtoLatitude(double latBase, double y){
        return latBase + y*360/CIRC_MERIDIONAL;
    }
    
    public static double converteXtoLongitude(double lonBase, double latBase, double x){
        return lonBase+ x*360/(CIRC_EQUATORIAL*Math.cos(latBase*Math.PI/180));
    }
    
    public static double convertGeoToX(PointGeo base, PointGeo point){
        double eixo = CIRC_EQUATORIAL*Math.cos(base.getLatitude()*Math.PI/180.0);
        return (point.getLongitude() - base.getLongitude())*eixo/360.0;
    }
    
    public static double convertGeoToX(PointGeo base, double longitude){
        double eixo = CIRC_EQUATORIAL*Math.cos(base.getLatitude()*Math.PI/180.0);
        return (longitude - base.getLongitude())*eixo/360.0;
    }
    
    public static double convertGeoToY(PointGeo base, PointGeo point){
        return (point.getLatitude() - base.getLatitude())*CIRC_MERIDIONAL/360.0;
    }  
    
    public static double convertGeoToY(PointGeo base, double latitude){
        return (latitude - base.getLatitude())*CIRC_MERIDIONAL/360.0;
    } 
    
    public static double convertGeoToZ(PointGeo base, PointGeo point){
        return point.getAltitude() - base.getAltitude();
    }
    
    public static double convertGeoToZ(PointGeo base, double altitudeAbs){
        return altitudeAbs - base.getAltitude();
    }
    
    public static Point3D convertGeoTo3D(PointGeo base, PointGeo point){
        double x = convertGeoToX(base, point);
        double y = convertGeoToY(base, point);        
        double z = convertGeoToZ(base, point);
        return new Point3D(x, y, z);
    }
    
    public static PointGeo getPointGeo(String pathFileGeoBase) throws FileNotFoundException {        
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
        double vel = 24;
        double angle = Math.atan2(py2 - py, px2 - px);//Math.atan2(py - py2, px - px2);
        vx = Math.cos(angle) * vel;
        vy = Math.sin(angle) * vel;
        System.out.println("angle:" + angle);
        output.println(
                String.format(Locale.ENGLISH,"%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", 
                px, py, pz, vx, vy, vz));
    }
}
