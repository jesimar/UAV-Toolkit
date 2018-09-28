package uav.generic.module.actuators;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.util.UtilRunThread;

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
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar buzzer-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python buzzer-edison.py " + config.getPinBuzzer();
                }else{
                    cmd = "./device";
                }
            }
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirBuzzer()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] turnOnBuzzer()");
        } 
    }
    
    public void turnOnAlarm(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar alarm-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python alarm-edison.py " + config.getPinBuzzer();
                }else{
                    cmd = "./device";
                }
            }
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirBuzzer()), print);
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
