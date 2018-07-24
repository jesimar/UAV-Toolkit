package uav.gcs.map;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author jesimar
 */
public class ReaderConfig {
    
    private static final ReaderConfig instance = new ReaderConfig();
    
    private final String nameFile = "./config-plot.properties";
    private Properties prop;    

    private String fileMap;
    private String fileRoute;

    /**
     * Class constructor.
     */
    private ReaderConfig() {
        
    }
    
    public static ReaderConfig getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
            
            fileMap   = prop.getProperty("prop.global.file_map");
            fileRoute = prop.getProperty("prop.global.file_route");
            
            return true;
        } catch (FileNotFoundException ex){     
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getFileMap() {
        return fileMap;
    }

    public String getFileRoute() {
        return fileRoute;
    }

}
