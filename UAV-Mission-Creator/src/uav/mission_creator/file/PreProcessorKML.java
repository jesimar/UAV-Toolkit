package uav.mission_creator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author Jesimar S. Arantes
 */
public class PreProcessorKML {

    private final File inFile;
    private final File outFile;
    
    public PreProcessorKML(File inFile, File outFile){
        this.inFile = inFile;
        this.outFile = outFile;
    }
    
    public void preProcessor() {
        try {
            Scanner sc = new Scanner(inFile);
            PrintStream print = new PrintStream(outFile);
            while(sc.hasNextLine()){
                String str = sc.nextLine();
                if (str.contains("<kml")){
                    str = "<kml>";
                }
                if (str.contains("<")){
                    str = str.replace(":", "_");
                }
                print.println(str);
            }
            print.close();
            sc.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[ERROR] [FileNotFoundException] preProcessor() " + ex);
        }                                
    }
}
