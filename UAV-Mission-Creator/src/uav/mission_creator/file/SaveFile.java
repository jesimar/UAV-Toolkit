package uav.mission_creator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import uav.mission_creator.struct.KeyWords;
import uav.mission_creator.struct.Mission;
import uav.mission_creator.struct.UtilGeo;

/**
 * The class is responsible for saving some information on file.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class SaveFile {

    /**
     * Class constructor.
     * @since version 4.0.0
     */
    public SaveFile() {
        
    }
    
    /**
     * Save in a file the map SGL with NFZ used in HGA4m.
     * @param fileMap file to save the map
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerMapSGL_NFZ(File fileMap, Mission mission) {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            printMap.println("<number of polygons>");
            printMap.println(mission.getSizeObstacle());
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_NFZ)) {
                    printMap.println("<x..., y..., n = 4, id = 0, type = n>");
                    printMap.println(mission.getPoly3DVetX(i));
                    printMap.println(mission.getPoly3DVetY(i));
                }
            }
            printMap.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printerMapSGL_NFZ() " + ex);
        }
    }

    /**
     * Save in a file the map SGL with NFZ used in A_STAR4m.
     * @param fileMap file to save the map
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerMapSGL_NFZ_AStar(File fileMap, Mission mission) {
        try {
            PrintStream printMap = new PrintStream(fileMap);
            int j = 0;
            for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_NFZ)) {
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
            System.err.println("[ERROR] [FileNotFoundException] printerMapSGL_NFZ_AStar() " + ex);
        }
    }
    
    /**
     * Save in a file the mission SGL used in CCQSP4m.
     * @param fileMission file to save the mission
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerMissionSGL_CCQSP(File fileMission, Mission mission) {
        double vx = 0.0;
        double vy = 0.0;
        double delta = 0.01;
        double timeMax = 60.0;
        double offset = 1.0;//radius of waypoint (in meters)
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
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_OBSTACLE)) {
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
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_OBSTACLE)) {
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
            System.err.println("[ERROR] [FileNotFoundException] printerMissionSGL_CCQSP() " + ex);
        }
    }
    
    /**
     * Save in a file the map SGL full (all regions nfz, penalty and bonus) 
     * used in path replanners (IFA).
     * @param fileMap file to save the map
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerMapSGL_IFA(File fileMap, Mission mission) {
        try{
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
                if (mission.getListPolyGeo().get(i).getName().contains(KeyWords.MAP_NFZ)) {
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
            System.err.println("[ERROR] [FileNotFoundException] printerMapSGL_IFA() " + ex);
        }
    }
    
    /**
     * Save in a file the frontier of the map.
     * @param fileMap file to save the map
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerFrontier(File fileMap, Mission mission) {
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
            System.err.println("[ERROR] [FileNotFoundException] printerFrontier() " + ex);
        }
    }
    
    /**
     * Save in a file the file kml after a pre-processing.
     * @param inFile file of input
     * @param outFile file of output
     * @since version 4.0.0
     */
    public void printerPreProcessorKML(File inFile, File outFile){
        try {
            Scanner sc = new Scanner(inFile);
            PrintStream print = new PrintStream(outFile);
            while(sc.hasNextLine()){
                String str = sc.nextLine();
                if (str.contains("<kml")){
                    str = "<kml>";
                }
                if (str.contains("<")){
                    str = str.replace(":", "_");
                }
                print.println(str);
            }
            print.close();
            sc.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printerPreProcessorKML() " + ex);
        }                                
    }
    
    /**
     * Save in a file the points with the directions of drone.
     * @param fileMission file to save features of mission
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerFeaturesMission(File fileMission, Mission mission){
        try {
            PrintStream print = new PrintStream(fileMission);
            print.print("Waypoints Mission\n");
            print.print(mission.getWaypointsMissionGeo());
            print.print("Waypoints Buzzer\n");
            print.print(mission.getWaypointsBuzzer());
            print.print("Waypoints Camera Picture\n");
            print.print(mission.getWaypointsCameraPicture());
            print.print("Waypoints Camera Video\n");
            print.print(mission.getWaypointsCameraVideo());
            print.print("Waypoints Camera Photo In Sequence\n");
            print.print(mission.getWaypointsCameraPhotoInSequence());
            print.print("Waypoints Spraying\n");
            print.print(mission.getWaypointsSpraying());
            print.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printerFileFeaturesMission() " + ex);
        } 
    }
    
    /**
     * Save in a file the waypoints 3D
     * @param fileMission file to save waypoints 3D
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerWaypoints3D(File fileMission, Mission mission){
        try {
            PrintStream printWptsMission = new PrintStream(fileMission);
            printWptsMission.print(mission.getWaypoints3D());
            printWptsMission.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printerFileWaypoints3D() " + ex);
        } 
    }
    
    /**
     * Save in a file the point geo base
     * @param fileGeoBase file to save point geo base
     * @param mission object with the mission
     * @param sep type of separator
     * @since version 4.0.0
     */
    public void printerGeoBase(File fileGeoBase, Mission mission, String sep){
        try {
            PrintStream print = new PrintStream(fileGeoBase);
            print.println("#longitude latitude altitude");
            print.println(mission.getPointBase().getLng() + sep + 
                          mission.getPointBase().getLat() + sep +
                          mission.getPointBase().getAlt());
            print.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printerFileGeoBase() " + ex);
        } 
    }
    
    /**
     * Save in a file the points with the directions of drone.
     * @param filePointsFailure file to save points of failure
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printerPointsFailure(File filePointsFailure, Mission mission) {
        try {      
            PrintStream printFailure = new PrintStream(filePointsFailure);            
            printFailure.println("<px, py, pz, vx, vy, vz>");
            for (int i = 0; i < mission.getSizeListLineGeo(); i++) {
                if (mission.getListLineGeo().get(i).getName().contains(KeyWords.POINT_FAILURE)) { 
                    System.out.println("\t Ponto de Falha: ");
                    System.out.println("\t\t 3D: ");
                    System.out.print("\t\t\t" + mission.getListLine3D().get(i).toString(0));
                    //System.out.println("\t\t\t" + mission.getListLine3D().get(i).toString(1));
                    UtilGeo.savePointsDirections(printFailure, mission.getListLine3D().get(i));
                    System.out.println("\t\t Geo: ");
                    System.out.print("\t\t\t" + mission.getListLineGeo().get(i).toString(0));
                    //System.out.println("\t\t\t" + mission.getListLine().get(i).toString(1));
                }
            }
            printFailure.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] printerPointsFailure() " + ex);
        }
    }
    
    /**
     * Prints on the screen some information about the failures.
     * @param mission object with the mission
     * @since version 4.0.0
     */
    public void printFailures(Mission mission){
        for (int i = 0; i < mission.getSizeListPolyGeo(); i++) {
            if (mission.getListPolyGeo().get(i).getName().contains("failure")) {
                System.out.println("\t Failure: ");
                System.out.println("\t\t 3D: ");
                System.out.print("\t\t\t" + mission.getListPoly3D().get(i).getCenterX());
                System.out.println("\t\t" + mission.getListPoly3D().get(i).getCenterY());
                System.out.println("\t\t Geo: ");
                System.out.print("\t\t\t" + mission.getListPolyGeo().get(i).getCenterY());
                System.out.println("\t\t" + mission.getListPolyGeo().get(i).getCenterX());
            }
        }
    }
    
}
