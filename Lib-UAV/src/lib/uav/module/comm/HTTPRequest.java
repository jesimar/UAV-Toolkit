package lib.uav.module.comm;

/**
 * Interface representing the protocol HTTP request with methods GET and POST.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public interface HTTPRequest {
    
    /**
     * Method that makes a GET request
     * @param urlGet the URL to the GET
     * @return the content of GET
     * @since version 4.0.0
     */
    public String GET(String urlGet);
    
    /**
     * Method that makes a POST request
     * @param urlPost the URL to the POST
     * @param jsonMsg the message in JSON to the POST
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean POST(String urlPost, String jsonMsg);
    
}
