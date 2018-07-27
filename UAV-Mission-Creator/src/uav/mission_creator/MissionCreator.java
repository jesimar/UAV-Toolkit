package uav.mission_creator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import uav.generic.util.UtilString;
import uav.mission_creator.struct.Mission;
import uav.mission_creator.file.PreProcessorKML;
import uav.mission_creator.file.ReaderFileConfigMission;
import uav.mission_creator.file.PrinterFrontier;
import uav.mission_creator.file.PrinterMapSGL_NFZ;
import uav.mission_creator.file.PrinterMapSGL_IFA;
import uav.mission_creator.file.PrinterMissionSGL_CCQSP;
import uav.mission_creator.file.PrinterPointsFailure;
import uav.mission_creator.file.ReaderKML;

/**
 *
 * @author Jesimar S. Arantes
 */
public class MissionCreator {

    private final ReaderFileConfigMission config;
        
    public static void main(String[] args) throws FileNotFoundException, IOException {        
        Locale.setDefault(Locale.ENGLISH);
        MissionCreator main = new MissionCreator();
        main.exec();
    }

    public MissionCreator() throws IOException {
        config = new ReaderFileConfigMission();
    }
    
    public void exec() throws FileNotFoundException{
        Mission mission = new Mission();
        createFilePreProcessor();
        readerKML(mission);
        createFileSGL_NFZ(mission);        
        createFileSGL_Full(mission);
        createFileMissionCCQSP4m(mission);
        createFileGeoBase(mission);      
        createFileWaypoints3D(mission);
        createFileFeaturesMission(mission);
        if (mission.hasFrontier()){
            createFileWaypointsFrontier(mission);
        }
        if (mission.hasPointFailure()){
            createPointsFailure(mission);
        }
    }
    
    private void createFilePreProcessor(){
        System.out.println("--------Begin PreProcessor--------");        
        File inFile = new File(config.getDirRouteKML() + config.getFileRouteKML());
        File outFile = new File(config.getDirRouteKML()+"new"+config.getFileRouteKML());
        PreProcessorKML preProcessor = new PreProcessorKML(inFile, outFile);
        preProcessor.preProcessor();        
        System.out.println("--------End PreProcessor--------");
    }
    
    private void readerKML(Mission mission){
        System.out.println("--------Begin ReaderKML--------");
        File newKmlFile = new File(config.getDirRouteKML()+"new"+config.getFileRouteKML());
        ReaderKML readerKML = new ReaderKML(newKmlFile, mission);
        readerKML.reader();
        mission.pointGeoTo3D();
        System.out.println("--------End ReaderKML--------");
    }
    
    private void createFileSGL_NFZ(Mission mission){
        System.out.println("--------Begin PrinterMapSGL NFZ--------");
        File fileMapNFZ = new File(config.getDirRouteKML() + config.getFileMapNFZ());
        PrinterMapSGL_NFZ mapNFZ = new PrinterMapSGL_NFZ(fileMapNFZ, mission);
        mapNFZ.printer();
        System.out.println("--------End PrinterMapSGL NFZ--------");
    }
    
    private void createFileSGL_Full(Mission mission){
        System.out.println("--------Begin PrinterMapSGL Full--------");
        File fileMapFull = new File(config.getDirRouteKML() + config.getFileMapFull());
        PrinterMapSGL_IFA mapFull = new PrinterMapSGL_IFA(fileMapFull, mission);
        mapFull.printer();
        System.out.println("--------End PrinterMapSGL Full--------");
    }
    
    private void createFileMissionCCQSP4m(Mission mission){
        System.out.println("--------Begin Printer MissionCCQSP4m--------");
        File fileMissionCCQSP = new File(config.getDirRouteKML() + config.getFileMissionCCQSP());
        PrinterMissionSGL_CCQSP missionCCQSP = new PrinterMissionSGL_CCQSP(
                fileMissionCCQSP, mission);
        missionCCQSP.printer();
        System.out.println("--------End Printer MissionCCQSP4m--------");
    }
    
    private void createFileGeoBase(Mission mission) throws FileNotFoundException{
        System.out.println("--------Begin GeoBase--------");
        String sep = UtilString.defineSeparator(config.getSeparatorGeoBaseOut());
        File fileGeoBase = new File(config.getDirRouteKML() + config.getFileGeoBaseOut());
        PrintStream print = new PrintStream(fileGeoBase);
        print.println("#longitude latitude altitude");
        print.println(mission.getPointBase().getLng() + sep + 
                      mission.getPointBase().getLat() + sep +
                      mission.getPointBase().getAlt());
        print.close();
        System.out.println("--------End GeoBase--------");
    }
    
    private void createFileWaypoints3D(Mission mission) throws FileNotFoundException{
        System.out.println("--------Begin WaypointsMission3D--------");
        File fileMission = new File(config.getDirRouteKML() + config.getFileWaypointsMission());
        PrintStream printWptsMission = new PrintStream(fileMission);
        printWptsMission.print(mission.getWaypoints3D());
        printWptsMission.close();
        System.out.println("--------End WaypointsMission3D--------");
    }
    
    private void createFileFeaturesMission(Mission mission) throws FileNotFoundException{
        System.out.println("--------Begin FeaturesMission--------");
        File fileFeatures = new File(config.getDirRouteKML() + config.getFileFeatureMission());
        PrintStream printFeaturesMission = new PrintStream(fileFeatures);
        
        printFeaturesMission.print("Waypoints Mission\n");
        printFeaturesMission.print(mission.getWaypointsMissionGeo());
        
        printFeaturesMission.print("Waypoints Buzzer\n");
        printFeaturesMission.print(mission.getWaypointsBuzzer());
        
        printFeaturesMission.print("Waypoints Camera Photo\n");
        printFeaturesMission.print(mission.getWaypointsCameraPhoto());
        
        printFeaturesMission.print("Waypoints Camera Video\n");
        printFeaturesMission.print(mission.getWaypointsCameraVideo());
        
        printFeaturesMission.print("Waypoints Spraying\n");
        printFeaturesMission.print(mission.getWaypointsSpraying());
        
        printFeaturesMission.close();
        System.out.println("--------End FeaturesMission--------");
    }
    
    private void createFileWaypointsFrontier(Mission mission){
        System.out.println("--------Begin PrinterFrontier--------");
        File fileMapFrontier = new File(config.getDirRouteKML()  + "map-frontier.sgl");
        PrinterFrontier mapFrontier = new PrinterFrontier(fileMapFrontier, mission);
        mapFrontier.printer();
        System.out.println("--------End PrinterFrontier--------");
    }
    
    private void createPointsFailure(Mission mission){
        System.out.println("--------Begin Pontos de Falha--------");
        File fileFailure = new File(config.getDirRouteKML() + "pontos_de_falha.txt");
        PrinterPointsFailure printerFailure = new PrinterPointsFailure(fileFailure, mission);
        printerFailure.printer();
        System.out.println("--------End Pontos de Falha--------");
    }
    
}
