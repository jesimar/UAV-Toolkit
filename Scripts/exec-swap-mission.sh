#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 13/02/2018
#Last Update: 14/03/2018
#Description: Script that does the swap mission files to be executed automatically.
#Descrição: Script que troca os arquivos da missão a ser executada de forma automática.

#DIR_FILES=UAV-Mission-Creator/mission/kml/iros1
#DIR_FILES=UAV-Mission-Creator/mission/kml/iros2
#DIR_FILES=UAV-Mission-Creator/mission/kml/iros3
DIR_FILES=UAV-Mission-Creator/mission/kml/pos-iros1

echo "=============swap file mission============="

echo "copy file geoBase.txt to files"
cp ../$DIR_FILES/geoBase.txt ../Modules-Global/Files/. 2>/dev/null

echo "copy file map-full.sgl to IFA"
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/DE4s/map.sgl 2>/dev/null
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/GA4s/map.sgl 2>/dev/null
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/GH4s/map.sgl 2>/dev/null
cp ../$DIR_FILES/map-full.sgl ../Modules-IFA/MPGA4s/map.sgl 2>/dev/null

echo "copy file map-nfz.sgl to MOSA"
cp ../$DIR_FILES/map-nfz.sgl ../Modules-MOSA/HGA4m/. 2>/dev/null

echo "copy file waypointsMission.txt to MOSA"
cp ../$DIR_FILES/waypointsMission.txt ../Modules-MOSA/HGA4m/. 2>/dev/null

echo "copy file waypointsBuzzer.txt to files"
cp ../$DIR_FILES/waypointsBuzzer.txt ../Modules-Global/Files/. 2>/dev/null

echo "====================done==================="
