package uav.generic.module.comm;

/**
 *
 * @author Jesimar S. Arantes
 */
public interface HTTPRequest {
    
    public String GET(String urlGet);
    
    public boolean POST(String urlPost, String jsonMsg);
    
}
