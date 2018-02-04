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
public class PrinterYAML {

    private final Mission mission;
    private final File file;
    private final TypeLocalization type;
    
    public PrinterYAML(File file, Mission mission, TypeLocalization type) {
        this.file = file;
        this.mission = mission;
        this.type = type;
    }           
    
    public void printer(){
        try {
            PrintStream print = new PrintStream(file);
            print.println("environment:");
            print.println("  obstacles:");
            for (PolyGeo poly : mission.getListPoly()){
                if (poly.getName().contains("obstacle")){
                    print.println("    " + poly.getName().substring(9) + ":");
                    print.println("      " + "shape: polygon");
                    print.println("      " + "corners:");
                    for (int j = 0; j < poly.getVetx().length; j++){
                        if (type == TypeLocalization.GEOREFERENCE){
                            print.printf("        - [%.16g, %.16g]\n", 
                                    poly.getVetx()[j], poly.getVety()[j]);
                        }else if (type == TypeLocalization.CARTEZIAN){
                            Point3D point = new Point3D(mission.getPointBase(), poly.getPointGeo(j));                                                           
                            print.printf("        - [%.16g, %.16g]\n", point.x, point.y);
                        }
                    }
                }
            }
            print.println("  features:");
            for (PolyGeo poly : mission.getListPoly()){
                if (poly.getName().contains("feature")){
                    print.println("    " + poly.getName().substring(8) + ":");
                    print.println("      " + "shape: polygon");
                    print.println("      " + "corners:");
                    for (int j = 0; j < poly.getVetx().length; j++){
                        if (type == TypeLocalization.GEOREFERENCE){
                            print.printf("        - [%.16g, %.16g]\n", 
                                    poly.getVetx()[j], poly.getVety()[j]);                            
                        }else if (type == TypeLocalization.CARTEZIAN){
                            Point3D point = new Point3D(mission.getPointBase(), poly.getPointGeo(j));                                                           
                            print.printf("        - [%.16g, %.16g]\n", point.x, point.y);
                        }
                    }
                }
            }
            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrinterYAML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
