/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import reader.data.PointGeo;
import reader.data.UtilGeo;

/**
 *
 * @author jesimar
 */
public class MainRoute3DToGeo_GraphicsKML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        MainRoute3DToGeo_GraphicsKML main = new MainRoute3DToGeo_GraphicsKML();
        main.exec();
    }   
    
    public void exec() throws FileNotFoundException{
        Locale.setDefault(Locale.ENGLISH);
        
        //String dir = "mission/";
        String dir = "graphics_kml/";
        
        System.out.println("--------Begin MainRoute3DToGeo--------");
        String nameFileRoute3D = "route-edison-full";
//        String nameFileRouteGeo = "routeGeo.txt";
        String nameFileGeoBase = "geoBase.txt";
        String separator = "\t";
        
        Scanner readGeoBase = new Scanner(new File(dir + nameFileGeoBase));
        String strGeoBase = readGeoBase.nextLine();
        String strGeo[] = strGeoBase.split(" ");
        readGeoBase.close();
        
        double lat = Double.parseDouble(strGeo[0]);
        double lon = Double.parseDouble(strGeo[1]);
        double alt = Double.parseDouble(strGeo[2]);
        PointGeo pGeo = new PointGeo(lon, lat, alt);
        
        File fileRouteGeo = new File(dir + nameFileRoute3D + "-geo.txt");
        PrintStream printGeo = new PrintStream(fileRouteGeo);
        
        Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D + ".txt"));
        while(readRoute3D.hasNext()){
            String str[] = readRoute3D.nextLine().split(separator);                        
            double x = Double.parseDouble(str[0]);
            double y = Double.parseDouble(str[1]);
            double h;
            if (str.length >= 3){
                h = Double.parseDouble(str[2]);
            }else{
                h = 100;
            }
            printGeo.print(UtilGeo.parseToGeo3(pGeo, x, y));
        }
        readRoute3D.close();
        printGeo.close();
        
        System.out.println("--------End MainRoute3DToGeo--------");
    }
}
