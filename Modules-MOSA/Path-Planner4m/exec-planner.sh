#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 29/03/2018
#Last Update: 29/05/2018
#Description: Script that runs the path planner Path-Planner4m on PC.
#Descrição: Script que executa o planejador de missões Path-Planner4m no PC.

#precisa trocar o path abaixo, onde está localizado a instalação do CPLEX.
#pode-se chamar o seu path planner com qual nome você quiser, basta alterar o nome abaixo.
java -jar -Djava.library.path="/home/jesimar/Apps/cplex/cplex/bin/x86-64_sles10_4.1" nome_path_planner4m.jar > output-simulation.log
