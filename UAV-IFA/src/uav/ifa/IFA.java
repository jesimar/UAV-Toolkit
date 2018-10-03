package uav.ifa;

import java.util.Locale;
import lib.color.StandardPrints;
import uav.ifa.module.security_manager.SecurityManager;

/**
 * Main class that starts the IFA system.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public final class IFA {            
    
    /**
     * Method main that start the IFA System.
     * @param args used only to see the version/help
     * @since version 1.0.0
     */
    public static void main(String[] args) {
        if (args.length == 0){
            Locale.setDefault(Locale.US);
            StandardPrints.printMsgEmph2("IFA");
            SecurityManager securityManager = new SecurityManager();        
            securityManager.init();
        }else{
            if (args[0].equals("--version")){
                System.out.println("UAV-IFA version: 4.0.0");
                System.exit(0);
            }else if (args[0].equals("--help")){
                System.out.println("UAV-IFA:");
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
