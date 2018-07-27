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
public class CheckFindApp implements Check<Boolean>{
    private final Find<File> finder;
    private final TriConsumer<File, String, String> saver;
    private final String propert_dir;
    private final String propert_app;
    public CheckFindApp(Find<File> finder, TriConsumer<File, String, String> saver, String propert_dir, String propert_app){
        this.finder = finder;
        this.saver = saver;
        this.propert_dir = propert_dir;
        this.propert_app = propert_app;
    }
    @Override
    public void check(Consumer<Boolean> consumer) {
        File file = finder.apply();
        if(file!=null){
            if(file.exists() && file.isFile()){
                saver.accept(file, propert_dir, propert_app);
                consumer.accept(true);
            }else{
                consumer.accept(false);
            }
        }else{
            consumer.accept(false);
        }
    }
}
/*
 properties.setProperty(propert_dir, file.getParent());
                properties.setProperty(propert_app, file.getName());
                try {
                    properties.store(new FileOutputStream(properties_file), "comments");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
*/
/*
 //int flag = fileChooser.showOpenDialog(null);
        if(flag == JFileChooser.APPROVE_OPTION){
            File file= fileChooser.getSelectedFile();
            if(file.exists() && file.isFile()){
                properties.setProperty(propert_dir, file.getParent());
                properties.setProperty(propert_app, file.getName());
                try {
                    properties.store(new FileOutputStream(properties_file), "comments");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                consumer.accept(true);
            }else{
                consumer.accept(false);
            }
        }else{
            consumer.accept(false);
        }
*/