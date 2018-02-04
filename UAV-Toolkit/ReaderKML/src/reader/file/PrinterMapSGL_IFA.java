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
import reader.data.TypeLocalization;
import reader.data.UtilGeo;

/**
 *
 * @author jesimar
 */
public class PrinterMapSGL_IFA {

    private final File fileMap;
    private final Mission mission;
    private final TypeLocalization type = TypeLocalization.CARTEZIAN;

    public PrinterMapSGL_IFA(File fileMap, Mission mission) {
        this.fileMap = fileMap;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            int size = mission.getSizeListPolyGeo() - 1;
            printMap.println("<number of polygons>");
            printMap.println(size);
            printMap.println("<number of zona n>");
            printMap.println(mission.getSizeObstacle());
            printMap.println("<number of zona p>");
            printMap.println(mission.getSizePenalty());
            printMap.println("<number of zona b>");
            printMap.println(mission.getSizeBonus());

            if (type == TypeLocalization.GEOREFERENCE) {
                for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                    if (!mission.getListPoly().get(i).getName().contains("fly")) {
                        printMap.println("<x..., y..., n = 4, id = 0, type = n>");
                        printMap.println(mission.getPolyVetX(i));
                        printMap.println(mission.getPolyVetY(i));
                    }
                }
            } else if (type == TypeLocalization.CARTEZIAN) {
                for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                    if (mission.getListPoly().get(i).getName().contains("obstacle")) {
                        printMap.println("<x..., y..., n = 4, id = 0, type = n>");
                        printMap.println(mission.getPoly3DVetX(i));
                        printMap.println(mission.getPoly3DVetY(i));
                    }
                }
                for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                    if (mission.getListPoly().get(i).getName().contains("penalty")) {
                        printMap.println("<x..., y..., n = 4, id = 0, type = p>");
                        printMap.println(mission.getPoly3DVetX(i));
                        printMap.println(mission.getPoly3DVetY(i));
                    }
                }
                for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                    if (mission.getListPoly().get(i).getName().contains("bonus")) {
                        printMap.println("<x..., y..., n = 4, id = 0, type = b>");
                        printMap.println(mission.getPoly3DVetX(i));
                        printMap.println(mission.getPoly3DVetY(i));
                    }
                }
                for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                    if (mission.getListPoly().get(i).getName().contains("failure")) { 
                        System.out.println("\t Failure: ");
                        System.out.println("\t\t 3D: ");
                        System.out.print("\t\t\t" + mission.getListPoly3D().get(i).getCenterX());
                        System.out.println("\t\t" + mission.getListPoly3D().get(i).getCenterY());                        
                        System.out.println("\t\t Geo: ");
                        System.out.print("\t\t\t" + mission.getListPoly().get(i).getCenterY());
                        System.out.println("\t\t" + mission.getListPoly().get(i).getCenterX());
                    }
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrinterMapSGL_IFA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
