package uav.mission_creator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import uav.mission_creator.struct.KeyWords;
import uav.mission_creator.struct.Mission;

/**
 *
 * @author Jesimar S. Arantes
 */
public class PrinterFrontier {

    private final File fileMap;
    private final Mission mission;

    public PrinterFrontier(File fileMap, Mission mission) {
        this.fileMap = fileMap;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            for (int i = 0; i < mission.getSizeListLineGeo(); i++) {
                System.out.println(mission.getListLine3D().get(i).getName());
                if (mission.getListLine3D().get(i).getName().contains(KeyWords.FRONTIER)) {
                    printMap.println("<x..., y..., n = 4, id = 0, type = frontier>");
                    printMap.println(mission.getListLine3D().get(i).toStringVetX());
                    printMap.println(mission.getListLine3D().get(i).toStringVetY());
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printer() " + ex);
        }
    }
}
