package lib.uav.module.sensors;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import lib.uav.struct.constants.TypeCC;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.util.UtilRunThread;

/**
 * The class models the camera control as it takes pictures and make videos.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class CameraControl {
    
    private final ReaderFileConfig config;

    /**
     * The constructor.
     * @since version 4.0.0
     */
    public CameraControl() {
        this.config = ReaderFileConfig.getInstance();
    }
    
    /**
     * Take a picture based on the type of operation mode and the existing device.
     * @since version 4.0.0
     */
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
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirCamera()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] takeAPicture()");
        } 
    }
    
    /**
     * Take a sequence of photographs based on the type of operating mode and existing device.
     * @since version 4.0.0
     */
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
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirCamera()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] photoInSequence()");
        } 
    }
    
    /**
     * Make a video based on the type of operating mode and existing device.
     * @since version 4.0.0
     */
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
            UtilRunThread.runCmdSingleThread(cmd, new File(config.getDirCamera()), print);
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] makeAVideo()");
        } 
    }
}
