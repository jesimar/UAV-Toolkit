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
public class PrinterMapSGL_NFZ {

    private final File fileMap;
    private final Mission mission;

    public PrinterMapSGL_NFZ(File fileMap, Mission mission) {
        this.fileMap = fileMap;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            printMap.println("<number of polygons>");
            printMap.println(mission.getSizeObstacle());
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPoly().get(i).getName().contains(KeyWords.MAP_OBSTACLE)) {
                    printMap.println("<x..., y..., n = 4, id = 0, type = n>");
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
