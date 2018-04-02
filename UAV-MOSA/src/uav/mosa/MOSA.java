package uav.mosa;

import java.util.Locale;
import lib.color.StandardPrints;
import uav.mosa.module.mission_manager.MissionManager;

/**
 * Main class that starts the MOSA system.
 * @author Jesimar S. Arantes
 */
public final class MOSA {        
       
    /**
     * Method main that start the MOSA System.
     * @param args not used
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        StandardPrints.printMsgEmph2("MOSA");
        MissionManager missionManager = new MissionManager();        
        missionManager.init(); 
    }
}
