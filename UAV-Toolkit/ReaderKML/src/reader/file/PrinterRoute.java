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
import reader.data.LineGeo;
import reader.data.Mission;
import reader.data.Point3D;
import reader.data.PointGeo;
import reader.data.PolyGeo;
import reader.data.TypeLocalization;

/**
 *
 * @author jesimar
 */
public class PrinterRoute {

    private final File fileLanding;
    private final File fileTakeoff;
    private final Mission mission;
    private final TypeLocalization type;

    public PrinterRoute(Mission mission, File fileTakeoff, File fileLanding, TypeLocalization type) {
        this.mission = mission;
        this.fileTakeoff = fileTakeoff;
        this.fileLanding = fileLanding;
        this.type = type;
    }

    public void printer() {
        try {      
            takeoff();
            landing();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrinterYAML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void takeoff() throws FileNotFoundException {
        double heightFly = mission.getHeightFly();
        PrintStream printTakeoff = new PrintStream(fileTakeoff);
        int size = 3;
        String p[] = new String[size];
        for (LineGeo line : mission.getListLine()) {
            if (line.getName().contains("airport")) {
                p[0] = line.toString(0);
                p[1] = line.toString(1, 50);
            }
        }
        for (PolyGeo poly : mission.getListPoly()) {
            if (poly.getName().contains("feature_start")) {
                p[2] = poly.toString(heightFly);
            }
        }
        PointGeo geo[] = new PointGeo[size];
        for (int i = 0; i < size; i++){
            String s[] = p[i].split(" ");           
            geo[i] = new PointGeo(Double.parseDouble(s[0]), Double.parseDouble(s[1]),
                    Double.parseDouble(s[2]));
        }
                        
        Point3D p3d[] = new Point3D[size];
        
        for (int i = 0; i < size; i++){
            p3d[i] = new Point3D(mission.getPointBase(), geo[i]);
        }
        
        if (type == TypeLocalization.GEOREFERENCE){
            for (int i = 0; i < size; i++){
                printTakeoff.printf(p[i]);
            }
        }else if (type == TypeLocalization.CARTEZIAN){
            for (int i = 0; i < size; i++){
                printTakeoff.printf(String.format("%.16g %.16g %.16g\n",
                    p3d[i].x, p3d[i].y, p3d[i].h + Double.parseDouble(p[0].split(" ")[2])));
            }
        }
        printTakeoff.close();
    }
    
    private void landing() throws FileNotFoundException {
        double heightFly = mission.getHeightFly();        
        int size = 5;
        String p[] = new String[size];
        
        for (PolyGeo poly : mission.getListPoly()) {
            if (poly.getName().contains("feature_end")) {
                p[0] = poly.toString(heightFly);
                LineGeo line = mission.getListLine().get(0);
                double vetx = (line.getVetx()[0] + poly.getCenterX()) / 2.0;
                double vety = (line.getVety()[0] + poly.getCenterY()) / 2.0;
                p[1] = String.format("%.16g %.16g %.16g\n", vetx, vety, heightFly);
            }
        }
        
        for (LineGeo line : mission.getListLine()) {
            if (line.getName().contains("airport")) {
                p[2] = line.toString(0, 80);
                double vetx = (line.getVetx()[0] + line.getVetx()[1]) / 2.0;
                double vety = (line.getVety()[0] + line.getVety()[1]) / 2.0;
                p[3] = String.format("%.16g %.16g %.16g\n", vetx, vety,
                        line.getVetz()[1]);
                p[4] = line.toString(1, -20);
            }
        }
        
        PointGeo geo[] = new PointGeo[size];
        for (int i = 0; i < size; i++){
            String s[] = p[i].split(" ");           
            geo[i] = new PointGeo(Double.parseDouble(s[0]), Double.parseDouble(s[1]),
                    Double.parseDouble(s[2]));
        }
                        
        Point3D p3d[] = new Point3D[size];
        
        for (int i = 0; i < size; i++){
            p3d[i] = new Point3D(mission.getPointBase(), geo[i]);
        }
        
        PrintStream printLanding = new PrintStream(fileLanding);
        if (type == TypeLocalization.GEOREFERENCE){
            for (int i = 0; i < size; i++){
                printLanding.printf(p[i]);
            }
        }else if (type == TypeLocalization.CARTEZIAN){
            for (int i = 0; i < size; i++){
                printLanding.printf(String.format("%.16g %.16g %.16g\n",
                    p3d[i].x, p3d[i].y, p3d[i].h + Double.parseDouble(p[3].split(" ")[2])));
            }
        }
        printLanding.close();
    }
}
