package uav.gcs2.google_maps;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uav.gcs2.CommunicationControl;

public class GoogleMaps {

    public static final int MIN_ZOOM = 0;
    public static final int MAX_ZOOM = 19;
    
    private static int zoomValue = 18;
    
    private final JFrame frame;
    private final JPanel panel;
    private final CommunicationControl sendCommands;

    public GoogleMaps(JFrame frame, JPanel panel, CommunicationControl sendCommands) {
        this.frame = frame;
        this.panel = panel;
        this.sendCommands = sendCommands;
    }

    public void plot(){
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        panel.add(browserView, BorderLayout.CENTER);
        frame.add(panel);

        JButton btnZoomIn = new JButton("Zoom In");
        btnZoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
                }
            }
        });

        JButton btnZoomOut = new JButton("Zoom Out");
        btnZoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
                }
            }
        });

        JButton btnSetMarker = new JButton("Start Point");
        btnSetMarker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.executeJavaScript(
                    "var myLatlng = new google.maps.LatLng(-22.0059726, -47.8986881);\n"
                    + "var marker = new google.maps.Marker({\n"
                    + "    position: myLatlng,\n"
                    + "    map: map,\n"
                    + "    title: 'Waypoint'\n"
                    + "});");
            }
        });
        
//        monitoringPositionDrone(browser);

        JPanel toolBar = new JPanel();
        toolBar.add(btnZoomIn);
        toolBar.add(btnZoomOut);
        toolBar.add(btnSetMarker);

        panel.add(toolBar, BorderLayout.SOUTH);

        browser.loadURL("file:///media/jesimar/Workspace/Work/UAV/UAV-GCS/google-maps.html");
    }
    
    private void monitoringPositionDrone(Browser browser){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        String latlng = sendCommands.latlng;
                        String javaScript = 
                            "var myLatlng = new google.maps.LatLng(" + latlng + ");\n"
                            + "var marker = new google.maps.Marker({\n"
                            + "    position: myLatlng,\n"
                            + "    map: map,\n"
                            + "    title: 'Drone'\n"
                            + "});";
                        browser.executeJavaScript(javaScript);
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println("Warning [InterruptedException] monitoringPositionDrone()");
                        ex.printStackTrace();
                    } 
                }
            }
        });
    }

}
