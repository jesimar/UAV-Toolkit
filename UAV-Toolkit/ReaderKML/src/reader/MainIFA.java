/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import reader.file.PreProcessorKML;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import reader.data.Mission;
import reader.file.PrinterMapSGL_IFA;
import reader.file.ReaderKML;

/**
 *
 * @author jesimar
 */
public class MainIFA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        MainIFA main = new MainIFA();
        main.exec();
    }
    
    public void exec() throws FileNotFoundException{
        Locale.setDefault(Locale.ENGLISH);
        
        String dir = "mission/";
        String exp = "Marcelo";//"Simoes"
        String nameFileKml = "Mission" + exp + ".kml";
        
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
        
        System.out.println("--------Begin PrinterMapSGL--------");
        String nameFileMap = "map" + exp + ".sgl";
        File fileMap = new File(dir + nameFileMap);
        PrinterMapSGL_IFA map = new PrinterMapSGL_IFA(fileMap, mission);
        map.printer();
        System.out.println("--------End PrinterMapSGL--------");
        
        System.out.println("--------Begin GeoBase--------");
        String nameFileGeoBase = "geoBase" + exp + ".txt";
        File fileGeoBase = new File(dir + nameFileGeoBase);
        PrintStream print = new PrintStream(fileGeoBase);
        print.println(mission.getPointBase().latitude + " " + 
                mission.getPointBase().longitude + " " + 
                mission.getPointBase().altitude);
        System.out.println("--------End GeoBase--------");
    }
}
