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
public class ParachuteControl {
    
    private final ReaderFileConfig config;

    public ParachuteControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
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
            UtilRunThread.singleThread(cmd, new File(config.getDirParachute()), print);
            return true;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] open()");
            return false;
        }
    }
    
}
