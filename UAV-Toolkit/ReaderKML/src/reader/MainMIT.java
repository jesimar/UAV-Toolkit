/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import reader.file.ReaderKML;
import reader.file.PrinterYAML;
import reader.file.PreProcessorKML;
import java.io.File;
import java.util.Locale;
import reader.data.Mission;
import reader.data.TypeLocalization;
import reader.file.PrinterInfo;
import reader.file.PrinterMapSGL_MIT;
import reader.file.PrinterRoute;

/**
 *
 * @author jesimar
 */
public class MainMIT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainMIT main = new MainMIT();
        main.exec();
    }
    
    public void exec(){
        Locale.setDefault(Locale.ENGLISH);
        TypeLocalization type = TypeLocalization.CARTEZIAN;
        
        String dir = "mission/";
        String nameFileKml = "Mission.kml";
        
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
                
        System.out.println("--------Begin PrinterYAML--------");
        String nameFileYaml = "yaml_mission.yaml";
        File fileYAML = new File(dir + nameFileYaml);
        PrinterYAML printer = new PrinterYAML(fileYAML, mission, type);
        printer.printer();
        System.out.println("--------End PrinterYAML--------");
        
        System.out.println("--------Begin PrinterRoute--------");
        String nameFileTakeoff = "route_takeoff.txt";
        String nameFileLanding = "route_landing.txt";
        File fileTakeoff = new File(dir + nameFileTakeoff);
        File fileLanding = new File(dir + nameFileLanding);        
        PrinterRoute route = new PrinterRoute(mission, fileTakeoff, fileLanding, type);
        route.printer();
        System.out.println("--------End PrinterRoute--------");
        
        System.out.println("--------Begin PrinterInfo--------");
        String nameFileInfo = "info.txt";
        File fileInfo = new File(dir + nameFileInfo);
        PrinterInfo coordInit = new PrinterInfo(fileInfo, mission);
        coordInit.printer();
        System.out.println("--------End PrinterInfo--------");
        
        System.out.println("--------Begin PrinterMapSGL--------");
        String nameFileMap = "map.sgl";
        File fileMap = new File(dir + nameFileMap);
        PrinterMapSGL_MIT map = new PrinterMapSGL_MIT(fileMap, mission);
        map.printer();
        System.out.println("--------End PrinterMapSGL--------");
    }
}
