package uav.ifa.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeAltitudeDecay;
import uav.generic.struct.constants.TypeReplanner;
import uav.generic.struct.constants.TypeSystemExecIFA;

/**
 * Classe que lê o arquivo com gerais do sistema IFA.
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigIFA {
    
    private static final ReaderFileConfigIFA instance = new ReaderFileConfigIFA();
    
    private final String nameFile = "./config-ifa.properties";
    private Properties prop;

    //global
    private String systemExec;
    
    //replanner
    private String typeReplanner;
    private String methodReplanner;
    private String dirReplanner;
    private String cmdExecReplanner;    
    private String timeExecReplanner;
    private String qtdWaypoints;
    private String delta; 
    private String typeAltitudeDecay;
    
    //fixed route
    private String dirFixedRoute;
    private String fileFixedRoute;

    /**
     * Class constructor.
     */
    private ReaderFileConfigIFA() {
        
    }
    
    public static ReaderFileConfigIFA getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
            
            systemExec               = prop.getProperty("prop.global.system_exec");
            
            methodReplanner          = prop.getProperty("prop.replanner.method");
            cmdExecReplanner         = prop.getProperty("prop.replanner.cmd_exec");
            typeAltitudeDecay        = prop.getProperty("prop.replanner.type_altitude_decay");
            timeExecReplanner        = prop.getProperty("prop.replanner.time_exec");
            qtdWaypoints             = prop.getProperty("prop.replanner.qtd_waypoints");
            delta                    = prop.getProperty("prop.replanner.delta");
                  
            dirFixedRoute            = prop.getProperty("prop.fixed_route.dir");
            fileFixedRoute           = prop.getProperty("prop.fixed_route.file_waypoints");
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
                (!systemExec.equals(TypeSystemExecIFA.REPLANNER) &&
                 !systemExec.equals(TypeSystemExecIFA.FIXED_ROUTE))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of system exec not valid");
            return false;
        }
        if (methodReplanner == null || 
               (!methodReplanner.equals(TypeReplanner.GH4S) &&
                !methodReplanner.equals(TypeReplanner.GA4S) &&
                !methodReplanner.equals(TypeReplanner.MPGA4S) && 
                !methodReplanner.equals(TypeReplanner.DE4S) &&
                !methodReplanner.equals(TypeReplanner.GA_GA_4S) && 
                !methodReplanner.equals(TypeReplanner.GA_GH_4S) && 
                !methodReplanner.equals(TypeReplanner.FIXED_ROUTE4s))){
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
            }else if (methodReplanner.equals(TypeReplanner.GA_GA_4S)){
                typeReplanner = TypeReplanner.GA_GA_4S;
                dirReplanner = "../Modules-IFA/GA-GA-4s/";
            }else if (methodReplanner.equals(TypeReplanner.GA_GH_4S)){
                typeReplanner = TypeReplanner.GA_GH_4S;
                dirReplanner = "../Modules-IFA/GA-GH-4s/";
            }else if (methodReplanner.equals(TypeReplanner.FIXED_ROUTE4s)){
                typeReplanner = TypeReplanner.FIXED_ROUTE4s;
                dirReplanner = "../Modules-IFA/Fixed-Route4s/";
            }
            return true;
        }catch (Exception ex){
            StandardPrints.printMsgError2("Error [Exception] parseToVariables()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public String getSystemExec() {
        return systemExec;
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
    
    public String getTypeAltitudeDecay() {
        return typeAltitudeDecay;
    }
    
    public String getTimeExec() {
        return timeExecReplanner;
    }

    public String getQtdWaypoints() {
        return qtdWaypoints;
    }

    public String getDelta() {
        return delta;
    }
    
    public String getDirFixedRoute() {
        return dirFixedRoute;
    }

    public String getFileFixedRoute() {
        return fileFixedRoute;
    }
    
}