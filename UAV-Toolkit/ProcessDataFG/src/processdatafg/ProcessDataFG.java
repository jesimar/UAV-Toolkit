/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processdatafg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author jesimar
 */
public class ProcessDataFG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int RATE = 20;//Taxa de dados que pretendo pegar (quanto maior menos dados eu pego).
        
        for (File dir : new File("./results/").listFiles()) {
            FileWriter fileOut = null;
            try {
                System.out.println("dir = " + dir.getName());
                fileOut = new FileWriter("out/" + dir.getName() + ".csv");
                PrintWriter writer = new PrintWriter(fileOut);
                for (File subdir : dir.listFiles()) {
                    System.out.println("\tsubdir = " + subdir.getName());
                    String nameFile = "position2d.txt";
                    if (subdir.getName().equals(nameFile)) {
                        Scanner sc = null;
                        try {
                            sc = new Scanner(new FileReader(subdir.getAbsoluteFile()));
                            int i = 0; 
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine().replace(".", ",");
                                String str[] = line.split(" ");
                                String lat = str[1];
                                String lon = str[2];
                                if (i % RATE == 0){
                                    writer.println(lat + " " + lon);
                                }
                                i++;
                            }
                            sc.close();
                        } catch (FileNotFoundException ex) {
                            if (sc != null){
                                sc.close();
                            }
                            System.err.println("Error: " + ex);
                        }
                    }
                }
                writer.close();
            } catch (IOException ex) {                               
                System.err.println("Error: " + ex);
            } finally {
                try {
                    fileOut.close();
                } catch (IOException ex) {
                    System.err.println("Error: " + ex);
                }
            }
        }
    }

}
