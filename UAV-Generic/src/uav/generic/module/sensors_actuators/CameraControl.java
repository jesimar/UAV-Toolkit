package uav.generic.module.sensors_actuators;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.ReaderFileConfigGlobal;

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
            String cmd = configGlobal.getCmdExecCameraPicture();
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
            StandardPrints.printMsgWarning("Warning [IOException] turnOn()");
        } 
    }
    
    public void makeAVideo(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirCamera());
            String cmd = configGlobal.getCmdExecCameraVideo();
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
            StandardPrints.printMsgWarning("Warning [IOException] turnOn()");
        } 
    }
}
