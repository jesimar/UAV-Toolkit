package uav.gcs.window;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uav.gcs.struct.Drone;

/**
 *
 * @author Jesimar S. Arantes
 */
public class LabelsInfo {
    
    private final JLabel labelDate;
    private final JLabel labelHour;
    private final JLabel labelTime;
    private final JLabel labelLat;
    private final JLabel labelLng;
    private final JLabel labelAltRel;
    private final JLabel labelAltAbs;
    private final JLabel labelVoltageBat;
    private final JLabel labelCurrentBat;
    private final JLabel labelLevelBat;
    private final JLabel labelPicth;
    private final JLabel labelYaw;
    private final JLabel labelRoll;
    private final JLabel labelVx;
    private final JLabel labelVy;
    private final JLabel labelVz;
    private final JLabel labelHeading;
    private final JLabel labelGroundspeed;
    private final JLabel labelAirspeed;
    private final JLabel labelGPSFixType;
    private final JLabel labelGPSSatelities;
    private final JLabel labelGPSEph;
    private final JLabel labelGPSEpv;
    private final JLabel labelMode;
    private final JLabel labelStatus;
    private final JLabel labelArmed;
    private final JLabel labelIsArmable;
    private final JLabel labelEkfOk;
    private final JLabel labelNextWpt;
    private final JLabel labelCountWpt;
    private final JLabel labelDistToHome;
    private final JLabel labelDistToCurrWpt;
    private final JLabel labelTypeFailure;
    private final JLabel labelEstTimeToDoRTL;
    private final JLabel labelEstConBatRTL;
    private final JLabel labelSonarDistance;
    private final JLabel labelTemperature;

