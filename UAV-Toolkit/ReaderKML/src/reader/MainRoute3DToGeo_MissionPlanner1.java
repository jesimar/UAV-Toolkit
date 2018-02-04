/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import reader.data.PointGeo;
import static reader.data.UtilGeo.CIRC_EQUATORIAL;
import static reader.data.UtilGeo.CIRC_MERIDIONAL;

/**
 *
 * @author jesimar
 */
public class MainRoute3DToGeo_MissionPlanner1 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        MainRoute3DToGeo_MissionPlanner1 main = new MainRoute3DToGeo_MissionPlanner1();
        main.exec();
    }
    
    public void exec() throws FileNotFoundException{
        Locale.setDefault(Locale.ENGLISH);
        
        String dir = "mission/";
        String exp = "Simoes";//"Simoes";
        
        System.out.println("--------Begin MainRoute3DToGeo_MissionPlanner--------");
        String nameFileRoute3D = "route" + exp +".txt";
        String nameFileRouteGeo = "route" + exp +"MissionPlanner.txt";
        String nameFileGeoBase = "geoBase" + exp +".txt";
        
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
        printGeo.println("QGC WPL 110");
        int i = 0;
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
            printGeo.println(i + "\t" + (i == 0 ? 1 : 0) + 
                    "\t" + (i == 0 ? 0 : 3) + "\t16\t0\t0\t0\t0\t" + 
                    parseToGeo2(new PointGeo(0, 0, 0), x, y, h) + "\t1");
            i++;
        }
        readRoute3D.close();
        printGeo.close();
        
        System.out.println("--------End MainRoute3DToGeo_MissionPlanner--------");
    }
    
    public String parseToGeo2(PointGeo base, double x, double y, double h){
        double lat = base.latitude + y*360/CIRC_MERIDIONAL;
        double lon = base.longitude+ x*360/(CIRC_EQUATORIAL*Math.cos(base.latitude*Math.PI/180));
        double alt = base.altitude + h;
        return lat + "\t" + lon + "\t" + alt;
    }
    
    public static int getLineNumber(File file) throws FileNotFoundException, IOException{
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        return lnr.getLineNumber();
    }
}
