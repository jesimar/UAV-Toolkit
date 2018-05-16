package uav.gcs2.struct;

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
    private boolean hasGoogleMaps;
    
    private String hostIFA;
    private int portIFA;
    
    private String hostOD;
    private int portOD;
    
    private String hostOD_DB;
    private String portOD_DB;

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
            hasGoogleMaps = Boolean.parseBoolean(prop.getProperty("prop.general.hasGoogleMaps"));
            
            hostIFA = prop.getProperty("prop.ifa.host");
            portIFA = Integer.parseInt(prop.getProperty("prop.ifa.port"));
            
            hostOD = prop.getProperty("prop.od.host");
            portOD = Integer.parseInt(prop.getProperty("prop.od.port"));
            
            hostOD_DB = prop.getProperty("prop.od.host_db");
            portOD_DB = prop.getProperty("prop.od.port_db");
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

    public boolean hasGoogleMaps() {
        return hasGoogleMaps;
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
    
    public String getHostOD() {
        return hostOD;
    }
    
    public int getPortOD() {
        return portOD;
    } 

    public String getHostOD_DB() {
        return hostOD_DB;
    }
    
    public String getPortOD_DB() {
        return portOD_DB;
    }

}