    public LabelsInfo(JPanel panel) {
        this.labelDate = new JLabel("Date: ");
        labelDate.setPreferredSize(new Dimension(160, 20));
        labelDate.setForeground(Color.BLACK);
        panel.add(labelDate);
        
        this.labelHour = new JLabel("Hour: ");
        labelHour.setPreferredSize(new Dimension(160, 20));
        labelHour.setForeground(Color.BLACK);
        panel.add(labelHour);
        
        this.labelTime = new JLabel("Time: ");
        labelTime.setPreferredSize(new Dimension(160, 20));
        labelTime.setForeground(Color.BLACK);
        panel.add(labelTime);
        
        this.labelLat = new JLabel("Lat: ");
        labelLat.setPreferredSize(new Dimension(160, 20));
        labelLat.setForeground(Color.BLACK);
        panel.add(labelLat);
        
        this.labelLng = new JLabel("Lnt: ");
        labelLng.setPreferredSize(new Dimension(160, 20));
        labelLng.setForeground(Color.BLACK);
        panel.add(labelLng);
        
        this.labelAltRel = new JLabel("Alt Rel: ");
        labelAltRel.setPreferredSize(new Dimension(160, 20));
        labelAltRel.setForeground(Color.BLACK);
        panel.add(labelAltRel);
        
        this.labelAltAbs = new JLabel("Alt Abs: ");
        labelAltAbs.setPreferredSize(new Dimension(160, 20));
        labelAltAbs.setForeground(Color.BLACK);
        panel.add(labelAltAbs);
        
        this.labelVoltageBat = new JLabel("Voltage Bat: ");
        labelVoltageBat.setPreferredSize(new Dimension(160, 20));
        labelVoltageBat.setForeground(Color.BLACK);
        panel.add(labelVoltageBat);
        
        this.labelCurrentBat = new JLabel("Current Bat: ");
        labelCurrentBat.setPreferredSize(new Dimension(160, 20));
        labelCurrentBat.setForeground(Color.BLACK);
        panel.add(labelCurrentBat);
        
        this.labelLevelBat = new JLabel("Level Bat: ");
        labelLevelBat.setPreferredSize(new Dimension(160, 20));
        labelLevelBat.setForeground(Color.BLACK);
        panel.add(labelLevelBat);
        
        this.labelPicth = new JLabel("Picth: ");
        labelPicth.setPreferredSize(new Dimension(160, 20));
        labelPicth.setForeground(Color.BLACK);
        panel.add(labelPicth);
        
        this.labelYaw = new JLabel("Yaw: ");
        labelYaw.setPreferredSize(new Dimension(160, 20));
        labelYaw.setForeground(Color.BLACK);
        panel.add(labelYaw);
        
        this.labelRoll = new JLabel("Roll: ");
        labelRoll.setPreferredSize(new Dimension(160, 20));
        labelRoll.setForeground(Color.BLACK);
        panel.add(labelRoll);
        
        this.labelVx = new JLabel("Vel x: ");
        labelVx.setPreferredSize(new Dimension(160, 20));
        labelVx.setForeground(Color.BLACK);
        panel.add(labelVx);
        
        this.labelVy = new JLabel("Vel y: ");
        labelVy.setPreferredSize(new Dimension(160, 20));
        labelVy.setForeground(Color.BLACK);
        panel.add(labelVy);
        
        this.labelVz = new JLabel("Vel z: ");
        labelVz.setPreferredSize(new Dimension(160, 20));
        labelVz.setForeground(Color.BLACK);
        panel.add(labelVz);
        
        this.labelHeading = new JLabel("Heading: ");
        labelHeading.setPreferredSize(new Dimension(160, 20));
        labelHeading.setForeground(Color.BLACK);
        panel.add(labelHeading);
        
        this.labelGroundspeed = new JLabel("Groundspeed: ");
        labelGroundspeed.setPreferredSize(new Dimension(160, 20));
        labelGroundspeed.setForeground(Color.BLACK);
        panel.add(labelGroundspeed);
        
        this.labelAirspeed = new JLabel("Airspeed: ");
        labelAirspeed.setPreferredSize(new Dimension(160, 20));
        labelAirspeed.setForeground(Color.BLACK);
        panel.add(labelAirspeed);
        
        this.labelGPSFixType = new JLabel("GPS Fix Type: ");
        labelGPSFixType.setPreferredSize(new Dimension(160, 20));
        labelGPSFixType.setForeground(Color.BLACK);
        panel.add(labelGPSFixType);
        
        this.labelGPSSatelities = new JLabel("GPS Satelities: ");
        labelGPSSatelities.setPreferredSize(new Dimension(160, 20));
        labelGPSSatelities.setForeground(Color.BLACK);
        panel.add(labelGPSSatelities);
        
        this.labelGPSEph = new JLabel("GPS EPH: ");
        labelGPSEph.setPreferredSize(new Dimension(160, 20));
        labelGPSEph.setForeground(Color.BLACK);
        panel.add(labelGPSEph);
        
        this.labelGPSEpv = new JLabel("GPS EPV: ");
        labelGPSEpv.setPreferredSize(new Dimension(160, 20));
        labelGPSEpv.setForeground(Color.BLACK);
        panel.add(labelGPSEpv);
        
        this.labelMode = new JLabel("Mode: ");
        labelMode.setPreferredSize(new Dimension(160, 20));
        labelMode.setForeground(Color.BLACK);
        panel.add(labelMode);
        
        this.labelStatus = new JLabel("Status: ");
        labelStatus.setPreferredSize(new Dimension(160, 20));
        labelStatus.setForeground(Color.BLACK);
        panel.add(labelStatus);
        
        this.labelArmed = new JLabel("Armed: ");
        labelArmed.setPreferredSize(new Dimension(160, 20));
        labelArmed.setForeground(Color.BLACK);
        panel.add(labelArmed);
        
        this.labelIsArmable = new JLabel("Is Armable: ");
        labelIsArmable.setPreferredSize(new Dimension(160, 20));
        labelIsArmable.setForeground(Color.BLACK);
        panel.add(labelIsArmable);
        
        this.labelEkfOk = new JLabel("Ekf-Ok: ");
        labelEkfOk.setPreferredSize(new Dimension(160, 20));
        labelEkfOk.setForeground(Color.BLACK);
        panel.add(labelEkfOk);
        
        this.labelNextWpt = new JLabel("Next Wpt: ");
        labelNextWpt.setPreferredSize(new Dimension(160, 20));
        labelNextWpt.setForeground(Color.BLACK);
        panel.add(labelNextWpt);
        
        this.labelCountWpt = new JLabel("Count Wpt: ");
        labelCountWpt.setPreferredSize(new Dimension(160, 20));
        labelCountWpt.setForeground(Color.BLACK);
        panel.add(labelCountWpt);
        
        this.labelDistToHome = new JLabel("Dist To Home: ");
        labelDistToHome.setPreferredSize(new Dimension(160, 20));
        labelDistToHome.setForeground(Color.BLACK);
        panel.add(labelDistToHome);
        
        this.labelDistToCurrWpt = new JLabel("Dist To Wpt: ");
        labelDistToCurrWpt.setPreferredSize(new Dimension(160, 20));
        labelDistToCurrWpt.setForeground(Color.BLACK);
        panel.add(labelDistToCurrWpt);
        
        this.labelTypeFailure = new JLabel("Type Fail: ");
        labelTypeFailure.setPreferredSize(new Dimension(160, 20));
        labelTypeFailure.setForeground(Color.BLACK);
        panel.add(labelTypeFailure);
        
        this.labelEstTimeToDoRTL = new JLabel("Estim. Time RTL: ");
        labelEstTimeToDoRTL.setPreferredSize(new Dimension(160, 20));
        labelEstTimeToDoRTL.setForeground(Color.BLACK);
        panel.add(labelEstTimeToDoRTL);
        
        this.labelEstConBatRTL = new JLabel("Estim. Bat RTL: ");
        labelEstConBatRTL.setPreferredSize(new Dimension(160, 20));
        labelEstConBatRTL.setForeground(Color.BLACK);
        panel.add(labelEstConBatRTL);
        
        this.labelSonarDistance = new JLabel("Sonar Distance: ");
        labelSonarDistance.setPreferredSize(new Dimension(160, 20));
        labelSonarDistance.setForeground(Color.BLACK);
        panel.add(labelSonarDistance);
        
        this.labelTemperature = new JLabel("Temper. Sensor: ");
        labelTemperature.setPreferredSize(new Dimension(170, 20));
        labelTemperature.setForeground(Color.BLACK);
        panel.add(labelTemperature);
        
    }
    
