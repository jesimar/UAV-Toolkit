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

    public PrinterMissionSGL_CCQSP(File fileMission, Mission mission) {
        this.fileMission = fileMission;
        this.mission = mission;
    }

    public void printer() {
        try {
            PrintStream printMission = new PrintStream(fileMission);
            printMission.println("<start state: px,py,vx,vy>");
            printMission.println(mission.getWaypoints3D(0) + " 0.0 0.0");
            printMission.println("<chance constraints: n, ...values...>");
            printMission.println("1");
            printMission.println("0.01");
            printMission.println("<events:n>");
            printMission.println(mission.getSizeWaypoints());
            printMission.println("<temporal constraints: n, ...(eS,eE,lb,ub)...>");
            printMission.println("1");
            printMission.println("0\t" + (mission.getSizeWaypoints()-1) + "\t0.0\t60.0");
            printMission.println("<episodes: n, ...(name,type,c,eS,eE,<Ra:|I|,|O|,...I...,...O...>)...> #I = |N|,...<N>... #N = |C|,...<C>...");
            //printMap.println(mission.getSizeBonus());
            /**
6
[Frountier] remain-in		0	0	3	1	0	1	0
[A]	end-in			0	0	1	1	0	1	1
[B]     end-in			0	1	2	1	0	1	2
[C]	end-in			0	2	3	1	0	1	3
[NFZ-1]	remain-in		0	0	3	0	1	1	4
[NFZ-2]	remain-in		0	0	3	0	1	1	5
             */

            int index = 1;
            for (int i = 0; i < mission.getSizeWaypoints() - 1; i++) {
                printMission.println(
                        "[WPT-" + (i+1) + "] \t end-in \t 0 \t " + (i) + " \t " + 
                        (i+1) + " \t 1 \t 0 \t 1 \t" + index);
                index++;
            }
            for (int i = 0; i < mission.getSizeWaypoints() - 1; i++) {
                printMission.println(
                        "[NFZ-" + (i+1) + "] \t remain-in \t 0 \t 0 \t " + 
                        (mission.getSizeWaypoints()-1) + " \t 0 \t 1 \t 1 \t" + index);
                index++;
            }
            printMission.println("<convex regions(C)>");
//            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
//                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_OBSTACLE)) {
//                    printMission.println("<x..., y..., n = 4, id = 0, type = n>");
//                    printMission.println(mission.getPoly3DVetX(i));
//                    printMission.println(mission.getPoly3DVetY(i));
//                }
//            }            
            
            printMission.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printer() " + ex);
        }
    }
}
