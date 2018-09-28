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
public class PrinterMapSGL_NFZ_AStar {

    private final File fileMap;
    private final Mission mission;

    public PrinterMapSGL_NFZ_AStar(File fileMap, Mission mission) {
        this.fileMap = fileMap;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            int j = 0;
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_OBSTACLE)) {
                    printMap.println("#obstaculo " + (j+1));
                    int size = mission.getListPolyGeo().get(i).getNpoints();
                    printMap.println(size);
                    for (int k = 0; k < size; k++){
                        printMap.println(String.format("%.8f;%.8f", 
                                mission.getListPoly3D().get(i).getVetx()[k],
                                mission.getListPoly3D().get(i).getVety()[k]));
                    }
                    j++;
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printer() " + ex);
        }
    }
}
