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
import reader.file.PrinterFrontier;
import reader.file.PrinterMapSGL_NFZ;
import reader.file.PrinterMapSGL_IFA;
import reader.file.ReaderKML;

/**
 *
 * @author jesimar
 */
public class MainGECCO2017 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        MainGECCO2017 main = new MainGECCO2017();
        main.exec();
    }
    
    public void exec() throws FileNotFoundException{
        Locale.setDefault(Locale.ENGLISH);
        
        String dir = "mission/usp_campus2/";
        String nameFileKml = "Mission-Campus2-USP.kml";
        
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
        String nameFileMapNFZ = "map-nfz.sgl";
        File fileMapNFZ = new File(dir + nameFileMapNFZ);
        PrinterMapSGL_NFZ mapNFZ = new PrinterMapSGL_NFZ(fileMapNFZ, mission);
        mapNFZ.printer();
        System.out.println("--------End PrinterMapSGL NFZ--------");
        
        System.out.println("--------Begin PrinterMapSGL Full--------");
        String nameFileMapFull = "map-full.sgl";
        File fileMapFull = new File(dir + nameFileMapFull);
        PrinterMapSGL_IFA mapFull = new PrinterMapSGL_IFA(fileMapFull, mission);
        mapFull.printer();
        System.out.println("--------End PrinterMapSGL Full--------");
        
        System.out.println("--------Begin GeoBase--------");
        String nameFileGeoBase = "geoBase.txt";
        File fileGeoBase = new File(dir + nameFileGeoBase);
        PrintStream print = new PrintStream(fileGeoBase);
        print.println(mission.getPointBase().latitude + " " + 
                mission.getPointBase().longitude + " " + 
                mission.getPointBase().altitude);
        print.close();
        System.out.println("--------End GeoBase--------");
        
        System.out.println("--------Begin PointsMission--------");
        String nameFilePointsMission = "pointsMission.txt";
        File filePointsMission = new File(dir + nameFilePointsMission);
        PrintStream printPointsMission = new PrintStream(filePointsMission);
        String strPointsMission = mission.getPontosPassagem();
        printPointsMission.print(strPointsMission);
        printPointsMission.close();
        System.out.println("--------End PointsMission--------");
        
        System.out.println("--------Begin PrinterFrontier--------");
        String nameFileFrontier = "map-frontier.sgl";
        File fileMapFrontier = new File(dir + nameFileFrontier);
        PrinterFrontier mapFrontier = new PrinterFrontier(fileMapFrontier, mission);
        mapFrontier.printer();
        System.out.println("--------End PrinterFrontier--------");
    }
}
