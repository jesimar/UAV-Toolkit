package uav.mission_creator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import uav.mission_creator.struct.KeyWords;
import uav.mission_creator.struct.Mission;
import uav.mission_creator.struct.UtilGeo;

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
            System.err.println("[ERROR] [FileNotFoundException] printer() " + ex);
        }
    }
}
