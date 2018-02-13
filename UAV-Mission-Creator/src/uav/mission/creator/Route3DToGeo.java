/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission.creator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import mission.creator.data.PointGeo;
import mission.creator.data.Util;
import mission.creator.data.UtilGeo;
import mission.creator.file.LoadConfig;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Route3DToGeo {

    private final LoadConfig config;    
   
    public static void main(String[] args) throws FileNotFoundException, IOException {        
        Route3DToGeo main = new Route3DToGeo();
        main.exec();
    }

    public Route3DToGeo() throws IOException {
        config = new LoadConfig();
    }
    
    public void exec() throws FileNotFoundException{
        Locale.setDefault(Locale.ENGLISH);
        
        String dir = config.getDirRoute3D();
        
        System.out.println("--------Begin Route3DToGeo--------");
        String nameFileRoute3D = config.getFileRoute3D();
        String nameFileRouteGeo = config.getFileRouteGeo();
        String nameFileGeoBase = config.getFileGeoBaseIn();
        
        Scanner readGeoBase = new Scanner(new File(dir + nameFileGeoBase));
        String strGeoBase = readGeoBase.nextLine();
        strGeoBase = readGeoBase.nextLine();
        String strGeo[] = strGeoBase.split(
                Util.defineSeparator(config.getSeparatorGeoBaseIn()));
        readGeoBase.close();
        
        double lon = Double.parseDouble(strGeo[0]);
        double lat = Double.parseDouble(strGeo[1]);
        double alt = Double.parseDouble(strGeo[2]);
        PointGeo pGeo = new PointGeo(lon, lat, alt);
        
        File fileRouteGeo = new File(dir + nameFileRouteGeo);
        PrintStream printGeo = new PrintStream(fileRouteGeo);
        
        Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
        while(readRoute3D.hasNext()){
            String str[] = readRoute3D.nextLine().split(
                    Util.defineSeparator(config.getSeparatorRoute3D()));                        
            double x = Double.parseDouble(str[0]);
            double y = Double.parseDouble(str[1]);
            double h;
            if (str.length >= 3){
                h = Double.parseDouble(str[2]);
            }else{
                h = 100;
            }
            if (config.getIsUsedKmlGoogleEarth()){
                printGeo.print(UtilGeo.parseToGeo3(pGeo, x, y));
            }else{
                printGeo.println(UtilGeo.parseToGeo(pGeo, x, y, h, 
                    Util.defineSeparator(config.getSeparatorRouteGeo())));
            }
        }
        readRoute3D.close();
        printGeo.close();
        
        System.out.println("--------End Route3DToGeo--------");                
    }
}
