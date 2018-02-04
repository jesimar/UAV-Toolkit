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

/**
 *
 * @author jesimar
 */
public class PrinterFrontier {

    private final File fileMap;
    private final Mission mission;
    private final TypeLocalization type = TypeLocalization.CARTEZIAN;

    public PrinterFrontier(File fileMap, Mission mission) {
        this.fileMap = fileMap;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            if (type == TypeLocalization.CARTEZIAN) {
                for (int i = 0; i < mission.getSizeListLineGeo(); i++) {
                    System.out.println("" + mission.getListLine3D().get(i).getName());
                    if (mission.getListLine3D().get(i).getName().contains("perimetro")) {
                        printMap.println("<x..., y..., n = 4, id = 0, type = frontier>");
                        printMap.println(mission.getListLine3D().get(i).toStringVetX());
                        printMap.println(mission.getListLine3D().get(i).toStringVetY());
                    }
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrinterFrontier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
