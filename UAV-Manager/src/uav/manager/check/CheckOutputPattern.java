/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager.check;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Marcio
 */
public class CheckOutputPattern extends CheckCommand{
    private final String version_pattern;
    public CheckOutputPattern(String command, String version_pattern) {
        this(command, version_pattern, "./resources/scripts/", 10000, 0);
    }
    public CheckOutputPattern(String command, String version_pattern, long timeout) {
        this(command, version_pattern, "./resources/scripts/", timeout, 0);
    }
    public CheckOutputPattern(String command, String version_pattern, long timeout, int exitOk) {
        this(command, version_pattern, "./resources/scripts/", timeout, exitOk);
    }
    public CheckOutputPattern(String command, String version_pattern, String dir, long timeout, int exitOk) {
        super(command, new File(dir), timeout, exitOk);
        this.version_pattern = version_pattern;
    }
    @Override
    public boolean checkStream(InputStream stream){
        boolean matches = false;
        try{
            Scanner sc = new Scanner(stream);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                System.out.println(line);
                if(!matches){
                    if(version_pattern==null || line.matches(version_pattern)){
                        matches =  true;
                    }
                }
            }
        }catch(Throwable ex){
            
        }
        return matches;
    }
    
}
