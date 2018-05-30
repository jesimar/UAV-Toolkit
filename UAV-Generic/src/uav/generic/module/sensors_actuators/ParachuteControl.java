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
public class ParachuteControl {
    
    private final ReaderFileConfigGlobal configGlobal;

    public ParachuteControl() {
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
    }
    
    public boolean open(){
        try {
            boolean print = true;
            File f = new File(configGlobal.getDirParachute());
            String cmd = "";
            if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_LOCAL)){
                cmd = "./open-parachute";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_EDISON)){
                cmd = "python open-parachute.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.SITL_CC_RPI)){
                cmd = "python open-parachute.py";
            } else if (configGlobal.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                cmd = "python open-parachute.py";
            }
            final Process comp = Runtime.getRuntime().exec(cmd, null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    if (print) {
                        while (sc.hasNextLine()) {
                            System.out.println(sc.nextLine());
                        }
                    }
                    sc.close();
                }
            });
            return true;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] open()");
            return false;
        }
    }
    
}
