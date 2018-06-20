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
        createFileGeoBase(mission);      
        createFileWaypoints3D(mission);
        createFileWaypointsGeo(mission);
        createFileWaypointsGeoLabel(mission);
        createFileWaypointsBuzzer(mission);
        
        if (mission.hasCameraPhoto()){
            createFileWaypointsCamera(mission);
        }
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
    
    private void createFileWaypointsGeo(Mission mission) throws FileNotFoundException{
        System.out.println("--------Begin WaypointsMissionGeo--------");
        File fileGeo = new File(config.getDirRouteKML() + config.getFileWaypointsMissionGeo());
        PrintStream printWptsMissionGeo = new PrintStream(fileGeo);
        printWptsMissionGeo.print(mission.getWaypointsGeo());
        printWptsMissionGeo.close();
        System.out.println("--------End WaypointsMissionGeo--------");
    }
    
    private void createFileWaypointsGeoLabel(Mission mission) throws FileNotFoundException{
        System.out.println("--------Begin WaypointsMissionGeoLabel--------");
        File fileGeoLabel = new File(config.getDirRouteKML() + config.getFileWaypointsMissionGeoLabel());
        PrintStream printMissionGeoLabel = new PrintStream(fileGeoLabel);
        printMissionGeoLabel.print(mission.getWaypointsGeoLabel());
        printMissionGeoLabel.close();
        System.out.println("--------End WaypointsMissionGeoLabel--------");
    }
    
    private void createFileWaypointsBuzzer(Mission mission) throws FileNotFoundException{
        System.out.println("--------Begin WaypointsBuzzer--------");
        File fileBuzzer = new File(config.getDirRouteKML() + config.getFileWaypointsBuzzer());
        PrintStream printWaypointsBuzzer = new PrintStream(fileBuzzer);
        printWaypointsBuzzer.print(mission.getWaypointsBuzzer());
        printWaypointsBuzzer.close();
        System.out.println("--------End WaypointsBuzzer--------");
    }
    
    private void createFileWaypointsCamera(Mission mission) throws FileNotFoundException{
        System.out.println("--------Begin WaypointsCamera--------");
        File fileCamera = new File(config.getDirRouteKML() + config.getFileWaypointsCamera());
        PrintStream printWptsCamera = new PrintStream(fileCamera);
        printWptsCamera.print(mission.getWaypointsCameraPhoto());
        printWptsCamera.close();
        System.out.println("--------End WaypointsCamera--------");
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
