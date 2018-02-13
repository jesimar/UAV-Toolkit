/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mission.creator.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Jesimar S. Arantes
 */
public class LoadConfig {

    private final String dirRoute3D;
    private final String fileRoute3D;
    private final String fileGeoBaseIn;
    private final String fileRouteGeo;
    private final String separatorRoute3D;
    private final String separatorGeoBaseIn;
    private final String separatorRouteGeo;
    private final String isUsedKmlGoogleEarth;
    
    private final String dirRouteKML;
    private final String fileRouteKML;
    private final String fileGeoBaseOut;
    private final String separatorGeoBaseOut;
    private final String fileMapNFZ;
    private final String fileMapFull;
    private final String fileWaypointsMission;
    private final String fileWaypointsMissionGeo;
    private final String fileWaypointsMissionGeoSimple;
    private final String fileWaypointsBuzzer;
    
    private final Properties prop = new Properties();
    private final InputStream input = new FileInputStream("./config.properties");

    public LoadConfig() throws FileNotFoundException, IOException {
        prop.load(input);
        dirRoute3D = prop.getProperty("prop.route3dtogeo.dir");
        fileRoute3D = prop.getProperty("prop.route3dtogeo.file_route");
        fileGeoBaseIn = prop.getProperty("prop.route3dtogeo.file_geobase");
        fileRouteGeo = prop.getProperty("prop.route3dtogeo.file_out_routegeo");
        separatorRoute3D = prop.getProperty("prop.route3dtogeo.separator_in_route3d");
        separatorGeoBaseIn = prop.getProperty("prop.route3dtogeo.separator_in_geobase");
        separatorRouteGeo = prop.getProperty("prop.route3dtogeo.separator_out_routegeo"); 
        isUsedKmlGoogleEarth = prop.getProperty("prop.route3dtogeo.is_used_kml_google_earth"); 
        dirRouteKML = prop.getProperty("prop.processorkml.dir"); 
        fileRouteKML = prop.getProperty("prop.processorkml.file_kml"); 
        fileGeoBaseOut = prop.getProperty("prop.processorkml.file_out_geobase"); 
        separatorGeoBaseOut = prop.getProperty("prop.processorkml.separator_out_geobase");   
        fileMapNFZ = prop.getProperty("prop.processorkml.file_out_map_nfz"); 
        fileMapFull = prop.getProperty("prop.processorkml.file_out_map_full");
        fileWaypointsMission = prop.getProperty("prop.processorkml.file_out_waypoints_mission");
        fileWaypointsMissionGeo = prop.getProperty("prop.processorkml.file_out_waypoints_mission_geo");
        fileWaypointsMissionGeoSimple = prop.getProperty("prop.processorkml.file_out_waypoints_mission_geo_simple");
        fileWaypointsBuzzer = prop.getProperty("prop.processorkml.file_out_waypoints_buzzer");
        //printProperties();        
    }
    
    public void printProperties(){
        System.out.println(dirRoute3D);
        System.out.println(fileRoute3D);
        System.out.println(fileGeoBaseIn);
        System.out.println(fileRouteGeo);
        System.out.println(separatorRoute3D);
        System.out.println(separatorGeoBaseIn);
        System.out.println(separatorRouteGeo);
        System.out.println(isUsedKmlGoogleEarth);
        System.out.println(dirRouteKML);
        System.out.println(fileRouteKML);
        System.out.println(fileGeoBaseOut);
        System.out.println(separatorGeoBaseOut);
        System.out.println(fileMapNFZ);
        System.out.println(fileMapFull);
        System.out.println(fileWaypointsMission);
        System.out.println(fileWaypointsMissionGeo);
        System.out.println(fileWaypointsMissionGeoSimple);
        System.out.println(fileWaypointsBuzzer);
    }

    public String getDirRoute3D() {
        return dirRoute3D;
    }

    public String getFileRoute3D() {
        return fileRoute3D;
    }      
    
    public String getFileRouteGeo() {
        return fileRouteGeo;
    }

    public String getSeparatorRoute3D() {
        return separatorRoute3D;
    } 
    
    public String getFileGeoBaseIn() {
        return fileGeoBaseIn;
    }

    public String getSeparatorGeoBaseIn() {
        return separatorGeoBaseIn;
    }

    public String getSeparatorRouteGeo() {
        return separatorRouteGeo;
    }

    public boolean getIsUsedKmlGoogleEarth() {
        if (isUsedKmlGoogleEarth.equals("true")){
            return true;
        }else{            
            return false;
        }
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

    public String getFileWaypointsMission() {
        return fileWaypointsMission;
    }  
    
    public String getFileWaypointsMissionGeo() {
        return fileWaypointsMissionGeo;
    }  
    
    public String getFileWaypointsMissionGeoSimple() {
        return fileWaypointsMissionGeoSimple;
    } 
    
    public String getFileWaypointsBuzzer() {
        return fileWaypointsBuzzer;
    } 

}
