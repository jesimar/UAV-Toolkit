#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 15/03/2018
#Last Update: 15/03/2018
#Description: Script that runs the path planner HGA4m in the Intel Edison.
#Descrição: Script que executa o planejador de missões HGA4m na Intel Edison.

java -jar -Djava.library.path="/media/sdcard/installs/cplex/bin/x86_linux/" hga4m.jar > output-simulation.log
