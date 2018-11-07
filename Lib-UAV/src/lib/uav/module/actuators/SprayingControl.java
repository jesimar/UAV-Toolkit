package lib.uav.module.actuators;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import lib.uav.struct.constants.TypeCC;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.util.UtilRunThread;

/**
 * The class models the spraying control to do mission.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class SprayingControl {
    
    private final ReaderFileConfig config;

    /**
     * Class constructor.
     * @since version 4.0.0
     */
    public SprayingControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    /**
     * Starts spraying on the plantation
     * @since version 4.0.0
     */
    public void openSpraying(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "python open-spraying-pc.py";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python open-spraying-rpi.py";
                } else if (config.getTypeCC().equals(TypeCC.BEAGLE_BONE)){
                    cmd = "python open-spraying-bbb.py";
                } else if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python open-spraying-edison.py";
                } else{
                    cmd = "python device.py";
                }
            } 
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirSpraying()), print);            
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] openSpraying()");
        } 
    }
    
    /**
     * Stop spraying on the plantation
     * @since version 4.0.0
     */
    public void closeSpraying(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "python close-spraying-pc.py";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python close-spraying-rpi.py";
                } else if (config.getTypeCC().equals(TypeCC.BEAGLE_BONE)){
                    cmd = "python close-spraying-bbb.py";
                } else if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                    cmd = "python close-spraying-edison.py";
                } else{
                    cmd = "python device.py";
                }
            } 
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirSpraying()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] closeSpraying()");
        } 
    }
}
