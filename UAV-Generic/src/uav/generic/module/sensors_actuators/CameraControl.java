package uav.generic.module.sensors_actuators;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.reader.ReaderFileConfigGlobal;

/**
 *
 * @author Jesimar S. Arantes
 */
public class CameraControl {
    
    private final ReaderFileConfigGlobal configGlobal;

    public CameraControl() {
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
    }
    
    public void takeAPicture(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirCamera());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "java -jar picture.jar";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC) ||
                    configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python picture.py";
            } 
            final Process comp = Runtime.getRuntime().exec(cmd, null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    if (print) {
                        while (sc.hasNextLine()) {
                            System.out.println(sc.nextLine());
                        }
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] takeAPicture()");
        } 
    }
    
    public void photoInSequence(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirCamera());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "java -jar photo-in-sequence.jar";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC) ||
                    configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python photo-in-sequence.py";
            } 
            final Process comp = Runtime.getRuntime().exec(cmd, null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    if (print) {
                        while (sc.hasNextLine()) {
                            System.out.println(sc.nextLine());
                        }
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] takeAPicture()");
        } 
    }
    
    public void makeAVideo(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirCamera());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "java -jar video.jar";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC) ||
                    configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python video.py";
            } 
            final Process comp = Runtime.getRuntime().exec(cmd, null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    if (print) {
                        while (sc.hasNextLine()) {
                            System.out.println(sc.nextLine());
                        }
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] makeAVideo()");
        } 
    }
}
