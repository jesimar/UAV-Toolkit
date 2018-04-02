# Changelog

## Version v2.0.0 (02/04/2018)

* Added code UAV-GCS
* Added directory Instances
* Added method getBarometer(...) in UAV-SOA-Interface
* Updated method getGPS(...) in UAV-SOA-Interface
* Updated method getAllInfoSensors(...) in UAV-SOA-Interface to return next_waypoint, count_waypoint, dist_to_home, dist_to_current_waypoint
* Updated method getAllInfoSensors(...) in UAV-SOA-Interface number of decimal places returned optimized
* Updated codes in UAV-Generic (time, getBarometer, nextWaypoint, countWaypoint, distToHome, distToCurrentWaypoint, date actual, fully refactored)
* Updated codes in UAV-IFA (time, removed getDistanceToHome, fully refactored)
* Updated codes in UAV-MOSA (time, removed getDistanceToHome, fully refactored)
* Updated codes in UAV-Tests (time, removed getDistanceToHome)
* Updated codes in UAV-PosAnalyser (time - little modifications)
* Updated codes in UAV-Monitoring (time - little modifications)
* Updated codes in UAV-Mission-Creator
* Added UAV-GCS system codes
* Documentation update GitHub, codes, properties files and UAV-Toolkit
* Updated print format in file log-aircraft (UAV-Generic)
* Several classes were refactored
* Several properties file were refactored
* Include new commands controller
* Updating communication messages between IFA and MOSA.
* Added Modules-Global (Sonar, Buzzer, Parachute, Camera, LED, Spraying)
* Updated scripts
* Updated Javadoc (UAV-MOSA, UAV-IFA, UAV-Generic)
* Added field (date, hours, type-failure) file log-aircraftN.csv 

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
