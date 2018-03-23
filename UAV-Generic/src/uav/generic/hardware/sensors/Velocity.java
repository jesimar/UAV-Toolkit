package uav.generic.hardware.sensors;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Velocity {
    
    public double vx;//velocity in m/s
    public double vy;//velocity in m/s
    public double vz;//velocity in m/s

    public Velocity() {
    }

    public Velocity(double vx, double vy, double vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }
    
    public void parserInfoVelocity(String line) {
        try{
            line = line.substring(9, line.length() - 2);
            String v[] = line.split(",");
            this.vx = Double.parseDouble(v[0]);        
            this.vy = Double.parseDouble(v[1]);
            this.vz = Double.parseDouble(v[2]);
        }catch (NumberFormatException ex){
            
        }
    } 
    
    public void updateVelocity(String vx, String vy, String vz) {
        try{
            this.vx = Double.parseDouble(vx);        
            this.vy = Double.parseDouble(vy);
            this.vz = Double.parseDouble(vz);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setVz(double vz) {
        this.vz = vz;
    }
    
    @Override
    public String toString() {
        return "Velocity{" + "vx=" + vx + ", vy=" + vy + ", vz=" + vz + '}';
    }   
        
}
