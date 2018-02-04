package uav.ifa;

import java.util.Locale;
import lib.color.StandardPrints;
import uav.ifa.module.security_manager.SecurityManager;

/**
 *
 * @author jesimar
 */
public final class IFA {            
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        StandardPrints.printMsgEmph2("IFA");
        SecurityManager securityManager = new SecurityManager();        
        securityManager.init();
    }    
}
