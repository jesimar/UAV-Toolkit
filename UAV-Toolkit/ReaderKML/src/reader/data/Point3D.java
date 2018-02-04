/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader.data;


/**
 *
 * @author marcio
 */
public class Point3D extends Point{
    
    public final double x;
    public final double y;
    public final double h;
    
    public Point3D(double x, double y, double h) {
        this.x = x;
        this.y = y;
        this.h = h;
    }
    
    public Point3D(PointGeo base, PointGeo point) {
        double dif_lat = point.latitude - base.latitude;
        this.y = dif_lat*UtilGeo.CIRC_MERIDIONAL/360.0;

        double dif_long = point.longitude - base.longitude;
        double eixo = UtilGeo.CIRC_EQUATORIAL*Math.cos(base.latitude*Math.PI/180.0);
        this.x = dif_long*eixo/360.0;

        this.h = point.altitude - base.altitude;
    }
    
    public PointGeo parseTo(PointGeo base){
        return new PointGeo(
            base.longitude+ x*360/(UtilGeo.CIRC_EQUATORIAL*Math.cos(base.latitude*Math.PI/180)), 
            base.latitude + y*360/UtilGeo.CIRC_MERIDIONAL, 
            base.altitude + h
        );
    }
    
    public Point3D minus(Point3D o){
        return new Point3D(x-o.x, y-o.y, h-o.h);
    }
    
    public double angle(){
        return Math.atan2(y, x);
    }

    @Override
    public String toString() {
        return String.format("%.16g %.16g %.16g\n", x, y, h);
    }
    
    @Override
    public double distance(Point point) {
        Point3D p = (Point3D) point;
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.h - h)*(p.h - h));
    }
}
