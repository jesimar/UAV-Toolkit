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
     * @param args used only to see the version/help
     * @since version 1.0.0
     */
    public static void main(String[] args) {  
        if (args.length == 0){
            Locale.setDefault(Locale.US);
            StandardPrints.printMsgEmph2("MOSA");
            MissionManager missionManager = new MissionManager();        
            missionManager.init();
        }else{
            if (args[0].equals("--version")){
                System.out.println("UAV-MOSA version: 4.0.0");
                System.exit(0);
            }else if (args[0].equals("--help")){
                System.out.println("UAV-MOSA:");
                System.out.println("    --version          prints the system version");
                System.out.println("    --help             prints the system help");
                System.exit(0);
            }{
                System.out.println("invalid arguments");
                System.exit(1);
            }
        }
    }
}
