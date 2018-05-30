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
public class BuzzerControl {
    
    private final ReaderFileConfigGlobal configGlobal;

    public BuzzerControl() {
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
    }
    
    public void turnOnBuzzer(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirBuzzer());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./buzzer";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_EDISON)){
                cmd = "python buzzer.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_RPI)){
                cmd = "python buzzer.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python buzzer.py";
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
            File f = new File(configGlobal.getDirBuzzer());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./alarm";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_EDISON)){
                cmd = "python alarm.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_RPI)){
                cmd = "python alarm.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python alarm.py";
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
