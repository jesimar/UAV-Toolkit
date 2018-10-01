package uav.mission_creator.struct.geom;

import uav.mission_creator.struct.UtilGeo;

/**
 * Concrete class that implements a 3D point.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Point3D extends Point{
    
    private final String name;
    private final double x;
    private final double y;
    private final double h;
    
    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @param h coordinate h of point.
     * @since version 2.0.0
     */
    public Point3D(double x, double y, double h) {
        this.name = "";
        this.x = x;
        this.y = y;
        this.h = h;
    }
    
    /**
     * Class constructor.
     * @param name name of point
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @param h coordinate h of point.
     * @since version 2.0.0
     */
    public Point3D(String name, double x, double y, double h) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.h = h;
    }
    
    /**
     * Class constructor.
     * @param base point geoBase
     * @param point point with 3D coordinates.
     * @since version 2.0.0
     */
    public Point3D(PointGeo base, PointGeo point) {
        this.name = point.getName();       
        this.y = UtilGeo.convertGeoToY(base, point);
        this.x = UtilGeo.convertGeoToX(base, point);
        this.h = UtilGeo.convertGeoToZ(base, point);
    }
    
    public PointGeo parseToGeo(PointGeo base){
        return new PointGeo( 
            UtilGeo.converteXtoLongitude(base.getLng(), base.getLat(), x),
            UtilGeo.converteYtoLatitude(base.getLat(), y),
            base.getAlt() + h
        );
    }
    
    public Point3D minus(Point3D o){
        return new Point3D(x-o.x, y-o.y, h-o.h);
    }
    
    public double angle(){
        return Math.atan2(y, x);
    }

    /**
     * Gets the name of the point
     * @return the name of the point
     * @since version 1.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of x
     * @return the value of x
     * @since version 2.0.0
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the value of y
     * @return the value of y
     * @since version 2.0.0
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the value of h
     * @return the value of h
     * @since version 2.0.0
     */
    public double getH() {
        return h;
    }

    /**
     * Gets a string with values x, y, z
     * @return a string with value of x, y, z
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return String.format("%.16g %.16g %.16g\n", x, y, h);
    }
    
    /**
     * Calculates the distance between two points
     * @param point point to calculate distance
     * @return the distance between two points
     * @since version 2.0.0
     */
    @Override
    public double distance(Point point) {
        Point3D p = (Point3D) point;
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.h - h)*(p.h - h));
    }
}
