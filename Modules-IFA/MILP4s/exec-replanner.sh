#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 11/07/2018
#Last Update: 11/07/2018
#Description: Script that runs the path replanner MILP4s.

java -jar -Djava.library.path="/home/jesimar/Apps/cplex/cplex/bin/x86-64_sles10_4.1"  milp4s.jar > output-simulation.log
