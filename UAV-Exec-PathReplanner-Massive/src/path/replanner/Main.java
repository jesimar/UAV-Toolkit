package path.replanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author jesimar
 */
public class Main {

    private final File fileStatesDrone;
    private final File fileConfigRead;
    private final File fileConfigWrite;

    public static void main(String[] args) throws FileNotFoundException {
        if (args != null) {
            if (args.length > 0) {
                Main exec = new Main(args[1]);
                String line = exec.readFile(Integer.parseInt(args[0]));
                exec.writeFile(line);
            }
        }
    }

    public Main(String method) {
        this.fileStatesDrone = new File("../execute/states-drone.txt");
        this.fileConfigRead = new File("../execute/config-copy.sgl");
        this.fileConfigWrite = new File("../../Modules-IFA/" + method + "/config.sgl");
    }
    
    private String readFile(int lineNumber) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(fileStatesDrone);
            int i = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (i == lineNumber) {
                    return line;
                }
                i++;
            }
            sc.close();
        } catch (NoSuchElementException ex) {

        }
        return "";
    }
    
    private void writeFile(String line) throws FileNotFoundException{
        PrintStream print = new PrintStream(fileConfigWrite);
        Scanner sc = new Scanner(fileConfigRead);
        int i = 1;
        while (sc.hasNextLine()) {
            String l = sc.nextLine();
            if (i == 8){
                print.println(line);
            }else{
                print.println(l);
            }
            i++;
        }
        sc.close();
        print.close();
    }

}
