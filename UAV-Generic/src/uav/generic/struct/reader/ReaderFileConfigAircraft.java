package uav.generic.struct.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeAircraft;

/**
 * Classe que lê o arquivo com dados da aeroanve.
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigAircraft {
    
    private static final ReaderFileConfigAircraft instance = new ReaderFileConfigAircraft();
    
    private final String nameFile = "../Modules-Global/config-aircraft.properties";
    private Properties prop;    

    private String typeAircraft;
    private String nameAircraft;
    private double speedCruize;
    private double speedMax;
    private double mass;
    private double payload;
    private double endurance;

    /**
     * Class constructor.
     */
    private ReaderFileConfigAircraft() {
        
    }
    
    public static ReaderFileConfigAircraft getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
            
            typeAircraft = prop.getProperty("prop.aircraft.type_aircraft");
            nameAircraft = prop.getProperty("prop.aircraft.name_aircraft");
            speedCruize  = Double.parseDouble(prop.getProperty("prop.aircraft.speed_cruize"));
            speedMax     = Double.parseDouble(prop.getProperty("prop.aircraft.speed_max"));
            mass         = Double.parseDouble(prop.getProperty("prop.aircraft.mass"));
            payload      = Double.parseDouble(prop.getProperty("prop.aircraft.payload"));
            endurance    = Double.parseDouble(prop.getProperty("prop.aircraft.endurance"));
            
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] read()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] read()");
            ex.printStackTrace();
            return false;
        } catch (NumberFormatException ex) {
            StandardPrints.printMsgError2("Error [NumberFormatException] read()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean checkReadFields(){
        if (typeAircraft == null || 
                (!typeAircraft.equals(TypeAircraft.FIXED_WING) && 
                !typeAircraft.equals(TypeAircraft.ROTARY_WING))){
            StandardPrints.printMsgError2("Error [[file ./config-aircraft.properties]] type of aircraft not valid");
            return false;
        }
        return true;
    }
    
    public String getTypeAircraft() {
        return typeAircraft;
    }

    public String getNameAircraft() {
        return nameAircraft;
    }

    public double getSpeedCruize() {
        return speedCruize;
    }

    public double getSpeedMax() {
        return speedMax;
    }

    public double getMass() {
        return mass;
    }

    public double getPayload() {
        return payload;
    }

    public double getEndurance() {
        return endurance;
    }
    
}