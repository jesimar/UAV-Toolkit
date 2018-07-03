#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017
#Last Update: 23/02/2018
#Description: Script that runs Dronekit Software-In-The-Loop (SITL) to do simulations.
#Descrição: Script que executa o Dronekit Software-In-The-Loop (SITL) para fazer simulações.

#initial latitude of the drone, for example: -22.00593264981567, -22.005640, -22.00597264465543, -22.00593331794564
#LAT=-22.00593331794564
LAT=-22.00587424417797 #-22.00216140338089

#initial longitude of the drone, for example: -47.89870966454083, -47.932474, -47.89868819614218, -47.898708372577346
#LNG=-47.898708372577346
LNG=-47.89874454308930 #-47.93305834493961

#initial absolute altitude of the drone in meters, for example: 870
ALT=870

#angle (orientation) of the drone in degrees, for example: 0   (0 degrees is north)
ANGLE=90

#drone speed during the simulated mission, for example: 1.0 (1.0 is normal speed)
SPEED=1.0

dronekit-sitl copter-3.3 --home=$LAT,$LNG,$ALT,$ANGLE --speedup=$SPEED
