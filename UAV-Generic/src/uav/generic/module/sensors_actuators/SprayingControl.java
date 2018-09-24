package uav.generic.module.sensors_actuators;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.reader.ReaderFileConfig;
import uav.generic.struct.thread.RunThread;

/**
 *
 * @author Jesimar S. Arantes
 */
public class SprayingControl {
    
    private final ReaderFileConfig config;

    public SprayingControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void openSpraying(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "./open-spraying";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python open-spraying.py";//fazer isso aqui ainda
                }else{
                    cmd = "./device";
                }
            } 
            boolean print = true;
            RunThread.singleThread(cmd, new File(config.getDirSpraying()), print);            
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] takeAPicture()");
        } 
    }
    
    public void closeSpraying(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "./close-spraying";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python close-spraying.py";//fazer isso aqui ainda
                }else{
                    cmd = "./device";
                }
            } 
            boolean print = true;
            RunThread.singleThread(cmd, new File(config.getDirSpraying()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] makeAVideo()");
        } 
    }
}
