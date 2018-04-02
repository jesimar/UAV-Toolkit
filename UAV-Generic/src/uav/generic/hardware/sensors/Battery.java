package uav.generic.hardware.sensors;

/**
 * Classe que modela a bateria do drone.
 * @author Jesimar S. Arantes
 */
public class Battery {
        
    public double voltage;//in millivolts
    public double current;//in 10 * milliamperes
    public double level;  //in percentage (%)

    /**
     * Class constructor.
     */
    public Battery() {
        
    }  

    /**
     * Class constructor.
     * @param voltage voltage in the battery
     * @param current current in the battery
     * @param level level of the battery
     */
    public Battery(double voltage, double current, double level) {
        this.voltage = voltage;
        this.current = current;
        this.level = level;
    } 
    
    /**
     * Converts line in JSON format to voltage, current and level values.
     * @param line FORMAT: {"bat": [12.587, 0.0, 100]}
     */
    public void parserInfoBattery(String line) {
        try{
            line = line.substring(9, line.length() - 2);
            String v[] = line.split(",");        
            this.voltage = Double.parseDouble(v[0]);        
            this.current = Double.parseDouble(v[1]);
            this.level   = Double.parseDouble(v[2]);
        }catch (NumberFormatException ex){
            
        }
    } 
    
    public void updateBattery(String voltage, String current, String level){
        try{
            this.voltage = Double.parseDouble(voltage);
            this.current = Double.parseDouble(current);
            this.level = Double.parseDouble(level);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setLevel(double level) {
        this.level = level;
    }        

    @Override
    public String toString() {
        return "Battery{" + "voltage=" + voltage + ", current=" + current + ", level=" + level + '}';
    }
}
