#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 13/02/2018
#Last Update: 30/09/2018
#Description: Script that does the swap mission files to be executed automatically.
#Descrição: Script que troca os arquivos da missão a ser executada de forma automática.

if [ -z $1 ]
then
	#DIR_FILES=../Missions/iros1
	#DIR_FILES=../Missions/iros2
	#DIR_FILES=../Missions/iros3
	#DIR_FILES=../Missions/pos-iros1
	#DIR_FILES=../Missions/icas
	#DIR_FILES=../Missions/campus2
	DIR_FILES=../Missions/campus2ccqsp
	echo "DIR_FILES used:" $DIR_FILES
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-swap-mission.sh DIR_FILES"
	echo "    Example: ./exec-swap-mission.sh ../Mission/campus2"
	exit 1
else
	DIR_FILES=$1
fi

echo "=============swap file mission============="

echo "copy file geoBase.txt, map-full.sgl, map-nfz.sgl and featureMission.txt to directory ../Modules-Global/Files/"
cp $DIR_FILES/geoBase.txt ../Modules-Global/Files/. 2>/dev/null
cp $DIR_FILES/map-full.sgl ../Modules-Global/Files/map-full.sgl 2>/dev/null
cp $DIR_FILES/map-nfz.sgl ../Modules-Global/Files/map-nfz.sgl 2>/dev/null
cp $DIR_FILES/featureMission.txt ../Modules-Global/Files/. 2>/dev/null

echo "copy file map-full.sgl to directory ../Modules-IFA/Method?/"
cp $DIR_FILES/map-full.sgl ../Modules-IFA/DE4s/map.sgl 2>/dev/null
cp $DIR_FILES/map-full.sgl ../Modules-IFA/GA4s/map.sgl 2>/dev/null
cp $DIR_FILES/map-full.sgl ../Modules-IFA/GH4s/map.sgl 2>/dev/null
cp $DIR_FILES/map-full.sgl ../Modules-IFA/MPGA4s/map.sgl 2>/dev/null
cp $DIR_FILES/map-full.sgl ../Modules-IFA/MS4s/map.sgl 2>/dev/null
cp $DIR_FILES/map-full.sgl ../Modules-IFA/MILP4s/map.sgl 2>/dev/null

echo "copy file map-nfz.sgl and waypointsMission.txt to directory ../Modules-MOSA/HGA4m/"
cp $DIR_FILES/map-nfz.sgl ../Modules-MOSA/HGA4m/. 2>/dev/null
cp $DIR_FILES/waypointsMission.txt ../Modules-MOSA/HGA4m/. 2>/dev/null

echo "copy file mission_ccqsp.sgl to directory ../Modules-MOSA/CCQSP4m/"
cp $DIR_FILES/mission_ccqsp.sgl ../Modules-MOSA/CCQSP4m/. 2>/dev/null

echo "copy file map-nfz-astar.sgl and waypointsMission.txt to directory ../Modules-MOSA/A-Star4m/"
cp $DIR_FILES/map-nfz-astar.sgl ../Modules-MOSA/A-Star4m/. 2>/dev/null
cp $DIR_FILES/waypointsMission.txt ../Modules-MOSA/A-Star4m/. 2>/dev/null

echo "copy file map-nfz.sgl and waypointsMission.txt to directory ../Modules-MOSA/Path-Planner4m/"
cp $DIR_FILES/map-nfz.sgl ../Modules-MOSA/Path-Planner4m/. 2>/dev/null
cp $DIR_FILES/waypointsMission.txt ../Modules-MOSA/Path-Planner4m/. 2>/dev/null

echo "====================done==================="
