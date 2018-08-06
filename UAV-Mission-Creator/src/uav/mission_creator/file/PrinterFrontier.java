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
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_FRONTIER)) {
                    printMap.println("<x..., y..., n = 4, id = 0, type = frontier>");
//                    printMap.println(mission.getListPoly3D().get(i).getVetx());
//                    printMap.println(mission.getListPoly3D().get(i).getVety());
                    int n = mission.getListPoly3D().get(i).getNpoints();
                    String str = "";
                    for (int j = 0; j < n-1; j++){
                        str += mission.getListPoly3D().get(i).getVetx()[j] + ",";
                    }
                    str += mission.getListPoly3D().get(i).getVetx()[n-1];
                    printMap.println(str);
                    String str2 = "";
                    for (int j = 0; j < n-1; j++){
                        str2 += mission.getListPoly3D().get(i).getVety()[j] + ",";
                    }
                    str2 += mission.getListPoly3D().get(i).getVety()[n-1];
                    printMap.println(str2);
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printer() " + ex);
        }
    }
}
