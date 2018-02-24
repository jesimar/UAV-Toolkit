#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017
#Last Update: 23/02/2018
#Description: Script that runs Dronekit Software-In-The-Loop (SITL) to do simulations.
#Descrição: Script que executa o Dronekit Software-In-The-Loop (SITL) para fazer simulações.

LAT=-22.00593264981567  #latitude inicial do drone, por exemplo: -22.005640
LNG=-47.89870966454083  #longitude inicial do drone, por exemplo: -47.932474
ALT=870					#altitude absoluta inicial do drone em metros, por exemplo: 870
ANGLE=0					#ângulo (orientação) inicial do drone em graus, por exemplo: 0   (0 graus é norte)
SPEED=1.0 				#velocidade do drone durante a missão simulada, por exemplo: 1.0 (1.0 é velocidade normal)

dronekit-sitl copter-3.3 --home=$LAT,$LNG,$ALT,$ANGLE --speedup=$SPEED
