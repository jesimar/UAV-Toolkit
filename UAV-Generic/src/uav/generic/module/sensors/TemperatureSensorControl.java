package uav.generic.module.sensors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.reader.ReaderFileConfig;

/**
 *
 * @author Jesimar S. Arantes
 */
public class TemperatureSensorControl {
    
    private final ReaderFileConfig config;
    private double temperature = -1.0;

    public TemperatureSensorControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void startTemperatureSensor(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar temperature-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python temperature-rpi.py " + config.getPinTemperatureCLK() + " "
                            + config.getPinTemperatureCS() + " " + config.getPinTemperatureSO();
                }else{
                    cmd = "./device";
                }
            }
            File file = new File(config.getDirTemperatureSensor());
            final Process comp = Runtime.getRuntime().exec(cmd, null, file);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        try{
                            temperature = Double.parseDouble(sc.nextLine());
                        }catch (NumberFormatException ex){
                            temperature = -1;
                        }
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] startTemperatureSensor()");
        } 
    }

    public double getTemperature() {
        return temperature;
    }
    
}