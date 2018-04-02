package uav.ifa;

import java.util.Locale;
import lib.color.StandardPrints;
import uav.ifa.module.security_manager.SecurityManager;

/**
 * Main class that starts the IFA system.
 * @author Jesimar S. Arantes
 */
public final class IFA {            
    
    /**
     * Method main that start the IFA System.
     * @param args not used
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        StandardPrints.printMsgEmph2("IFA");
        SecurityManager securityManager = new SecurityManager();        
        securityManager.init();
    }    
}
