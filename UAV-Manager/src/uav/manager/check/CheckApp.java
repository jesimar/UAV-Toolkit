/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager.check;

import java.io.File;
import java.util.function.Consumer;

/**
 *
 * @author Marcio
 */
public class CheckApp implements Check<Boolean>{
    
    private final String dir;
    private final String app;
    
    public CheckApp(String dir, String app){
        this.dir = dir;
        this.app = app;
    }
    
    @Override
    public void check(Consumer<Boolean> consumer) {
        File file = new File(dir, app);
        System.out.println(file);
        System.out.println(file.isFile());
        System.out.println(file.exists());
        consumer.accept(file.isFile() && file.exists());
    }
}
