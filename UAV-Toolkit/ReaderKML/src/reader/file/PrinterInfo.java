/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import reader.data.Mission;
import reader.data.Point3D;
import reader.data.PolyGeo;
import reader.data.TypeLocalization;

/**
 *
 * @author jesimar
 */
public class PrinterInfo {

    private final Mission mission;
    private final File file;
    private final TypeLocalization type = TypeLocalization.CARTEZIAN;
    
    public PrinterInfo(File file, Mission mission) {
        this.file = file;
        this.mission = mission;
        
    }           
    
    public void printer(){
        try {
            PrintStream print = new PrintStream(file);
            print.println("#Longitude Latitude");
            int tam = mission.getSizeListLineGeo();
            double lon = mission.getListLine().get(tam-1).getVetx()[0];
            double lat = mission.getListLine().get(tam-1).getVety()[0];
            print.printf("%.16g %.16g\n", lon, lat);
            
            print.println("#Height-Fly");
            print.printf("%f\n", mission.getHeightFly());
            
            print.println("#Posições Centrais");            
            for (PolyGeo poly : mission.getListPoly()){  
                if (poly.getName().contains("feature")){
                    if (type == TypeLocalization.GEOREFERENCE){
                        print.printf("[%s, %.16g, %.16g]\n", 
                                poly.getName(), poly.getCenterX(), poly.getCenterY());
                    }else if (type == TypeLocalization.CARTEZIAN){
                        Point3D point = new Point3D(mission.getPointBase(), poly.getPointGeoCenter());                                                           
                        print.printf("[%s, %.16g, %.16g]\n", poly.getName(), point.x, point.y);
                    }                
                }
            }            
            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrinterInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
