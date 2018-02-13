/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uav.mission.creator.data;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Point2D extends Point{
    
    private double x;
    private double y;

    public Point2D() {
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point2D(Point2D point2D) {
        this.x = point2D.x;
        this.y = point2D.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    } 

    @Override
    public String toString() {
        return "(x, y) = (" + x + ", " + y + ")";
    }

    @Override
    public double distance(Point point) {
        Point2D p = (Point2D) point;
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y));
    }
}
