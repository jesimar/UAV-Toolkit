package uav.mosa;

import java.util.Locale;
import lib.color.StandardPrints;
import uav.mosa.module.mission_manager.MissionManager;

/**
 * Main class that starts the MOSA system.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public final class MOSA {        
       
    /**
     * Method main that start the MOSA System.
     * @param args not used
     * @since version 1.0.0
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        StandardPrints.printMsgEmph2("MOSA");
        MissionManager missionManager = new MissionManager();        
        missionManager.init(); 
    }
}
