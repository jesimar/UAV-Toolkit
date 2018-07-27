/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager.check;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
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
public abstract class CheckCommand implements Check<Boolean>{
    public final String command;
    private final File dir;
    private final long timeout;
    private final int exitOk;

    public CheckCommand(String comand, File dir, long timeout, int exitOk) {
        this.command = comand;
        this.dir = dir;
        this.timeout = timeout;
        this.exitOk = exitOk;
    }
    public abstract boolean checkStream(InputStream stream);
    @Override
    public void check(Consumer<Boolean> consumer) {
        Executors.newSingleThreadExecutor().execute(()->{
            try {
                Process process = Runtime.getRuntime().exec(command, null, new File(dir.getCanonicalPath()));
                //checkOutput(process, consumer);
                //Thread.sleep(1000);
                ArrayBlockingQueue<Boolean> matches = new ArrayBlockingQueue(5);
                Executors.newSingleThreadExecutor().execute(()->{
                    try {
                        matches.put(checkStream(process.getInputStream()));
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                Executors.newSingleThreadExecutor().execute(()->{
                    try {
                        matches.put(checkStream(process.getErrorStream()));
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                if(process.waitFor(timeout, TimeUnit.MILLISECONDS)){
                    if(process.exitValue()==exitOk){
                        if(matches.size()==2){
                            boolean hasOne = false;
                            for(Boolean b : matches){
                                if(b){
                                    hasOne = true;
                                }
                            }
                            if(hasOne){
                                System.out.println("Match for command: "+command);
                                consumer.accept(Boolean.TRUE);
                            }else{
                                System.err.println("Dont hasOne for command: "+command);
                                consumer.accept(Boolean.FALSE);
                            }
                        }else{
                            System.err.println("Stream="+matches+" has not two elements for command: "+command);
                            consumer.accept(Boolean.FALSE);
                        }
                    }else{
                        System.err.println("Exit="+process.exitValue()+" for command: "+command);
                        consumer.accept(Boolean.FALSE);
                    }
                }else{
                    System.err.println("TimeOut for command: "+command);
                    consumer.accept(Boolean.FALSE);
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
                consumer.accept(Boolean.FALSE);
                
            }
        });
    }
}
