/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author jesimar
 */
public class CalcDistUAVtoRoute {
    
    private final String log = "./logs/logs_console.log";
    
    public static void main(String[] args) throws FileNotFoundException {
        CalcDistUAVtoRoute calc = new CalcDistUAVtoRoute();
        calc.readLog();
    }
    
    public void readLog() throws FileNotFoundException{
        Scanner sc = new Scanner(new File(log));
        int nline = 0;
        double sum = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.contains("APM: Passed waypoint #")){
                //System.out.println(line);
                String sp = line.split(" dist ")[1];
                int size = sp.length();
                String s = sp.substring(0, size-1);
                //System.out.println(s);
                sum += Integer.parseInt(s);
                nline++;
            }
        }
        System.out.println("nline: " + nline);
        System.out.println("sum: " + sum);
        double avg = sum/nline;
        System.out.println("avg: " + avg + "m");
    }
}
