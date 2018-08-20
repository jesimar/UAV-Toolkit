package uav.generic.module.sensors_actuators;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.reader.ReaderFileConfigGlobal;

/**
 *
 * @author Jesimar S. Arantes
 */
public class SonarControl {
    
    private final ReaderFileConfigGlobal configGlobal;
    private double distance = -1.0;

    public SonarControl() {
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
    }
    
    public void startSonarSensor(){
        try {
            File f = new File(configGlobal.getDirSonar());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "java -jar sonar.jar";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC) ||
                    configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python sonar.py";
            }
            final Process comp = Runtime.getRuntime().exec(cmd, null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    while (sc.hasNextLine()) {
                        distance = Double.parseDouble(sc.nextLine());
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] startSonar()");
        } 
    }

    public double getDistance() {
        return distance;
    }
    
}
