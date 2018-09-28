/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.generic.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 *
 * @author Jesimar S. Arantes
 */
public class UtilRunThread {

    public static void singleThread(String cmd, File file) throws IOException {
        singleThread(cmd, file, false);
    }
    
    public static void singleThreadWaitFor(String cmd, File file) 
            throws IOException, InterruptedException {
        singleThreadWaitFor(cmd, file, false);
    }

    public static void singleThread(String cmd, File file, boolean isPrint) 
            throws IOException {
        final Process comp = Runtime.getRuntime().exec(cmd, null, file);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (isPrint) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
    }
    
    public static void singleThreadWaitFor(String cmd, File file, boolean isPrint) 
            throws IOException, InterruptedException {
        final Process comp = Runtime.getRuntime().exec(cmd, null, file);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (isPrint) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        comp.waitFor();
    }

    public static void dualSingleThread(String cmd, File file) 
            throws IOException, InterruptedException {
        dualSingleThread(cmd, file, false, false);
    }

    public static void dualSingleThread(String cmd, File file, boolean isPrintOut, 
            boolean isPrintError) throws IOException, InterruptedException {
        final Process comp = Runtime.getRuntime().exec(cmd, null, file);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (isPrintOut) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getErrorStream());
                if (isPrintError) {
                    while (sc.hasNextLine()) {
                        System.err.println("err:" + sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        comp.waitFor();
    }

}
