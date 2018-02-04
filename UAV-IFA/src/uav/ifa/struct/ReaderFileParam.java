package uav.ifa.struct;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileParam {
    
    private final List<String> nameParam = new LinkedList<>();
    private final List<Double> valueParam = new LinkedList<>();    
    
    public ReaderFileParam(){
        
    }
    
    public void reader(File inFile) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(inFile);
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
    
    public int getSize(){
        return nameParam.size();
    }
        
}