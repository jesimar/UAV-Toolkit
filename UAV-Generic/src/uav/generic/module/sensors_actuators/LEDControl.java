package uav.generic.module.sensors_actuators;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.reader.ReaderFileConfig;

/**
 *
 * @author Jesimar S. Arantes
 */
public class LEDControl {
    
    private final ReaderFileConfig config;

    public LEDControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void turnOnLED(){
        try {
            boolean print = true;
            File f = new File(config.getDirLED());
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./turn-on-led";
            } else if (config.getOperationMode().equals(TypeOperationMode.SITL_CC) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python turn-on-led.py";
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
            StandardPrints.printMsgWarning("Warning [IOException] turnOnLED()");
        } 
    }
    
    public void turnOffLED(){
        try {
            boolean print = true;
            File f = new File(config.getDirLED());
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./turn-off-led";
            } else if (config.getOperationMode().equals(TypeOperationMode.SITL_CC) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python turn-off-led.py";
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
            StandardPrints.printMsgWarning("Warning [IOException] turnOffLED()");
        } 
    }
    
    public void blinkLED(){
        try {
            boolean print = true;
            File f = new File(config.getDirLED());
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./blink-led";
            } else if (config.getOperationMode().equals(TypeOperationMode.SITL_CC) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python blink-led.py";
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
            StandardPrints.printMsgWarning("Warning [IOException] blinkLED()");
        } 
    }
    
}
