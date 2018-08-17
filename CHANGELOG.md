# Changelog

## Version v3.0.0 (16/08/2018)

### Features:

* Removed code UAV-Routes-Standard (copied to UAV-Tools).
* Updated code UAV-IFA to support sonar altitude and temperature sensor.
* Updated diretory Modules-Global with TemperatureSensor.
* Updated code UAV-GCS to plot Google Maps with route the UAV in real time.
* Updated code UAV-GCS to plot simple mission with route the UAV in real time.
* Updated code UAV-Generic (UAV-IFA and UAV-MOSA) to support buzzer-pc, alarm-pc, picture-pc and video-pc.
* Updated file config-global.properties and removed file config-gcs.properties.
* Updated code UAV-GCS to show options whether resource exists in config-global.properties.
* Updated code to support CCQSP4m executed in GCS (executed OFFBOARD).
* Updated code to support MS4s, GH4s, GA4s, DE4s executed in GCS (executed OFFBOARD).
* Directory Instances with new formats JSON and XML for the maps.
* Updated UAV-Mission-Manager to support missions with CCQSP4m path planner.
* Added code UAV-Manager.
* Added support to the MS4m path replanner.
* Added support to the CCQSP4m path planner.
* Updated the documentation.
* Removed directory Missions-Ardupilot-SITL.
* Removed Code UAV-Ensemble-GA-GA_GA-GH.
* Removed Code UAV-Exec-PathReplanner-Massive.
* Removed Code UAV-Fixed-Route4s.
* Added resource in UAV-GCS to plot the mission (map and route).
* Added resource to controller the drone using keyboard (UAV-GCS).
* Added resource to controller the drone using voice (UAV-GCS).
* Added resource to exec path replanner in UAV-GCS (offboard).
* Added resource to exec path planner in UAV-GCS (offboard).
* Added resource to take a picture in UAV-MOSA.
* Added new features to Raspberry Pi in UAV-IFA e UAV-MOSA.
* Added new scripts about Raspberry Pi.
* Added new functions in UAV-SOA-Interface (getNextWaypoint, getCountWaypoint, getDistanceToWptCurrent).
* Added new functions in UAV-Generic (getNextWaypoint, getCountWaypoint, getDistanceToWptCurrent).
* Added resource in UAV-Route-Standard (routes in geographical coordenates).
* Updated UAV-Generic to support the Raspberry Pi.
* Removed Keyboard-Commands, Voice-Commands, UAV-Keyboard-Commands, UAV-Voice-Commands, UAV-Insert-Failure.
* Updated the documentation (javadoc).
* Separation between projects UAV-Mission-Creator and UAV-Route3DToGeo.
* Updated UAV-Mission-Creator (created file featuremission.txt).
* Updated code update data battery generalization.
* Updated code get home location.
* Updated scripts to support args using terminal.

### Bug Fixes:

* Method getBarometer in UAV-Generic

## Version v2.0.0 (17/05/2018)

### Features:

* Updated method getGPS(...) in UAV-SOA-Interface
* Updated method getAllInfoSensors(...) in UAV-SOA-Interface to return next_waypoint, count_waypoint, dist_to_home, dist_to_current_waypoint
* Updated method getAllInfoSensors(...) in UAV-SOA-Interface number of decimal places returned optimized
* Updated codes in UAV-Generic (time, getBarometer, nextWaypoint, countWaypoint, distToHome, distToCurrentWaypoint, date actual, fully refactored)
* Updated codes in UAV-IFA (time, removed getDistanceToHome, fully refactored, added code Fixed-Route4s-Static)
* Updated codes in UAV-MOSA (time, removed getDistanceToHome, fully refactored)
* Updated codes in UAV-Tests (time, removed getDistanceToHome)
* Updated codes in UAV-PosAnalyser (time - little modifications)
* Updated codes in UAV-Monitoring (time - little modifications)
* Updated codes in UAV-Mission-Creator
* Updated several scripts
* Updated Javadoc (UAV-MOSA, UAV-IFA, UAV-Generic)
* Updated print format in file log-aircraft (UAV-Generic)
* Updated documentation GitHub, codes, properties files and UAV-Toolkit
* Updated communication messages between IFA and MOSA
* Updated several classes (refactored)
* Updated several properties file (refactored)
* Added new commands controller
* Added code UAV-GCS (with communication with Oracle Drone using DataBase)
* Added directory Instances
* Added method getBarometer(...) in UAV-SOA-Interface
* Added Modules-Global (Sonar, Buzzer, Parachute, Camera, LED, Spraying)
* Added data field (date, hours, type-failure) in file log-aircraftN.csv 
* Added GA-GH-4s module in IFA System
* Added GA-GA-4s module in IFA System
* Added Fixed-Route4s module in IFA System
* Added UAV-GCS system codes
* Added UAV-Exec-MPGA4s-Massive code
* Added UAV-Ensemble-GA-GA_GA-GH code
* Added UAV-Fixed-Route4s code
* Added UAV-IFA mode offline route processing (falta terminar)
* Added UAV-MOSA mode offline route processing (falta terminar)

### Bug Fixes:

* Bug fixes in GH4s, DE4s, GA4s and MPGA4s modules (heading values were wrong)
* Bug fixes in GH4s, DE4s modules (update files were wrong)

## Version v1.1.0 (07/03/2018)

### Features:

* Github documentation update
* Inclusion of pre-planned files in Ardupilot-SITL (directory Missions-Ardupilot-SITL/)
* Inclusion of directory Print-Screen (directory Print-Screen/)
* Inclusion of directory Figures (directory Figures/)
* Renamed UAV-Services-Dronekit to UAV-SOA-Interface
* Renamed Mission-Creator-4UAV to UAV-Mission-Creator
* Renamed UAV-MOSA-C to UAV-Toolkit-C
* Calibration of the Modules-IFA
* Calibration of the Modules-MOSA
* Code of system UAV-Insertion-Failure
* Code of system UAV-Routes-Standard
* Update libraries to latest version
* Added module CCQSP4m
* Added module OpenParachute4s in IFA
* Added module Alarm4s in IFA
* Added module Buzzer4m in MOSA
* Added script list-ports-in-use.sh
* Added script list-ips-in-use.sh
* Added script show-my-ip.sh
* Added script show-my-linux.sh
* Added script exec-mavproxy-edison-sitl.sh
* Added script exec-insert-failure.sh
* Added script dir.sh
* Added script clear-simulations.sh
* Added script clear-telemetry-qgc.sh
* Added script exec-swap-mission.sh
* Added script exec-copy-files-results.sh
* Added script exec-turn-off-buzzer.sh

### Bug Fixes:

* UAV-IFA
* UAV-MOSA
* UAV-Generic
* UAV-Services-Dronekit

## Version v1.0.0 (08/02/2018)

### Features:

* Code of system UAV-IFA
* Code of system UAV-MOSA
* Code of system UAV-Services-Dronekit
* Code of system UAV-Generic
* Code of system UAV-Tests
* Code of system UAV-Monitoring
* Code of system UAV-PosAnalyser
* Code of system UAV-Keyboard-Commands
* Code of system UAV-Voice-Commands
* Code of system UAV-MOSA-C
* Code of system Mission-Creator-4UAV
* Modules of system MOSA (directory Modules-MOSA/)
* Modules of system IFA (directory Modules-IFA/)
* Inclusion of pre-planned missions in Google Earth (directory Missions-Google-Earth/)
* Inclusion of several scripts (directory Scripts/)
* Inclusion of documentation (directory Docs/)
* Inclusion of libraries (directory Libs/)
* Adding documentation to Github (files README.md)
