package uav.generic.module.sensors_actuators;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.reader.ReaderFileConfig;

/**
 *
 * @author Jesimar S. Arantes
 */
public class BuzzerControl {
    
    private final ReaderFileConfig config;

    public BuzzerControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void turnOnBuzzer(){
        try {
            boolean print = true;
            File f = new File(config.getDirBuzzer());
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "java -jar buzzer-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.SITL_CC) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python buzzer-edison.py " + config.getPinBuzzer();
                }else{
                    cmd = "./device";
                }
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
            StandardPrints.printMsgWarning("Warning [IOException] turnOnBuzzer()");
        } 
    }
    
    public void turnOnAlarm(){
        try {
            boolean print = true;
            File f = new File(config.getDirBuzzer());
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "java -jar alarm-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.SITL_CC) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python alarm-edison.py " + config.getPinBuzzer();
                }else{
                    cmd = "./device";
                }
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
            StandardPrints.printMsgWarning("Warning [IOException] turnOnAlarm()");
        } 
    }
    
    public void turnOnBuzzer(int numberOfTimes){
        for (int i = 0; i < numberOfTimes; i++){
            try {
                turnOnBuzzer();
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
}
