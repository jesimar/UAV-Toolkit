package uav.generic.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The class reads the file with autopilot parameter update.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class ReaderFileConfigParam {
    
    private static final ReaderFileConfigParam instance = new ReaderFileConfigParam();
    
    private final String nameFile = "../Modules-Global/config-param.properties";
    
    private final List<String> nameParam = new LinkedList<>();
    private final List<Double> valueParam = new LinkedList<>();  
           
    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public ReaderFileConfigParam(){
        
    }
    
    /**
     * Gets a instance this class (singleton pattern).
     * @return the instance this class
     * @since version 2.0.0
     */
    public static ReaderFileConfigParam getInstance() {
        return instance;
    }
    
    /**
     * Read the file with the parameters
     * @throws FileNotFoundException 
     * @since version 2.0.0
     */
    public void read() throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(new File(nameFile));
            while(sc.hasNextLine()){
                String line = sc.nextLine();                
                if (!line.contains("#")){
                    if (line.contains("=")){
                        String v[] = line.split("=");                        
                        nameParam.add(v[0]);
                        valueParam.add(Double.parseDouble(v[1]));
                    }
                }
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    /**
     * Gets the name of the i-th parameter of list
     * @param i the i-th parameter
     * @return the name of i-th parameter
     * @since version 2.0.0
     */
    public String getName(int i){
        return nameParam.get(i);
    }
    
    /**
     * Gets the value of the i-th parameter of list
     * @param i the i-th parameter
     * @return the value of i-th parameter
     * @since version 2.0.0
     */
    public double getValue(int i){
        return valueParam.get(i);
    }
    
    /**
     * Gets the size of list of paramters
     * @return the size of list of parameters
     * @since version 2.0.0
     */
    public int size(){
        return nameParam.size();
    }
        
}