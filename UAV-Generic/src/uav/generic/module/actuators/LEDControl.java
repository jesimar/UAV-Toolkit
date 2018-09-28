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
public class LEDControl {
    
    private final ReaderFileConfig config;

    public LEDControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void turnOnLED(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "./turn-on-led";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python turn-on-led.py";//fazer isso aqui ainda
                }else{
                    cmd = "./device";
                }
            } 
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirLED()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] turnOnLED()");
        } 
    }
    
    public void turnOffLED(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "./turn-off-led";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python turn-off-led.py";//fazer isso aqui ainda
                }else{
                    cmd = "./device";
                }
            }
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirLED()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] turnOffLED()");
        } 
    }
    
    public void blinkLED(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "./blink-led";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python blink-led.py";//fazer isso aqui ainda
                }else{
                    cmd = "./device";
                }
            } 
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirLED()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] blinkLED()");
        } 
    }
    
}
