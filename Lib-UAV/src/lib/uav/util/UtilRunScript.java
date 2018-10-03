package lib.uav.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * Class with feature set to run scripts
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class UtilRunScript {
    
    /**
     * Execute the script clear simulations.
     * @since version 4.0.0
     */
    public static void execClearSimulations(){
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./clear-simulations.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();                      
                        System.out.println(str);
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Execute the script copy files of results.
     * @since version 4.0.0
     */
    public static void execCopyFilesResults(){
        try {
            File f = new File("../Scripts/");
            final Process comp = Runtime.getRuntime().exec("./exec-copy-files-results.sh", null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();                      
                        System.out.println(str);
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Execute a script
     * @param cmd the command to exec the script
     * @since version 4.0.0
     */
    public static void execScript(String cmd) {
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            proc.waitFor();
            while (read.ready()) {
                System.out.println(read.readLine());
            }
        }catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
}
