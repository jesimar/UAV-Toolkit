package uav.hardware.sensors;

/**
 *
 * @author jesimar
 */
public class Battery {
        
    public double voltage;//in millivolts
    public double current;//in 10 * milliamperes
    public double level;  //in percentage (%)

    public Battery() {
    }  

    public Battery(double voltage, double current, double level) {
        this.voltage = voltage;
        this.current = current;
        this.level = level;
    } 
    
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
