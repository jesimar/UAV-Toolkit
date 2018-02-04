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
import reader.data.UtilGeo;

/**
 *
 * @author jesimar
 */
public class MainRoute3DToGeo_MissionPlanner {

    private boolean decaimentoLinear = true;
    private double altitudeBase;
    private double amplitude;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        MainRoute3DToGeo_MissionPlanner main = new MainRoute3DToGeo_MissionPlanner();
        main.exec();
    }

    public void exec() throws FileNotFoundException, IOException {
        System.out.println("--------Begin MainRoute3DToGeo_MissionPlanner--------");

        Locale.setDefault(Locale.ENGLISH);

        String dir = "mission/";
        String exp = "";//"Simoes";
        String nameFileRoute3D = "route" + exp + ".txt";
        String nameFileRouteGeo = "route" + exp + "MissionPlanner.txt";
        String nameFileGeoBase = "geoBaseOnofre.txt";//"geoBase" + exp +".txt";
        String nameFileConfig = "config.txt";//"geoBase" + exp +".txt";                                            

        PointGeo pGeo = readFileGeoBase(dir, nameFileGeoBase);
        readFileConfig(dir, nameFileConfig);

        File fileRouteGeo = new File(dir + nameFileRouteGeo);
        PrintStream printGeo = new PrintStream(fileRouteGeo);
        File fileRoute3D = new File(dir + nameFileRoute3D);
        Scanner readRoute3D = new Scanner(fileRoute3D);
        printGeo.println("QGC WPL 110");
        int i = 0;
        double waypoint = getLineNumber(fileRoute3D);
        while (readRoute3D.hasNext()) {
            String str[] = readRoute3D.nextLine().split(" ");
            double x = Double.parseDouble(str[0]);
            double y = Double.parseDouble(str[1]);
            double h;
            if (str.length >= 3) {
                h = Double.parseDouble(str[2]);
                if (decaimentoLinear) {
                    double altitude = altitudeBase - i * (amplitude / (waypoint - 1));
                    printGeo.println(i + "\t" + (i == 0 ? 1 : 0)
                            + "\t" + (i == 0 ? 0 : 3) + "\t16\t0\t0\t0\t0\t"
                            + UtilGeo.parseToGeoRelativeGround2(pGeo, x, y, altitude) + "\t1");
                } else {
                    printGeo.println(i + "\t" + (i == 0 ? 1 : 0)
                            + "\t" + (i == 0 ? 0 : 3) + "\t16\t0\t0\t0\t0\t"
                            + UtilGeo.parseToGeoRelativeGround2(pGeo, x, y, h) + "\t1");
                }
            } else {
                h = altitudeBase;
                if (decaimentoLinear) {
                    double altitude = altitudeBase - i * (amplitude / (waypoint - 1));
                    printGeo.println(i + "\t" + (i == 0 ? 1 : 0)
                            + "\t" + (i == 0 ? 0 : 3) + "\t16\t0\t0\t0\t0\t"
                            + UtilGeo.parseToGeoRelativeGround2(pGeo, x, y, altitude) + "\t1");
                } else {
                    printGeo.println(i + "\t" + (i == 0 ? 1 : 0)
                            + "\t" + (i == 0 ? 0 : 3) + "\t16\t0\t0\t0\t0\t"
                            + UtilGeo.parseToGeoRelativeGround2(pGeo, x, y, h) + "\t1");
                }
            }
            i++;
        }
        readRoute3D.close();
        printGeo.close();

        System.out.println("--------End MainRoute3DToGeo_MissionPlanner--------");
    }

    private PointGeo readFileGeoBase(String dir, String nameFileGeoBase) throws FileNotFoundException {
        Scanner readGeoBase = new Scanner(new File(dir + nameFileGeoBase));
        String strGeoBase = readGeoBase.nextLine();
        String strGeo[] = strGeoBase.split(" ");
        readGeoBase.close();

        double lat = Double.parseDouble(strGeo[0]);
        double lon = Double.parseDouble(strGeo[1]);
        double alt = Double.parseDouble(strGeo[2]);
        return new PointGeo(lon, lat, alt);
    }

    private void readFileConfig(String dir, String nameFileConfig) throws FileNotFoundException {
        Scanner readConfig = new Scanner(new File(dir + nameFileConfig));
        readConfig.nextLine();
        decaimentoLinear = (int) (Integer.parseInt(readConfig.nextLine())) != 0;
        readConfig.nextLine();
        altitudeBase = (int) (Integer.parseInt(readConfig.nextLine()));
        readConfig.nextLine();
        amplitude = (int) (Integer.parseInt(readConfig.nextLine()));
        readConfig.close();
    }

    public static int getLineNumber(File file) throws FileNotFoundException, IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        return lnr.getLineNumber();
    }
}
