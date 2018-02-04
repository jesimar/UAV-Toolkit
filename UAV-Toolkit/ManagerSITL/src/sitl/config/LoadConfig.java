/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitl.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author jesimar
 */
public class LoadConfig {

    public final String dir_ardupilot;
    public final String cmd_exec_sim_vehicle;
    public final String dir_wp_load;

    public final int time_wait_init_sec;
    public final int time_wait_guided_sec;
    public final int time_wpload_sec;
    public final int time_sim_sec;

    public final int alt_takeoff_meters;

    private final Properties prop = new Properties();
    private final InputStream input = new FileInputStream("./config.properties");

    public LoadConfig() throws FileNotFoundException, IOException {
        prop.load(input);
        dir_ardupilot = prop.getProperty("prop.dir_ardupilot");
        cmd_exec_sim_vehicle = prop.getProperty("prop.cmd_exec_sim_vehicle");
        dir_wp_load = prop.getProperty("prop.dir_wp_load");
        time_wait_init_sec = Integer.parseInt(prop.getProperty("prop.time_wait_init_sec", "12"));
        time_wait_guided_sec = Integer.parseInt(prop.getProperty("prop.time_wait_guided_sec", "30"));
        time_wpload_sec = Integer.parseInt(prop.getProperty("prop.time_wpload_sec", "4"));
        time_sim_sec = Integer.parseInt(prop.getProperty("prop.time_sim_sec", "135"));
        alt_takeoff_meters = Integer.parseInt(prop.getProperty("prop.alt_takeoff_meters", "40"));
    }
    
    public void printProperties(){
        System.out.println(dir_ardupilot);
        System.out.println(cmd_exec_sim_vehicle);
        System.out.println(dir_wp_load);
        
        System.out.println(time_sim_sec);
        System.out.println(time_wpload_sec);
        System.out.println(time_wait_init_sec);
        System.out.println(time_wait_guided_sec);
        
        System.out.println(alt_takeoff_meters);
    }
}
