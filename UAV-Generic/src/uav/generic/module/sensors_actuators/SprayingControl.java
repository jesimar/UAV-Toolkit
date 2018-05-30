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
public class SprayingControl {
    
    private final ReaderFileConfigGlobal configGlobal;

    public SprayingControl() {
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
    }
    
    public void openSpraying(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirSpraying());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./open-spraying";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_EDISON)){
                cmd = "python open-spraying.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_RPI)){
                cmd = "python open-spraying.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python open-spraying.py";
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
    
    public void closeSpraying(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirSpraying());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./close-spraying";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_EDISON)){
                cmd = "python close-spraying.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_RPI)){
                cmd = "python close-spraying.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python close-spraying.py";
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
