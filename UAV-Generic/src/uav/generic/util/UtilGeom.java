package uav.generic.util;

import java.awt.Polygon;
import java.awt.geom.Point2D;

/**
 *
 * @author jesimar
 */
public class UtilGeom {    
    
    /*Calcula o centro X do poligono*/
    public static double centerXPoly(Polygon poly){
        double sumX = 0;
        for (int i = 0; i < poly.npoints; i++) {
            sumX += poly.xpoints[i];
        }
        return sumX/poly.npoints;
    }
    
    /*Calcula o centro Y do poligono*/
    public static double centerYPoly(Polygon poly){
        double sumY = 0;
        for (int i = 0; i < poly.npoints; i++) {
            sumY += poly.ypoints[i];
        }
        return sumY/poly.npoints;
    }
    
    /*Calcula o centro X do poligono dado*/
    public static double centerXPoly(Point2D poly[]){
        double sumX = 0;        
        for (Point2D item : poly) {
            sumX += item.getX();
        }
        return sumX/poly.length;
    }
    
    /*Calcula o centro Y do poligono dado*/
    public static double centerYPoly(Point2D poly[]){
        double sumY = 0;        
        for (Point2D item : poly) {
            sumY += item.getY();
        }
        return sumY/poly.length;
    }        
    
    /*Calcula a distância da posição (x, y) para o centro do poligono dado*/
    public static double distPointToCenter(Point2D poly[], double x, double y){
        double x1 = centerXPoly(poly);
        double y1 = centerYPoly(poly);
        return Math.sqrt((x - x1)* (x - x1) + (y - y1) * (y - y1));
    }
      
    /*Calcula a distancia euclidiana entre quatro pontos*/
    public static double distanceEuclidian(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2) * (y1 - y2));
    }
    
}
