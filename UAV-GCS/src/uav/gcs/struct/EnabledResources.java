package uav.gcs.struct;

/**
 * The class groups the attributes displayed in the GUI.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
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

    /**
     * Class constructor.
     * @since version 4.0.0
     */
    private EnabledResources() {
    
    }
    
    /**
     * Gets a instance this class (singleton pattern).
     * @return the instance this class
     * @since version 4.0.0
     */
    public static EnabledResources getInstance(){
        if (instance == null){
            instance = new EnabledResources();
        }
        return instance;
    }

}
