/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission.creator;

import mission.creator.file.PreProcessorKML;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import mission.creator.data.Mission;
import mission.creator.data.Util;
import mission.creator.file.LoadConfig;
import mission.creator.file.PrinterMapSGL_NFZ;
import mission.creator.file.PrinterMapSGL_IFA;
import mission.creator.file.ReaderKML;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ProcessorKML {

    private final LoadConfig config;
        
    public static void main(String[] args) throws FileNotFoundException, IOException {        
        ProcessorKML main = new ProcessorKML();
        main.exec();
    }

    public ProcessorKML() throws IOException {
        config = new LoadConfig();
    }
    
    public void exec() throws FileNotFoundException{
        Locale.setDefault(Locale.ENGLISH);
        
        String dir = config.getDirRouteKML();
        String nameFileKml = config.getFileRouteKML();
        
        System.out.println("--------Begin PreProcessor--------");
        
        File inFile = new File(dir + nameFileKml);
        File outFile = new File(dir + "new" + nameFileKml);
        PreProcessorKML preProcessor = new PreProcessorKML(inFile, outFile);
        preProcessor.preProcessor();        
        System.out.println("--------End PreProcessor--------");
        
        System.out.println("--------Begin ReaderKML--------");
        Mission mission = new Mission();        
        File newKmlFile = new File(dir + "new" + nameFileKml);
        ReaderKML readerKML  = new ReaderKML(newKmlFile, mission);
        readerKML.reader();
        System.out.println("--------End ReaderKML--------");                
        
        System.out.println("--------Begin PrinterMapSGL NFZ--------");
        String nameFileMapNFZ = config.getFileMapNFZ();
        File fileMapNFZ = new File(dir + nameFileMapNFZ);
        PrinterMapSGL_NFZ mapNFZ = new PrinterMapSGL_NFZ(fileMapNFZ, mission);
        mapNFZ.printer();
        System.out.println("--------End PrinterMapSGL NFZ--------");
        
        System.out.println("--------Begin PrinterMapSGL Full--------");
        String nameFileMapFull = config.getFileMapFull();
        File fileMapFull = new File(dir + nameFileMapFull);
        PrinterMapSGL_IFA mapFull = new PrinterMapSGL_IFA(fileMapFull, mission);
        mapFull.printer();
        System.out.println("--------End PrinterMapSGL Full--------");
        
        System.out.println("--------Begin GeoBase--------");
        String nameFileGeoBase = config.getFileGeoBaseOut();
        String sep = Util.defineSeparator(config.getSeparatorGeoBaseOut());
        File fileGeoBase = new File(dir + nameFileGeoBase);
        PrintStream print = new PrintStream(fileGeoBase);
        print.println("#longitude latitude altitude");
        print.println(mission.getPointBase().getLongitude() + sep + 
                      mission.getPointBase().getLatitude()  + sep +
                      mission.getPointBase().getAltitude());
        print.close();
        System.out.println("--------End GeoBase--------");
        
        System.out.println("--------Begin WaypointsMission3D--------");
        String nameFilePointsMission = config.getFileWaypointsMission();
        File filePointsMission = new File(dir + nameFilePointsMission);
        PrintStream printPointsMission = new PrintStream(filePointsMission);
        String strPointsMission = mission.getWaypoints3D();
        printPointsMission.print(strPointsMission);
        printPointsMission.close();
        System.out.println("--------End WaypointsMission3D--------");
        
        System.out.println("--------Begin WaypointsMission--------");
        String nameFilePointsMissionGeo = config.getFileWaypointsMissionGeo();
        File filePointsMissionGeo = new File(dir + nameFilePointsMissionGeo);
        PrintStream printPointsMissionGeo = new PrintStream(filePointsMissionGeo);
        String strPointsMissionGeo = mission.getWaypoints();
        printPointsMissionGeo.print(strPointsMissionGeo);
        printPointsMissionGeo.close();
        System.out.println("--------End WaypointsMission--------");
        
        System.out.println("--------Begin WaypointsMissionSimple--------");
        String nameFilePointsMissionGeoSimple = config.getFileWaypointsMissionGeoSimple();
        File filePointsMissionGeoSimple = new File(dir + nameFilePointsMissionGeoSimple);
        PrintStream printPointsMissionGeoSimple = new PrintStream(filePointsMissionGeoSimple);
        String strPointsMissionGeoSimple = mission.getWaypointsSimple();
        printPointsMissionGeoSimple.print(strPointsMissionGeoSimple);
        printPointsMissionGeoSimple.close();
        System.out.println("--------End WaypointsMissionSimple--------");
        
//        System.out.println("--------Begin PrinterFrontier--------");
//        String nameFileFrontier = "map-frontier.sgl";
//        File fileMapFrontier = new File(dir + nameFileFrontier);
//        PrinterFrontier mapFrontier = new PrinterFrontier(fileMapFrontier, mission);
//        mapFrontier.printer();
//        System.out.println("--------End PrinterFrontier--------");                
        
//        System.out.println("--------Begin Pontos de Falha--------");
//        String nameFilePontosFalha = "pontos_de_falha.txt";
//        File filePontoFalha = new File(dir + nameFilePontosFalha);
//        PrinterPointsFailure printerPointsFailure = new PrinterPointsFailure(filePontoFalha, mission);
//        printerPointsFailure.printer();
//        System.out.println("--------End Pontos de Falha--------");
    }
}
