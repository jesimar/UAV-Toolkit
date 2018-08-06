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
public class PrinterMissionSGL_CCQSP {

    private final File fileMission;
    private final Mission mission;
    private final double vx = 0.0;
    private final double vy = 0.0;
    private final double delta = 0.01;
    private final double timeMax = 60.0;
    private final double offset = 1.0;
    
    public PrinterMissionSGL_CCQSP(File fileMission, Mission mission) {
        this.fileMission = fileMission;
        this.mission = mission;
    }

    public void printer() {
        try {
            int wpt = mission.getSizeWaypoints();
            PrintStream printMission = new PrintStream(fileMission);
            printMission.println("<start state: px,py,vx,vy>");
            printMission.println(mission.getWaypoints3D(0) + " " + vx + " " + vy);
            printMission.println("<chance constraints: n, ...values...>");
            printMission.println("1");
            printMission.println(delta);
            printMission.println("<events:n>");
            printMission.println(wpt);
            printMission.println("<temporal constraints: n, ...(eS,eE,lb,ub)...>");
            printMission.println("1");
            printMission.println("0\t" + (wpt-1) + "\t0.0\t" + timeMax);
            printMission.println("<episodes: n, ...(name,type,c,eS,eE,<Ra:|I|,|O|,...I...,...O...>)...> #I = |N|,...<N>... #N = |C|,...<C>...");
            int qtdFrontier = mission.hasFrontier() ? 1 : 0;
            int qtdWpt = wpt-1;
            int qtdObsObj = mission.getSizeObstacleObject();
            int totalEpisodes = qtdFrontier + qtdWpt + qtdObsObj;
            printMission.println(totalEpisodes);
            if (mission.hasFrontier()) {
                printMission.println(
                        "[Frountier] \t remain-in \t 0 \t 0 \t " + (wpt-1) + 
                        " \t 1 \t 0 \t 1 \t0");
            }
            int index = 1;
            for (int i = 0; i < wpt - 1; i++) {
                printMission.println(
                        "[WPT-" + (i+1) + "] \t\t end-in \t 0 \t " + (i) + 
                        " \t " + (i+1) + " \t 1 \t 0 \t 1 \t" + index);
                index++;
            }
            int w=0;
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_OBSTACLE_OBJ)) {
                    printMission.println(
                            "[NFZ-" + (w+1) + "] \t\t remain-in \t 0 \t 0 \t " + 
                            (wpt-1) + " \t 0 \t 1 \t 1 \t" + index);
                    index++;
                    w++;
                }
            }
            printMission.println("<convex regions(C)>");
            index = mission.hasFrontier() ? index : index - 1;
            printMission.println(index);
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_FRONTIER)) {
                    int n = mission.getListPoly3D().get(i).getNpoints();
                    printMission.println("<region(" + "frountier" +")>");
                    printMission.println(n);
                    for (int j = 0; j < n; j++){
                        String str = "";
                        str += mission.getListPoly3D().get(i).getVetx()[(n-j)%n];
                        str += "\t"+mission.getListPoly3D().get(i).getVety()[(n-j)%n];
                        printMission.println(str);
                    }
                }
            }
            
            for (int i = 1; i < wpt; i++) {
                printMission.println("<region(" + "WPT-" + (i) +")>");
                printMission.println(4);
                printMission.println((mission.getPointWaypoints3D(i).getX()-offset) + 
                        "\t" + (mission.getPointWaypoints3D(i).getY()-offset));
                printMission.println((mission.getPointWaypoints3D(i).getX()+offset) + 
                        "\t" + (mission.getPointWaypoints3D(i).getY()-offset));
                printMission.println((mission.getPointWaypoints3D(i).getX()+offset) + 
                        "\t" + (mission.getPointWaypoints3D(i).getY()+offset));
                printMission.println((mission.getPointWaypoints3D(i).getX()-offset) + 
                        "\t" + (mission.getPointWaypoints3D(i).getY()+offset));
            }
            
            index = 1;
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_OBSTACLE_OBJ)) {
                    printMission.println("<region(" + "NFZ-" + (index) +")>");
                    int n = mission.getListPoly3D().get(i).getNpoints();
                    printMission.println(n);
                    for (int j = 0; j < n; j++){
                        String str = "";
                        str += mission.getListPoly3D().get(i).getVetx()[(n-j)%n];
                        str += "\t"+mission.getListPoly3D().get(i).getVety()[(n-j)%n];
                        printMission.println(str);
                    }
                    index++;
                }
            }
            printMission.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printer() " + ex);
        }
    }
}
