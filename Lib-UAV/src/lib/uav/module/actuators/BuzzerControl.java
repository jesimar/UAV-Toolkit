package lib.uav.module.actuators;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import lib.uav.struct.constants.TypeCC;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.util.UtilRunThread;

/**
 * The class models the buzzer control to report on aspects of mission or security
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class BuzzerControl {
    
    private final ReaderFileConfig config;

    /**
     * Class constructor.
     * @since version 4.0.0
     */
    public BuzzerControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    /**
     * Turn on the buzzer based type of operation mode and on the connected device. 
     * Used by MOSA
     * @since version 4.0.0
     */
    public void turnOnBuzzer(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar buzzer-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python buzzer-edison.py " + config.getPinBuzzer();
                } else if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python buzzer-rpi.py " + config.getPinBuzzer();//fazer isso aqui ainda
                } else{
                    cmd = "python device.py";
                }
            }
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirBuzzer()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] turnOnBuzzer()");
        } 
    }
    
    /**
     * Turn on the alarm based type of operation mode and on the connected device.
     * Used by IFA
     * @since version 4.0.0
     */
    public void turnOnAlarm(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar alarm-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python alarm-edison.py " + config.getPinBuzzer();
                } else if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python alarm-rpi.py " + config.getPinBuzzer();//fazer isso aqui ainda
                } else{
                    cmd = "python device.py";
                }
            }
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirBuzzer()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] turnOnAlarm()");
        } 
    }
    
    /**
     * Turn on the buzzer based type of operation mode and on the connected device
     * @param numberOfTimes number of times to sound buzzer
     * @since version 4.0.0
     */
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
