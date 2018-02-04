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
public class PointGeo {
    
    public final double longitude;
    public final double latitude;
    public final double altitude;
    
    public PointGeo(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }
    
    public PointGeo(String point) {
        String v[] = point.split(",");
        this.longitude = Double.parseDouble(v[0]);
        this.latitude = Double.parseDouble(v[1]);
        this.altitude = Double.parseDouble(v[2]);
    }
}
