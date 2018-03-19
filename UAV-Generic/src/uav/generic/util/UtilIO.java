package uav.generic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 *
 * @author Jesimar S. Arantes
 */
public class UtilIO {
    
    public static int getLineNumber(File file) throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        return lnr.getLineNumber();
    }
        
    public static void deleteFile(File directory, String extension){        
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().contains(extension)){
                    file.delete();
                }
            }
        }
    }  
    
    public static long copyFile(File source, File destiny) 
            throws FileNotFoundException, IOException{
       FileInputStream fSource = new FileInputStream(source);
       FileOutputStream fWork = new FileOutputStream(destiny);
       FileChannel fcSource = fSource.getChannel();
       FileChannel fcWork = fWork.getChannel();
       long size = fcSource.transferTo(0, fcSource.size(), fcWork);
       fSource.close();
       fWork.close();
       return size;
    }
    
    public static void copyFileMofifIfa(File src, File dst, String content, int line) 
            throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        PrintStream print = new PrintStream(dst);
        int i = 1;
        while (sc.hasNextLine()) {
            if (i != line){
                print.println(sc.nextLine());
            }else{
                print.println(content);
                sc.nextLine();
            }
            i++;
        }
        sc.close();
        print.close();
    }
    
    public static void copyFileMofifIfa(File src, File dst,
            String delta, int lineDelta, String wp, int lineWp, 
            String timeH, int lineTimeH) throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        PrintStream print = new PrintStream(dst);
        int i = 1;
        while (sc.hasNextLine()) {
            if (i != lineDelta && i != lineWp && i != lineTimeH){
                print.println(sc.nextLine());
            } else if (i == lineDelta){
                print.println(delta);
                sc.nextLine();
            } else if (i == lineWp){
                print.println(wp);
                sc.nextLine();
            } else if (i == lineTimeH){
                print.println(timeH);
                sc.nextLine();
            } 
            i++;
        }
        sc.close();
        print.close();
    }
    
    public static void copyFileMofifMosa(File src, File dst, String time, int lineTime,
            String delta, int lineDelta, String wp, int lineWp, 
            String timeH, int lineTimeH, String maxVel, int lineMaxVel, 
            String maxCtrl, int lineMaxCtrl) throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        PrintStream print = new PrintStream(dst);
        int i = 1;
        while (sc.hasNextLine()) {
            if (i != lineTime && i != lineDelta && i != lineWp && i != lineTimeH &&
                    i != lineMaxVel && i != lineMaxCtrl){
                print.println(sc.nextLine());
            } else if (i == lineTime){
                print.println(time);
                sc.nextLine();
            } else if (i == lineDelta){
                print.println(delta);
                sc.nextLine();
            } else if (i == lineWp){
                print.println(wp);
                sc.nextLine();
            } else if (i == lineTimeH){
                print.println(timeH);
                sc.nextLine();
            } else if (i == lineMaxVel){
                print.println(maxVel);
                sc.nextLine();
            } else if (i == lineMaxCtrl){
                print.println(maxCtrl);
                sc.nextLine();
            }
            i++;
        }
        sc.close();
        print.close();
    }
    
}
