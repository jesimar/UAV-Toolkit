package uav.generic.module.sensors;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.util.UtilRunThread;

/**
 *
 * @author Jesimar S. Arantes
 */
public class CameraControl {
    
    private final ReaderFileConfig config;

    public CameraControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void takeAPicture(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar picture-pc.jar";
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python picture-rpi.py";
                }else{
                    cmd = "./device";
                }
            } 
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirCamera()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] takeAPicture()");
        } 
    }
    
    public void photoInSequence(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar photo-in-sequence-pc.jar " + 
                            config.getNumberPhotoInSequence() + " " + 
                            config.getDelayPhotoInSequence();
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python photo-in-sequence-rpi.py " + 
                            config.getNumberPhotoInSequence() + " " + 
                            config.getDelayPhotoInSequence();
                }else{
                    cmd = "./device";
                }
            }
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirCamera()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] photoInSequence()");
        } 
    }
    
    public void makeAVideo(){
        try {
            String cmd = "";
            if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                cmd = "java -jar video-pc.jar " + config.getTimeVideo();
            } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                    config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                if (config.getTypeCC().equals(TypeCC.RASPBERRY)){
                    cmd = "python video-rpi.py " + config.getTimeVideo();
                }else{
                    cmd = "./device";
                }
            } 
            boolean print = true;
            UtilRunThread.singleThread(cmd, new File(config.getDirCamera()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] makeAVideo()");
        } 
    }
}
