package mpga4s;

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

    private final File file = new File("./route-fix.txt");
    private final File fileConfig = new File("./config-copy.sgl");
    private final File fileConfigWrite = new File("./config.sgl");

    public static void main(String[] args) throws FileNotFoundException {
        if (args != null) {
            if (args.length > 0) {
                Main exec = new Main();
                String line = exec.read(Integer.parseInt(args[0]));
                exec.write(line);
            }
        }
    }
    
    public String read(int lineNumber) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
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
    
    private void write(String line) throws FileNotFoundException{
        PrintStream print = new PrintStream(fileConfigWrite);
        Scanner sc = new Scanner(fileConfig);
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
