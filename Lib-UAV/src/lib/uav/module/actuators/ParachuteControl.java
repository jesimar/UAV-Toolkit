package lib.uav.module.actuators;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import lib.uav.struct.constants.TypeCC;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.util.UtilRunThread;

/**
 * The class models the parachute control to give greater security to the aircraft
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class ParachuteControl {
    
    private final ReaderFileConfig config;

    /**
     * Class constructor.
     * @since version 4.0.0
     */
    public ParachuteControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    /**
     * Open the parachute based type of operation mode and on the connected device
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean open(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "./open-parachute";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) || 
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python open-parachute.py";//fazer isso aqui ainda
                }else{
                    cmd = "./device";
                }
            } 
            boolean print = true;
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirParachute()), print);
            return true;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] open()");
            return false;
        }
    }
    
}
