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
 * Class with util methods on Input/Output (I/O).
 * @author Jesimar S. Arantes
 */
public class UtilIO {
    
    /**
     * Method that return the number os lines of the file.
     * @param file the input file 
     * @return the number of lines of the file.
     * @throws IOException 
     */
    public static int getLineNumber(File file) throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        return lnr.getLineNumber();
    }
        
    /**
     * Method that delete the files based in the directory and extension.
     * @param directory - directory of origin
     * @param extension - extension of the files.
     */
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
    
    /**
     * Method that copy the file based in a file the source and destiny.
     * @param source - File of source (file to be copied).
     * @param destiny - File of destiny (file copied).
     * @return The number of bytes that were transferred.
     * @throws FileNotFoundException
     * @throws IOException 
     */
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
    
    /**
     * Method that copy the file source to destiny modified the line (line) for 
     * the content (content).
     * @param src File of source (file to be copied).
     * @param dst File of destiny (file copied).
     * @param content the new content the line.
     * @param line the number of line.
     * @throws FileNotFoundException 
     */
    public static void copyFileModifiedIFA(File src, File dst, String content, int line) 
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
    
    /**
     * Method that copy the file source to destiny modified the lines (line*) for 
     * the content (content).
     * @param src File of source (file to be copied).
     * @param dst File of destiny (file copied).
     * @param contentDelta the new content the line delta.
     * @param lineDelta the number of line delta.
     * @param contentWpt the new content the line waypoint.
     * @param lineWpt the number of line waypoint.
     * @param contentTimeH  the new content the line time horizon.
     * @param lineTimeH the number of line time horizon.
     * @throws FileNotFoundException 
     */
    public static void copyFileModifiedIFA(File src, File dst, String contentDelta, 
            int lineDelta, String contentWpt, int lineWpt, 
            String contentTimeH, int lineTimeH) 
            throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        PrintStream print = new PrintStream(dst);
        int i = 1;
        while (sc.hasNextLine()) {
            if (i != lineDelta && i != lineWpt && i != lineTimeH){
                print.println(sc.nextLine());
            } else if (i == lineDelta){
                print.println(contentDelta);
                sc.nextLine();
            } else if (i == lineWpt){
                print.println(contentWpt);
                sc.nextLine();
            } else if (i == lineTimeH){
                print.println(contentTimeH);
                sc.nextLine();
            } 
            i++;
        }
        sc.close();
        print.close();
    }
    
    /**
     * Method that copy the file source to destiny modified the lines (line*) for 
     * the content (content).
     * @param src File of source (file to be copied).
     * @param dst File of destiny (file copied).
     * @param contentTime the new content the line time.
     * @param lineTime the number of line time.
     * @param contentDelta the new content the line delta.
     * @param lineDelta the number of line delta.
     * @param contentWpt the new content the line waypoint.
     * @param lineWpt the number of line waypoint.
     * @param contentTimeH  the new content the line time horizon.
     * @param lineTimeH the number of line time horizon.
     * @param contentMaxVel the new content the line max velocity.
     * @param lineMaxVel the number of line max velocity.
     * @param contentMaxCtrl the new content the line max control.
     * @param lineMaxCtrl the number of line max control.
     * @throws FileNotFoundException 
     */
    public static void copyFileModifiedMOSA(File src, File dst, String contentTime, 
            int lineTime, String contentDelta, int lineDelta, 
            String contentWpt, int lineWpt, String contentTimeH, int lineTimeH, 
            String contentMaxVel, int lineMaxVel, String contentMaxCtrl, int lineMaxCtrl) 
            throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        PrintStream print = new PrintStream(dst);
        int i = 1;
        while (sc.hasNextLine()) {
            if (i != lineTime && i != lineDelta && i != lineWpt && i != lineTimeH &&
                    i != lineMaxVel && i != lineMaxCtrl){
                print.println(sc.nextLine());
            } else if (i == lineTime){
                print.println(contentTime);
                sc.nextLine();
            } else if (i == lineDelta){
                print.println(contentDelta);
                sc.nextLine();
            } else if (i == lineWpt){
                print.println(contentWpt);
                sc.nextLine();
            } else if (i == lineTimeH){
                print.println(contentTimeH);
                sc.nextLine();
            } else if (i == lineMaxVel){
                print.println(contentMaxVel);
                sc.nextLine();
            } else if (i == lineMaxCtrl){
                print.println(contentMaxCtrl);
                sc.nextLine();
            }
            i++;
        }
        sc.close();
        print.close();
    }
    
    /**
     * Method that copy the file source to destiny modified the lines (line*) for 
     * the content (content).
     * @param src File of source (file to be copied).
     * @param dst File of destiny (file copied).
     * @param contentDelta the new content the line delta.
     * @param lineDelta the number of line delta.
     * @param contentWpt the new content the line waypoint.
     * @param lineWpt the number of line waypoint.
     * @param contentTimeH  the new content the line time horizon.
     * @param lineTimeH the number of line time horizon.
     * @throws FileNotFoundException 
     */
    public static void copyFileModifiedMOSA(File src, File dst, 
            String contentDelta, int lineDelta, String contentWpt, int lineWpt, 
            String contentTimeH, int lineTimeH) throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        PrintStream print = new PrintStream(dst);
        int i = 1;
        while (sc.hasNextLine()) {
            if (i != lineDelta && i != lineWpt && i != lineTimeH){
                print.println(sc.nextLine());
            } else if (i == lineDelta){
                print.println(contentDelta);
                sc.nextLine();
            } else if (i == lineWpt){
                print.println(contentWpt);
                sc.nextLine();
            } else if (i == lineTimeH){
                print.println(contentTimeH);
                sc.nextLine();
            }
            i++;
        }
        sc.close();
        print.close();
    }
    
}
