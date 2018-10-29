#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 08/10/2018
#Last Update: 08/10/2018
#Description: Script that runs the path planner RouteStandard4m on PC or CC.
#Descrição: Script que executa o planejador de missões RouteStandard4m no PC ou CC.

if [ "$1" = "local" ];
then
	./RouteStandard4m-PC > output-simulation.log
elif [ "$1" = "edison" ]; 
then
	./RouteStandard4m-Edison > output-simulation.log
elif [ "$1" = "rpi" ]; 
then
	./RouteStandard4m-RPi > output-simulation.log
elif [ "$1" = "bbb" ]; 
then
	./RouteStandard4m-BBB > output-simulation.log
elif [ "$1" = "odroid" ]; 
then
	./RouteStandard4m-Odroid > output-simulation.log
fi
