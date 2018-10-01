package uav.generic.hardware.sensors;

/**
 * The class models drone battery (data obtained through power module).
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Battery {
        
    public double voltage;//in millivolts
    public double current;//in 10 * milliamperes
    public double level;  //in percentage (%)

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Battery() {
        
    }  

    /**
     * Class constructor.
     * @param voltage voltage in the battery
     * @param current current in the battery
     * @param level level of the battery
     * @since version 2.0.0
     */
    public Battery(double voltage, double current, double level) {
        this.voltage = voltage;
        this.current = current;
        this.level = level;
    } 
    
    /**
     * Converts line in JSON format to voltage, current and level values.
     * @param line FORMAT: {"bat": [12.587, 0.0, 100]}
     * @since version 2.0.0
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
    
    /**
     * Update the info the voltage, current and level
     * @param voltage voltage in the battery
     * @param current current in the battery
     * @param level level of the battery
     * @since version 2.0.0
     */
    public void updateBattery(String voltage, String current, String level){
        try{
            this.voltage = Double.parseDouble(voltage);
            this.current = Double.parseDouble(current);
            this.level = Double.parseDouble(level);
        }catch (NumberFormatException ex){
            
        }
    }

    /**
     * Set the voltage
     * @param voltage the voltage
     * @since version 2.0.0
     */
    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    /**
     * Set the current
     * @param current the current
     * @since version 2.0.0
     */
    public void setCurrent(double current) {
        this.current = current;
    }

    /**
     * Set the level
     * @param level the level
     * @since version 2.0.0
     */
    public void setLevel(double level) {
        this.level = level;
    }        

    @Override
    public String toString() {
        return "Battery{" + "voltage=" + voltage + ", current=" + current + ", level=" + level + '}';
    }
}
