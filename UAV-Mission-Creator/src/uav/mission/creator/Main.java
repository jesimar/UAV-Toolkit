/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mission.creator;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author jesimar
 */
public class Main {   
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args != null){
            if (args.length > 0){
                if (args[0].equals("--processorkml")){
                    ProcessorKML processorKml = new ProcessorKML();
                    processorKml.exec();
                }else if (args[0].equals("--route3dtogeo")){
                    Route3DToGeo route3dtogeo = new Route3DToGeo();
                    route3dtogeo.exec();
                }else if (args[0].equals("--help")){
                    System.out.println("Run modes: ");
                    System.out.println("\tjava -jar programa.jar --processorkml");
                    System.out.println("\tjava -jar programa.jar --route3dtogeo");
                }else if (args[0].equals("--version")){
                    System.out.println("Program version: ");
                    System.out.println("\tProgram: Mission-Creator-4UAV");
                    System.out.println("\tVersion: 2.0");                
                }else if (args[0].equals("--list")){
                    System.out.println("Program arguments: ");
                    System.out.println("\t--help");
                    System.out.println("\t--version");                
                    System.out.println("\t--list");
                    System.out.println("\t--processorkml");
                    System.out.println("\t--route3dtogeo");
                }else{
                    System.err.println("Arguments not found: " + args[0]);
                }
            }else{
                System.err.println("Parameters are missing");
                System.err.println("\tjava -jar programa.jar --list");
            }
        }
    }
    
}
