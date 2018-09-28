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
public class SonarControl {
    
    private final ReaderFileConfig config;
    private double distance = -1.0;

    public SonarControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void startSonarSensor(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar sonar-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python sonar-rpi.py " + config.getPinSonarTrig() + " " +
                            config.getPinSonarEcho();
                }else{
                    cmd = "./device";
                }
            }
            File file = new File(config.getDirSonar());
            final Process comp = Runtime.getRuntime().exec(cmd, null, file);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        try{
                            distance = Double.parseDouble(sc.nextLine());
                        }catch (NumberFormatException ex){
                            distance = -1;
                        }
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] startSonarSensor()");
        } 
    }

    public double getDistance() {
        return distance;
    }
    
}
