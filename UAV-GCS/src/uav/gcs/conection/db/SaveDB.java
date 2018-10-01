package uav.gcs.conection.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import uav.gcs.communication.CommunicationIFA;
import uav.gcs.struct.Drone;

/**
 * Class used to send data to Database (Oracle Drone)
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class SaveDB {
    
    private final int TIME_CONNECTION = 480;//2 minutos
    
    private final MysqlDB db;

    public SaveDB(String ip, String port) {
        db = new MysqlDB(ip, port);
    }

    public void saveDB(Drone drone, CommunicationIFA communicationIFA) {
        System.out.println("Trying save data DB in OD");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.connect();
                    PreparedStatement ps = null;
                    int i = 0;
                    try {
                        do {
                            Thread.sleep(500);
                            System.out.println("isConnected");
                        } while (!communicationIFA.isConnected());
                        do{
                            Thread.sleep(500);
                            System.out.println("lat lng (0, 0)");
                        }while(drone.gps.lat == 0 && drone.gps.lng == 0);
                        while (i < TIME_CONNECTION) {
                            String sql = "INSERT INTO logidronereal(email_user,nome_rota,"
                                    + "date,hour,time,nextWaypoint,countWaypoint,distToHome,"
                                    + "distToCurrentWpt,typeFailure,voltage,current,level,"
                                    + "lat,lng,alt_rel,alt_abs,pitch,yaw,roll,vx,vy,vz,fixtype,"
                                    + "satellitesvisible,eph,epv,heading,groundspeed,"
                                    + "airspeed,mode,systemStatus,armed,isArmable,ekfOk)"
                                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            ps = db.conn.prepareStatement(sql);
                            String dateHour = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                            ps.setString(1, drone.userEmail);
                            ps.setString(2, "route-" + dateHour);
                            ps.setString(3, drone.date);
                            ps.setString(4, drone.hour);
                            ps.setString(5, drone.time + "");
                            ps.setString(6, drone.nextWaypoint + "");
                            ps.setString(7, drone.countWaypoint + "");
                            ps.setString(8, drone.distanceToHome + "");
                            ps.setString(9, drone.distanceToCurrentWaypoint + "");
                            ps.setString(10, drone.typeFailure);
                            ps.setString(11, drone.battery.voltage + "");
                            ps.setString(12, drone.battery.current + "");
                            ps.setString(13, drone.battery.level + "");
                            ps.setString(14, drone.gps.lat + "");
                            ps.setString(15, drone.gps.lng + "");
                            ps.setString(16, drone.barometer.alt_rel + "");
                            ps.setString(17, drone.barometer.alt_abs + "");
                            ps.setString(18, drone.attitude.pitch + "");
                            ps.setString(19, drone.attitude.yaw + "");
                            ps.setString(20, drone.attitude.roll + "");
                            ps.setString(21, drone.velocity.vx + "");
                            ps.setString(22, drone.velocity.vy + "");
                            ps.setString(23, drone.velocity.vz + "");
                            ps.setString(24, drone.gpsinfo.fixType + "");
                            ps.setString(25, drone.gpsinfo.satellitesVisible + "");
                            ps.setString(26, drone.gpsinfo.eph + "");
                            ps.setString(27, drone.gpsinfo.epv + "");
                            ps.setString(28, drone.sensorUAV.heading + "");
                            ps.setString(29, drone.sensorUAV.groundspeed + "");
                            ps.setString(30, drone.sensorUAV.airspeed + "");
                            ps.setString(31, drone.statusUAV.mode);
                            ps.setString(32, drone.statusUAV.systemStatus);
                            ps.setString(33, drone.statusUAV.armed + "");
                            ps.setString(34, drone.statusUAV.isArmable + "");
                            ps.setString(35, drone.statusUAV.ekfOk + "");
                            ps.executeUpdate();
                            ps.close();
                            Thread.sleep(500);
                            i++;
                        }
                    } catch (InterruptedException ex) {
                        
                    }

                } catch (SQLException ex) {
                    System.err.println("Erro[SQLException] saveDB");
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public boolean isConnected(){
        return db.isConnected();
    }
}
