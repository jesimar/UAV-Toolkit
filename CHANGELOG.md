# Changelog

## Version v5.0.0 (27/11/2018)

### Features:

* Updated frequency of some threads. 
* Added support for file-based behavior change (mission-spraying.txt).
* Updated scripts (exec-mavproxy-real-?.sh, clear-simulations.sh, exec-ifa.sh, exec-mosa.sh).
* Added feature where MOSA and IFA are terminated if flight mode is changed to RTL using the Radio Controller or GCS.
* Added plot scenic region in UAV-GCS.
* Added plot behavior in UAV-GCS.
* Added features MOSA Adaptive for mission (onboard and offboard). 
* Added file param of drones (iDroneAlpha, iDroneBeta) on ./UAV-Toolkit/Configs/
* Added file instances (kml) on ./UAV-Toolkit/Missions-Google-Earth/.
* Added file EXPERIMENTS.md and SPECIFICATIONS.md on github.
* Updated documentation on github.
* Added code to control sensors and actuators (LED, Buzzer, parachute, spraying).
* Updated code HGA4m (in UAV-MOSA and UAV-GCS) to decrease the number of waypoints.
* Added auto finished execution in UAV-IFA (change status disarmed, armed, disarmed).
* Added new resources hardware status called UAV-Status in UAV-GCS.
* Updated documentation in markdown.
* Added automatic route simplification feature based on route size used by CCQSP4m (only onboard) algorithm.
* Updated file overhead MOSA and IFA.
* Added Script to support BeagleBone Black.
* Removed code UAV-PosAnalyser and UAV-Monitoring
* Added support to artificial maps (instances Path-Planner and Path-Replanner). You need to set the start and goal points
* Added a script to compile all the C/C++ system codes.
* Updated code UAV-Manager (parameter names) in GUI.
* Changed the sequence of the buttons in UAV-Manager.

### Bug Fixes:

* Fixed a bug in FAIL_GPS with pixhawk with for fixType 4 
* Fixed a bug in the UAV-GCS that sometimes did not plot the route.
* Updated automatic route simplification.
* Updated HGA4m to support map with long distance (the HGA4m did not find solution for large maps).

## Version v4.0.0 (08/10/2018)

### Features:

* Added plot G-Path-Planner4m and G-Path-Replanner4s in UAV-GCS.
* Added plot behavior in UAV-GCS.
* Added plot of uncertainty in UAV-GCS (plot mission) in method CCQSP4m.
* Added three parameters in CCQSP4m (time_horizon, steps, std_position) to be read in file of properties.
* Updated code UAV-Mission-Creator to define objective waypoint with 2 meters of radius (before it was 1 meter).
* Added in path replanner G-Path-Replanner4s the data input and output specifications.
* Added in path planner G-Path-Planner4m the data input and output specifications.
* Added map in XML and JSON format in software UAV-Mission-Creator.
* Added special markers on Plots (Google Maps and Simple Map) in UAV-GCS for waypoints, buzzer, photos, video and spraying.
* Added color legend of the routes on the UAV-GCS.
* Added new scripts Beagle Bone and tests the softwares installed.
* Added generic path replanner, called G-Path-Replanner4s, in UAV-IFA.
* Added generic path planner, called G-Path-Planner4m, in UAV-MOSA.
* Renamed the project UAV-Generic to Lib-UAV.
* Updated readme file in Github with the documentation. 
* Added javadoc for the systems: UAV-Generic, UAV-MOSA, UAV-IFA, UAV-GCS, UAV-Mission-Creator.
* Added mensages of log in the system (info about time and overhead of functions).
* Added path planner A* in UAV-MOSA (onboard and offboard).
* Added UAV-GCS buttons resources that enable plots in mission of drone.
* Added feature maximun distance reached based in battery and speed average of drone.
* Added feature maximun time of flight based in battery of drone.
* Updated all code (refactored UAV-MOSA, UAV-IFA, UAV-GCS, UAV-Generic).
* Added in Modules-MOSA RouteStandard4m (circle, triangle, rectangle) (behavior) (MOSA-Adaptive).
* Renamed method of path replanning Fixed-Route4s to Pre-Planned4s.
* Updated file log-aircraft.csv with information estTimeToDoRTL and estConsumptionBatRTL.
* Added the feature of only doing RTL if the amount of battery is enough.
* Updated file config-global.properties with values of the old class Constants.
* Added feature in UAV-IFA that verify RTL always (estimated time for RTL, estimated consumption of battery for RTL).
* Added support UAV-GCS to paint color Route-Simplifier.
* Added code Route-Simplifier (methods replanning (IFA) not supported this resource).
* Updated code sonar and temperature sensor to support signal pin by file of properties.
* Added code to support the temperature sensor in raspberry pi.
* Added code to support the sonar in raspberry pi.
* Hardware of the raspberry pi incorporated in the system.
* Updated code UAV-Mission-Creator to support new format of video and photo-in-sequence.
* Updated file of properties to define the directory of the mission (IFA always copy the mission to the directory).
* Updated code camera, video and photo-in-sequence to support parameters by file of properties.
* Updated code buzzer and alarm to support signal pin by file of properties.
* Updated code to support picture, photo in sequence and video.
* Updated code UAV-IFA, UAV-MOSA, UAV-Generic and UAV-S2DK to support only one file input with properties.
* Renamed UAV-SOA-Interface to UAV-S2DK (UAV-Services to DroneKit).

### Bug Fixes:

* Fixed a error in CCQSP4m (strengthening constraints).
* Enable Google Maps feature in UAV-GCS (google maps need the internet to work) (defined by file of properties).
* Fixed some of the untreated data input combinations on the UAV-IFA.

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
