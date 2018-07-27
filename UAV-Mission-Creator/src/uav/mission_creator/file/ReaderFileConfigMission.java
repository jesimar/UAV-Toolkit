package uav.mission_creator.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigMission {

    private final String dirRouteKML;
    private final String fileRouteKML;
    private final String fileGeoBaseOut;
    private final String separatorGeoBaseOut;
    private final String fileMapNFZ;
    private final String fileMapFull;
    private final String fileMissionCCQSP;
    private final String fileWaypointsMission;
    private final String fileWaypointsMissionGeo;
    private final String fileWaypointsMissionGeoLabel;
    private final String fileFeatureMission;
    
    private final Properties prop = new Properties();
    private final InputStream input = new FileInputStream("./config-mission.properties");

    public ReaderFileConfigMission() throws FileNotFoundException, IOException {
        prop.load(input);
        dirRouteKML = prop.getProperty("prop.mission_creator.dir"); 
        fileRouteKML = prop.getProperty("prop.mission_creator.file_kml"); 
        fileGeoBaseOut = prop.getProperty("prop.mission_creator.file_out_geobase"); 
        separatorGeoBaseOut = prop.getProperty("prop.mission_creator.separator_out_geobase");   
        fileMapNFZ = prop.getProperty("prop.mission_creator.file_out_map_nfz"); 
        fileMapFull = prop.getProperty("prop.mission_creator.file_out_map_full");
        fileMissionCCQSP = prop.getProperty("prop.mission_creator.file_out_mission_ccqsp");
        fileWaypointsMission = prop.getProperty("prop.mission_creator.file_out_waypoints_mission");
        fileWaypointsMissionGeo = prop.getProperty("prop.mission_creator.file_out_waypoints_mission_geo");
        fileWaypointsMissionGeoLabel = prop.getProperty("prop.mission_creator.file_out_waypoints_mission_geo_label");
        fileFeatureMission = prop.getProperty("prop.mission_creator.file_out_feature_mission");            
    }
    
    public String getDirRouteKML() {
        return dirRouteKML;
    }

    public String getFileRouteKML() {
        return fileRouteKML;
    }

    public String getFileGeoBaseOut() {
        return fileGeoBaseOut;
    }

    public String getSeparatorGeoBaseOut() {
        return separatorGeoBaseOut;
    }

    public String getFileMapNFZ() {
        return fileMapNFZ;
    }

    public String getFileMapFull() {
        return fileMapFull;
    }   
    
    public String getFileMissionCCQSP() {
        return fileMissionCCQSP;
    }

    public String getFileWaypointsMission() {
        return fileWaypointsMission;
    }  
    
    public String getFileWaypointsMissionGeo() {
        return fileWaypointsMissionGeo;
    }  
    
    public String getFileWaypointsMissionGeoLabel() {
        return fileWaypointsMissionGeoLabel;
    } 
    
    public String getFileFeatureMission() {
        return fileFeatureMission;
    } 

}
