/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mission.creator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import uav.mission.creator.data.KeyWords;
import uav.mission.creator.data.Mission;
import uav.mission.creator.data.UtilGeo;

/**
 *
 * @author Jesimar S. Arantes
 */
public class PrinterPointsFailure {

    private final File filePointsFailure;
    private final Mission mission;

    public PrinterPointsFailure(File filePointsFailure, Mission mission) {
        this.filePointsFailure = filePointsFailure;
        this.mission = mission;
    }

    public void printer() {
        try {      
            PrintStream printFailure = new PrintStream(filePointsFailure);            
            printFailure.println("<px, py, pz, vx, vy, vz>");
            for (int i = 0; i < mission.getSizeListLineGeo(); i++) {
                if (mission.getListLine().get(i).getName().contains(KeyWords.POINT_FAILURE)) { 
                    System.out.println("\t Ponto de Falha: ");
                    System.out.println("\t\t 3D: ");
                    System.out.print("\t\t\t" + mission.getListLine3D().get(i).toString(0));
                    //System.out.println("\t\t\t" + mission.getListLine3D().get(i).toString(1));

                    UtilGeo.savePointsDirections(printFailure, mission.getListLine3D().get(i));
                    System.out.println("\t\t Geo: ");
                    System.out.print("\t\t\t" + mission.getListLine().get(i).toString(0));
                    //System.out.println("\t\t\t" + mission.getListLine().get(i).toString(1));
                }
            }
            printFailure.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrinterPointsFailure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
