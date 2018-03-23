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
public class BuzzerControl {
    
    private final ReaderFileConfigGlobal configGlobal;

    public BuzzerControl() {
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
    }
    
    public void turnOnBuzzer(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirBuzzer());
            String cmd = configGlobal.getCmdExecBuzzer();
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
    
    public void turnOnAlarm(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirBuzzer());
            String cmd = configGlobal.getCmdExecAlarm();
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
