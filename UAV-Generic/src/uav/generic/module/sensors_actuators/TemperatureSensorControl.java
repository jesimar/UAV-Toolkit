package uav.generic.module.sensors_actuators;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.reader.ReaderFileConfig;

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
            File f = new File(config.getDirTemperatureSensor());
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
            final Process comp = Runtime.getRuntime().exec(cmd, null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        temperature = Double.parseDouble(sc.nextLine());
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] startSonar()");
        } 
    }

    public double getTemperature() {
        return temperature;
    }
    
}
