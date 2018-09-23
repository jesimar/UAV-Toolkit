/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.gcs2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * @author Jesimar S. Arantes
 */
public class StartModules {

    public StartModules() {
        
    }
    
    public void execSITL(JTextArea textArea) {
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-sitl.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        try {
                            String str = sc.nextLine();
                            textArea.append(str + "\n"); 
                            Thread.sleep(200);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            break;
                        }
                    }
                    sc.close();               
                }
            });
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getErrorStream());
                    while (sc.hasNextLine()) {
                        try {
                            String str = sc.nextLine();
                            textArea.append(str + "\n");  
                            Thread.sleep(200);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            break;
                        }
                    }
                    sc.close();                  
                }
            });
            comp.waitFor();
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartModules.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void execMAVProxy(JTextArea textArea) {
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-mavproxy-sitl.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();
                        textArea.append(str + "\n");
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }

    public void execS2DK(JTextArea textArea) {
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-s2dk.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();                     
                        textArea.append(str + "\n");
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }

    public void execIFA(JTextArea textArea) {
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-ifa.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();
                        str = str.replace("[", "");
                        str = str.replaceAll("[0-9]+m", "");                        
                        textArea.append(str + "\n");
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }

    public void execMOSA(JTextArea textArea) {
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-mosa.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();
                        str = str.replace("[", "");
                        str = str.replaceAll("[0-9]+m", "");                        
                        textArea.append(str + "\n");
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }
    
    public void execListIPs(JTextArea textArea){
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./list-ips-in-use.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();                      
                        textArea.append(str + "\n");
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }
    
    public void execClearSimulations(JTextArea textArea){
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./clear-simulations.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();                      
                        textArea.append(str + "\n");
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }
    
    public void execCopyFilesResults(JTextArea textArea){
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-copy-files-results.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();                      
                        textArea.append(str + "\n");
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }

}
