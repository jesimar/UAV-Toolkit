/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitl;

import sitl.config.LoadConfig;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;

/**
 *
 * @author jesimar
 */
public class ManagerSITL {
    
    private LoadConfig config;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ManagerSITL sitl = new ManagerSITL();
        sitl.config = new LoadConfig();        
        sitl.exec_sitl();
    }
    
    private void exec_sitl() throws IOException, InterruptedException{
        StandardPrints.printMsgEmph2("Start Simulation SITL");
        boolean print = true;
        boolean error = true;
        File f = new File(config.dir_ardupilot);
        final Process comp = Runtime.getRuntime().exec(config.cmd_exec_sim_vehicle, null, f);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (print) {
                    while (sc.hasNextLine()) {
                        StandardPrints.printMsgWarning(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                PrintStream ps = new PrintStream(comp.getOutputStream());
                try {
                    StandardPrints.printMsgEmph("begin simulation");
                    Thread.sleep(config.time_wait_init_sec * 1000);
                    StandardPrints.printMsgEmph("wp load");
                    ps.println("wp load " + config.dir_wp_load);
                    ps.flush();
                    Thread.sleep(config.time_wpload_sec * 1000);
                    StandardPrints.printMsgEmph("mode guided");
                    ps.println("mode guided");
                    ps.flush();
                    Thread.sleep(config.time_wait_guided_sec * 1000);
                    StandardPrints.printMsgEmph("arm throttle");
                    ps.println("arm throttle");
                    ps.flush();
                    Thread.sleep(1500);
                    StandardPrints.printMsgEmph("takeoff");
                    ps.println("takeoff " + config.alt_takeoff_meters);
                    ps.flush();
                    Thread.sleep(1500);
                    StandardPrints.printMsgEmph("mode auto");
                    ps.println("mode auto");
                    ps.flush();
                    Thread.sleep(config.time_sim_sec * 1000);
                    StandardPrints.printMsgEmph("end simulation");
                    ps.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getErrorStream());
                if (error) {
                    while (sc.hasNextLine()) {
                        StandardPrints.printMsgError(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        comp.waitFor();
    }
}
