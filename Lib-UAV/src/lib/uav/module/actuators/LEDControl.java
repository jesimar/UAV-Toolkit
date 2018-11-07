package lib.uav.module.actuators;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import lib.uav.struct.constants.TypeCC;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.util.UtilRunThread;

/**
 * The class models the LED control to for feedback of user.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class LEDControl {
    
    private final ReaderFileConfig config;

    /**
     * Class constructor.
     * @since version 4.0.0
     */
    public LEDControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    /**
     * Turn on the LED based type of operation mode and on the connected device
     * @since version 4.0.0
     */
    public void turnOnLED(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "python turn-on-led-pc.py";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python turn-on-led-rpi.py";
                } else if (config.getTypeCC().equals(TypeCC.BEAGLE_BONE)){
                    cmd = "python turn-on-led-bbb.py " + config.getPinLED();
                } else if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python turn-on-led-edison.py " + config.getPinLED();
                } else{
                    cmd = "python device.py";
                }
            }
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirLED()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] turnOnLED()");
        } 
    }
    
    /**
     * Turn off the LED based type of operation mode and on the connected device
     * @since version 4.0.0
     */
    public void turnOffLED(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "python turn-off-led-pc.py";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python turn-off-led-rpi.py";
                } else if (config.getTypeCC().equals(TypeCC.BEAGLE_BONE)){
                    cmd = "python turn-off-led-bbb.py " + config.getPinLED();
                } else if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python turn-off-led-edison.py " + config.getPinLED();
                } else{
                    cmd = "python device.py";
                }
            }
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirLED()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] turnOffLED()");
        } 
    }
    
    /**
     * Blink the LED based type of operation mode and on the connected device
     * @since version 4.0.0
     */
    public void blinkLED(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "python blink-led-pc.py";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python blink-led-rpi.py " + config.getPinLED() + " " + config.getDelayLED();
                } else if (config.getTypeCC().equals(TypeCC.BEAGLE_BONE)){
                    cmd = "python blink-led-bbb.py " + config.getPinLED() + " " + config.getDelayLED();
                } else if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python blink-led-edison.py " + config.getPinLED() + " " + config.getDelayLED();
                } else{
                    cmd = "python device.py";
                }
            } 
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirLED()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] blinkLED()");
        } 
    }
    
}
