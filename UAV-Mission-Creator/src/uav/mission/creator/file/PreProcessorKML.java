/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission.creator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(PreProcessorKML.class.getName()).log(Level.SEVERE, null, ex);
        }                                
    }
}
