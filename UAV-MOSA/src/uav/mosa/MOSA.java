package uav.mosa;

import java.util.Locale;
import lib.color.StandardPrints;
import uav.mosa.module.mission_manager.MissionManager;

/**
 *
 * @author jesimar
 */
public final class MOSA {        
       
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        StandardPrints.printMsgEmph2("MOSA");
        MissionManager missionManager = new MissionManager();        
        missionManager.init(); 
    }
}
