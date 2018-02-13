#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 13/02/2018
#Last Update: 13/02/2018

DIR_FILES=UAV-Mission-Creator/mission/kml/iros1
#DIR_FILES=UAV-Mission-Creator/mission/kml/iros2
#DIR_FILES=UAV-Mission-Creator/mission/kml/iros3

echo "copy file geoBase.txt to IFA and MOSA"
cp ../$DIR_FILES/geoBase.txt ../Modules-IFA/DE4s/.
cp ../$DIR_FILES/geoBase.txt ../Modules-IFA/GA4s/.
cp ../$DIR_FILES/geoBase.txt ../Modules-IFA/GH4s/.
cp ../$DIR_FILES/geoBase.txt ../Modules-IFA/MPGA4s/.
cp ../$DIR_FILES/geoBase.txt ../Modules-MOSA/HGA4m/.

echo "copy file map-full.sgl to IFA"
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/DE4s/map.sgl
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/GA4s/map.sgl
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/GH4s/map.sgl
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/MPGA4s/map.sgl

echo "copy file map-nfz.sgl to MOSA"
cp ../$DIR_FILES/map-nfz.sgl ../Modules-MOSA/HGA4m/.

echo "copy file waypointsMission.txt to MOSA"
cp ../$DIR_FILES/waypointsMission.txt ../Modules-MOSA/HGA4m/.

echo "copy file waypointsBuzzer.txt to MOSA"
cp ../$DIR_FILES/waypointsBuzzer.txt ../Modules-MOSA/Turn-On-The-Buzzer/.
