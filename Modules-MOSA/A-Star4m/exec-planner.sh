#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 18/09/2018
#Last Update: 08/10/2018
#Description: Script that runs the path planner A-Star4m on PC or CC.
#Descrição: Script que executa o planejador de missões A-Star4m no PC ou CC.

if [ "$1" = "local" ];
then
	./A-Start-PC > output-simulation.log
elif [ "$1" = "edison" ]; 
then
	./A-Start-Edison > output-simulation.log
elif [ "$1" = "rpi" ]; 
then
	./A-Start-RPi > output-simulation.log
elif [ "$1" = "bbb" ]; 
then
	./A-Start-BBB > output-simulation.log
elif [ "$1" = "odroid" ]; 
then
	./A-Start-Odroid > output-simulation.log
fi
