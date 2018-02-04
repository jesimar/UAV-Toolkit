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
public class MainRoute3DToGeo_ICTAI2017 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        MainRoute3DToGeo_ICTAI2017 main = new MainRoute3DToGeo_ICTAI2017();
        main.exec();
    }   
    
    public void exec() throws FileNotFoundException{
        Locale.setDefault(Locale.ENGLISH);
        
        //String dir = "mission/";
        String dir = "mission/ictai2017/";
        
        System.out.println("--------Begin MainRoute3DToGeo--------");
        //String nameFileRoute3D = "route.txt";
        String nameFileRoute3D = "route-GA-M4.txt";
        String nameFileRouteGeo = "routeGeo.txt";
        String nameFileGeoBase = "geoBase.txt";
        
        Scanner readGeoBase = new Scanner(new File(dir + nameFileGeoBase));
        String strGeoBase = readGeoBase.nextLine();
        String strGeo[] = strGeoBase.split(" ");
        readGeoBase.close();
        
        double lat = Double.parseDouble(strGeo[0]);
        double lon = Double.parseDouble(strGeo[1]);
        double alt = Double.parseDouble(strGeo[2]);
        PointGeo pGeo = new PointGeo(lon, lat, alt);
        
        File fileRouteGeo = new File(dir + nameFileRouteGeo);
        PrintStream printGeo = new PrintStream(fileRouteGeo);
        
        Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
        while(readRoute3D.hasNext()){
            String str[] = readRoute3D.nextLine().split(" ");                        
            double x = Double.parseDouble(str[0]);
            double y = Double.parseDouble(str[1]);
            double h;
            if (str.length >= 3){
                h = Double.parseDouble(str[2]);
            }else{
                h = 100;
            }
            printGeo.println(UtilGeo.parseToGeo2(pGeo, x, y, h));
        }
        readRoute3D.close();
        printGeo.close();
        
        System.out.println("--------End MainRoute3DToGeo--------");
    }
}
