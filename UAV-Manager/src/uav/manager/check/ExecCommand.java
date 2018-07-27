/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager.check;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcio
 */
public class ExecCommand {
    
    private final File dir;
    private final int exitOk;
    
    private Process process;

    public ExecCommand() {
        this(new File("./resources/scripts/"), 0);
    }
    public ExecCommand(File dir, int exitOk) {
        this.dir = dir;
        this.exitOk = exitOk;
    }
    private void destroy(String force){
        try {
            Runtime.getRuntime().exec(force, null, new File(dir.getCanonicalPath()));
        } catch (Throwable ex) {

        }
    }
    private void close() throws IOException{
        process.getInputStream().close();
        process.getErrorStream().close();
        process.getOutputStream().close();
        process.destroy();
        process.destroyForcibly();
    }
    public void stop(String force, Consumer<Boolean> check){
        if(process!=null && process.isAlive()){
            try {
                destroy(force);
                if(process.waitFor(10, TimeUnit.SECONDS)){
                    close();
                    check.accept(Boolean.TRUE);
                }else{
                    close();
                    check.accept(Boolean.FALSE);
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
                check.accept(Boolean.FALSE);
            }
        }else{
            check.accept(Boolean.TRUE);
        }
    }
    public void execute(String command, Consumer<String> output, Consumer<String> error, Consumer<Boolean> check, Consumer<PrintStream> terminal) {
        if(process==null || !process.isAlive()){
            Executors.newSingleThreadExecutor().execute(()->{
                try {
                    process = Runtime.getRuntime().exec(command, null, new File(dir.getCanonicalPath()));
                    
                    Executors.newSingleThreadExecutor().execute(()->{
                        Scanner sc = new Scanner(process.getInputStream());
                        while(sc.hasNextLine()){
                            output.accept(sc.nextLine());
                        }
                    });
                    Executors.newSingleThreadExecutor().execute(()->{
                        Scanner sc = new Scanner(process.getErrorStream());
                        while(sc.hasNextLine()){
                            error.accept(sc.nextLine());
                        }
                    });
                    terminal.accept(new PrintStream(process.getOutputStream(), true));
                        
                    if(process.waitFor()==exitOk){
                        close();
                        check.accept(Boolean.TRUE);
                    }else{
                        close();
                        check.accept(Boolean.FALSE);
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    check.accept(Boolean.FALSE);
                }
            });
        }else{
            check.accept(Boolean.FALSE);
        }
    }
}
