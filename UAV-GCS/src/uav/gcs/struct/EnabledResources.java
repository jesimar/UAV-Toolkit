package uav.gcs.struct;

/**
 *
 * @author Jesimar S. Arantes
 */
public class EnabledResources {
    
    private static EnabledResources instance;
    
    public boolean showRoutePlanner = true;
    public boolean showRouteReplanner = true;
    public boolean showRouteSimplifier = true;
    public boolean showRouteDrone = true;
    public boolean showPositionDrone = true;
    public boolean showMarkers = true;
    public boolean showMap = true;
    public boolean showMaxDistReached = false;

    private EnabledResources() {
    
    }
    
    public static EnabledResources getInstance(){
        if (instance == null){
            instance = new EnabledResources();
        }
        return instance;
    }
    
    
    
}
