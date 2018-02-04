/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission.creator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import mission.creator.data.KeyWords;
import mission.creator.data.Mission;
import mission.creator.data.TypeLocalization;

/**
 *
 * @author Jesimar S. Arantes
 */
public class PrinterMapSGL_NFZ {

    private final File fileMap;
    private final Mission mission;
    private final TypeLocalization type = TypeLocalization.CARTEZIAN;

    public PrinterMapSGL_NFZ(File fileMap, Mission mission) {
        this.fileMap = fileMap;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            printMap.println("<number of polygons>");
            printMap.println(mission.getSizeObstacle());
            if (type == TypeLocalization.CARTEZIAN) {
                for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                    if (mission.getListPoly().get(i).getName().contains(KeyWords.MAP_OBSTACLE)) {
                        printMap.println("<x..., y..., n = 4, id = 0, type = n>");
                        printMap.println(mission.getPoly3DVetX(i));
                        printMap.println(mission.getPoly3DVetY(i));
                    }
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrinterMapSGL_NFZ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
