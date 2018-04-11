package uav.generic.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import uav.generic.struct.geom.PointGeo;
import uav.generic.struct.geom.Line3D;
import uav.generic.struct.geom.Point3D;

/**
 * Classe com métodos úteis sobre transformações entre coordenadas cartesianas e geográficas.
 * @author Jesimar S. Arantes
 */
public class UtilGeo {
        
    public final static double CIRC_EQUATORIAL = 40075017.0;//40075000.0;
    public final static double CIRC_MERIDIONAL = 40007860.0;//40000000.0;            
    
    public static String parseToGeo(PointGeo base, double x, double y, double h, String separator){
        double lat = converteYtoLatitude(base.getLat(), y);
        double lon = converteXtoLongitude(base.getLng(), base.getLat(), x);        
        double alt = base.getAlt() + h;
        return lat + separator + lon + separator + alt;
    }
    
    public static PointGeo parseToGeo(PointGeo base, Point3D point){
        return new PointGeo(
            converteXtoLongitude(base.getLng(), base.getLat(), point.getX()), 
            converteYtoLatitude(base.getLat(), point.getY()), 
            base.getAlt() + point.getZ()
        );
    }
    
    public static PointGeo parseToGeo1(PointGeo base, double x, double y, double h){
        return new PointGeo(
            converteXtoLongitude(base.getLng(), base.getLat(), x), 
            converteYtoLatitude(base.getLat(), y), 
            base.getAlt() + h
        );
    }
    
    public static String parseToGeo2(PointGeo base, double x, double y, double h){
        double lat = converteYtoLatitude(base.getLat(), y);
        double lon = converteXtoLongitude(base.getLng(), base.getLat(), x);        
        double alt = base.getAlt() + h;
        return lat + "\t" + lon + "\t" + alt;
    }
    
    public static String parseToGeo3(PointGeo base, double x, double y){
        double lat = converteYtoLatitude(base.getLat(), y);
        double lon = converteXtoLongitude(base.getLng(), base.getLat(), x);
        return lon + "," + lat + ",0 ";
    }
    
    public static String parseToGeoRelativeGround2(PointGeo base, double x, double y, double h){
        double lat = converteYtoLatitude(base.getLat(), y);
        double lon = converteXtoLongitude(base.getLng(), base.getLat(), x);
        return lat + "\t" + lon + "\t" + h;
    }
    
    public static double converteYtoLatitude(double latBase, double y){
        return latBase + y*360/CIRC_MERIDIONAL;
    }
    
    public static double converteXtoLongitude(double lonBase, double latBase, double x){
        return lonBase+ x*360/(CIRC_EQUATORIAL*Math.cos(latBase*Math.PI/180));
    }
    
    public static double convertGeoToX(PointGeo base, PointGeo point){
        double eixo = CIRC_EQUATORIAL*Math.cos(base.getLat()*Math.PI/180.0);
        return (point.getLng() - base.getLng())*eixo/360.0;
    }
    
    public static double convertGeoToX(PointGeo base, double longitude){
        double eixo = CIRC_EQUATORIAL*Math.cos(base.getLat()*Math.PI/180.0);
        return (longitude - base.getLng())*eixo/360.0;
    }
    
    public static double convertGeoToY(PointGeo base, PointGeo point){
        return (point.getLat() - base.getLat())*CIRC_MERIDIONAL/360.0;
    }  
    
    public static double convertGeoToY(PointGeo base, double latitude){
        return (latitude - base.getLat())*CIRC_MERIDIONAL/360.0;
    } 
    
    public static double convertGeoToZ(PointGeo base, PointGeo point){
        return point.getAlt() - base.getAlt();
    }
    
    public static double convertGeoToZ(PointGeo base, double altitudeAbs){
        return altitudeAbs - base.getAlt();
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
    
    public static int convertAngleAviationToAngleMath(int heading){
        return (360 - heading + 90) % 360;
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
