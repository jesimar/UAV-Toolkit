package uav.ifa.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeAltitudeDecay;
import uav.generic.struct.constants.TypeReplanner;
import uav.generic.struct.constants.TypeSystemExecIFA;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfig {
    
    private static final ReaderFileConfig instance = new ReaderFileConfig();
    private Properties prop;
    private InputStream input;

    //global
    private String systemExec;
    private String uavInsertFailure;
    
    //replanner
    private String typeReplanner;
    private String methodReplanner;
    private String dirReplanner;
    private String cmdExecReplanner;    
    private String timeExec;
    private String qtdWaypoints;
    private String delta; 
    private String typeAltitudeDecay;
    
    private boolean isUavInsertFailure;

    private ReaderFileConfig() {
        
    }
    
    public static ReaderFileConfig getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            input = new FileInputStream("./config-ifa.properties");
            prop.load(input);
            
            systemExec               = prop.getProperty("prop.global.system_exec");
            uavInsertFailure         = prop.getProperty("prop.global.uav_insert_failure");
            
            methodReplanner          = prop.getProperty("prop.replanner.method");
            cmdExecReplanner         = prop.getProperty("prop.replanner.cmd_exec");
            timeExec                 = prop.getProperty("prop.replanner.time_exec");
            qtdWaypoints             = prop.getProperty("prop.replanner.qtd_waypoints");
            delta                    = prop.getProperty("prop.replanner.delta");
            typeAltitudeDecay        = prop.getProperty("prop.replanner.type_altitude_decay");
                        
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean checkReadFields(){        
        if (systemExec == null || 
                !systemExec.equals(TypeSystemExecIFA.REPLANNER)){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of replanner not valid");
            return false;
        }
        if (methodReplanner == null || 
               (!methodReplanner.equals(TypeReplanner.GH4S) &&
                !methodReplanner.equals(TypeReplanner.GA4S) &&
                !methodReplanner.equals(TypeReplanner.MPGA4S) && 
                !methodReplanner.equals(TypeReplanner.DE4S))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of method not valid");
            return false;
        }
        if (typeAltitudeDecay == null ||
                (!typeAltitudeDecay.equals(TypeAltitudeDecay.CONSTANTE) && 
                !typeAltitudeDecay.equals(TypeAltitudeDecay.LINEAR))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type altitude decay not valid");
            return false;
        }
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            isUavInsertFailure = Boolean.parseBoolean(uavInsertFailure);
            if (methodReplanner.equals(TypeReplanner.DE4S)){
                typeReplanner = TypeReplanner.DE4S;
                dirReplanner = "../Modules-IFA/DE4s/";
            }else if (methodReplanner.equals(TypeReplanner.GH4S)){
                typeReplanner = TypeReplanner.GH4S;
                dirReplanner = "../Modules-IFA/GH4s/";
            }else if (methodReplanner.equals(TypeReplanner.GA4S)){
                typeReplanner = TypeReplanner.GA4S;
                dirReplanner = "../Modules-IFA/GA4s/";
            }else if (methodReplanner.equals(TypeReplanner.MPGA4S)){
                typeReplanner = TypeReplanner.MPGA4S;
                dirReplanner = "../Modules-IFA/MPGA4s/";
            }
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public String getSystemExec() {
        return systemExec;
    }
    
    public boolean isUavInsertFailure() {
        return isUavInsertFailure;
    }
    
    public String getTypeReplanner() {
        return typeReplanner;
    }

    public String getDirReplanner() {
        return dirReplanner;
    }

    public String getCmdExecReplanner() {
        return cmdExecReplanner;
    }
    
    public String getTimeExec() {
        return timeExec;
    }

    public String getQtdWaypoints() {
        return qtdWaypoints;
    }

    public String getDelta() {
        return delta;
    }
    
    public String getTypeAltitudeDecay() {
        return typeAltitudeDecay;
    }
}