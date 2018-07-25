package uav.gcs.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfig {
    
    private static final ReaderFileConfig instance = new ReaderFileConfig();
    
    private final String nameFile = "./config-gcs.properties";
    private Properties prop;
    
    private boolean hasDB;
    
    private String hostIFA;
    private int portIFA;
    
    private String hostMOSA;
    private int portMOSA;
    
    private String hostOD;
    private String portOD;
    private String userEmail;

    public ReaderFileConfig() {
        
    }
    
    public static ReaderFileConfig getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
                    
            hasDB = Boolean.parseBoolean(prop.getProperty("prop.general.hasDB"));
            
            hostIFA   = prop.getProperty("prop.ifa.host");
            portIFA   = Integer.parseInt(prop.getProperty("prop.ifa.port"));
            
            hostMOSA  = prop.getProperty("prop.mosa.host");
            portMOSA  = Integer.parseInt(prop.getProperty("prop.mosa.port"));
            
            hostOD    = prop.getProperty("prop.od.host");
            portOD    = prop.getProperty("prop.od.port");
            userEmail = prop.getProperty("prop.od.user_email");
            return true;
        } catch (FileNotFoundException ex){     
            System.out.println("Error [FileNotFoundException] read()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            System.out.println("Error [IOException] read()");
            ex.printStackTrace();
            return false;
        } catch (NumberFormatException ex) {
            System.out.println("Error [NumberFormatException] read()");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean hasDB() {
        return hasDB;
    }

    public String getHostIFA() {
        return hostIFA;
    }
    
    public int getPortIFA() {
        return portIFA;
    }  
    
    public String getHostMOSA() {
        return hostMOSA;
    }
    
    public int getPortMOSA() {
        return portMOSA;
    } 

    public String getHostOD() {
        return hostOD;
    }
    
    public String getPortOD() {
        return portOD;
    }

    public String getUserEmail() {
        return userEmail;
    }

}
