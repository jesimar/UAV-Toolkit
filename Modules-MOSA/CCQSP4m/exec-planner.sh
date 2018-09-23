#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 15/03/2018
#Last Update: 26/07/2018
#Description: Script that runs the path planner CCQSP4m on PC or Intel Edison.
#Descrição: Script que executa o planejador de missões CCQSP4m no PC ou na Intel Edison.

if [ "$1" = "local" ];
then
	java -jar -Djava.library.path="/home/jesimar/Apps/cplex/cplex/bin/x86-64_sles10_4.1" ccqsp4m.jar run ./instance ./ > output-simulation.log
elif [ "$1" = "edison" ]; 
then
	java -jar -Djava.library.path="/media/sdcard/installs/cplex/bin/x86_linux/" ccqsp4m.jar run ./instance ./ > output-simulation.log
fi
