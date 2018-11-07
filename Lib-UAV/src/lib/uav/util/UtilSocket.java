package lib.uav.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

/**
 * Class with util methods on Socket.
 * @author Jesimar S. Arantes
 * @since version 5.0.0
 */
public class UtilSocket {
    
    public static boolean isAliveSocket(String hostName, int port) {
        boolean isAlive = false;
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();
        int timeout = 2000; //Timeout required - it's in milliseconds
        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isAlive = true;
        } catch (SocketTimeoutException ex) {
            System.out.println("SocketTimeoutException");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        return isAlive;
    }
    
}
