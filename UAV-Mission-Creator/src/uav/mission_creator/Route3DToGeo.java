package uav.mission_creator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import uav.mission_creator.struct.geom.PointGeo;
import uav.mission_creator.struct.Util;
import uav.mission_creator.struct.UtilGeo;
import uav.mission_creator.file.LoadConfig;

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
        
        Scanner readGeoBase = new Scanner(new File(dir + config.getFileGeoBaseIn()));
        String strGeoBase = readGeoBase.nextLine();
        strGeoBase = readGeoBase.nextLine();
        String separatorGeo = Util.defineSeparator(config.getSeparatorGeoBaseIn());
        String strGeo[] = strGeoBase.split(separatorGeo);
        readGeoBase.close();
        
        double lon = Double.parseDouble(strGeo[0]);
        double lat = Double.parseDouble(strGeo[1]);
        double alt = Double.parseDouble(strGeo[2]);
        PointGeo pGeo = new PointGeo(lon, lat, alt);
        
        PrintStream printGeo = new PrintStream(new File(dir + config.getFileRouteGeo()));
        
        Scanner readRoute3D = new Scanner(new File(dir + config.getFileRoute3D()));
        String separatorRoute3D = Util.defineSeparator(config.getSeparatorRoute3D());
        String separatorRouteGeo = Util.defineSeparator(config.getSeparatorRouteGeo());
        while(readRoute3D.hasNext()){
            String str[] = readRoute3D.nextLine().split(separatorRoute3D);                        
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
                printGeo.println(UtilGeo.parseToGeo(pGeo, x, y, h, separatorRouteGeo));
            }
        }
        readRoute3D.close();
        printGeo.close();
        
        System.out.println("--------End Route3DToGeo--------");                
    }
}
