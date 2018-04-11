package uav.fixed_route4s;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 *
 * @author jesimar
 */
public class UAVFixedRoute4s {

    private final String nameFileFailure = "position-failure.txt";
    private final String dirFiles = "./routes-iros-new-2018/";
    private final File filePositionFailure;
    private double pxFailure;
    private double pyFailure;
    
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        UAVFixedRoute4s uav = new UAVFixedRoute4s();
        uav.readFilePositionFailure();
        File file = uav.findFileWithBestRoute();
        uav.copyFile(file, new File("route.txt"));
    }

    public UAVFixedRoute4s() {
        this.filePositionFailure = new File(nameFileFailure);
    }
    
    
    private void readFilePositionFailure() throws FileNotFoundException {
        Scanner sc = new Scanner(filePositionFailure);            
        String line = sc.nextLine();
        String v[] = line.split(" ");
        pxFailure = Double.parseDouble(v[0]);
        pyFailure = Double.parseDouble(v[1]);
        System.out.println("px, py = " + pxFailure + " " + pyFailure);
        sc.close();
    }
    
    private File findFileWithBestRoute() throws FileNotFoundException{
        double minDist = Double.MAX_VALUE;
        String fileBest = "null";
        for (File file: new File(dirFiles).listFiles()){
            System.out.println("File: " + file.getName());
            double dist = readFirstLine(new File(dirFiles + file.getName()));
            if (dist < minDist){
                minDist = dist;
                fileBest = file.getName();
            }
        }  
        return new File(dirFiles + fileBest);
    }
    
    private double readFirstLine(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);            
        String line = sc.nextLine();
        String v[] = line.split(" ");
        double pxActual = Double.parseDouble(v[0]);
        double pyActual = Double.parseDouble(v[1]);
        System.out.println("    line: " + line);
        sc.close();
        return distance(pxFailure, pyFailure, pxActual, pyActual);
    }
    
    public double distance(double px1, double py1, double px2, double py2) {
        return Math.sqrt((px1 - px2)*(px1 - px2) + (py1 - py2)*(py1 - py2));
    }  
    
    public long copyFile(File source, File destiny) throws FileNotFoundException, IOException{
       FileInputStream fSource = new FileInputStream(source);
       FileOutputStream fWork = new FileOutputStream(destiny);
       FileChannel fcSource = fSource.getChannel();
       FileChannel fcWork = fWork.getChannel();
       long size = fcSource.transferTo(0, fcSource.size(), fcWork);
       fSource.close();
       fWork.close();
       return size;
    }
}
