/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.gcs2;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
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
                    System.out.println("open sitl");
                    Scanner sc = new Scanner(comp.getInputStream());
                    while(true){
                        try{
                            try {
                                System.out.println("sc next line");
                                String str = sc.nextLine();
                                textArea.append(str + "\n");
                                System.out.println("str: " + str);    
                                Thread.sleep(200);
                            } catch (NoSuchElementException ex) {
                                Thread.sleep(200);
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(StartModules.class.getName()).log(Level.SEVERE, null, ex);
                            break;
                        }
                    }
                    sc.close();
                    System.out.println("close sitl");                    
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }

    public void execMAVProxy(JTextArea textArea) {
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-mavproxy-local.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("open mavproxy");
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        System.out.println("aqui mavproxy");
                        String str = sc.nextLine();
                        textArea.append(str + "\n");
                        System.out.println("str: " + str);
                    }
                    sc.close();
                    System.out.println("close mavproxy");
                }
            });
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex);
        }
    }

    public void execSOAInterface(JTextArea textArea) {
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-soa-interface.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("open soa");
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        System.out.println("aqui soa");
                        String str = sc.nextLine();
                        str = str.replace("[", "");
                        str = str.replaceAll("[0-9]+m", "");                        
                        textArea.append(str + "\n");
                        System.out.println("str: " + str);
                    }
                    sc.close();
                    System.out.println("close soa");
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
                    System.out.println("open ifa");
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        System.out.println("aqui ifa");
                        String str = sc.nextLine();
                        str = str.replace("[", "");
                        str = str.replaceAll("[0-9]+m", "");                        
                        textArea.append(str + "\n");
                        System.out.println("str: " + str);
                    }
                    sc.close();
                    System.out.println("close ifa");
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
                    System.out.println("open mosa");
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        System.out.println("aqui mosa");
                        String str = sc.nextLine();
                        str = str.replace("[", "");
                        str = str.replaceAll("[0-9]+m", "");                        
                        textArea.append(str + "\n");
                    }
                    sc.close();
                    System.out.println("close mosa");
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
