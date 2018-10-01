package uav.mission_creator;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import uav.generic.util.UtilString;
import uav.mission_creator.struct.Mission;
import uav.mission_creator.file.ReaderFileConfigMission;
import uav.mission_creator.file.ReaderKML;
import uav.mission_creator.file.SaveFile;

/**
 * Main class that starts the Mission Creator system.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class MissionCreator {

    private final ReaderFileConfigMission config;
    private final SaveFile save;
        
    /**
     * Method main that start the MOSA System.
     * @param args not used
     * @throws java.io.IOException
     * @since version 3.0.0
     */
    public static void main(String[] args) throws IOException {        
        Locale.setDefault(Locale.ENGLISH);
        MissionCreator main = new MissionCreator();
        main.exec();
    }

    /**
     * Class constructor.
     * @throws IOException 
     * @since version 3.0.0
     */
    public MissionCreator() throws IOException {
        config = new ReaderFileConfigMission();
        save = new SaveFile();
    }
    
    public void exec() {
        Mission mission = new Mission();
        createFilePreProcessor();
        readerKML(mission);
        createFileSGL_NFZ(mission); 
        createFileSGL_NFZ_AStar(mission);
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
        save.printerPreProcessorKML(inFile, outFile);        
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
        save.printerMapSGL_NFZ(fileMapNFZ, mission);
        System.out.println("--------End PrinterMapSGL NFZ--------");
    }
    
    private void createFileSGL_NFZ_AStar(Mission mission){
        System.out.println("--------Begin PrinterMapSGL NFZ AStar--------");
        File fileMapAstar = new File(config.getDirRouteKML() + "map-nfz-astar.sgl");
        save.printerMapSGL_NFZ_AStar(fileMapAstar, mission);
        System.out.println("--------End PrinterMapSGL NFZ AStar--------");
    }
    
    private void createFileSGL_Full(Mission mission){
        System.out.println("--------Begin PrinterMapSGL Full--------");
        File fileMapFull = new File(config.getDirRouteKML() + config.getFileMapFull());
        save.printerMapSGL_IFA(fileMapFull, mission);
        System.out.println("--------End PrinterMapSGL Full--------");
    }
    
    private void createFileMissionCCQSP4m(Mission mission){
        System.out.println("--------Begin Printer MissionCCQSP4m--------");
        File fileMissionCCQSP = new File(config.getDirRouteKML() + config.getFileMissionCCQSP());
        save.printerMissionSGL_CCQSP(fileMissionCCQSP, mission);
        System.out.println("--------End Printer MissionCCQSP4m--------");
    }
    
    private void createFileGeoBase(Mission mission){
        System.out.println("--------Begin GeoBase--------");
        String sep = UtilString.defineSeparator(config.getSeparatorGeoBaseOut());
        File fileGeoBase = new File(config.getDirRouteKML() + config.getFileGeoBaseOut());
        save.printerGeoBase(fileGeoBase, mission, sep);
        System.out.println("--------End GeoBase--------");
    }
    
    private void createFileWaypoints3D(Mission mission){
        System.out.println("--------Begin WaypointsMission3D--------");
        File fileMission = new File(config.getDirRouteKML() + config.getFileWaypointsMission());
        save.printerWaypoints3D(fileMission, mission);
        System.out.println("--------End WaypointsMission3D--------");
    }
    
    private void createFileFeaturesMission(Mission mission) {
        System.out.println("--------Begin FeaturesMission--------");
        File fileFeatures = new File(config.getDirRouteKML() + config.getFileFeatureMission());
        save.printerFeaturesMission(fileFeatures, mission);
        System.out.println("--------End FeaturesMission--------");
    }
    
    private void createFileWaypointsFrontier(Mission mission){
        System.out.println("--------Begin PrinterFrontier--------");
        File fileMapFrontier = new File(config.getDirRouteKML()  + "map-frontier.sgl");
        save.printerFrontier(fileMapFrontier, mission);
        System.out.println("--------End PrinterFrontier--------");
    }
    
    private void createPointsFailure(Mission mission){
        System.out.println("--------Begin Pontos de Falha--------");
        File fileFailure = new File(config.getDirRouteKML() + "pontos_de_falha.txt");
        save.printerPointsFailure(fileFailure, mission);
        System.out.println("--------End Pontos de Falha--------");
    }
    
}
