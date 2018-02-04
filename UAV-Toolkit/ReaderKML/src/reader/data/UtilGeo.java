package reader.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jesimar
 */
public class UtilGeo {
        
    public final static double CIRC_EQUATORIAL = 40075017.0;//40075000.0;
    public final static double CIRC_MERIDIONAL = 40007860.0;//40000000.0;
        
    public static PointGeo parseToGeo1(PointGeo base, double x, double y, double h){
        return new PointGeo(
            base.longitude+ x*360/(CIRC_EQUATORIAL*Math.cos(base.latitude*Math.PI/180)), 
            base.latitude + y*360/CIRC_MERIDIONAL, 
            base.altitude + h
        );
    }
    
    public static String parseToGeo2(PointGeo base, double x, double y, double h){
        double lat = base.latitude + y*360/CIRC_MERIDIONAL;
        double lon = base.longitude+ x*360/(CIRC_EQUATORIAL*Math.cos(base.latitude*Math.PI/180));
        double alt = base.altitude + h;
        return lat + "\t" + lon + "\t" + alt;
    }
    
    public static String parseToGeo3(PointGeo base, double x, double y){
        double lat = base.latitude + y*360/CIRC_MERIDIONAL;
        double lon = base.longitude+ x*360/(CIRC_EQUATORIAL*Math.cos(base.latitude*Math.PI/180));
        return lon + "," + lat + ",0 ";
    }
    
    public static String parseToGeoRelativeGround2(PointGeo base, double x, double y, double h){
        double lat = base.latitude + y*360/CIRC_MERIDIONAL;
        double lon = base.longitude+ x*360/(CIRC_EQUATORIAL*Math.cos(base.latitude*Math.PI/180));
        return lat + "\t" + lon + "\t" + h;
    }
    
    public static void savePointsDirections(PrintStream output, Line3D line3d) {
        double vx = 24;
        double vy = 0;
        Point3D p1 = line3d.getPoint3D(0);
        Point3D p2 = line3d.getPoint3D(1);
        double px = p1.x;
        double py = p1.y;
        double pz = 100.0;
        double vz = 0.0;         
        double px2 = p2.x;
        double py2 = p2.y;
        double angle = Math.atan2(py2 - py, px2 - px);//Math.atan2(py - py2, px - px2);
        vx = Math.cos(angle) * 24;
        vy = Math.sin(angle) * 24;
        System.out.println("angle:" + angle);
        output.println(String.format(Locale.ENGLISH,"%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", 
                px, py, pz, vx, vy, vz));
    }
}
