package uav.generic.hardware.sensors;

/**
 * Classe que modela algumas informações do GPS do drone (dados obtidos através do GPS).
 * @author Jesimar S. Arantes
 */
public class GPSInfo {
    
    public int fixType;             //0-1: no fix, 2: 2D fix, 3: 3D fix
    public int satellitesVisible;   //number of visible satellites
    public int eph;                 //GPS horizontal dilution of position (HDOP)
    public int epv;                 //GPS vertical   dilution of position (VDOP)

    /**
     * Class constructor.
     */
    public GPSInfo() {
        
    }

    /**
     * Class constructor.
     * @param fix 0-1: no fix, 2: 2D fix, 3: 3D fix
     * @param num_sat number of visible satellites
     * @param eph GPS horizontal dilution of position (HDOP)
     * @param epv GPS vertical   dilution of position (VDOP)
     */
    public GPSInfo(int fix, int num_sat, int eph, int epv) {
        this.fixType = fix;
        this.satellitesVisible = num_sat;
        this.eph = eph;
        this.epv = epv;
    }
    
    /**
     * Converts line in JSON format to fixtype, num_sat, eph and epv values.
     * @param line FORMAT: {"gpsinfo": [3, 10, 121, 65535]}
     */
    public void parserInfoGPSInfo(String line) {
        try{
            line = line.substring(13, line.length() - 2);        
            String v[] = line.split(", ");
            this.fixType = Integer.parseInt(v[0]);        
            this.satellitesVisible = Integer.parseInt(v[1]);
            this.eph = Integer.parseInt(v[2]);
            this.epv = Integer.parseInt(v[3]);
        }catch (NumberFormatException ex){
            
        }
    } 
    
    public void setFixType(String fixType) {
        try{
            this.fixType = Integer.parseInt(fixType);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setFixType(int fixType) {
        this.fixType = fixType;
    }
    
    public void setSatellitesVisible(String satellitesVisible) {
        try{
            this.satellitesVisible = Integer.parseInt(satellitesVisible);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setSatellitesVisible(int satellitesVisible) {
        this.satellitesVisible = satellitesVisible;
    }

    public void setEPH(String eph) {
        try{
            this.eph = Integer.parseInt(eph);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setEPH(int eph) {
        this.eph = eph;
    }
    
    public void setEPV(String epv) {
        try{
            this.epv = Integer.parseInt(epv);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setEPV(int epv) {
        this.epv = epv;
    }    

    @Override
    public String toString() {
        return "GPSInfo{" + "fixType=" + fixType + ", satellitesVisible=" + satellitesVisible 
                + "eph=" + eph + ", epv=" + epv + '}';
    }        
    
}
