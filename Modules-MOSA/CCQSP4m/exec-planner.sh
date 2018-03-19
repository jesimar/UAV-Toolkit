#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 15/03/2018
#Last Update: 15/03/2018
#Description: Script that runs the path planner CCQSP4m.
#Descrição: Script que executa o planejador de missões CCQSP4m.

java -jar -Djava.library.path="/home/jesimar/Apps/cplex/cplex/bin/x86-64_sles10_4.1" ccqsp4m.jar run ./instance ./ > output-simulation.log
