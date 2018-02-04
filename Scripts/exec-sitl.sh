#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017

#-22.00593264981567;-47.89870966454083
LAT=-22.00593264981567 #-22.005925 #-22.00587424417797	#latitude  inicial do drone: -22.005640
LNG=-47.89870966454083 #-47.898643 #-47.89874454308930	#longitude inicial do drone: -47.932474
ALT=870					#altitude absoluta inicial do drone
ANGLE=0					#angulo (orientacao) inicial do drone
SPEED=1.0 				#velocidade do drone durante a missao simulada: 1.0 normal

dronekit-sitl copter-3.3 --home=$LAT,$LNG,$ALT,$ANGLE --speedup=$SPEED
