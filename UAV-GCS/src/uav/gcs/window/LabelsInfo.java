/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.gcs.window;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uav.gcs.struct.Drone;

/**
 *
 * @author jesimar
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
    }
    
    public void updateInfo(Drone drone){
        labelDate.setText("Date: " + drone.date);
        labelHour.setText("Hour: " + drone.hour);
        labelTime.setText("Time: " + drone.time);
        labelLat.setText("Lat: " + String.format("%.8f", drone.gps.lat));
        labelLng.setText("Lng: " + String.format("%.8f", drone.gps.lng));
        labelAltRel.setText("Alt Rel: " + String.format("%.1f", drone.barometer.alt_rel));
        labelAltAbs.setText("Alt Abs: " + String.format("%.1f", drone.barometer.alt_abs));
        labelVoltageBat.setText("Voltage Bat: " + drone.battery.voltage);
        labelCurrentBat.setText("Current Bat: " + drone.battery.current);
        labelLevelBat.setText("Level Bat: " + drone.battery.level + "%");
        labelPicth.setText("Picth: " + String.format("%.4f", drone.attitude.pitch));
        labelYaw.setText("Yaw: " + String.format("%.4f", drone.attitude.yaw));
        labelRoll.setText("Roll: " + String.format("%.4f", drone.attitude.roll));
        labelVx.setText("Vx: " + String.format("%.2f", drone.velocity.vx));
        labelVy.setText("Vy: " + String.format("%.2f", drone.velocity.vy));
        labelVz.setText("Vz: " + String.format("%.2f", drone.velocity.vz));
        labelHeading.setText("Heading: " + drone.sensorUAV.heading);
        labelGroundspeed.setText("Groundspeed: " + drone.sensorUAV.groundspeed);
        labelAirspeed.setText("Airspeed: " + drone.sensorUAV.airspeed);
        labelGPSFixType.setText("GPS Fix Type: " + drone.gpsinfo.fixType);
        labelGPSSatelities.setText("GPS Sat: " + drone.gpsinfo.satellitesVisible);
        labelGPSEph.setText("GPS EPH: " + drone.gpsinfo.eph);
        labelGPSEpv.setText("GPS EPV: " + drone.gpsinfo.epv);
        labelMode.setText("Mode: " + drone.statusUAV.mode);
        labelStatus.setText("Status: " + drone.statusUAV.systemStatus);
        labelArmed.setText("Armed: " + drone.statusUAV.armed);
        labelIsArmable.setText("Is Armable: " + drone.statusUAV.isArmable);
        labelEkfOk.setText("Ekf-Ok: " + drone.statusUAV.ekfOk);
        labelNextWpt.setText("Netx Wpt: " + drone.nextWaypoint);
        labelCountWpt.setText("Count Wpt: " + drone.countWaypoint);
        labelDistToHome.setText("Dist To Home: " + drone.distanceToHome);
        labelDistToCurrWpt.setText("Dist To Wpt: " + drone.distanceToCurrentWaypoint);
        labelTypeFailure.setText("Type Fail: " + drone.typeFailure);
    }
    
}
