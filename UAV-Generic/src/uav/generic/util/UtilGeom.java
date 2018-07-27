package uav.generic.util;

import java.awt.Polygon;
import java.awt.geom.Point2D;

/**
 * Class with utils methods on Geometry.
 * @author Jesimar S. Arantes
 */
public class UtilGeom {    
    
    /**
     * Calc the center X of the polygon.
     * @param poly the object polygon
     * @return the center x of the polygon.
     */
    public static double centerXPoly(Polygon poly){
        double sumX = 0;
        for (int i = 0; i < poly.npoints; i++) {
            sumX += poly.xpoints[i];
        }
        return sumX/poly.npoints;
    }
    
    /**
     * Calc the center Y of the polygon.
     * @param poly the object polygon
     * @return the center y of the polygon.
     */
    public static double centerYPoly(Polygon poly){
        double sumY = 0;
        for (int i = 0; i < poly.npoints; i++) {
            sumY += poly.ypoints[i];
        }
        return sumY/poly.npoints;
    }
    
    /**
     * Calc the center X of the polygon using the objetct Point2D.
     * @param poly a vector of points that represent the polygon
     * @return the center x of the polygon.
     */
    public static double centerXPoly(Point2D poly[]){
        double sumX = 0;        
        for (Point2D item : poly) {
            sumX += item.getX();
        }
        return sumX/poly.length;
    }
    
    /**
     * Calc the center Y of the polygon using the objetct Point2D.
     * @param poly a vector of points that represent the polygon
     * @return the center y of the polygon.
     */
    public static double centerYPoly(Point2D poly[]){
        double sumY = 0;        
        for (Point2D item : poly) {
            sumY += item.getY();
        }
        return sumY/poly.length;
    }        
    
    /**
     * Calc the distance of the position (x, y) to the center of polygon poly.
     * @param poly the polygon to calc.
     * @param x the position x.
     * @param y the position y.
     * @return the euclidian distante.
     */
    public static double distPointToCenter(Point2D poly[], double x, double y){
        double x1 = centerXPoly(poly);
        double y1 = centerYPoly(poly);
        return Math.sqrt((x - x1)* (x - x1) + (y - y1) * (y - y1));
    }
      
    /**
     * Calc the euclidian distance amoung two points
     * @param x1 coordinate x of first point.
     * @param y1 coordinate y of first point.
     * @param x2 coordinate x of second point.
     * @param y2 coordinate y of second point.
     * @return the euclidian distance.
     */
    public static double distanceEuclidian(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2) * (y1 - y2));
    }
    
}
