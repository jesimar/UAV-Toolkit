package uav.gcs2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfig {
    
    private static final ReaderFileConfig instance = new ReaderFileConfig();
    
    private final String nameFile = "./config.properties";
    private Properties prop;
    
    private String host;
    private int port;

    public ReaderFileConfig() {
        
    }
    
    public static ReaderFileConfig getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
            
            host = prop.getProperty("prop.host");
            port = Integer.parseInt(prop.getProperty("prop.port"));
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

    public String getHost() {
        return host;
    }
    
    public int getPort() {
        return port;
    }    
    
}
