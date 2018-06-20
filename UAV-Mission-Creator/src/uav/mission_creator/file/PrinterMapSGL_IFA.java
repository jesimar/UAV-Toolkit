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
public class PrinterMapSGL_IFA {

    private final File fileMap;
    private final Mission mission;

    public PrinterMapSGL_IFA(File fileMap, Mission mission) {
        this.fileMap = fileMap;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            printMap.println("<number of polygons>");
            printMap.println(mission.getSizeListPolyGeo());
            printMap.println("<number of zona n>");
            printMap.println(mission.getSizeObstacle());
            printMap.println("<number of zona p>");
            printMap.println(mission.getSizePenalty());
            printMap.println("<number of zona b>");
            printMap.println(mission.getSizeBonus());

            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_OBSTACLE)) {
                    printMap.println("<x..., y..., n = 4, id = 0, type = n>");
                    printMap.println(mission.getPoly3DVetX(i));
                    printMap.println(mission.getPoly3DVetY(i));
                }
            }
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_PENALTY)) {
                    printMap.println("<x..., y..., n = 4, id = 0, type = p>");
                    printMap.println(mission.getPoly3DVetX(i));
                    printMap.println(mission.getPoly3DVetY(i));
                }
            }
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_BONUS)) {
                    printMap.println("<x..., y..., n = 4, id = 0, type = b>");
                    printMap.println(mission.getPoly3DVetX(i));
                    printMap.println(mission.getPoly3DVetY(i));
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printer() " + ex);
        }
    }
}
