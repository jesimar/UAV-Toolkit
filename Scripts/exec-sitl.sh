#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017
#Last Update: 24/08/2018
#Description: Script that runs Dronekit Software-In-The-Loop (SITL) to do simulations.
#Descrição: Script que executa o Dronekit Software-In-The-Loop (SITL) para fazer simulações.

if [ -z $1 ]
then
	#initial latitude of the drone, for example: -22.00593264981567, -22.005640, -22.00597264465543, -22.00593331794564
	#LAT=-22.00593331794564
	LAT=-22.00169469160027 #-22.00597264465543 #-22.00208833822264 #-22.00216140338089

	#initial longitude of the drone, for example: -47.89870966454083, -47.932474, -47.89868819614218, -47.898708372577346
	#LNG=-47.898708372577346
	LNG=-47.93321388888889 #-47.89868819614218 #-47.93304254164829 #-47.93305834493961
	
	echo "-------------------------"
	echo "LAT used:" $LAT
	
	echo "LNG used:" $LNG
	echo "-------------------------"
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-sitl.sh LAT LNG"
	echo "    Example: ./exec-sitl.sh -22.00597264 -47.89868819"
	exit 1
else
	LAT=$1
	LNG=$2
fi

#initial absolute altitude of the drone in meters, for example: 870
ALT=870

#angle (orientation) of the drone in degrees, for example: 0   (0 degrees is north)
ANGLE=90

#drone speed during the simulated mission, for example: 1.0 (1.0 is normal speed)
SPEED=1.0

dronekit-sitl copter-3.3 --home=$LAT,$LNG,$ALT,$ANGLE --speedup=$SPEED
