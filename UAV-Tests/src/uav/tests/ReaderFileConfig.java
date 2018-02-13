package uav;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lib.color.StandardPrints;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfig {
    
    private static final ReaderFileConfig instance = new ReaderFileConfig();
    private Properties prop;
    private InputStream input;   
    private int numberTest;

    private ReaderFileConfig() {
        
    }
    
    public static ReaderFileConfig getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            input = new FileInputStream("./config.properties");
            prop.load(input);
            numberTest = Integer.parseInt(prop.getProperty("prop.number_test"));
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public int getNumberTest() {
        return numberTest;
    }
    
}