    public void updateInfo(Drone drone){
        labelDate.setText(String.format("Date: %s", drone.date));
        labelHour.setText(String.format("Hour: %s", drone.hour));
        labelTime.setText(String.format("Time: %.1fs", drone.time));
        labelLat.setText(String.format("Lat: %.8f", drone.gps.lat));
        labelLng.setText(String.format("Lng: %.8f", drone.gps.lng));
        labelAltRel.setText(String.format("Alt Rel: %.1fm", drone.barometer.alt_rel));
        labelAltAbs.setText(String.format("Alt Abs: %.1fm", drone.barometer.alt_abs));
        labelVoltageBat.setText(String.format("Voltage Bat: %.2fmV", drone.battery.voltage));
        labelCurrentBat.setText(String.format("Current Bat: %.2fmA*", drone.battery.current));
        labelLevelBat.setText(String.format("Level Bat: %.2f%s", drone.battery.level, "%"));
        labelPicth.setText(String.format("Picth: %.4f rad", drone.attitude.pitch));
        labelYaw.setText(String.format("Yaw: %.4f rad", drone.attitude.yaw));
        labelRoll.setText(String.format("Roll: %.4f rad", drone.attitude.roll));
        labelVx.setText(String.format("Vel x: %.2fm/s", drone.velocity.vx));
        labelVy.setText(String.format("Vel y: %.2fm/s", drone.velocity.vy));
        labelVz.setText(String.format("Vel z: %.2fm/s", drone.velocity.vz));
        labelHeading.setText(String.format("Heading: %.0f degree", drone.sensorUAV.heading));
        labelGroundspeed.setText(String.format("Groundspeed: %.2fm/s", drone.sensorUAV.groundspeed));
        labelAirspeed.setText(String.format("Airspeed: %.2fm/s", drone.sensorUAV.airspeed));
        labelGPSFixType.setText(String.format("GPS Fix Type: %d", drone.gpsinfo.fixType));
        labelGPSSatelities.setText(String.format("GPS Sat: %d", drone.gpsinfo.satellitesVisible));
        labelGPSEph.setText(String.format("GPS EPH: %d", drone.gpsinfo.eph));
        labelGPSEpv.setText(String.format("GPS EPV: %d", drone.gpsinfo.epv));
        labelMode.setText(String.format("Mode: %s", drone.statusUAV.mode));
        labelStatus.setText(String.format("Status: %s", drone.statusUAV.systemStatus));
        labelArmed.setText(String.format("Armed: %b", drone.statusUAV.armed));
        labelIsArmable.setText(String.format("Is Armable: %b", drone.statusUAV.isArmable));
        labelEkfOk.setText(String.format("Ekf-Ok: %b", drone.statusUAV.ekfOk));
        labelNextWpt.setText(String.format("Netx Wpt: %d", drone.nextWaypoint));
        labelCountWpt.setText(String.format("Count Wpt: %d", drone.countWaypoint));
        String distHome = drone.distanceToHome == -1 ? "NONE" : drone.distanceToHome + "m";
        labelDistToHome.setText(String.format("Dist To Home: %s", distHome));
        String distCurrWpt = drone.distanceToCurrentWaypoint == -1 ? "NONE" : drone.distanceToCurrentWaypoint + "m";
        labelDistToCurrWpt.setText(String.format("Dist To Wpt: %s", distCurrWpt));
        labelTypeFailure.setText(String.format("Type Fail: %s", drone.typeFailure));
        labelEstTimeToDoRTL.setText(String.format("Estim. Time RTL: %.1fs", drone.estimatedTimeToDoRTL));
        labelEstConBatRTL.setText(String.format("Estim. Bat RTL: %.1f%s", drone.estimatedConsumptionBatForRTL, "%"));
        String distSonar = drone.sonar.distance == -1 ? "NONE" : drone.sonar.distance + "m";
        labelSonarDistance.setText(String.format("Sonar Distance: %s", distSonar));
        String temp = drone.temperature.temperature == -1 ? "NONE" : drone.temperature.temperature+"C";
        labelTemperature.setText(String.format("Temper. Sensor: %s", temp));
    }
    
}
