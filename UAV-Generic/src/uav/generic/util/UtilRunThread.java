package uav.generic.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * Class with feature set to run threads
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class UtilRunThread {

    /**
     * Run a command in a single thread.
     * @param cmd command to execute.
     * @param file file containing the directory
     * @throws java.io.IOException
     * @since version 4.0.0
     */
    public static void runCmdSingleThread(String cmd, File file) throws IOException {
        UtilRunThread.runCmdSingleThread(cmd, file, false);
    }
    
    /**
     * Run a command in a single thread with wait.
     * @param cmd command to execute.
     * @param file file containing the directory
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @since version 4.0.0
     */
    public static void runCmdSingleThreadWaitFor(String cmd, File file) 
            throws IOException, InterruptedException {
        runCmdSingleThreadWaitFor(cmd, file, false);
    }

    /**
     * Run a command in a single thread.
     * @param cmd command to execute.
     * @param file file containing the directory
     * @param isPrint {@code true} print the message of process in execution.
     *                {@code false} dont print the message.
     * @throws java.io.IOException
     * @since version 4.0.0
     */
    public static void runCmdSingleThread(String cmd, File file, boolean isPrint) 
            throws IOException {
        final Process comp = Runtime.getRuntime().exec(cmd, null, file);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (isPrint) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
    }
    
    /**
     * Run a command in a single thread with wait.
     * @param cmd command to execute.
     * @param file file containing the directory
     * @param isPrint {@code true} print the message of process in execution.
     *                {@code false} dont print the message.
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @since version 4.0.0
     */
    public static void runCmdSingleThreadWaitFor(String cmd, File file, boolean isPrint) 
            throws IOException, InterruptedException {
        final Process comp = Runtime.getRuntime().exec(cmd, null, file);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (isPrint) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        comp.waitFor();
    }

    /**
     * Run a command in a dual thread with wait.
     * @param cmd command to execute.
     * @param file file containing the directory
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @since version 4.0.0
     */
    public static void dualSingleThreadWaitFor(String cmd, File file) 
            throws IOException, InterruptedException {
        dualSingleThreadWaitFor(cmd, file, false, false);
    }

    /**
     * Run a command in a dual thread with wait.
     * @param cmd command to execute.
     * @param file file containing the directory
     * @param isPrintOut {@code true} print the message of process in execution (standard output).
     *                   {@code false} dont print the message.
     * @param isPrintError {@code true} print the message of process in execution (error output).
     *                     {@code false} dont print the message.
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @since version 4.0.0
     */
    public static void dualSingleThreadWaitFor(String cmd, File file, boolean isPrintOut, 
            boolean isPrintError) throws IOException, InterruptedException {
        final Process comp = Runtime.getRuntime().exec(cmd, null, file);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (isPrintOut) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getErrorStream());
                if (isPrintError) {
                    while (sc.hasNextLine()) {
                        System.err.println("err:" + sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        comp.waitFor();
    }

}
