package uav.generic.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import lib.color.StandardPrints;

/**
 * Class with feature set with file manipulation.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class UtilFile {
    
    /**
     * Create a file log.
     * @param name name of file
     * @param extension extension of file [.csv or .txt]
     * @return the File created.
     * @since version 4.0.0
     */
    public static PrintStream createFileLog(String name, String extension) {
        StandardPrints.printMsgEmph("create file log: " + name + extension + " ...");
        try {
            int i = 0;
            File file;
            do {
                i++;
                file = new File(name + i + extension);
            } while (file.exists());
            return new PrintStream(file);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: createFileLog()");
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            StandardPrints.printMsgError2("Error [Exception]: createFileLog()");
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    
}
