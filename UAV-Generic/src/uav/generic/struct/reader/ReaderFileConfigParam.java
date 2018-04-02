package uav.generic.struct.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Classe que lê o arquivo com atualização de parâmetros do piloto automático.
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigParam {
    
    private static final ReaderFileConfigParam instance = new ReaderFileConfigParam();
    
    private final String nameFile = "../Modules-Global/config-param.properties";
    
    private final List<String> nameParam = new LinkedList<>();
    private final List<Double> valueParam = new LinkedList<>();  
           
    /**
     * Class constructor.
     */
    public ReaderFileConfigParam(){
        
    }
    
    public static ReaderFileConfigParam getInstance() {
        return instance;
    }
    
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
    
    public String getName(int i){
        return nameParam.get(i);
    }
    
    public double getValue(int i){
        return valueParam.get(i);
    }
    
    public int size(){
        return nameParam.size();
    }
        
}